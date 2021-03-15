package GUI;

import javax.swing.*;

public class MainWindow {

    // Provide this class with methods to change window (setWindow etc)

    private JPanel rootPanel;

    public MainWindow()
    {
        JFrame rootFrame = new JFrame("CSC4110 Term Project");
        rootFrame.setContentPane(new Login().getPanel());
        rootFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        rootFrame.pack();
        rootFrame.setLocationRelativeTo(null);
        rootFrame.setVisible(true);
    }
}
