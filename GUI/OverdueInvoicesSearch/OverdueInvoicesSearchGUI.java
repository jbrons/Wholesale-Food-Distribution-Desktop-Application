package GUI.OverdueInvoicesSearch;

import GUI.Login.LoginGUI;
import GUI.MainMenu.MainMenuGUI;
import GUI.MainWindow.MainWindowGUI;
import src.Invoice.Invoice;
import src.Invoice.InvoiceDatabase;
import src.OverdueCustomerInvoice.OverdueCustomerInvoice;

import javax.swing.*;

/**
 * GUI for users to view and search all
 * overdue customer invoices (past 30 days).
 *
 * @author Jacob Price | ga4116
 */
public class OverdueInvoicesSearchGUI {
    private JPanel rootPanel;
    private JPanel mainPanel;
    private JPanel buttonPanel;
    private JList<String> overdueInvoicesList;
    private JPanel mainTopBarPanel;
    private JButton mainMenuButton;
    private JButton logoutButton;

    MainWindowGUI mainWindowGUI;
    InvoiceDatabase invoiceDatabase;
    OverdueInvoicesLogic overdueInvoicesLogic;

    Invoice[] invoiceList;
    OverdueCustomerInvoice[] overdueCustomers;

    public OverdueInvoicesSearchGUI()
    {
        mainWindowGUI = MainWindowGUI.getInstance();
        invoiceDatabase = InvoiceDatabase.getInstance();

        overdueInvoicesLogic = new OverdueInvoicesLogic();
        invoiceList = null;
        overdueCustomers = null;

        setupGUI();
        initializeList();
    }

    private void setupGUI()
    {
        mainMenuButton.addActionListener(e -> {
            mainWindowGUI.setJPanel(new MainMenuGUI().getPanel());
        });

        logoutButton.addActionListener(e -> {
            mainWindowGUI.setJPanel(new LoginGUI().getPanel());
        });
    }

    private void initializeList() {
        overdueCustomers = overdueInvoicesLogic.getOverdueCustomers();

        String[] resultList = new String[overdueCustomers.length];
        for(int i = 0; i < overdueCustomers.length; i++)
            resultList[i] =  overdueCustomers[i].toString();

        overdueInvoicesList.setListData(resultList);
    }

    private void displayError(String message) {
        JOptionPane.showMessageDialog(JOptionPane.getRootFrame(), message, "Error", JOptionPane.ERROR_MESSAGE);
    }

    public JPanel getPanel()
    {
        return rootPanel;
    }
}