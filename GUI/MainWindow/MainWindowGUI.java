package GUI.MainWindow;

import GUI.Login.LoginGUI;

import javax.swing.*;

/**
 * Handles the Main Window for this entire software. Allows for setting
 * this windows content pane with other GUI panels such that new frames
 * do not need to be created everytime the user is redirected to a different
 * page.
 *
 * @author Jacob Price | ga4116
 */
public class MainWindowGUI {

    private static MainWindowGUI mainWindowInstance = null;

    JFrame rootFrame;
    private JPanel rootPanel;

    private MainWindowGUI() {
        rootFrame = new JFrame("CSC4110 Term Project");
        rootFrame.setContentPane(new LoginGUI().getPanel());
        rootFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        rootFrame.pack();
        rootFrame.setLocationRelativeTo(null);
        rootFrame.setVisible(true);
    }

    public static MainWindowGUI getInstance()
    {
        if(mainWindowInstance == null)
            mainWindowInstance = new MainWindowGUI();

        return mainWindowInstance;
    }

    public void setJPanel(JPanel newPanel)
    {
        rootFrame.setContentPane(newPanel);
        rootFrame.pack();
        rootFrame.setVisible(true);
    }
}
