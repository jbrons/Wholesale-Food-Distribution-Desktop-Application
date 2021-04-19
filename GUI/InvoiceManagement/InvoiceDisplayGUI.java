package GUI.InvoiceManagement;

/**
 *  This class allows the user to view invoice header details and item details of the created invoice
 *
 *
 * @author Zachary Nicolai
 * @date 04/18/2021
 */
import GUI.Login.LoginGUI;
import GUI.MainWindow.MainWindowGUI;
import src.CustomerOrder.CustomerOrderDatabase;
import src.Invoice.Invoice;
import src.Invoice.InvoiceDatabase;
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
    private Invoice invoice;

    CustomerOrderDatabase customerOrderDatabase = CustomerOrderDatabase.getInstance();
    InvoiceDatabase invoiceList = InvoiceDatabase.getInstance();
    public InvoiceDisplayGUI(Invoice inv){
        mainWindowGUI = MainWindowGUI.getInstance();
        this.setInvoice(inv);
        setupGUI();
    }

    private void setupGUI()
    {
        //setting header details and item details
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
        mainWindowGUI.setJPanel(new InvoiceGUI().getPanel(),"Customer Invoice Management");
    }

    public JPanel getPanel()
    {
        return rootPanel;
    }
}
