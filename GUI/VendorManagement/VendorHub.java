package GUI.VendorManagement;

import GUI.PurchaseOrderManagement.DialogDisplay;
import src.PurchaseOrder.PurchaseOrderDatabase;
import src.Vendor.VendorDatabase;

import javax.swing.*;

/**
 *  VendorHub handles the logic of VendorHubGUI.
 *
 * @author Jordan Bronstetter
 * @date 04/08/2021
 *
 */
public class VendorHub {
    private static VendorDatabase vendordatabase = VendorDatabase.getInstance();

    public static int getSearchResults(String input) {
        int index = -1;
        int option = DialogDisplay.displayQuestion("Search by:",
                "Search Options", new Object[] {"ID", "Full Name"});

        if (option == JOptionPane.YES_OPTION) {
            index = getIndex(input);
        } else if (option == JOptionPane.NO_OPTION) {
            index = vendordatabase.getIndex(input);
        }
        return index;
    }

    private static int getIndex(String input) {
        int idLength = 6;
        int index = -1;
        if (input.length() <= idLength) {
            try {
                int vendorId = Integer.parseInt(input);
                index = vendordatabase.getIndex(vendorId);
            } catch (NumberFormatException ex) {
                DialogDisplay.displayError("Not a valid ID.");
            }
        } else {
            DialogDisplay.displayError("Not a valid ID.");
        }
        return index;
    }

    public static boolean deleteVendor(int index) {
        if (index < 0) {
            DialogDisplay.displayMessage("Please select a Vendor to delete");
            return false;
        }
        if (vendordatabase.canDelete(index)) {
            int vendorId = vendordatabase.getVendor(index).getId();
            if (PurchaseOrderDatabase.getInstance().containsVendor(vendorId)) {
                if (!deleteVendorPO()) {
                    return false;
                }
                PurchaseOrderDatabase.getInstance().
                        deletePurchaseOrders(vendordatabase.getVendor(index).getId());
            }
            vendordatabase.deleteVendor(index);
            VendorModel.getInstance().remove(index);
            DialogDisplay.displayMessage("Vendor removed.");
            return true;
        } else {
            DialogDisplay.displayError("Can only delete when balance = 0.");
        }
        return false;
    }

    private static boolean deleteVendorPO() {
        int option = DialogDisplay.displayQuestion("All associated purchase orders will be deleted.",
                "Warning", new Object[]{"Confirm", "Cancel"});

        if (option == JOptionPane.YES_OPTION) {
            return true;
        } else {
            return false;
        }
    }
}
