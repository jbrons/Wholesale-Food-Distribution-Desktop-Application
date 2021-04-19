package src.PurchaseOrder;

import src.Item.Item;
import src.Vendor.Vendor;
import src.Vendor.VendorDatabase;

import java.util.HashMap;
import java.util.Vector;

public class PurchaseOrderDatabase {
    private static PurchaseOrderDatabase firstInstance = null;
    private HashMap<Integer, PurchaseOrder> purchaseOrders;
    private static VendorDatabase vendorDatabase = VendorDatabase.getInstance();

    private PurchaseOrderDatabase() {
        purchaseOrders = new HashMap<>();
        
        purchaseOrders.put(1, new PurchaseOrder());
    }

    public static PurchaseOrderDatabase getInstance() {
        if (firstInstance == null) {
            firstInstance = new PurchaseOrderDatabase();
        }
        return firstInstance;
    }

    public boolean containsItem(int itemId, int vendorId) {
        if (purchaseOrders.containsKey(vendorId)) {
            return purchaseOrders.get(vendorId).containsItem(itemId);
        }
        return false;
    }
}
