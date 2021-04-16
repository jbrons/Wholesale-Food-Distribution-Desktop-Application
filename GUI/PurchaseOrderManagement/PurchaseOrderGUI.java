package GUI.PurchaseOrderManagement;

import GUI.MainWindow.MainWindowGUI;
import src.Item.ItemsArray;

import javax.swing.*;

public class PurchaseOrderGUI {
    private JList lstItems;
    private JPanel rootPanel;

    ItemsArray itemsList = ItemsArray.getInstance();
    MainWindowGUI mainWindowGUI;

    public PurchaseOrderGUI() {
        mainWindowGUI = MainWindowGUI.getInstance();
        mainWindowGUI.setTitle("Purchase Order Management");
        setUpGUI();
    }


    private void setUpGUI() {
        addListeners();
        lstItems.setListData(itemsList.getAllItemDetails());
    }


    /**
     * Invoked to add a ActionListener to JButtons and JTextFields
     */
    private void addListeners() {

    }

    public JPanel getPanel()
    {
        return rootPanel;
    }
}
