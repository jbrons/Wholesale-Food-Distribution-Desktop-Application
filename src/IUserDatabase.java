package src;

public interface IUserDatabase {
    boolean addUser(User user);
    boolean deleteUser(User user);
    boolean updateUser(User user);
    User getUser(String userID);
}
