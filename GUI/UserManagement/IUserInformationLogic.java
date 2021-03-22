package GUI.UserManagement;

import src.User.User;


/**
 * Interface for UserInformationLogic.java. This just provides a template for UserInformationLogic.java
 * and makes it much easier to see what UserInformationLogic.java actually does in an
 * abstract view.
 *
 * @author Jacob Price | ga4116
 */
public interface IUserInformationLogic {
    boolean formValidated(User newUser);
}
