package GUI.InvoiceManagement;

import GUI.ItemManagement.EditItemsGUI;
import GUI.ItemManagement.ItemsGUI;
import GUI.Login.LoginGUI;
import GUI.MainWindow.MainWindowGUI;
import src.CustomerOrder.CustomerOrder;
import src.CustomerOrder.CustomerOrderDatabase;
import src.Invoice.Invoice;
import src.Invoice.InvoiceList;
import src.Item.Items;
import src.User.EnumUserRoles;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
//testing git on new pc

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
    private Invoice invoice;

    CustomerOrderDatabase customerOrderDatabase = CustomerOrderDatabase.getInstance();
    InvoiceList invoiceList = InvoiceList.getInstance();
    public InvoiceDisplayGUI(Invoice inv){
        mainWindowGUI = MainWindowGUI.getInstance();

        this.setInvoice(inv);
        setupGUI();
    }

    private void setupGUI()
    {
        invoiceHeader.setText(this.invoice.toString());
        invoiceDetails.setText(this.invoice.getItemDetails());
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

    public Invoice getIndex(){
        return this.invoice;
    }
    public void setInvoice(Invoice inv){
        this.invoice = inv;
    }
    public void closeInvoiceDisplay(){
        mainWindowGUI.setJPanel(new InvoiceGUI().getPanel());
    }

    public JPanel getPanel()
    {
        return rootPanel;
    }
}
