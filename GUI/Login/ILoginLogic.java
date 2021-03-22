package GUI.Login;

/**
 * Interface for LoginLogic.java. This just provides a template for LoginLogic.java
 * and makes it much easier to see what LoginLogic.java actually does in an
 * abstract view.
 *
 * @author Jacob Price | ga4116
 */
public interface ILoginLogic {
    boolean validateLogin(String userID, char[] password);
}
