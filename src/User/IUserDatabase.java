package src.User;

/**
 * Interface for UserDatabase.java. This just provides a template for UserDatabase.java
 * and makes it much easier to see what UserDatabase.java actually does in an
 * abstract view.
 *
 * @author Jacob Price | ga4116
 */
public interface IUserDatabase {
    boolean addUser(User user);
    boolean deleteUser(User user);
    boolean updateUser(User oldUser, User newUser);
    User getUser(String userID);
    User[] getAllUsers();
    void setCurrentUser(User user);
    User getCurrentUser();
}
