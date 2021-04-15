/**
 * Customer Order
 *
 */
package GUI.CustomerOrderManagement;

import GUI.Login.LoginGUI;
import GUI.MainMenu.MainMenuGUI;
import GUI.MainWindow.MainWindowGUI;
import src.Customer.CustomerProfile;
import src.Customer.CustomerProfileDatabase;
import src.CustomerOrder.CustomerOrder;
import src.CustomerOrder.CustomerOrderDatabase;
import src.Item.Items;
import src.Item.ItemsArray;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.DefaultFormatterFactory;
import javax.swing.text.NumberFormatter;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.Vector;

public class CustomerOrderGUI {
    private JPanel mainPanel;
    private JList<CustomerProfile> listCustomers;
    private JList<String> listItems;
    private JList<CustomerOrder> listCOrders;
    private JButton btnMainMenu;
    private JTextField textCustomerName;
    private JButton btnSearchCustomer;
    private JButton btnLogout;
    private JTextField textNeedByDate;
    private JTextField textOrderDate;
    private JFormattedTextField textQuantity;
    private JLabel lbPrice;
    private JButton btnAppendItem;
    private JButton btnNewOrder;
    private JButton btnDeleteOrder;
    MainWindowGUI mainWindowGUI;


    /**
     * customer Database
     */
    private final CustomerProfileDatabase customerDB;
    /**
     * item database
     */
    private final ItemsArray itemDB;
    /**
     * order database
     */
    private final CustomerOrderDatabase orderDB;

    /**
     * Constructor
     */
    public CustomerOrderGUI() {
        // Initialize databases
        customerDB = CustomerProfileDatabase.getInstance();
        itemDB = ItemsArray.getInstance();
        orderDB = CustomerOrderDatabase.getInstance();
        mainWindowGUI = MainWindowGUI.getInstance();

        // Initialize GUI
        initGUI();
    }

    /**
     * Initialize GUI
     */
    private void initGUI() {
        // Set list data
        listCustomers.setListData(customerDB.getAllProfiles());
        listItems.setListData(itemDB.getAllItemDetails());
        listCOrders.setListData(orderDB.getAllOrders());

        // Set quantity as number
        textQuantity.setFormatterFactory(new DefaultFormatterFactory(new NumberFormatter(NumberFormat.getInstance())));

        // Search customer button event
        btnSearchCustomer.addActionListener(e -> {
            // Get all profiles
            CustomerProfile[] profiles = customerDB.getAllProfiles();

            // Searched profiles
            Vector<CustomerProfile> searched = new Vector<>();

            // Check search string
            for (CustomerProfile profile : profiles) {
                if (profile.getCustomerName().contains(textCustomerName.getText()))
                    searched.add(profile);
            }

            // set data
            listCustomers.setListData(searched);
        });

        // Goto main menu
        btnMainMenu.addActionListener(e -> mainWindowGUI.setJPanel(new MainMenuGUI().getPanel()));

        // Logout
        btnLogout.addActionListener(e-> mainWindowGUI.setJPanel(new LoginGUI().getPanel()));

        // New order - it adds new order to the order database
        btnNewOrder.addActionListener(e -> {
            // Get selected customer
            CustomerProfile profile = listCustomers.getSelectedValue();

            if (profile == null) {
                JOptionPane.showMessageDialog(null, "Please select customer in the customer list");
                return;
            }

            Date today = new Date();

            // verify if need by date is valid
            String needByDate = textNeedByDate.getText();
            try {
                Date needDate = new SimpleDateFormat("MM/dd/yyyy").parse(needByDate);
                if (needDate.before(today)) {
                    JOptionPane.showMessageDialog(null, "Past Need by date is not allowed!");
                    return;
                }
            } catch (ParseException parseException) {
                JOptionPane.showMessageDialog(null, "Incorrect Format! - (MM/dd/yyyy)");
                return;
            }

            // Check order date is valid
            String orderDate = textOrderDate.getText();
            try {
                Date ordered = new SimpleDateFormat("MM/dd/yyyy").parse(orderDate);
                if (ordered.before(today)) {
                    JOptionPane.showMessageDialog(null, "Past Order Date is not allowed!");
                    return;
                }
            } catch (ParseException parseException) {
                JOptionPane.showMessageDialog(null, "Incorrect Format! - (MM/dd/yyyy)");
                return;
            }

            // Create new order
            CustomerOrder order = new CustomerOrder(profile.getCustomerID(), needByDate, orderDate);
            // Add item
            if (addItem(profile, order)) {
                // Add order to database
                orderDB.addOrder(order);
            }

        });

        // append item to existing order
        btnAppendItem.addActionListener(e -> {
            // Get order
            CustomerOrder order = listCOrders.getSelectedValue();
            // Get customer
            CustomerProfile customer = listCustomers.getSelectedValue();

            // Add item
            addItem(customer, order);
        });

        btnDeleteOrder.addActionListener(e -> {

            // Get order
            CustomerOrder order = listCOrders.getSelectedValue();

            // Get profile of order
            CustomerProfile profile = customerDB.getProfile(order.getCustomerID());

            // Loop all items in order
            for (Map.Entry<Items, Integer> entry : order.getItems().entrySet()) {
                // Get price
                float price = (float) (entry.getKey().getSellingPrice() * entry.getValue());

                // Set item quantity
                entry.getKey().setQuantity(entry.getKey().getQuantity() + entry.getValue());
                // Set balance of customer
                profile.setBalance(price + profile.getBalance());
            }

            orderDB.deleteOrder(order);


            // Set list data
            listCOrders.setListData(orderDB.getAllOrders());
            listCustomers.setListData(customerDB.getAllProfiles());
            listItems.setListData(itemDB.getAllItemDetails());
        });

        // Get quantity and calculate the price
        textQuantity.getDocument().addDocumentListener(new DocumentListener() {
            public void changedUpdate(DocumentEvent e) {
                showPrice();
            }
            public void removeUpdate(DocumentEvent e) {
                showPrice();
            }
            public void insertUpdate(DocumentEvent e) {
                showPrice();
            }
        });

        // Show price for item select change
        listItems.addListSelectionListener(e -> showPrice());

        /**
         * Check for expired items
         */

        int itemCount = itemDB.getAllItemDetails().size();
        int expireCount = 0;

        for (int i = 0; i < itemCount; i++) {
            try {
                Items items = itemDB.get(i);
                Date expDate = new SimpleDateFormat("MM/dd/yyyy").parse(items.getExpirationDate());
                Date today = new Date();

                if (expDate.before(today)) {
                    expireCount++;
                }
            } catch (ParseException ignored) {
            }
        }

        if (expireCount >= 2)
            JOptionPane.showMessageDialog(null, "There are two more expired items");
    }

