package GUI.PurchaseOrderManagement;

import src.Item.Item;
import src.Item.ItemsDatabase;
import src.PurchaseOrder.PurchaseOrder;
import src.PurchaseOrder.PurchaseOrderDatabase;
import src.Vendor.DateValidator;
import src.Vendor.VendorDatabase;
import java.time.format.DateTimeFormatter;
import java.util.Vector;

/**
 *  PurchaseOrderHub handles the logic of PurchaseOrderHubGUI.
 *
 * @author Jordan Bronstetter
 * @date 04/08/2021
 *
 */
public class PurchaseOrderHub {
    private static VendorDatabase vendorDatabase = VendorDatabase.getInstance();
    private static Vector<Item> vendorItems = null;
    private static String searchBarPrompt = "Search by Vendor Name";

    public static int selectVendor(String name) {
        int vendorID = -1;

        if (name.equals("") || name.equals(searchBarPrompt)) {
            return vendorID;
        } else if (vendorDatabase.isEmpty()) {
            DialogDisplay.displayError("No Vendors available to select from");
            return vendorID;
        }

        int index = vendorDatabase.searchVendorDatabase(name);
        if (index > -1) {
            vendorID = vendorDatabase.getId(index);
        } else {
            DialogDisplay.displayError("No Vendors by the name of " + name);
        }
        return vendorID;
    }

    public static Vector<Item> filterItems(int vendorID) {
        DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("MM/dd/yyyy");
        DateValidator validator = new DateValidator(dateFormat);

        vendorItems = ItemsDatabase.getInstance().getItemsForVendor(vendorID);
        int i = 0;
        while (i < vendorItems.size()) if (validator.isPastDate(vendorItems.get(i).getExpirationDate())) {
            vendorItems.remove(i);
        } else ++i;
        return vendorItems;
    }

    public static void setUpVendorPO(PurchaseOrderModel purchaseOrderModel, int vendorID) {
        Vector<PurchaseOrder> vendorPOs = PurchaseOrderDatabase.getInstance().getPurchaseOrders(vendorID);
        purchaseOrderModel.updateModel(vendorPOs, purchaseOrderModel.size());
    }
}
