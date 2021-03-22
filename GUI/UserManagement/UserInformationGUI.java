package GUI.UserManagement;

import GUI.MainWindow.MainWindowGUI;
import src.User.EnumUserRoles;
import src.User.User;
import src.User.UserDatabase;

import javax.swing.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Arrays;

/**
 * UserInformationGui.java handles both some of the functionality for the
 * UserInformationGui.form and some very basic logic that cannot be handled
 * int UserInformationLogic.java.
 *
 * This handles when a user is either creating a new User or updating a current
 * User.
 *
 * @author Jacob Price | ga4116
 */
public class UserInformationGUI {
    private JFrame rootFrame;
    private JPanel rootPanel;
    private JPanel userInformationPanel;
    private JPanel buttonPanel;
    private JTextField userIDTextField;
    private JTextField firstNameTextField;
    private JTextField lastNameTextField;
    private JPasswordField passwordField1;
    private JPasswordField passwordField2;
    private JComboBox<EnumUserRoles> userRolesComboBox;
    private JButton confirmButton;
    private JButton cancelButton;

    UserDatabase database;
    MainWindowGUI mainWindowGUI;
    UserInformationLogic userInformationLogic;


    boolean updatingUser = false;
    User selectedUser = null;

    public UserInformationGUI()
    {
        database = UserDatabase.getInstance();
        mainWindowGUI = MainWindowGUI.getInstance();
        setupGUI();
    }

    public UserInformationGUI(User selectedUser)
    {
        database = UserDatabase.getInstance();
        mainWindowGUI = MainWindowGUI.getInstance();
        updatingUser = true;
        this.selectedUser = selectedUser;
        setupGUI();
        retrieveInformation(this.selectedUser);
    }

    private void setupGUI()
    {
        setTextFieldMax(userIDTextField, 6);
        setTextFieldMax(firstNameTextField, 15);
        setTextFieldMax(lastNameTextField, 15);
        setTextFieldMax(passwordField1, 16);
        setTextFieldMax(passwordField2, 16);

        for (EnumUserRoles role : EnumUserRoles.values())
            userRolesComboBox.addItem(role);

        confirmButton.addActionListener(e ->
        {
            String userID = userIDTextField.getText();
            String firstName = firstNameTextField.getText();
            String lastName = lastNameTextField.getText();
            char[] password = passwordField1.getPassword();
            EnumUserRoles role = (EnumUserRoles) userRolesComboBox.getSelectedItem();

            if(!Arrays.equals(password, passwordField2.getPassword()))
            {
                JOptionPane.showMessageDialog(JOptionPane.getRootFrame(), "Passwords do not match!",
                        "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Check that form is correct
            User newUser;

            if(updatingUser)
            {
                newUser = new User(userID, firstName, lastName,
                    password, role, selectedUser.getFirstLogin());
            }
            else
            {
                newUser = new User(userID, firstName, lastName,
                        password, role);
            }

             userInformationLogic = new UserInformationLogic();

            if(updatingUser)
            {
                if (!userInformationLogic.formValidatedUpdate(selectedUser, newUser))
                    return;
            } else if(!userInformationLogic.formValidated(newUser))
                return;

            if(updatingUser)
            {
                database.updateUser(this.selectedUser, newUser);
            } else database.addUser(newUser);

            mainWindowGUI.setJPanel(new UserManagementGUI().getPanel());
        });

        cancelButton.addActionListener(e ->
        {
            mainWindowGUI.setJPanel(new UserManagementGUI().getPanel());
        });
    }

    private void setTextFieldMax(JTextField textField, int max)
    {
        textField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                int textFieldLength = textField.getText().length();

                if(textFieldLength >= max)
                {
                    e.consume();
                }
            }
        });
    }

    private void retrieveInformation(User selectedUser)
    {
        userIDTextField.setText(selectedUser.getUserID());
        firstNameTextField.setText(selectedUser.getFirstName());
        lastNameTextField.setText(selectedUser.getLastName());
        passwordField1.setText(new String(selectedUser.getPassword()));
        passwordField2.setText(new String(selectedUser.getPassword()));
        userRolesComboBox.setSelectedItem(selectedUser.getRole());
    }

    public void userSearch()
    {
        // Setup panel so user cannot edit it
        userIDTextField.setEditable(false);
        firstNameTextField.setEditable(false);
        lastNameTextField.setEditable(false);
        passwordField1.setEditable(false);
        passwordField2.setEditable(false);
        userRolesComboBox.setEnabled(false);

        confirmButton.setVisible(false);
    }

    public JPanel getPanel()
    {
        return rootPanel;
    }
}
