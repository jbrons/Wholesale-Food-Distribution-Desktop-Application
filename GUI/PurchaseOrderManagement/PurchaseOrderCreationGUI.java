package GUI.PurchaseOrderManagement;

import GUI.Login.LoginGUI;
import GUI.MainMenu.MainMenuGUI;
import GUI.MainWindow.MainWindowGUI;
import src.Item.Item;
import src.PurchaseOrder.PurchaseOrder;
import src.PurchaseOrder.PurchaseOrderDatabase;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Vector;

/**
 * PurchaseOrderCreationGUI implements a GUI page for purchase users to create Purchase Orders
 *
 * @author Jordan Bronstetter
 * @date 04/08/2021
 *
 */
public class PurchaseOrderCreationGUI implements ActionListener {
    private JPanel rootPanel;
    private JButton btnLogOut;
    private JButton btnMainMenu;
    private JPanel pnlFormatDisplay;
    private JScrollPane scpDisplayList;
    private JList lstItems;
    private JButton btnCancelPO;
    private JButton btnCreatePO;
    private JTextField txtSearchBar;
    private JLabel lblListInfo;
    private JLabel lblSearch;
    private JButton btnSelectVendor;

    private JPanel pnlPurchaseOrder;

    ItemModel itemModel = new ItemModel();
    Vector<Item> vendorItems = null;

    private PurchaseOrder purchaseOrder = new PurchaseOrder();
    private PurchaseOrderDatabase purchaseOrderDatabase = PurchaseOrderDatabase.getInstance();
    private MainWindowGUI mainWindowGUI;

    private int vendorID;
    private String vendorName;
    private String selectedText = "Selected Vendor:";
    private String purchaseOrderInfo = "Select 1-5 items to add to the Purchase Order";
    private String purchaseOrderFullText = "Cannot select any more items: Purchase Order is full";
    private String purchaseOrderStartedText = "Select next item";

    public PurchaseOrderCreationGUI(JPanel pnlPurchaseOrder, Vector<Item> vendorItems, String vendorName) {
        mainWindowGUI = MainWindowGUI.getInstance();
        mainWindowGUI.setTitle("Purchase Order Management");

        this.pnlPurchaseOrder = pnlPurchaseOrder;
        this.vendorItems = vendorItems;
        this.vendorName = vendorName;
        this.vendorID = vendorItems.get(0).getVendorId();

        setUpGUI();
        addListeners();
    }

    private void setUpGUI() {
        setSelectedVendor();
        setUpItemsList();
    }

    private void addListeners() {
        btnCreatePO.addActionListener(this);
        btnCancelPO.addActionListener(this);
        btnMainMenu.addActionListener(this);
        btnLogOut.addActionListener(this);
        btnSelectVendor.addActionListener(this);

        lstItems.addMouseListener(new MouseAdapter() {
            /**
             * {@inheritDoc}
             *
             * Calls to ItemPOInfoGUI to add Purchase Order Details to the selected item
             *
             * @param e
             */
            @Override
            public void mouseClicked(MouseEvent e) {
                if (!purchaseOrder.isFull()) {
                    int index = lstItems.getSelectedIndex();
                    Item item = vendorItems.get(index);
                    mainWindowGUI.setJPanel(new ItemPOInfoGUI(getPanel(), purchaseOrder, item).getPanel());
                    setListInfo();
                }
                lstItems.clearSelection();
            }

            /**
             * {@inheritDoc}
             *
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
             * Invoked when the mouse button has been moved on a lstItems
             * (with no buttons no down).
             * Sets the cell the mouse point is over as selected, otherwise
             * it clears the list selection
             *
             * @param e
             */
            @Override
            public void mouseMoved(MouseEvent e) {
                if (!purchaseOrder.isFull()) {
                    int index = lstItems.locationToIndex(e.getPoint());
                    Rectangle cellBounds = lstItems.getCellBounds(index, index);

                    if (cellBounds.contains(e.getPoint())) {
                        lstItems.setSelectedIndex(index);
                        return;
                    }
                }
                lstItems.clearSelection();
            }
        });
    }

    private void setListInfo() {
        if (purchaseOrder.isAlmostFull()) {
            lblListInfo.setText(purchaseOrderFullText);
            lstItems.setSelectionModel(new ListSelectionModel());
        } else if (!purchaseOrder.isEmpty()) {
            lblListInfo.setText(purchaseOrderStartedText);
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
            if (PurchaseOrderCreation.deselectVendor()) {
                closeGUI();
            }
        } else if (userAction == btnCreatePO) {
            if (purchaseOrder.isEmpty()) {
                DialogDisplay.displayError("Must add at least one item to Purchase Order");
            } else {
                purchaseOrderDatabase.add(vendorID, purchaseOrder);
                DialogDisplay.displayMessage("Purchase Order Created");
                closeGUI();
            }
        } else if (userAction == btnCancelPO) {
            int choice = PurchaseOrderCreation.cancelPurchaseOrder();
            if (choice == JOptionPane.YES_OPTION) {
                closeGUI();
            }
        } else if (userAction == btnMainMenu) {
            if (PurchaseOrderCreation.deselectVendor()) {
                mainWindowGUI.setJPanel(new MainMenuGUI().getPanel());
            }
        } else if (userAction == btnLogOut) {
            if (PurchaseOrderCreation.deselectVendor()) {
                mainWindowGUI.setJPanel(new LoginGUI().getPanel());
            }
        }
    }

    private void setSelectedVendor() {
        lblSearch.setText(selectedText);
        txtSearchBar.setText(vendorName);
        txtSearchBar.setEditable(false);
        btnSelectVendor.setText("Select New Vendor");
    }

    private void setUpItemsList() {
        lstItems.setModel(itemModel.getDisplayListModel());
        lblListInfo.setText(purchaseOrderInfo);
        itemModel.initializeModel(vendorItems);
    }

    private void closeGUI() {
        mainWindowGUI.setJPanel(pnlPurchaseOrder);
    }

    public JPanel getPanel() {
        return rootPanel;
    }
}
