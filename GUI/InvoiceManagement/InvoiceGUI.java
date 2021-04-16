package GUI.InvoiceManagement;

import GUI.ItemManagement.AddItemsGUI;
import GUI.ItemManagement.ItemDisplayGUI;
import GUI.Login.LoginGUI;
import GUI.MainMenu.MainMenuGUI;
import GUI.MainWindow.MainWindowGUI;
import src.Customer.CustomerProfileDatabase;
import src.Customer.CustomerProfile;
import src.Customer.CustomerProfileDatabase;
import src.CustomerOrder.CustomerOrder;
import src.CustomerOrder.CustomerOrderDatabase;
import src.Invoice.InvoiceList;
import src.User.EnumUserRoles;

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
    private JTextField focused = searchField;
    private MainWindowGUI mainWindowGUI;

    InvoiceList invoiceList = InvoiceList.getInstance();
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

            }
        });
        leaveButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                closeCatalog();
            }});


        iList.addMouseListener(new MouseAdapter(){
            public void mouseClicked(MouseEvent evt) {
                try {
                    JList jList = (JList) evt.getSource();
                    if (evt.getClickCount() == 2) {
                        //index of selected jList is stored and then ItemsGUI displays selectedIndex
                        /*int index = searchIndex(iList.locationToIndex(evt.getPoint()));
                        ItemDisplayGUI dis = new ItemDisplayGUI(index);
                        mainWindowGUI.setJPanel(dis.getPanel());*/
                    }
                }
                catch(ArrayIndexOutOfBoundsException a){
                    JOptionPane.showMessageDialog(null, "Please make sure you have selected an item.");
                }
            }
        });
        logoutButton.addActionListener(e ->
        {
            mainWindowGUI.setJPanel(new LoginGUI().getPanel());
        });
    }

  //  int customerId(

    public void setCatalog(Vector<CustomerOrder> v){
        iList.setListData(v);
        iList.setFont(new Font("Arial",Font.BOLD,12));
    }
    /*
    public int searchIndex(int index){
        String itemsString;
        itemsString = (String) iList.getModel().getElementAt(index);
        return itemsList.getId(itemsString);
    }
    */

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
