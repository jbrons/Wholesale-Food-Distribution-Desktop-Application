package GUI.MainMenu;

import GUI.ItemManagement.ItemsGUI;
import GUI.Login.LoginGUI;
import GUI.MainWindow.MainWindowGUI;
import GUI.UserManagement.UserManagementGUI;
import GUI.CustomerProfileManager.CustomerProfileManagerGUI;

import GUI.VendorManagement.VendorUI;
import src.User.EnumUserRoles;
import src.User.UserDatabase;
import javax.swing.*;

public class MainMenuGUI {
    private JPanel rootPanel;
    private JButton userManagementButton;
    private JButton customerManagementButton;
    private JButton logoutButton;
    private JButton itemManagementButton;
    private JButton vendorManagementButton;

    MainWindowGUI mainWindowGUI = MainWindowGUI.getInstance();
    UserDatabase database = UserDatabase.getInstance();

    public MainMenuGUI()
    {
        if (database.getCurrentUser().getPermissionLevel() < EnumUserRoles.ADMINISTRATOR.getPermissionLevel())
        {
            userManagementButton.setEnabled(false);
            userManagementButton.setVisible(false);
        }

        if (database.getCurrentUser().getRole() != EnumUserRoles.PURCHASER
                && database.getCurrentUser().getRole() != EnumUserRoles.OWNER) {
            vendorManagementButton.setEnabled(false);
            vendorManagementButton.setVisible(false);
        }

        userManagementButton.addActionListener(e->
                mainWindowGUI.setJPanel(new UserManagementGUI().getPanel()));

        customerManagementButton.addActionListener(e -> {
            if (database.getCurrentUser().getRole() == EnumUserRoles.OWNER)
                mainWindowGUI.setJPanel(new CustomerProfileManagerGUI().getPanel());
            else
                JOptionPane.showMessageDialog(null, "You are not OWNER user.");
        });

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
    }

    public JPanel getPanel()
    {
        return rootPanel;
    }
}
