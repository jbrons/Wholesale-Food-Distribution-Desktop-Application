package GUI.PurchaseOrderManagement;

import GUI.ItemManagement.ItemDisplayGUI;
import GUI.Login.LoginGUI;
import GUI.MainMenu.MainMenuGUI;
import GUI.MainWindow.MainWindowGUI;
import GUI.VendorManagement.SearchModel;
import src.Item.Item;
import src.Item.ItemsDatabase;
import src.Vendor.VendorDatabase;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.nio.file.attribute.AclEntry;
import java.util.Vector;
import java.util.stream.Stream;

public class PurchaseOrderGUI implements ActionListener {
    private JPanel rootPanel;
    private JList lstItems;
    private JTextField txtSearchBar;
    private JButton btnSelectVendor;
    private JTextField txtInstructions;
    private JLabel lblSearch;
    private JButton btnMainMenu;
    private JButton btnLogOut;
    private JButton btnCreatePO;
    private JButton btnCancelPO;
    private JButton btnViewPO;
    private JScrollPane scpDisplayList;
    private JPanel pnlList;

    ItemModel itemModel = new ItemModel();

    private VendorDatabase vendorDatabase = VendorDatabase.getInstance();
    private ItemsDatabase itemsDatabase = ItemsDatabase.getInstance();
    private MainWindowGUI mainWindowGUI;

    private String searchBarPrompt = "Search by Vendor Name";
    private int vendorID;
    private String vendorName;
    private boolean vendorSelected = false;
    private boolean mouseInList = true;

    public PurchaseOrderGUI() {
        mainWindowGUI = MainWindowGUI.getInstance();
        mainWindowGUI.setTitle("Purchase Order Management");

        setUpGUI();
        addListeners();
    }

    private void setUpGUI() {
        txtSearchBar.setText(searchBarPrompt);
        lstItems.setModel(itemModel.getDisplayListModel());

        //lstItems.setListData(itemsDatabase.getAllItemDetails());

    }

    public JPanel getPanel()
    {
        return rootPanel;
    }

    /**
     * Invoked to add a ActionListener to JButtons and JTextFields
     */
    private void addListeners() {
        //txtSearchBar.addFocusListener(this);
        //lstItems.addMouseListener(this);
        btnCreatePO.addActionListener(this);
        btnCancelPO.addActionListener(this);
        btnViewPO.addActionListener(this);
        btnMainMenu.addActionListener(this);
        btnLogOut.addActionListener(this);
        btnSelectVendor.addActionListener(this);

        /**
         * Invoked when a key has been typed.
         * See the class description for {@link KeyEvent} for a definition of
         * a key typed event.
         *
         * @param e the event to be processed
         */

        txtSearchBar.addKeyListener(new KeyAdapter() {
            /**
             * Invoked when a key has been typed.
             * This event occurs when a key press is followed by a key release.
             *
             * @param e
             */
            @Override
            public void keyTyped(KeyEvent e) {
                int maxChars = 20;
                int length = txtSearchBar.getText().length();
                if (length >= maxChars) {
                    e.consume();
                }
            }
        });

        txtSearchBar.addFocusListener(new FocusAdapter() {
            /**
             * Invoked when a component gains the keyboard focus.
             *
             * @param e
             */
            @Override
            public void focusGained(FocusEvent e) {
                if (txtSearchBar.getText().equals(searchBarPrompt)) {
                    txtSearchBar.setText("");
                }
            }

            /**
             * Invoked when a component loses the keyboard focus.
             *
             * @param e
             */
            @Override
            public void focusLost(FocusEvent e) {
                Object userAction = e.getSource();

                if (userAction == txtSearchBar) {
                    if (txtSearchBar.getText().equals("")) {
                        txtSearchBar.setText(searchBarPrompt);
                    }
                }
            }
        });

        lstItems.addMouseListener(new MouseAdapter() {
            /**
             * {@inheritDoc}
             *
             * @param e
             */
            @Override
            public void mouseClicked(MouseEvent e) {
                int index = lstItems.getSelectedIndex();
                mainWindowGUI.setJPanel(new ItemPOInfo().getPanel());
            }

            /**
             * {@inheritDoc}
             *
             * @param e
             */
            @Override
            public void mouseExited(MouseEvent e) {
                lstItems.clearSelection();
            }
        });

        lstItems.addMouseMotionListener(new MouseMotionAdapter() {
            /**
             * Invoked when the mouse button has been moved on a component
             * (with no buttons no down).
             *
             * @param e
             */
            @Override
            public void mouseMoved(MouseEvent e) {
                super.mouseMoved(e);
                if (lstItems.isEnabled()) {
                    int index = lstItems.locationToIndex(e.getPoint());
                    Rectangle cellBounds = lstItems.getCellBounds(index, index);
                    if (cellBounds.contains(e.getPoint())) {
                        lstItems.setSelectedIndex(index);
                    } else {
                        lstItems.clearSelection();
                    }
                }
            }
        });
    }

    /**
     * Invoked when an action occurs.
     *
     * @param e the event to be processed
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        Object userAction = e.getSource();

        if (userAction == btnSelectVendor) {
            vendorName = txtSearchBar.getText();
            if (vendorName.equals("") || vendorName.equals(searchBarPrompt)) {
                return;
            }

            if (!vendorSelected) {
                if (vendorDatabase.isEmpty()) {
                    DialogDisplay.displayError("No Vendors are available");
                }

                int index = vendorDatabase.searchVendorDatabase(vendorName);
                if (index > -1) {
                    vendorID = vendorDatabase.getId(index);
                    txtSearchBar.setEditable(false);
                    btnSelectVendor.setText("Select New Vendor");
                    vendorSelected = true;
                } else {
                    DialogDisplay.displayError("No Vendors by the name of " + vendorName);
                }
            } else {
                int choice = cancelPurchaseOrder();
                if (choice == JOptionPane.YES_OPTION){
                    txtSearchBar.setText(searchBarPrompt);
                    txtSearchBar.setEditable(true);
                    vendorSelected = false;
                }
            }

        } else if (userAction == btnCreatePO) {
            if (vendorSelected) {
                Vector<Item> vendorItems = itemsDatabase.getItemsForVendor(vendorID);
                if (vendorItems.size() == 0) {
                    DialogDisplay.displayError(vendorName + " does not have any items to choose from");
                } else {
                    itemModel.updateModel(vendorItems);
                    setBtnCancelPO();
                }
            } else {
                DialogDisplay.displayError("Must select a Vendor first");
            }

        } else if (userAction == btnCancelPO) {
            int choice = cancelPurchaseOrder();
            itemModel.clearModel();
            if (choice == JOptionPane.YES_OPTION){
                txtSearchBar.setEditable(true);
                lstItems.setEnabled(false);
                vendorSelected = false;
            }
        } else if (userAction == btnViewPO) {

        } else if (userAction == btnMainMenu) {
            mainWindowGUI.setJPanel(new MainMenuGUI().getPanel());
        } else if (userAction == btnLogOut) {
            mainWindowGUI.setJPanel(new LoginGUI().getPanel());
        }
    }

    private void setBtnCancelPO() {
        btnCancelPO.setVisible(true);;
        btnCancelPO.setEnabled(true);
    }

    private int cancelPurchaseOrder() {
        return DialogDisplay.displayQuestion("Discard Purchase Order?",
                "Cancel Purchase Order", new Object[] {"Confirm", "Cancel"});
    }
}
