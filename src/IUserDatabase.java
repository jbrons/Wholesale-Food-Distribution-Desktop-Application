package src;

public interface IUserDatabase {
    boolean addUser(User user);
    boolean deleteUser(User user);
    boolean updateUser(User oldUser, User newUser);
    User getUser(String userID);
    User[] getAllUsers();
    void setCurrentUser(User user);
    User getCurrentUser();
}
