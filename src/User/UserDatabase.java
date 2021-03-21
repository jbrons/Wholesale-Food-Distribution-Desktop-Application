package src.User;

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

    public boolean updateUser(User oldUser, User newUser)
    {
        boolean wasDeleted = deleteUser(oldUser);
        boolean wasAdded = addUser(newUser);

        return wasDeleted && wasAdded;
    }

    public User getUser(String userID) {
        return database.get(userID);
    }

    public User[] getAllUsers()
    {
        return database.values().toArray(new User[0]);
    }

    public void setCurrentUser(User user)
    {
        currentUser = user;
    }
    public User getCurrentUser() { return currentUser; }
}
