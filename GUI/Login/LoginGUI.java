package GUI.Login;

import javax.swing.*;
import java.util.Arrays;

import GUI.MainMenu.MainMenuGUI;
import GUI.MainWindow.MainWindowGUI;
import GUI.PasswordChange.PasswordChangeGUI;
import src.User.EnumUserRoles;
import src.User.User;
import src.User.UserDatabase;

/**
 * LoginGUI handles all the GUI for the LoginGUI.form.
 *
 * This will allow a registered user to login to the system
 * and prevent anyone not registered from accessing the system.
 *
 * @author Jacob Price | ga4116
 */
public class LoginGUI {
    private JPanel rootPanel;
    private JLabel userIDLabel;
    private JLabel passwordLabel;
    private JPasswordField passwordTextField;
    private JTextField userIDTextField;
    private JButton btnLogin;

    LoginLogic loginLogic;

    public LoginGUI()
    {
        loginLogic = new LoginLogic();
        setupGUI();
    }

    public void setupGUI()
    {
        btnLogin.addActionListener(e ->
        {
            String userID = "";
            char[] password = "".toCharArray();

            try {
                userID = userIDTextField.getText();
                password = passwordTextField.getPassword();
            } catch (NullPointerException error) { /* do nothing */ } // Tell user incorrect userID or password

            if (loginLogic.validateLogin(userID, password))
            {
                Arrays.fill(password, '0');

                User currentUser = UserDatabase.getInstance().getCurrentUser();
                MainWindowGUI mainWindowGUI = MainWindowGUI.getInstance();

                boolean isNormalUser = currentUser.getPermissionLevel() < EnumUserRoles.ADMINISTRATOR.getPermissionLevel();

                if(currentUser.getFirstLogin() && isNormalUser)
                    mainWindowGUI.setJPanel(new PasswordChangeGUI().getPanel());
                else
                    mainWindowGUI.setJPanel(new MainMenuGUI().getPanel());

                System.out.println("Success!");
            }
            else
            {
                JOptionPane.showMessageDialog(JOptionPane.getRootFrame(), "Incorrect User ID / Password",
                        "Error Message", JOptionPane.ERROR_MESSAGE);
            }
        });
    }

    public JPanel getPanel()
    {
        return rootPanel;
    }
}
