package GUI.PurchaseOrderManagement;

import GUI.Login.LoginGUI;
import GUI.MainMenu.MainMenuGUI;
import GUI.MainWindow.MainWindowGUI;
import src.Item.Item;
import src.Item.ItemsDatabase;
import src.PurchaseOrder.PurchaseOrder;
import src.PurchaseOrder.PurchaseOrderDatabase;
import src.Vendor.DateValidator;
import src.Vendor.VendorDatabase;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.time.format.DateTimeFormatter;
import java.util.Vector;
import java.util.stream.IntStream;

/**
 *  This class implements the Vendor profile for the owner
 *  and purchaser users to create, update, and delete Vendors
 *
 * @author Jordan Bronstetter
 * @date 04/06/2021
 *
 */
public class PurchaseOrderGUI implements ActionListener {
    private JPanel rootPanel;
    private JList lstPurchaseOrders;
    private JTextField txtSearchBar;
    private JButton btnSelectVendor;
    private JLabel lblSearch;
    private JButton btnMainMenu;
    private JButton btnLogOut;
    private JButton btnCreatePO;
    private JButton btnCancelPO;
    private JButton btnStartPO;
    private JButton btnViewPO;
    private JScrollPane scpDisplayList;
    private JPanel pnlFormatDisplay;
    private JLabel lblListInfo;
    private JPanel pnlList;

    PurchaseOrderModel purchaseOrderModel = new PurchaseOrderModel();
    Vector<PurchaseOrder> vendorPOs = null;
    Vector<Item> vendorItems = null;

    private PurchaseOrder purchaseOrder = new PurchaseOrder();
    private VendorDatabase vendorDatabase = VendorDatabase.getInstance();
    private ItemsDatabase itemsDatabase = ItemsDatabase.getInstance();
    private PurchaseOrderDatabase purchaseOrderDatabase = PurchaseOrderDatabase.getInstance();
    private MainWindowGUI mainWindowGUI;

    private boolean toggle = true;
    private boolean vendorSelected = false;

    private String searchBarPrompt = "Search by Vendor Name";
    private String searchLabel = "Search for Vendor:";
    private String selectedLabel = "Selected Vendor:";
    private String viewButton = "View Purchase Orders";
    private String hideButton = "Hide Purchase Orders";
    private int vendorID;
    private String vendorName;

    public PurchaseOrderGUI() {
        mainWindowGUI = MainWindowGUI.getInstance();
        mainWindowGUI.setTitle("Purchase Order Management");

       // Vector v = new Vector();
        //v.add("<html>egg<br>12/12/2222<br>8.0<br></html>");

        System.out.println("New");
       // lstPurchaseOrders.setListData(v);
        //lstPurchaseOrders.setListData(Ob"<html>egg<b> b>8.0<b>14<b></html>");

        setUpGUI();
        addListeners();
    }

    private void setUpGUI() {
        txtSearchBar.setText(searchBarPrompt);

        lstPurchaseOrders.setSelectionModel(new DefaultListSelectionModel() {
            @Override
            public void setSelectionMode(int selectionMode) {
                super.setSelectionMode(SINGLE_SELECTION);
            }

            public void setSelectionInterval(int index0, int index01) {}
            public void addSelectionInterval(int i, int j) {}

            public void setLeadSelectionIndex(int i) {}
            public void setAnchorSelectionIndex(int i) {}
        });

        lstPurchaseOrders.setCellRenderer(new DefaultListCellRenderer() {

            @Override
            public Component getListCellRendererComponent(JList list, Object value, int index,
                                                          boolean isSelected, boolean cellHasFocus) {

                Component c = super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);

                if (index % 2 != 0) {
                    //setBackground(getBackground().darker());
                    setBackground(Color.LIGHT_GRAY);
                }

                return c;
            }

        });
    }

    public JPanel getPanel()
    {
        return rootPanel;
    }

    /**
     * Invoked to add a ActionListener to JButtons and JTextFields
     */
    private void addListeners() {
        btnStartPO.addActionListener(this);
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
            if (vendorSelected) {
                toggleSearchInfo();
            } else {
                vendorName = txtSearchBar.getText();
                vendorID = PurchaseOrderLogic.selectVendor(vendorName);
                if (vendorID > -1) {
                    toggleSearchInfo();
                }
            }
        } else if (userAction == btnStartPO) {
            if (vendorSelected) {
                filterItems();
                if (!vendorItems.isEmpty()) {
                    mainWindowGUI.setJPanel(new CreatePurchaseOrderGUI(rootPanel, vendorItems, vendorName).getPanel());
                } else {
                    DialogDisplay.displayError(vendorName + " does not have any items to choose from");
                }
            } else {
                DialogDisplay.displayError("Please select a Vendor first");
            }
        } else if (userAction == btnViewPO) {
            if (vendorSelected) {
                if (btnViewPO.getText().equals(viewButton)) {
                    if (purchaseOrderDatabase.containsVendor(vendorID)) {
                        setUpVendorPOs();
                        btnViewPO.setText(hideButton);
                    } else {
                        DialogDisplay.displayError(vendorName + " does not have any Purchase Orders");
                    }
                } else {
                    btnViewPO.setText(viewButton);
                    lstPurchaseOrders.setListData(new Vector());
                }
            } else {
                DialogDisplay.displayError("Please select a Vendor first");
            }
        } else if (userAction == btnMainMenu) {
            mainWindowGUI.setJPanel(new MainMenuGUI().getPanel());
        } else if (userAction == btnLogOut) {
            mainWindowGUI.setJPanel(new LoginGUI().getPanel());
        }
    }

    private void toggleSearchInfo() {
        if (lblSearch.getText().equals(searchLabel)) {
            vendorSelected = true;
            lblSearch.setText(selectedLabel);
            txtSearchBar.setEditable(false);
            btnSelectVendor.setText("Select New Vendor");
        } else {
            vendorSelected = false;
            lblSearch.setText(searchLabel);
            txtSearchBar.setText(searchBarPrompt);
            txtSearchBar.setEditable(true);
            btnSelectVendor.setText("Select Vendor");
        }
    }

    private void filterItems() {
        DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("MM/dd/yyyy");
        DateValidator validator = new DateValidator(dateFormat);

        vendorItems = itemsDatabase.getItemsForVendor(vendorID);
        int i = 0;
        while (i < vendorItems.size()) if (validator.isPastDate(vendorItems.get(i).getExpirationDate())) {
            vendorItems.remove(i);
        } else ++i;
    }

    private void setUpVendorPOs() {
        lblListInfo.setText("Purchase Orders for " + vendorName);
        vendorPOs = purchaseOrderDatabase.getPurchaseOrders(vendorID);
        lstPurchaseOrders.setListData(vendorPOs);
    }
}