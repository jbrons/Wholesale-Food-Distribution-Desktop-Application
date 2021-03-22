package src;

import GUI.MainWindow.MainWindowGUI;
import src.User.UserDatabase;

/**
 * Application.java just handles the main method and initializes the software to load
 * the login screen first. When executing this program with maven, this is the class
 * that will be used as the second parameter.
 *
 * This class also instantiates the database and the main window that is used during
 * the entire usage of this software.
 *
 * @author Jacob Price | ga4116
 */
public class Application {

    public static void main(String[] args) {
        // Initialized the database
        UserDatabase database = UserDatabase.getInstance();
        MainWindowGUI mainWindowGUI = MainWindowGUI.getInstance();
    }
}
