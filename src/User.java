package src;

public class User implements IUser{
    private String userID;
    private String firstName;
    private String lastName;
    private String password;
    private EnumUserRoles role;

    public User(String userID, String firstName, String lastName,
                String password, EnumUserRoles role)
    {
        this.userID = userID;
        this.firstName = firstName;
        this.lastName = lastName;
        this.password = password;
        this.role = role;
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

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword() {
        return password;
    }

    public void setRole(EnumUserRoles role) {
        this.role = role;
    }

    public EnumUserRoles getRole() {
        return role;
    }
}
