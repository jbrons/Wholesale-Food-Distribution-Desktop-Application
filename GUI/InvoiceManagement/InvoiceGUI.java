package GUI.InvoiceManagement;

/**
 *  This class is the main GUI for invoices. It allows account users to search for an invoice and create an invoice.
 *
 *
 * @author Zachary Nicolai
 * @date 04/18/2021
 */

import GUI.Login.LoginGUI;
import GUI.MainMenu.MainMenuGUI;
import GUI.MainWindow.MainWindowGUI;
import src.Customer.CustomerProfileDatabase;
import src.Customer.CustomerProfile;
import src.CustomerOrder.CustomerOrder;
import src.CustomerOrder.CustomerOrderDatabase;
import src.Invoice.Invoice;
import src.Invoice.InvoiceDatabase;
import src.User.EnumUserRoles;
import src.User.UserDatabase;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Vector;

public class InvoiceGUI implements FocusListener {
    private JPanel rootPanel;
    private JTextField searchField;
    private JList iList;
    private JButton searchButton;
    private JButton leaveButton;
    private JButton logoutButton;
    private JLabel invoiceLabel;
    private JButton createInvoiceButton;
    private JLabel InvoiceNameLabel;
    private JTextField focused = searchField;
    private MainWindowGUI mainWindowGUI;

    InvoiceDatabase invoiceDatabase = InvoiceDatabase.getInstance();
    CustomerProfileDatabase customerProfileDatabase = CustomerProfileDatabase.getInstance();
    CustomerOrderDatabase customerOrderDatabase = CustomerOrderDatabase.getInstance();
    UserDatabase dataBase = UserDatabase.getInstance();


    public InvoiceGUI(){
        mainWindowGUI = MainWindowGUI.getInstance();
        setupGUI();
    }

    public void setupGUI()
    {
        if(dataBase.getCurrentUser().getRole() == EnumUserRoles.ACCOUNTANT) {
            if ((customerOrderDatabase.getAllOrders().length - invoiceDatabase.getInvoiceList().size()) > 2) {
                JOptionPane.showMessageDialog(null, "There are more than two customer orders available");
            }
        }

        //button actions
        searchButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                if (dataBase.getCurrentUser().getRole() == EnumUserRoles.ACCOUNTANT) {
                    Vector<Integer> customerIds = new Vector<>();
                    for (CustomerProfile profile : customerProfileDatabase.getAllProfiles()) {
                        if (profile.getCustomerName().contains(searchField.getText())) {
                            customerIds.add(profile.getCustomerID());
                        }
                    }
                    Vector<CustomerOrder> orders = new Vector<>();
                    for (int i = 0; i < customerIds.size(); i++) {
                        for (CustomerOrder order : customerOrderDatabase.getAllOrders()) {
                            if (order.getCustomerID() == customerIds.get(i)) {
                                if (invoiceDatabase.invoiceAlreadyExists(order.getOrderID())) {
                                    orders.add(order);
                                }
                            }
                        }
                    }
                    setCatalog(orders);
                    if (iList.getModel().getSize() == 0) {
                        JOptionPane.showMessageDialog(null, searchField.getText() + " not found");
                    }

                }
                else{
                    JOptionPane.showMessageDialog(null, "You must be an accountant to search items");
                }
            }

            });
        createInvoiceButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                try{
                    CustomerOrder selectedOrder = (CustomerOrder) iList.getSelectedValue();
                    if(dataBase.getCurrentUser().getRole() == EnumUserRoles.ACCOUNTANT) {
                        //checks if invoice exists
                        if (invoiceDatabase.invoiceAlreadyExists(selectedOrder.getOrderID())) {
                            for (CustomerProfile customer : customerProfileDatabase.getAllProfiles()) {
                                if (selectedOrder.getCustomerID() == customer.getCustomerID()) {
                                    //ensures customer has enough balance
                                    if (customer.getBalance() >= (float) selectedOrder.getPrice()) {
                                        customer.setBalance(customer.getBalance() - (float) selectedOrder.getPrice());
                                        Invoice invoice = new Invoice(selectedOrder);
                                        invoiceDatabase.addInvoice(invoice);
                                        InvoiceDisplayGUI dis = new InvoiceDisplayGUI(invoice);
                                        mainWindowGUI.setJPanel(dis.getPanel(), "Customer Invoice Management/View Invoice");
                                    } else {
                                        JOptionPane.showMessageDialog(null, "Customer Does not have enough balance");
                                    }
                                }
                            }
                        }
                        else{
                            JOptionPane.showMessageDialog(null, "Invoice already created for this customer order");
                        }
                    }
                    else{
                        JOptionPane.showMessageDialog(null, "You must be an accountant user to create invoices");
                    }

                }
                catch(IndexOutOfBoundsException ex) {
                    JOptionPane.showMessageDialog(null, "Please Select a customer order");
                }
                catch(NullPointerException ex){
                    JOptionPane.showMessageDialog(null, "Please make sure you have selected a customer order");
                }

            }
        });
        leaveButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                closeCatalog();
            }});


        logoutButton.addActionListener(e ->
        {
            mainWindowGUI.setJPanel(new LoginGUI().getPanel());
        });
    }

    public void setCatalog(Vector<CustomerOrder> v){
        iList.setListData(v);
        iList.setFont(new Font("Arial",Font.BOLD,12));
    }

    public void closeCatalog(){
        mainWindowGUI.setJPanel(new MainMenuGUI().getPanel());
    }

    //methods required for implementing focusListener
    public void focusGained(FocusEvent e) {
        if (e.getSource() instanceof JTextField) {
            focused = (JTextField) e.getSource();
        }
    }
    public void focusLost(FocusEvent e) {
    }
    public JPanel getPanel() { return rootPanel; }
}
