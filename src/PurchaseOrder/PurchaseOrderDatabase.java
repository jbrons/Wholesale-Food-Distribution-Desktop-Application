package src.PurchaseOrder;

import java.util.HashMap;
import java.util.Vector;
import java.util.stream.IntStream;

/**
 *  PurchaseOrderDatabase implements a database to store and handle all the Purchase Orders,
 *  mapping them to their respective Vendor ID.
 *  It implements a singleton design pattern so that all purchaser users share the same database.
 *
 * @author Jordan Bronstetter
 * @date 04/06/2021
 *
 */
public class PurchaseOrderDatabase {
    private static PurchaseOrderDatabase firstInstance = null;
    /* HashMap<VendorID, PurchaseOrders> */
    private HashMap<Integer, Vector<PurchaseOrder>> purchaseOrders;

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
        Vector<PurchaseOrder> newPOs;
        if (purchaseOrders.get(vendorId) == null) {
            newPOs = new Vector<>();
        } else {
            newPOs = purchaseOrders.get(vendorId);
        }

        newPOs.add(purchaseOrder);
        purchaseOrders.put(vendorId, newPOs);
        purchaseOrder.updateBalance(vendorId);
    }

    public Vector<PurchaseOrder> getPurchaseOrders(int vendorId) {
        if (containsVendor(vendorId)) {
          return purchaseOrders.get(vendorId);
        }
        return new Vector<PurchaseOrder>();
    }

    public boolean deletePurchaseOrders(int vendorId) {
        if (containsVendor(vendorId)) {
           purchaseOrders.remove(vendorId);
           return true;
        }
        return false;
    }

    public boolean containsItem(int itemId, int vendorId) {
        if (containsVendor(vendorId)) {
            Vector<PurchaseOrder> vendorPOs = purchaseOrders.get(vendorId);
            return IntStream.range(0, vendorPOs.size()).anyMatch(i -> vendorPOs.get(i).containsItem(itemId));
        }
        return false;
    }

    public boolean containsVendor(int vendorId){
        return purchaseOrders.containsKey(vendorId);
    }
}
