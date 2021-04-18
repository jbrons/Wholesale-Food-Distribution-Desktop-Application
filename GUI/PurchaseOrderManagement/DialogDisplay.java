package GUI.PurchaseOrderManagement;

import javax.swing.*;

public class DialogDisplay {
    public static void displayStockAlert(String message) {
        JOptionPane.showMessageDialog(JOptionPane.getRootFrame(), message, "Stock Alert", JOptionPane.WARNING_MESSAGE);
    }

    public static void displayError(String message) {
        JOptionPane.showMessageDialog(JOptionPane.getRootFrame(), message, "Error", JOptionPane.ERROR_MESSAGE);
    }

    public static void displayMessage(String message) {
        JOptionPane.showMessageDialog(JOptionPane.getRootFrame(), message, "Message", JOptionPane.INFORMATION_MESSAGE);
    }

    public static int displayQuestion(String message, String title, Object[] options) {
        return JOptionPane.showOptionDialog(JOptionPane.getRootFrame(), message, title,
                JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
    }

}