package src;

import java.util.HashMap;

public class UserDatabase implements IUserDatabase{
    HashMap<String, User> database;

    public UserDatabase()
    {
        database = new HashMap<>();
    }

    public boolean addUser(User user) {
        if(database.containsKey(user.getUserID()))
            return false;
        else
            database.put(user.getUserID(), user);

        return true;
    }

    public boolean deleteUser(User user) {
        return database.remove(user.getUserID(), user);
    }

    public boolean updateUser(User user) {

        return database.replace(user.getUserID(), user) != null;
    }

    public User getUser(String userID) {
        return database.get(userID);
    }
}
