package GUI.PurchaseOrderManagement;

import GUI.ItemManagement.ItemDisplayGUI;
import GUI.Login.LoginGUI;
import GUI.MainMenu.MainMenuGUI;
import GUI.MainWindow.MainWindowGUI;
import src.Item.ItemsDatabase;
import src.Vendor.VendorDatabase;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Vector;

public class PurchaseOrderGUI implements ActionListener, MouseListener, FocusListener {
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
    private JPanel pnltems;
    private JPanel pnlList;

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
        txtSearchBar.addFocusListener(this);
        lstItems.addMouseListener(this);
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

        lstItems.addMouseMotionListener(new MouseAdapter() {
            /**
             * {@inheritDoc}
             *
             * @param e
             * @since 1.6
             */
            @Override
            public void mouseMoved(MouseEvent e) {
                System.out.println("In list");
                int index = lstItems.locationToIndex(e.getPoint());
                Rectangle cellBounds = lstItems.getCellBounds(index, index);
                if (cellBounds.contains(e.getPoint())) {
                    lstItems.setSelectedIndex(index);
                } else {
                    lstItems.clearSelection();
                }
            }
        });

        pnltems.addMouseMotionListener(new MouseAdapter() {
            /**
             * {@inheritDoc}
             *
             * @param e
             * @since 1.6
             */
            @Override
            public void mouseMoved(MouseEvent e) {
                lstItems.clearSelection();
            }
        });
    }

    /**
     * Invoked when the mouse button has been clicked (pressed
     * and released) on a component.
     *
     * @param e the event to be processed
     */
    @Override
    public void mouseClicked(MouseEvent e) {}

    /**
     * Invoked when a mouse button has been pressed on a component.
     *
     * @param e the event to be processed
     */
    @Override
    public void mousePressed(MouseEvent e) {}

    /**
     * Invoked when a mouse button has been released on a component.
     *
     * @param e the event to be processed
     */
    @Override
    public void mouseReleased(MouseEvent e) {}

    /**
     * Invoked when the mouse enters a component.
     *
     * @param e the event to be processed
     */
    @Override
    public void mouseEntered(MouseEvent e) {
    }

    /**
     * Invoked when the mouse exits a component.
     *
     * @param e the event to be processed
     */
    @Override
    public void mouseExited(MouseEvent e) {}

    /**
     * Invoked when a component gains the keyboard focus.
     *
     * @param e the event to be processed
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
     * @param e the event to be processed
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
                int id = PurchaseOrderLogic.searchForVendor(vendorName);

                if (id > 0) {
                    txtSearchBar.setEditable(false);
                    btnSelectVendor.setText("Select New Vendor");
                    vendorSelected = true;
                } else if (id < 0 ){
                    DialogDisplay.displayError("No Vendors by the name of " + vendorName);
                } else {
                    DialogDisplay.displayError("No Vendors are available");
                }
            } else {
                int choice = DialogDisplay.displayQuestion("Discard Purchase Order?",
                        "Cancel Purchase Order", new Object[] {"Confirm", "Cancel"});
                if (choice == JOptionPane.YES_OPTION){
                    txtSearchBar.setText(searchBarPrompt);
                    txtSearchBar.setEditable(true);
                    vendorSelected = false;
                }
            }

        } else if (userAction == btnCreatePO) {
            if (vendorSelected) {
                Vector<String> vendorItems = itemsDatabase.getItemsForVendor(vendorID);
                if (vendorItems.size() == 0) {
                    DialogDisplay.displayError(vendorName + " does not have any items to choose from");
                } else {
                    lstItems.setListData(vendorItems);
                }
                lstItems.setListData(itemsDatabase.getAllItemDetails());
            } else {
                DialogDisplay.displayError("Must select a Vendor first");
            }



        } else if (userAction == btnCancelPO) {

        } else if (userAction == btnViewPO) {

        } else if (userAction == btnMainMenu) {
            mainWindowGUI.setJPanel(new MainMenuGUI().getPanel());
        } else if (userAction == btnLogOut) {
            mainWindowGUI.setJPanel(new LoginGUI().getPanel());
        }
    }
}
