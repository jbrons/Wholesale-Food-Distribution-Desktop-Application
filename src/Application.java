package src;

// This application, potentially temporary, will handle the main
// stuff for this program (descriptive, I know).

/*
    Handles the switching between GUI's / is the mediator between all?
 */

import GUI.MainWindow.MainWindowGUI;
import src.User.UserDatabase;

public class Application {

    public static void main(String[] args) {
        // Initialized the database
        UserDatabase database = UserDatabase.getInstance();
        MainWindowGUI mainWindowGUI = MainWindowGUI.getInstance();
    }
}
