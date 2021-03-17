package GUI.MainMenu;

import GUI.Login.LoginGUI;
import GUI.MainWindow.MainWindowGUI;
import GUI.UserManagement.UserManagementGUI;

import src.UserDatabase;
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
        btnManageUser.addActionListener(e->
        {
            mainWindowGUI.setJPanel(new UserManagementGUI().getPanel());
        });

        button2.addActionListener(e ->
        {
            System.out.println(database.getCurrentUser().toString());
        });

        btnLogout.addActionListener(e ->
        {
            mainWindowGUI.setJPanel(new LoginGUI().getPanel());
        });
    }

    public JPanel getPanel()
    {
        return rootPanel;
    }
}
