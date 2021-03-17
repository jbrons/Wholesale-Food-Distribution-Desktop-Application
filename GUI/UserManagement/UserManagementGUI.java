package GUI.UserManagement;

import javax.swing.*;

public class UserManagementGUI {
    JPanel rootPanel;
    private JPanel buttonPanel;
    private JButton addNewUserButton;
    private JButton editUserButton;
    private JButton deleteUserButton;
    private JButton mainMenuButton;
    private JButton logoutButton;
    private JPanel mainPanel;
    private JPanel mainTopBarPanel;
    private JList userSearchList;
    private JLabel searchUserIDLabel;
    private JTextField searchTextField;
    private JButton searchButton;
    private JButton viewAllButton;

    AddUserGUI addUserGui;

    public UserManagementGUI()
    {
        setupGUI();
    }

    void setupGUI()
    {
        addNewUserButton.addActionListener(e->
        {
            addUserGui = new AddUserGUI();
        });
    }

    public JPanel getPanel()
    {
        return rootPanel;
    }
}
