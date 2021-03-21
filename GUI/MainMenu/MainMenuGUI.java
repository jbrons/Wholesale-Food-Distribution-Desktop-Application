package GUI.MainMenu;

import GUI.Login.LoginGUI;
import GUI.MainWindow.MainWindowGUI;
import GUI.UserManagement.UserManagementGUI;
import GUI.CustomerProfileManager.CustomerProfileManagerGUI;

import src.User.EnumUserRoles;
import src.User.UserDatabase;
import javax.swing.*;

public class MainMenuGUI {
    private JPanel rootPanel;
    private JButton btnManageUser;
    private JButton button2;
    private JButton btnLogout;

    MainWindowGUI mainWindowGUI = MainWindowGUI.getInstance();
    UserDatabase database = UserDatabase.getInstance();

    public MainMenuGUI()
    {
        if (database.getCurrentUser().getPermissionLevel() < EnumUserRoles.ADMINISTRATOR.getPermissionLevel())
        {
            btnManageUser.setEnabled(false);
            btnManageUser.setVisible(false);
        }

        btnManageUser.addActionListener(e->
                mainWindowGUI.setJPanel(new UserManagementGUI().getPanel()));

        button2.addActionListener(e -> {
            if (database.getCurrentUser().getRole() == EnumUserRoles.OWNER)
                mainWindowGUI.setJPanel(new CustomerProfileManagerGUI().getPanel());
            else
                JOptionPane.showMessageDialog(null, "You are not OWNER user.");
        });

        btnLogout.addActionListener(e ->
                mainWindowGUI.setJPanel(new LoginGUI().getPanel()));
    }

    public JPanel getPanel()
    {
        return rootPanel;
    }
}
