package GUI.CustomerOrderSearch;

import GUI.Login.LoginGUI;
import GUI.MainMenu.MainMenuGUI;
import GUI.MainWindow.MainWindowGUI;
import src.CustomerOrder.CustomerOrder;
import src.CustomerOrder.CustomerOrderDatabase;

import javax.swing.*;

/**
 * GUI for users to view and search for customer's orders.
 *
 * @author Jacob Price | ga4116
 */
public class CustomerOrderSearchGUI {
    private JPanel rootPanel;
    private JPanel buttonPanel;
    private JPanel mainPanel;
    private JPanel mainTopBarPanel;
    private JTextField searchTextField;
    private JButton searchButton;
    private JButton viewAllButton;
    private JButton mainMenuButton;
    private JButton logoutButton;
    private JList<CustomerOrder> customerOrderList;

    MainWindowGUI mainWindowGUI;
    CustomerOrderDatabase customerOrderDatabase;

    CustomerOrderSearchLogic customerOrderSearchLogic;

    public CustomerOrderSearchGUI()
    {
        mainWindowGUI = MainWindowGUI.getInstance();
        customerOrderDatabase = CustomerOrderDatabase.getInstance();

        customerOrderSearchLogic = new CustomerOrderSearchLogic();

        setupGUI();
        initializeList();
    }

    private void setupGUI()
    {
        searchButton.addActionListener(e -> {
            String searchQuery = searchTextField.getText();
            searchList(searchQuery);
        });

        viewAllButton.addActionListener(e ->{
            initializeList();
        });

        mainMenuButton.addActionListener(e -> {
            mainWindowGUI.setJPanel(new MainMenuGUI().getPanel());
        });

        logoutButton.addActionListener(e -> {
            mainWindowGUI.setJPanel(new LoginGUI().getPanel());
        });
    }

    private void initializeList()
    {
        customerOrderList.setListData(customerOrderDatabase.getAllOrders());
    }

    private void searchList(String customerName)
    {
        CustomerOrder[] list = customerOrderSearchLogic.getCustomerOrders(customerName);

        if(list == null)
        {
            displayError("No customer found with the name [" + customerName + "].");
            return;
        }

        customerOrderList.setListData(list);
    }

    private void displayError(String message) {
        JOptionPane.showMessageDialog(JOptionPane.getRootFrame(), message, "Error", JOptionPane.ERROR_MESSAGE);
    }

    public JPanel getPanel()
    {
        return rootPanel;
    }
}
