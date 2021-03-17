package GUI.UserManagement;

import src.EnumUserRoles;

import javax.swing.*;

public class AddUserGUI {
    private JPanel rootPanel;
    private JPanel userInformationPanel;
    private JPanel buttonPanel;
    private JTextField userIDTextField;
    private JTextField firstNameTextField;
    private JTextField lastNameTextField;
    private JPasswordField passwordField1;
    private JPasswordField passwordField2;
    private JComboBox<EnumUserRoles> userRolesComboBox;
    private JButton addNewUserButton;
    private JButton cancelButton;

    public AddUserGUI()
    {
        JFrame rootFrame = new JFrame("CSC4110 Term Project - Add New User");
        rootFrame.setContentPane(rootPanel);

        rootFrame.pack();
        rootFrame.setLocationRelativeTo(null);
        rootFrame.setVisible(true);

        setupGUI();
    }

    private void setupGUI()
    {
        for (EnumUserRoles role : EnumUserRoles.values())
            userRolesComboBox.addItem(role);
    }
}
