package GUI.UserManagement;

import src.User;
import src.UserDatabase;

import javax.swing.*;

public class UserInformationLogic implements IUserInformationLogic {

    UserDatabase database;

    UserInformationLogic()
    {
        database = UserDatabase.getInstance();
    }

    public boolean formValidated(User newUser)
    {
        // Check first that userID is not already in database
        if(database.getUser(newUser.getUserID()) != null)
        {
            System.out.println(database.getUser(newUser.getUserID()).toString());
            displayError("User ID already exists!");
            return false;
        }

        // Check that the userID is in correct format
        if(!newUser.getUserID().matches("^[a-zA-Z0-9_]*$"))
        {
            displayError("User ID may only contain alpha numeric characters!");
            return false;
        }

        // Check that password meets correct conditions
        final int passwordMinLength = 8;

        if(newUser.getPassword().length < passwordMinLength)
        {
            displayError("Password must be at least 8 characters long!");
            return false;
        }

        if(newUser.getRole() == null)
        {
            displayError("You must choose a role!");
            return false;
        }

        int newUserRole = newUser.getRole().getPermissionLevel();
        int currentUserRole = database.getCurrentUser().getRole().getPermissionLevel();

        if(newUserRole >= currentUserRole)
        {
            displayError("Cannot create a new user with a role higher than you!"
            + "\nYour role is: " + database.getCurrentUser().getRole());
            return false;
        }

        return true;
    }

    public boolean formValidatedUpdate(User oldUser, User newUser)
    {
        // Check first that userID is not already in database
        if(database.getUser(newUser.getUserID()) != null)
        {
            // If the User ID did not change since we are updating
            if(!oldUser.getUserID().equals(newUser.getUserID()))
            {
                System.out.println(database.getUser(newUser.getUserID()).toString());
                displayError("User ID already exists!");
                return false;
            }
        }

        // Check that the userID is in correct format
        if(!newUser.getUserID().matches("^[a-zA-Z0-9_]*$"))
        {
            displayError("User ID may only contain alpha numeric characters!");
            return false;
        }

        // Check that password meets correct conditions
        final int passwordMinLength = 8;

        if(newUser.getPassword().length < passwordMinLength)
        {
            displayError("Password must be at least 8 characters long!");
            return false;
        }

        if(newUser.getRole() == null)
        {
            displayError("You must choose a role!");
            return false;
        }

        int newUserRole = newUser.getRole().getPermissionLevel();
        int currentUserRole = database.getCurrentUser().getRole().getPermissionLevel();

        if(newUserRole >= currentUserRole)
        {
            displayError("Cannot create a new user with a role higher than you!"
                    + "\nYour role is: " + database.getCurrentUser().getRole());
            return false;
        }

        return true;
    }

    private void displayError(String errorMessage)
    {
        JOptionPane.showMessageDialog(JOptionPane.getRootFrame(), errorMessage);
    }
}
