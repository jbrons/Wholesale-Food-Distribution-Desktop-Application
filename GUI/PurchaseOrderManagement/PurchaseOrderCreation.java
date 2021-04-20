package GUI.PurchaseOrderManagement;

import javax.swing.*;

/**
 *  PurchaseOrderCreation handles the logic of PurchaseOrderCreationGUI.
 *
 * @author Jordan Bronstetter
 * @date 04/06/2021
 *
 */
public class PurchaseOrderCreation {
    private static ItemModel itemModel = new ItemModel();

    public static int cancelPurchaseOrder() {
        return DialogDisplay.displayQuestion("Discard Current Purchase Order?",
                "Cancel Purchase Order", new Object[]{"Confirm", "Cancel"});
    }

    public static boolean deselectVendor() {
        int choice = cancelPurchaseOrder();

        if (choice == JOptionPane.YES_OPTION) {
            itemModel.clearModel();
            return true;
        }
        return false;
    }

}
