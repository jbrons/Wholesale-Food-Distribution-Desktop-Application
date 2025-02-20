package GUI.ItemManagement;

/**
 * Class Description: This class controls the ItemDisplayGUI. it gives instructions for all of the buttons and
 * labels. The GUI allows users view the item they selected and then choose to edit or delete it. It ensures only
 * correct users can edit or delete an item
 *
 * @author Zachary Nicolai
 * @date 03/15/2021
 */

import GUI.Login.LoginGUI;
import src.Invoice.InvoiceDatabase;
import src.Item.Item;
import src.Item.ItemsDatabase;
import GUI.MainWindow.MainWindowGUI;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import src.PurchaseOrder.PurchaseOrderDatabase;
import src.User.UserDatabase;
import src.User.EnumUserRoles;

public class ItemDisplayGUI {
    private JButton editButton;
    private JButton leaveButton;
    private JButton deleteButton;
    private JPanel iDisplayPanel;
    private JPanel contentPanel;
    private JPanel buttonPanel;
    private JPanel rootPanel;
    private JLabel contentLabel;
    private JButton logoutButton;
    private int index;
    ItemsDatabase itemsList = ItemsDatabase.getInstance();
    private MainWindowGUI mainWindowGUI;
    UserDatabase dataBase = UserDatabase.getInstance();
    InvoiceDatabase invoiceDatabase = InvoiceDatabase.getInstance();
    PurchaseOrderDatabase purchaseOrderDatabase = PurchaseOrderDatabase.getInstance();

    public ItemDisplayGUI(int i){
        mainWindowGUI = MainWindowGUI.getInstance();

        this.setIndex(i);
        setupGUI();
    }

    private void setupGUI()
    {
        Item item = itemsList.get(this.index);
        //sets label to the index of selected item
        contentLabel.setText(item.toString());
        //button actions
        editButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                if(dataBase.getCurrentUser().getRole() == EnumUserRoles.OWNER ||dataBase.getCurrentUser().getRole() == EnumUserRoles.PURCHASER) {
                    EditItemsGUI edit = new EditItemsGUI(index);
                    mainWindowGUI.setJPanel(edit.getPanel(),"Item Profile Management/Edit Item");
                }
                else{
                    JOptionPane.showMessageDialog(null, "You must be a owner or purchase user");
                }
            }});
        deleteButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                if(dataBase.getCurrentUser().getRole() == EnumUserRoles.OWNER ||dataBase.getCurrentUser().getRole() == EnumUserRoles.PURCHASER
                || dataBase.getCurrentUser().getRole() == EnumUserRoles.INVENTORY_MANAGER) {
                    if (invoiceDatabase.itemInList(itemsList.get(index).getId()) && !purchaseOrderDatabase.containsItem(itemsList.get(index).getId(),itemsList.get(index).getVendorId())) {
                        itemsList.removeItem(index);
                        closeItemEdit();
                    }
                    else{
                        JOptionPane.showMessageDialog(null, "There are associated invoices or purchase orders");
                    }
                }
                else{
                    JOptionPane.showMessageDialog(null, "You must be a owner or purchase user");
                }

            }});
        leaveButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                closeItemEdit();
            }});
        logoutButton.addActionListener(e ->
        {
            mainWindowGUI.setJPanel(new LoginGUI().getPanel());
        });
    }

    public int getIndex(){
        return this.index;
    }
    public void setIndex(int i){
        this.index = i;
    }
    public void closeItemEdit(){
        mainWindowGUI.setJPanel(new ItemsGUI().getPanel(),"Item Profile Management");
    }

    public JPanel getPanel()
    {
        return rootPanel;
    }
}
