package GUI.Login;

import javax.swing.*;
import java.util.Arrays;

import GUI.MainMenu.MainMenuGUI;
import GUI.MainWindow.MainWindowGUI;

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

                MainWindowGUI mainWindowGUI = MainWindowGUI.getInstance();
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
