package GUI.MainWindow;

import GUI.Login.LoginGUI;

import javax.swing.*;

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
