package GUI.CustomerInvoiceSearch;

import GUI.CustomerOrderSearch.CustomerOrderSearchLogic;
import GUI.Login.LoginGUI;
import GUI.MainMenu.MainMenuGUI;
import GUI.MainWindow.MainWindowGUI;
import GUI.OverdueInvoicesSearch.OverdueInvoicesLogic;
import src.Invoice.Invoice;
import src.Invoice.InvoiceDatabase;

import javax.swing.*;

public class CustomerInvoiceSearchGUI {
    private JPanel rootPanel;
    private JPanel buttonPanel;
    private JPanel mainPanel;
    private JPanel mainTopBarPanel;
    private JList<Invoice> customerInvoiceList;
    private JButton mainMenuButton;
    private JButton logoutButton;
    private JButton searchButton;
    private JTextField searchTextField;

    MainWindowGUI mainWindowGUI;
    InvoiceDatabase invoiceDatabase;

    CustomerInvoiceSearchLogic customerInvoiceSearchLogic;

    /**
     * GUI for users to search for and view customer invoices.
     *
     * @author Jacob Price | ga4116
     */
    public CustomerInvoiceSearchGUI()
    {
        mainWindowGUI = MainWindowGUI.getInstance();
        invoiceDatabase = InvoiceDatabase.getInstance();

        customerInvoiceSearchLogic = new CustomerInvoiceSearchLogic();

        setupGUI();
        initializeList();
    }

    private void setupGUI()
    {
        searchButton.addActionListener(e -> {
            String searchQuery = searchTextField.getText();
            searchList(searchQuery);
        });

        mainMenuButton.addActionListener(e -> {
            mainWindowGUI.setJPanel(new MainMenuGUI().getPanel());
        });

        logoutButton.addActionListener(e -> {
            mainWindowGUI.setJPanel(new LoginGUI().getPanel());
        });
    }

    private void initializeList() {
        customerInvoiceList.setListData(new Invoice[0]);
    }

    private void searchList(String customerName)
    {
        Invoice[] invoices = customerInvoiceSearchLogic.getCustomerInvoices(customerName);

        if(invoices == null) {
            displayError("Customer [" + customerName + "] does not have any invoices.");
            return;
        }

        customerInvoiceList.setListData(invoices);
    }

    private void displayError(String message) {
        JOptionPane.showMessageDialog(JOptionPane.getRootFrame(), message, "Error", JOptionPane.ERROR_MESSAGE);
    }

    public JPanel getPanel()
    {
        return rootPanel;
    }
}
