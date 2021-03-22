package src.User;

/**
 * This class handles all the data for the various users in the system.
 * User makes use of EnumUserRoles.java for setting roles and dealing with
 * any checks related to the roles.
 *
 * @author Jacob Price | ga4116
 */
public class User implements IUser{
    private String userID;
    private String firstName;
    private String lastName;
    private char[] password;
    private EnumUserRoles role;
    boolean firstLogin = true;

    public User(String userID, String firstName, String lastName,
                char[] password, EnumUserRoles role)
    {
        this.userID = userID;
        this.firstName = firstName;
        this.lastName = lastName;
        this.password = password;
        this.role = role;
    }

    public User(String userID, String firstName, String lastName,
                char[] password, EnumUserRoles role, boolean firstLogin)
    {
        this.userID = userID;
        this.firstName = firstName;
        this.lastName = lastName;
        this.password = password;
        this.role = role;
        this.firstLogin = firstLogin;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getUserID() {
        return userID;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setName(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public String getName() {
        return firstName.concat(" " + lastName);
    }

    public void setPassword(char[] password) {
        this.password = password;
    }

    public char[] getPassword() {
        return password;
    }

    public void setRole(EnumUserRoles role) {
        this.role = role;
    }

    public EnumUserRoles getRole() {
        return role;
    }

    public int getPermissionLevel()
    {
        return role.getPermissionLevel();
    }

    public boolean getFirstLogin() { return firstLogin; }
    public void setFirstLogin(boolean firstLogin) { this.firstLogin = firstLogin; }

    @Override
    public String toString()
    {
        return "UserID: " + userID
                + " First Name: " + firstName
                + " Last Name: "+ lastName
                + " Role: " + role;
    }
}
