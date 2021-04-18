package GUI.InvoiceManagement;

import GUI.Login.LoginGUI;
import GUI.MainMenu.MainMenuGUI;
import GUI.MainWindow.MainWindowGUI;
import src.Customer.CustomerProfileDatabase;
import src.Customer.CustomerProfile;
import src.CustomerOrder.CustomerOrder;
import src.CustomerOrder.CustomerOrderDatabase;
import src.Invoice.Invoice;
import src.Invoice.InvoiceDatabase;

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


    public InvoiceGUI(){
        mainWindowGUI = MainWindowGUI.getInstance();
        setupGUI();
    }

    public void setupGUI()
    {
        //button actions
        searchButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {

                Vector<Integer> customerIds = new Vector<>();
                for (CustomerProfile profile : customerProfileDatabase.getAllProfiles()) {
                    if (profile.getCustomerName().contains(searchField.getText()))
                        customerIds.add(profile.getCustomerID());
                }
                Vector<CustomerOrder> orders = new Vector<>();
                for(int i=0;i<customerIds.size();i++) {
                    for (CustomerOrder order : customerOrderDatabase.getAllOrders()) {
                        if (order.getCustomerID() == customerIds.get(i)) {
                        orders.add(order);
                        }
                    }
                }
                    setCatalog(orders);
                if(iList.getModel().getSize() == 0 ){
                    JOptionPane.showMessageDialog(null, searchField.getText() + " not found");
                }

            }

            });
        createInvoiceButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                try{
                    CustomerOrder selectedOrder = (CustomerOrder) iList.getSelectedValue();

                    if(invoiceDatabase.invoiceAlreadyExists(selectedOrder.getOrderID())) {
                        for (CustomerProfile customer : customerProfileDatabase.getAllProfiles()) {
                            if (selectedOrder.getCustomerID() == customer.getCustomerID()) {
                                if (customer.getBalance() >= (float) selectedOrder.getPrice()) {
                                    customer.setBalance(customer.getBalance() - (float) selectedOrder.getPrice());
                                    Invoice invoice = new Invoice(selectedOrder);
                                    invoiceDatabase.addInvoice(invoice);
                                    System.out.println("order added");
                                    InvoiceDisplayGUI dis = new InvoiceDisplayGUI(invoice);
                                    mainWindowGUI.setJPanel(dis.getPanel());
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
            catch(IndexOutOfBoundsException ex){
                JOptionPane.showMessageDialog(null, "Please Select a customer order");
            }

            }
        });
        leaveButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                //iList.setListData(invoiceDatabase.getInvoiceList());
                closeCatalog();
            }});


        logoutButton.addActionListener(e ->
        {
            mainWindowGUI.setJPanel(new LoginGUI().getPanel());
            //iList.setListData(customerOrderDatabase.getAllOrders());
        });
    }

  //  int customerId(

    public void setCatalog(Vector<CustomerOrder> v){
        iList.setListData(v);
        iList.setFont(new Font("Arial",Font.BOLD,12));
    }
    public boolean isDuplicate(CustomerOrder order){
        for(Invoice invoice: invoiceDatabase.getInvoiceList()){
            if(invoice.getOrderId() == order.getOrderID()){
                return false;
            }
        }

        return true;
    }

    public int searchIndex(int index){
        System.out.println(iList.getModel().getElementAt(index));

        int i=0;
        for (CustomerOrder order : customerOrderDatabase.getAllOrders()) {
            if (order ==iList.getModel().getElementAt(index)) {
                return i;
            }
        }
        System.out.println("-1");
        return -1;
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
    public JPanel getPanel()
    {
        return rootPanel;
    }
}
