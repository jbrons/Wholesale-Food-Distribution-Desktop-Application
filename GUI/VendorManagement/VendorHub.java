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
    private static VendorDatabase vendorDatabase = VendorDatabase.getInstance();

    public static int getSearchResults(String input) {
        int index = -1;
        int option = DialogDisplay.displayQuestion("Search by:",
                "Search Options", new Object[] {"ID", "Full Name"});

        if (option == JOptionPane.YES_OPTION) {
            index = getIndex(input);
            if (index == -1) {
                DialogDisplay.displayError("Not a valid ID.");
            }
        } else if (option == JOptionPane.NO_OPTION) {
            index = vendorDatabase.getIndex(input);
            if (index == -1) {
                DialogDisplay.displayError("No Profile Vendor found.");
            }
        }
        return index;
    }

    private static int getIndex(String input) {
        int idLength = 6;
        int index = -1;
        if (input.length() <= idLength) {
            try {
                int vendorId = Integer.parseInt(input);
                index = vendorDatabase.getIndex(vendorId);
                return index;
            } catch (NumberFormatException ex) {}
        }
        return index;
    }

    public static boolean deleteVendor(int index) {
        if (index < 0) {
            DialogDisplay.displayMessage("Please select a Vendor to delete");
            return false;
        }
        if (vendorDatabase.canDelete(index)) {
            int vendorId = vendorDatabase.getVendor(index).getId();
            if (PurchaseOrderDatabase.getInstance().containsVendor(vendorId)) {
                if (!deleteVendorPO()) {
                    return false;
                }
                PurchaseOrderDatabase.getInstance().
                        deletePurchaseOrders(vendorDatabase.getVendor(index).getId());
            }
            vendorDatabase.deleteVendor(index);
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
