package src;

import java.util.HashMap;

public interface IUserDatabase {
    boolean addUser(User user);
    boolean deleteUser(User user);
    boolean updateUser(User user);
    User getUser(String userID);
    // HashMap<String, User> getDatabase();
}