    /**
     * Add new item to order
     * @param customer customer
     * @param order order
     * @return
     */
    private boolean addItem(CustomerProfile customer, CustomerOrder order) {
        // Check order is valid
        if (order == null) {
            JOptionPane.showMessageDialog(null, "Please select order in the order list");
            return false;
        }

        // Get selected item
        Items item = itemDB.get(listItems.getSelectedIndex());

        // Check quantity of item
        if (item.getQuantity() == 0) {
            JOptionPane.showMessageDialog(null, "This item has 0 quantities");
            return false;
        } else if (order.getItemCount() >= 5) {
            // Check order has 5 items
            JOptionPane.showMessageDialog(null, "This order has 5 items");
            return false;
        }

        // Get quantity
        int quantity;
        try {
            quantity = Integer.parseInt(textQuantity.getText());

            // Compare item's quantity and input quantity
            if (item.getQuantity() >= quantity) {
                // Get price of it
                double price = quantity * item.getSellingPrice();

                // Check customer has enough balance
                if (customer.getBalance() < price) {
                    JOptionPane.showMessageDialog(null, "Customer has no enough balance");
                    return false;
                }

                // Set item quantity
                item.setQuantity(item.getQuantity() - quantity);

                // Set customer quantity
                customer.setBalance((float) (customer.getBalance() - price));

                // Add item
                order.addItem(item, quantity);

                // Set list data
                listCOrders.setListData(orderDB.getAllOrders());
                listCustomers.setListData(customerDB.getAllProfiles());
                listItems.setListData(itemDB.getAllItemDetails());
            } else {
                JOptionPane.showMessageDialog(null, "Quantity value is much than item's quantity in inventory");
                return false;
            }

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(null, "Quantity value is not integer format");
            return false;
        }

        return true;
    }

    /**
     * Show price of item, and quantity
     */
    private void showPrice() {
        // Get quantity
        String value = textQuantity.getText();
        int quantity = 0;
        try {
            quantity = Integer.parseInt(value);
        } catch (NumberFormatException e) {
        }

        // Get the price
        double price;
        int itemId = listItems.getSelectedIndex();
        if (itemId == -1) {
            price = 0;
        } else {
            Items item = itemDB.get(itemId);
            price = item.getSellingPrice() * quantity;
        }

        // Print price to label
        lbPrice.setText(String.format("Price: $%.2f", price));
    }

    /**
     * Get main panel
     * @return
     */
    public JPanel getPanel() {
        return mainPanel;
    }


}
