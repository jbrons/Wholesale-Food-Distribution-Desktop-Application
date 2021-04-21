package GUI.PurchaseOrderManagement;

import GUI.Login.LoginGUI;
import GUI.MainMenu.MainMenuGUI;
import GUI.MainWindow.MainWindowGUI;
import src.Item.Item;
import src.PurchaseOrder.PurchaseOrderDatabase;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Vector;

/**
 * PurchaseOrderHubGUI implements the main hub for purchaser users to create and view Purchase Orders
 *
 * @author Jordan Bronstetter
 * @date 04/07/2021
 *
 */
public class PurchaseOrderHubGUI implements ActionListener {
    private JPanel rootPanel;
    private JList lstPurchaseOrders;
    private JTextField txtSearchBar;
    private JButton btnSelectVendor;
    private JLabel lblSearch;
    private JButton btnMainMenu;
    private JButton btnLogOut;
    private JButton btnStartPO;
    private JButton btnViewPO;
    private JScrollPane scpDisplayList;
    private JPanel pnlFormatDisplay;
    private JLabel lblListInfo;
    private JPanel pnlList;

    private PurchaseOrderModel purchaseOrderModel = new PurchaseOrderModel();
    private static Vector<Item> vendorItems = null;

    private MainWindowGUI mainWindowGUI;
    private static PurchaseOrderDatabase purchaseOrderDatabase = PurchaseOrderDatabase.getInstance();

    private boolean toggleView = false;
    private boolean vendorSelected = false;

    private int vendorID;
    private String vendorName;
    private String searchBarPrompt = "Search by Vendor Name";
    private String searchLabel = "Search for Vendor:";
    private String selectedLabel = "Selected Vendor:";
    private String viewButton = "View Purchase Orders";
    private String hideButton = "Hide Purchase Orders";

    public PurchaseOrderHubGUI() {
        mainWindowGUI = MainWindowGUI.getInstance();
        mainWindowGUI.setTitle("Purchase Order Management");

        setUpGUI();
        addListeners();
    }

    private void setUpGUI() {
        txtSearchBar.setText(searchBarPrompt);
        lstPurchaseOrders.setModel(purchaseOrderModel.getDisplayListModel());
        lstPurchaseOrders.setSelectionModel(new ListSelectionModel());

        lblListInfo.setText("");

        lstPurchaseOrders.setCellRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList list, Object value, int index,
                                                          boolean isSelected, boolean cellHasFocus) {
                Component c = super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                if (index % 2 != 0) {
                    setBackground(Color.lightGray);
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
         * Sets max characters for txtSearchBar to 20
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
             * Clears the searchBarPrompt when users click in the txtSearchBar
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
             * Resets searchBarPrompt if user didn't type anything
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
                if (toggleView) {
                    toggleViewInfo(vendorName);
                }
            } else {
                vendorName = txtSearchBar.getText();
                vendorID = PurchaseOrderHub.selectVendor(vendorName);
                purchaseOrderModel.clearModel();
                if (vendorID > -1) {
                    toggleSearchInfo();
                }
            }
        } else if (userAction == btnStartPO) {
            if (vendorSelected) {
                vendorItems = PurchaseOrderHub.filterItems(vendorID);
                if (vendorItems.isEmpty()) {
                    DialogDisplay.displayError(vendorName + " does not have any items to choose from");
                    return;
                }
                if (toggleView) {
                    toggleViewInfo(vendorName);
                }
                mainWindowGUI.setJPanel(
                        new PurchaseOrderCreationGUI(rootPanel, vendorItems, vendorName).getPanel());
            } else {
                DialogDisplay.displayError("Please select a Vendor first");
            }
        } else if (userAction == btnViewPO) {
            if (vendorSelected) {
                if (!purchaseOrderDatabase.containsVendor(vendorID)) {
                    DialogDisplay.displayError(vendorName + " does not have any Purchase Orders");
                    return;
                }
                PurchaseOrderHub.setUpVendorPO(purchaseOrderModel, vendorID);
                toggleViewInfo(vendorName);
            } else {
                DialogDisplay.displayError("Please select a Vendor first");
            }
        } else if (userAction == btnMainMenu) {
            mainWindowGUI.setJPanel(new MainMenuGUI().getPanel());
        } else if (userAction == btnLogOut) {
            mainWindowGUI.setJPanel(new LoginGUI().getPanel());
        }
    }

    public void toggleSearchInfo() {
        if (vendorSelected) {
            lblSearch.setText(searchLabel);
            txtSearchBar.setText(searchBarPrompt);
            txtSearchBar.setEditable(vendorSelected);
            btnSelectVendor.setText("Select Vendor");
        } else {
            lblSearch.setText(selectedLabel);
            txtSearchBar.setEditable(vendorSelected);
            btnSelectVendor.setText("Select New Vendor");
        }
        vendorSelected = !vendorSelected;
    }

    public void toggleViewInfo(String vendorName) {
        if (toggleView) {
            lblListInfo.setText("");
            scpDisplayList.setVisible(false);
            btnViewPO.setText(viewButton);
        } else {
            lblListInfo.setText("Purchase Orders for " + vendorName);
            scpDisplayList.setVisible(true);
            btnViewPO.setText(hideButton);
        }
        toggleView = !toggleView;
    }
}