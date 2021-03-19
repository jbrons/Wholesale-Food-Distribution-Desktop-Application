package GUI.UserManagement;

import GUI.Login.LoginGUI;
import GUI.MainMenu.MainMenuGUI;
import GUI.MainWindow.MainWindowGUI;
import src.User;
import src.UserDatabase;

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
    private JList<User> userSearchList;
    private JLabel searchUserIDLabel;
    private JTextField searchTextField;
    private JButton searchButton;

    UserInformationGUI userInformationGUI;
    UserInformationLogic userInformationLogic;
    MainWindowGUI mainWindowGUI;
    UserDatabase database;

    User selectedUser = null;

    public UserManagementGUI()
    {
        userInformationLogic = new UserInformationLogic();
        mainWindowGUI = MainWindowGUI.getInstance();
        database = UserDatabase.getInstance();
        setupGUI();
        initializeList();
    }

    void setupGUI()
    {
        addNewUserButton.addActionListener(e->
        {
            mainWindowGUI.setJPanel(new UserInformationGUI().getPanel());
        });

        editUserButton.addActionListener(e->
        {
            selectedUser = userSearchList.getSelectedValue();

            if(selectedUser == null)
            {
                displayError("Please select a user to edit");
                return;
            }

            int currentUserRole = database.getCurrentUser().getRole().getPermissionLevel();
            int selectedUserRole = selectedUser.getRole().getPermissionLevel();

            if (currentUserRole <= selectedUserRole)
            {
                displayError("Your role cannot edit this user"
                + "\nYour role: " + database.getCurrentUser().getRole());

                return;
            }

            mainWindowGUI.setJPanel(new UserInformationGUI(selectedUser).getPanel());
        });

        deleteUserButton.addActionListener(e->
        {
            selectedUser = userSearchList.getSelectedValue();

            if(selectedUser == null)
            {
                displayError("Please select a user to delete");
                return;
            }

            int currentUserRole = database.getCurrentUser().getRole().getPermissionLevel();
            int selectedUserRole = selectedUser.getRole().getPermissionLevel();

            if (currentUserRole <= selectedUserRole)
            {
                displayError("Your role cannot delete this user"
                        + "\nYour role: " + database.getCurrentUser().getRole());

                return;
            }

            database.deleteUser(selectedUser);
            initializeList();
        });

        mainMenuButton.addActionListener(e->
        {
            mainWindowGUI.setJPanel(new MainMenuGUI().getPanel());
        });

        logoutButton.addActionListener(e->
        {
            mainWindowGUI.setJPanel(new LoginGUI().getPanel());
        });

        searchButton.addActionListener(e->
        {
            searchList(searchTextField.getText());
        });
    }

    private void initializeList()
    {
        userSearchList.setListData(database.getAllUsers());
    }

    private void searchList(String userID)
    {
        User[] list = new User[1];
        list[0] = database.getUser(userID);

        if(list[0] == null)
        {
            displayError("No users found with User ID: " + userID);
            return;
        }

        UserInformationGUI userInformationGUI = new UserInformationGUI(list[0]);
        userInformationGUI.userSearch();
        mainWindowGUI.setJPanel(userInformationGUI.getPanel());


        userSearchList.setListData(list);
    }

    public JPanel getPanel()
    {
        return rootPanel;
    }

    private void displayError(String errorMessage)
    {
        JOptionPane.showMessageDialog(JOptionPane.getRootFrame(), errorMessage);
    }
}
