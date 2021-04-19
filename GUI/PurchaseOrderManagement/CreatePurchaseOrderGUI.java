package GUI.PurchaseOrderManagement;

import GUI.Login.LoginGUI;
import GUI.MainMenu.MainMenuGUI;
import GUI.MainWindow.MainWindowGUI;
import src.Item.Item;
import src.Item.ItemsDatabase;
import src.PurchaseOrder.PurchaseOrder;
import src.PurchaseOrder.PurchaseOrderDatabase;
import src.Vendor.VendorDatabase;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Vector;

public class CreatePurchaseOrderGUI implements ActionListener {
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
    private VendorDatabase vendorDatabase = VendorDatabase.getInstance();
    private ItemsDatabase itemsDatabase = ItemsDatabase.getInstance();
    private PurchaseOrderDatabase purchaseOrderDatabase = PurchaseOrderDatabase.getInstance();
    private MainWindowGUI mainWindowGUI;

    private int vendorID;
    private String vendorName;
    private String selectedLabel = "Selected Vendor:";

    public CreatePurchaseOrderGUI(JPanel pnlPurchaseOrder, int vendorID, String vendorName) {
        mainWindowGUI = MainWindowGUI.getInstance();
        mainWindowGUI.setTitle("Create Purchase Order");

        this.pnlPurchaseOrder = pnlPurchaseOrder;
        this.vendorID = vendorID;
        this.vendorName = vendorName;

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
             * @param e
             */
            @Override
            public void mouseClicked(MouseEvent e) {
                if (!itemModel.isEmpty()) {
                    int index = lstItems.getSelectedIndex();
                    Item item = vendorItems.get(index);
                    lstItems.clearSelection();
                    mainWindowGUI.setJPanel(new ItemPOInfo(getPanel(), purchaseOrder, item).getPanel());
                }
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
                if (!itemModel.isEmpty()) {
                    if (purchaseOrder.isFull()) {
                        lstItems.clearSelection();
                        lblListInfo.setText("Cannot select any more items: Purchase Order is full");
                        return;
                    }

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
            if (PurchaseOrderLogic.deselectVendor()) {
                closeGUI();
            }
        } else if (userAction == btnCreatePO) {
            if (purchaseOrder.isEmpty()) {
                DialogDisplay.displayError("Must add at least one item to Purchase Order");
            } else {
                purchaseOrderDatabase.add(vendorID, purchaseOrder);
                DialogDisplay.displayMessage("Purchase Order Created");
                itemModel.clearModel();
                closeGUI();
            }
        } else if (userAction == btnCancelPO) {
            int choice = PurchaseOrderLogic.cancelPurchaseOrder();
            if (choice == JOptionPane.YES_OPTION) {
                itemModel.clearModel();
                closeGUI();
            }
        } else if (userAction == btnMainMenu) {
            mainWindowGUI.setJPanel(new MainMenuGUI().getPanel());
        } else if (userAction == btnLogOut) {
            mainWindowGUI.setJPanel(new LoginGUI().getPanel());
        }
    }

    private void setSelectedVendor() {
        lblSearch.setText(selectedLabel);
        txtSearchBar.setText(vendorName);
        txtSearchBar.setEditable(false);
        btnSelectVendor.setText("Select New Vendor");
    }

    private void setUpItemsList() {
        lstItems.setModel(itemModel.getDisplayListModel());
        lblListInfo.setText("Select 1-5 items to add to the Purchase Order");
        vendorItems = itemsDatabase.getItemsForVendor(vendorID);
        itemModel.updateModel(vendorItems);
    }

    private void closeGUI() {
        mainWindowGUI.setJPanel(pnlPurchaseOrder);
    }

    public JPanel getPanel() {
        return rootPanel;
    }
}
