package GUI.OverdueInvoicesSearch;

import GUI.InvoiceManagement.InvoiceDisplayGUI;
import GUI.Login.LoginGUI;
import GUI.MainMenu.MainMenuGUI;
import GUI.MainWindow.MainWindowGUI;
import com.sun.tools.javac.Main;
import src.Customer.CustomerProfile;
import src.CustomerOrder.CustomerOrder;
import src.CustomerOrder.CustomerOrderDatabase;
import src.Invoice.Invoice;
import src.Invoice.InvoiceDatabase;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Vector;

public class OverdueInvoicesSearchGUI {
    private JPanel rootPanel;
    private JPanel mainPanel;
    private JPanel buttonPanel;
    private JList<CustomerProfile> overdueInvoicesList;
    private JPanel mainTopBarPanel;
    private JButton mainMenuButton;
    private JButton logoutButton;

    MainWindowGUI mainWindowGUI;
    InvoiceDatabase invoiceDatabase;
    OverdueInvoicesLogic overdueInvoicesLogic;

    Invoice[] invoiceList;
    CustomerProfile[] overdueCustomers;

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

        overdueInvoicesList.setListData(overdueCustomers);
    }

    private void displayError(String message) {
        JOptionPane.showMessageDialog(JOptionPane.getRootFrame(), message, "Error", JOptionPane.ERROR_MESSAGE);
    }

    public JPanel getPanel()
    {
        return rootPanel;
    }
}
