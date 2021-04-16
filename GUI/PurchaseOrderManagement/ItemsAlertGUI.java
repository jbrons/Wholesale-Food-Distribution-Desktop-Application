package GUI.PurchaseOrderManagement;

import javax.swing.*;

public class ItemsAlertGUI {
    public static void displayAlert(String message) {
        JOptionPane.showMessageDialog(JOptionPane.getRootFrame(), message, "Stock Alert", JOptionPane.INFORMATION_MESSAGE);
    }
}
