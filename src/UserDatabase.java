package src;

import java.util.HashMap;

public class UserDatabase implements IUserDatabase{

    private static UserDatabase databaseInstance = null;
    HashMap<String, User> database;

    private static User currentUser = null;

    private UserDatabase()
    {
        database = new HashMap<>();
        addUser(new User("Owner", "", "",
                "pass1234".toCharArray(), EnumUserRoles.OWNER));
    }

    public static UserDatabase getInstance()
    {
        if(databaseInstance == null)
            databaseInstance = new UserDatabase();

        return databaseInstance;
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

    public void setCurrentUser(User user)
    {
        currentUser = user;
    }
    public User getCurrentUser() { return currentUser; }
}
