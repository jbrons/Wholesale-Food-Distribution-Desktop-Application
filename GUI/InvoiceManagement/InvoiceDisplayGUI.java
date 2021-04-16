package GUI.InvoiceManagement;

import GUI.ItemManagement.EditItemsGUI;
import GUI.ItemManagement.ItemsGUI;
import GUI.Login.LoginGUI;
import GUI.MainWindow.MainWindowGUI;
import src.CustomerOrder.CustomerOrder;
import src.CustomerOrder.CustomerOrderDatabase;
import src.Item.Items;
import src.User.EnumUserRoles;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class InvoiceDisplayGUI {
    private JPanel rootPanel;
    private JPanel invoicePanel;
    private JPanel headerPanel;
    private JPanel detailPanel;
    private JButton leaveButton;
    private JButton logoutButton;
    private JLabel invoiceDetails;
    private JLabel invoiceHeader;
    private JLabel invoiceCreated;
    private MainWindowGUI mainWindowGUI;
    private int index;
    CustomerOrderDatabase customerOrderDatabase = CustomerOrderDatabase.getInstance();

    public InvoiceDisplayGUI(int i){
        mainWindowGUI = MainWindowGUI.getInstance();

        this.setIndex(i);
        setupGUI();
    }

    private void setupGUI()
    {

        CustomerOrder order = customerOrderDatabase.get(this.index);
        //sets label to the index of selected item
        invoiceDetails.setText(order.toString());
        invoiceHeader.setText(order.toString());
        //button actions


        leaveButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                closeInvoiceDisplay();
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
    public void closeInvoiceDisplay(){
        mainWindowGUI.setJPanel(new InvoiceGUI().getPanel());
    }

    public JPanel getPanel()
    {
        return rootPanel;
    }
}
