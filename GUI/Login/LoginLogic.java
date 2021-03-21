package GUI.Login;

import src.User;
import src.UserDatabase;

import java.util.Arrays;

public class LoginLogic implements ILoginLogic{

    UserDatabase database;
    User user;

    public LoginLogic()
    {
        database = UserDatabase.getInstance();
        database.setCurrentUser(null);
    }

    public boolean validateLogin(String userID, char[] password) {
        user = database.getUser(userID);

        if(user != null)
        {
            if(Arrays.equals(password, user.getPassword()))
            {
                database.setCurrentUser(user);
                return true;
            }
        }

        return false;
    }
}
