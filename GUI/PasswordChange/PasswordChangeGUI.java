package GUI.PasswordChange;

import GUI.MainMenu.MainMenuGUI;
import GUI.MainWindow.MainWindowGUI;
import src.User.User;
import src.User.UserDatabase;

import javax.swing.*;
import java.util.Arrays;

public class PasswordChangeGUI {
    private JPanel rootPanel;
    private JPasswordField passwordField1;
    private JPasswordField passwordField2;
    private JButton confirmButton;

    UserDatabase database;
    MainWindowGUI mainWindowGUI;

    public PasswordChangeGUI()
    {
        database = UserDatabase.getInstance();
        mainWindowGUI = MainWindowGUI.getInstance();
        setupGUI();
    }

    public void setupGUI()
    {
        confirmButton.addActionListener(e->
        {
            User user = database.getCurrentUser();
            char[] password = passwordField1.getPassword();

            // Ensure the two text fields match
            if(!Arrays.equals(password, passwordField2.getPassword()))
            {
                displayError("Passwords do not match!");
                return;
            }

            if(Arrays.equals(password, user.getPassword()))
            {
                displayError("New password cannot be the same as the current password!");
                return;
            }

            user.setPassword(password);
            user.setFirstLogin(false);
            mainWindowGUI.setJPanel(new MainMenuGUI().getPanel());
        });
    }

    public void displayError(String errorMessage)
    {
        JOptionPane.showMessageDialog(JOptionPane.getRootFrame(), errorMessage,
                "Error", JOptionPane.ERROR_MESSAGE);
    }

    public JPanel getPanel()
    {
        return rootPanel;
    }
}
