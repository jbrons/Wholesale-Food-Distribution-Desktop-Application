package src.User;

/**
 * Interface for User.java. This just provides a template for User.java
 * and makes it much easier to see what User.java actually does in an
 * abstract view.
 *
 * @author Jacob Price | ga4116
 */
public interface IUser {

    void setUserID(String userID);
    String getUserID();

    void setFirstName(String firstName);
    String getFirstName();

    void setLastName(String lastName);
    String getLastName();

    void setName(String firstName, String lastName);
    String getName();

    void setPassword(char[] password);
    char[] getPassword();

    void setRole(EnumUserRoles role);
    EnumUserRoles getRole();

    int getPermissionLevel();
}
