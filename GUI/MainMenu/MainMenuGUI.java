package GUI.MainMenu;

import GUI.CustomerInvoiceSearch.CustomerInvoiceSearchGUI;
import GUI.CustomerOrderManagement.CustomerOrderGUI;
import GUI.CustomerOrderSearch.CustomerOrderSearchGUI;
import GUI.ExpiredItems.ExpiredItemsGUI;
import GUI.InvoiceManagement.InvoiceGUI;
import GUI.ItemManagement.ItemsGUI;
import GUI.Login.LoginGUI;
import GUI.MainWindow.MainWindowGUI;
import GUI.OverdueInvoicesSearch.OverdueInvoicesSearchGUI;
import GUI.ProfitSearch.ProfitSearchGUI;
import GUI.UserManagement.UserManagementGUI;
import GUI.CustomerProfileManager.CustomerProfileManagerGUI;
import GUI.PurchaseOrderManagement.PurchaseOrderHubGUI;

import GUI.VendorManagement.VendorHubGUI;
import src.User.EnumUserRoles;
import src.User.UserDatabase;
import src.Vendor.Vendor;

import javax.swing.*;
import java.util.ArrayList;

/**
 * Handles the GUI form MainMenuGUI.form
 *
 * The Main Menu allows the user to traverse the software.
 *
 * @author Jacob Price | ga4116
 */
public class MainMenuGUI {
    private JPanel rootPanel;
    private JButton userManagementButton;
    private JButton customerManagementButton;
    private JButton logoutButton;
    private JButton itemManagementButton;
    private JButton vendorManagementButton;
    private JButton customerOrderManagementButton;
    private JButton customerInvoiceManagementButton;
    private JButton profitSearchButton;
    private JButton expiredItemsButton;
    private JButton customerOrderSearchButton;
    private JButton purchaseOrderManagementButton;
    private JButton overdueInvoicesSearchButton;
    private JButton customerInvoiceSearchButton;

    MainWindowGUI mainWindowGUI = MainWindowGUI.getInstance();
    UserDatabase database = UserDatabase.getInstance();

    public MainMenuGUI()
    {
        mainWindowGUI.setTitle("Main Menu");

        /** BUTTON VISIBILITY */

        if (database.getCurrentUser().getPermissionLevel() < EnumUserRoles.ADMINISTRATOR.getPermissionLevel())
        {
            userManagementButton.setEnabled(false);
            userManagementButton.setVisible(false);
        }

        if (database.getCurrentUser().getRole() != EnumUserRoles.PURCHASER &&
                database.getCurrentUser().getRole() != EnumUserRoles.OWNER) {
            vendorManagementButton.setEnabled(false);
            vendorManagementButton.setVisible(false);
        }

        if (database.getCurrentUser().getRole() != EnumUserRoles.PURCHASER)
        {
            purchaseOrderManagementButton.setEnabled(false);
            purchaseOrderManagementButton.setVisible(false);
        }

        if (database.getCurrentUser().getRole() != EnumUserRoles.SALES_PERSON &&
                database.getCurrentUser().getRole() != EnumUserRoles.OWNER) {
            customerOrderManagementButton.setEnabled(false);
            customerOrderManagementButton.setVisible(false);
        }

        if (database.getCurrentUser().getRole() != EnumUserRoles.OWNER)
        {
            profitSearchButton.setEnabled(false);
            profitSearchButton.setVisible(false);
        }

        if (database.getCurrentUser().getRole() != EnumUserRoles.PURCHASER)
        {
            expiredItemsButton.setEnabled(false);
            expiredItemsButton.setVisible(false);
        }

        if (database.getCurrentUser().getRole() != EnumUserRoles.OWNER)
        {
            customerOrderSearchButton.setEnabled(false);
            customerOrderSearchButton.setVisible(false);
        }

        if (database.getCurrentUser().getRole() != EnumUserRoles.OWNER)
        {
            overdueInvoicesSearchButton.setEnabled(false);
            overdueInvoicesSearchButton.setVisible(false);
        }

        if (database.getCurrentUser().getRole() != EnumUserRoles.ACCOUNTANT)
        {
            customerInvoiceSearchButton.setEnabled(false);
            customerInvoiceSearchButton.setVisible(false);
        }

        /** BUTTON FUNCTIONALITY */

        userManagementButton.addActionListener(e->
                mainWindowGUI.setJPanel(new UserManagementGUI().getPanel()));

        vendorManagementButton.addActionListener(e->
        {
            mainWindowGUI.setJPanel(new VendorHubGUI().getPanel());
        });

        itemManagementButton.addActionListener((e ->
        {
            mainWindowGUI.setJPanel(new ItemsGUI().getPanel(),"Item Profile Management");
        }));

        purchaseOrderManagementButton.addActionListener((e ->
        {
            mainWindowGUI.setJPanel(new PurchaseOrderHubGUI().getPanel());
        }));

        logoutButton.addActionListener(e ->
                mainWindowGUI.setJPanel(new LoginGUI().getPanel()));

        //customer management
        customerManagementButton.addActionListener(e -> {
            if (database.getCurrentUser().getRole() == EnumUserRoles.OWNER)
                mainWindowGUI.setJPanel(new CustomerProfileManagerGUI().getPanel(), "Customer Profile Management");
            else
                JOptionPane.showMessageDialog(null, "You are not OWNER user.");
        });
        //customer order management
        customerOrderManagementButton.addActionListener(e -> {
            mainWindowGUI.setJPanel(new CustomerOrderGUI().getPanel(), "Customer Order Management");
        });

        // customer invoice management
        customerInvoiceManagementButton.addActionListener(e -> {
            mainWindowGUI.setJPanel(new InvoiceGUI().getPanel(), "Customer Invoice Management");
        });

        // Expired Items Search
        expiredItemsButton.addActionListener(e -> {
            mainWindowGUI.setJPanel(new ExpiredItemsGUI().getPanel(), "Expired Items Search");
        });

        // Profit Search
        profitSearchButton.addActionListener(e -> {
            mainWindowGUI.setJPanel(new ProfitSearchGUI().getPanel(), "Profit Search");
        });

        // Customer Order Search
        customerOrderSearchButton.addActionListener(e -> {
            mainWindowGUI.setJPanel(new CustomerOrderSearchGUI().getPanel(), "Customer Order Search");
        });

        overdueInvoicesSearchButton.addActionListener(e -> {
            mainWindowGUI.setJPanel(new OverdueInvoicesSearchGUI().getPanel(), "Overdue Invoices Search");
        });

        customerInvoiceSearchButton.addActionListener(e -> {
            mainWindowGUI.setJPanel(new CustomerInvoiceSearchGUI().getPanel(), "Customer Invoice Search");
        });
    }

    public void currentDiscountsAlert()
    {
        DiscountAlertLogic discountAlertLogic = new DiscountAlertLogic();
        ArrayList<Vendor> vendors = discountAlertLogic.getCurrentDiscounts();

        if(vendors.isEmpty())
            return;

        StringBuilder alertMessage = new StringBuilder("=== The below vendor(s) have started their discounts ===\n");

        for (Vendor vendor : vendors) {
            alertMessage.append(vendor.getName()).append("\n");
        }

        JOptionPane.showMessageDialog(null, alertMessage.toString(),
                "Current Seasonal Discounts", JOptionPane.INFORMATION_MESSAGE);
    }

    public JPanel getPanel()
    {
        return rootPanel;
    }
}
