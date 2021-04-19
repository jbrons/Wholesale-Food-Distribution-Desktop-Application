package GUI.PurchaseOrderManagement;

import src.Item.Item;
import src.PurchaseOrder.PurchaseOrder;
import src.Vendor.VendorDatabase;

import javax.swing.*;
import java.util.Vector;

/**
 *  This class implements the Vendor profile for the owner
 *  and purchaser users to create, update, and delete Vendors
 *
 * @author Jordan Bronstetter
 * @date 04/06/2021
 *
 */
public class PurchaseOrderLogic {
    private static VendorDatabase vendorDatabase = VendorDatabase.getInstance();
    private static String searchBarPrompt = "Search by Vendor Name";

    private static ItemModel itemModel = new ItemModel();
    private static Vector<Item> vendorItems = null;
    private static Vector<PurchaseOrder> vendorPOs = null;

    private static String searchLabel = "Search for Vendor:";
    private static String selectedLabel = "Selected Vendor:";

    public PurchaseOrderLogic() {

    }

    public static int cancelPurchaseOrder() {
        return DialogDisplay.displayQuestion("Discard Current Purchase Order?",
                "Cancel Purchase Order", new Object[]{"Confirm", "Cancel"});
    }

    public static int selectVendor(String name) {
        int index = vendorDatabase.searchVendorDatabase(name);
        int vendorID = -1;
        if (!(name.equals("") || name.equals(searchBarPrompt)) && index > -1) {
            vendorID = vendorDatabase.getId(index);
        } else if (vendorDatabase.isEmpty()) {
            DialogDisplay.displayError("No Vendors are available to select from");
        } else {
            DialogDisplay.displayError("No Vendors by the name of " + name);
        }
        return vendorID;
    }

    public static boolean deselectVendor() {
        int choice = PurchaseOrderLogic.cancelPurchaseOrder();
        if (choice == JOptionPane.YES_OPTION) {
            itemModel.clearModel();
            return true;
        }
        return false;
    }
}
