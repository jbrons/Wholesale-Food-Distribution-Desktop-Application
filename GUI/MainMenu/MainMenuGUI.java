package GUI.MainMenu;

import GUI.CustomerOrderManagement.CustomerOrderGUI;
import GUI.InvoiceManagement.InvoiceGUI;
import GUI.ItemManagement.ItemsGUI;
import GUI.Login.LoginGUI;
import GUI.MainWindow.MainWindowGUI;
import GUI.UserManagement.UserManagementGUI;
import GUI.CustomerProfileManager.CustomerProfileManagerGUI;

import GUI.VendorManagement.VendorUI;
import src.User.EnumUserRoles;
import src.User.UserDatabase;
import javax.swing.*;

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

    MainWindowGUI mainWindowGUI = MainWindowGUI.getInstance();
    UserDatabase database = UserDatabase.getInstance();

    public MainMenuGUI()
    {
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

        if (database.getCurrentUser().getRole() != EnumUserRoles.SALES_PERSON &&
                database.getCurrentUser().getRole() != EnumUserRoles.OWNER) {
            customerOrderManagementButton.setEnabled(false);
            customerOrderManagementButton.setVisible(false);
        }

        userManagementButton.addActionListener(e->
                mainWindowGUI.setJPanel(new UserManagementGUI().getPanel()));

        /*
        customerManagementButton.addActionListener(e -> {
            if (database.getCurrentUser().getRole() == EnumUserRoles.OWNER)
                mainWindowGUI.setJPanel(new CustomerProfileManagerGUI().getPanel());
            else
                JOptionPane.showMessageDialog(null, "You are not OWNER user.");
        });*/

        vendorManagementButton.addActionListener(e->
        {
            mainWindowGUI.setJPanel(new VendorUI().getPanel());
        });

        itemManagementButton.addActionListener((e ->
        {
            mainWindowGUI.setJPanel(new ItemsGUI().getPanel());
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

        //
        customerInvoiceManagementButton.addActionListener(e -> {
            mainWindowGUI.setJPanel(new InvoiceGUI().getPanel(), "Customer Invoice Management");
        });


    }

    public JPanel getPanel()
    {
        return rootPanel;
    }
}
