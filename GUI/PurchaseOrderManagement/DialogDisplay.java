package GUI.PurchaseOrderManagement;

import javax.swing.*;

public class DialogDisplay {
    public static void displayStockAlert(String message) {
        JOptionPane.showMessageDialog(JOptionPane.getRootFrame(), message, "Stock Alert", JOptionPane.WARNING_MESSAGE);
    }

    public static void displayError(String message) {
        JOptionPane.showMessageDialog(JOptionPane.getRootFrame(), message, "Error", JOptionPane.ERROR_MESSAGE);
    }

    private void displayMessage(String message) {
        JOptionPane.showMessageDialog(JOptionPane.getRootFrame(), message, "Message", JOptionPane.INFORMATION_MESSAGE);
    }
}
