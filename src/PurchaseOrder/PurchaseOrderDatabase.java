package src.PurchaseOrder;

import src.Vendor.VendorDatabase;

import java.util.HashMap;
import java.util.Vector;

public class PurchaseOrderDatabase {
    private static PurchaseOrderDatabase firstInstance = null;
    /* HashMap<VendorID, PurchaseOrders> */
    private HashMap<Integer, Vector<PurchaseOrder>> purchaseOrders;
    private static VendorDatabase vendorDatabase = VendorDatabase.getInstance();

    private PurchaseOrderDatabase() {
        purchaseOrders = new HashMap<>();
        //Vector<PurchaseOrder> order = new
       // purchaseOrders.put(1, new Vector<PurchaseOrder> )
    }

    public static PurchaseOrderDatabase getInstance() {
        if (firstInstance == null) {
            firstInstance = new PurchaseOrderDatabase();
        }
        return firstInstance;
    }

    public void add(Integer vendorId, PurchaseOrder purchaseOrder) {
        Vector<PurchaseOrder> newOrders = purchaseOrders.get(vendorId);
        newOrders.add(purchaseOrder);
        purchaseOrders.put(vendorId, newOrders);
    }

    public boolean containsItem(int itemId, int vendorId) {
        if (purchaseOrders.containsKey(vendorId)) {

            Vector<PurchaseOrder> orders = purchaseOrders.get(vendorId);
            for (int i = 0; i < orders.size(); ++i) {
                if (orders.get(i).containsItem(itemId)) {
                    return true;
                }
            }
        }
        return false;
    }
}
