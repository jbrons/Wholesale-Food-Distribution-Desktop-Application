package GUI.PurchaseOrderManagement;

import GUI.MainWindow.MainWindowGUI;

import javax.swing.*;

public class ItemPOInfo {
    private JFormattedTextField formattedTextField1;
    private JFormattedTextField formattedTextField2;
    private JPanel rootPanel;

    private MainWindowGUI mainWindowGUI;

    public ItemPOInfo() {
        mainWindowGUI = MainWindowGUI.getInstance();
        mainWindowGUI.setTitle("Purchase Order Management");

        setUpGUI();
        addListeners();
    }

    private void setUpGUI() {
        //lstItems.setListData(itemsDatabase.getAllItemDetails());

    }

    private void addListeners() {

    }

    public JPanel getPanel()
    {
        return rootPanel;
    }
}
