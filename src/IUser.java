package src;

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
