package src.PurchaseOrder;

import GUI.PurchaseOrderManagement.PurchaseOrderGUI;
import src.Invoice.Invoice;
import src.Item.Item;
import src.Vendor.VendorDatabase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

public class PurchaseOrder {
    /* HashMap<Need by Date and Quantity, Item> */
    private HashMap<PurchaseOrderDetails, Item> items = new HashMap<>();
    private static VendorDatabase vendorDatabase = VendorDatabase.getInstance();
    private int purchaseID;
    private int vendorID;
    private double totalCost;
    private static int idCount;
    private static int maxIDSize = 1000000;
    private static int minimumItems = 1;
    private static int maximumItems = 5;

    public PurchaseOrder() {
        setPurchaseID();
        updateBalance();

       // items.put(2, new Item(2, 1, "potato", 12,
       //         "sweet potato", "12/12/2020", 3, "pound", 8));
    }

    public boolean addItem(PurchaseOrderDetails details, Item item) {
        if (items.size() < 5) {
            items.put(details, getItemDetails(item));
            return true;
        }
        return false;
    }
    public boolean containsItem(int itemId) {
        for (Map.Entry<PurchaseOrderDetails, Item> entry : items.entrySet()) {
            if (itemId == entry.getValue().getId()) {
                return true;
            }
        }

        return false;
    }

    private void setPurchaseID() {
        if (idCount < maxIDSize) {
            purchaseID = idCount++;
        } else {
            idCount = 1;
            purchaseID = findNewPurchaseID();
        }
    }

    private int findNewPurchaseID() {
        while (items.containsKey(idCount)) {
            ++idCount;
        }
        if (idCount < maxIDSize) {
            return idCount;
        } else {
            return -1;
        }
    }

    public boolean isFull() {
        return true;
    }

    private void updateBalance() {
        double newBalance = vendorDatabase.getVendor(vendorID).getBalance() - totalCost;
        vendorDatabase.getVendor(vendorID).setBalance(newBalance);
    }

    public Item getItemDetails(Item item) {
        return new Item(
                item.getId(),
                item.getVendorId(),
                item.getName(),
                item.getSellingPrice(),
                item.getCategory(),
                item.getExpirationDate(),
                item.getPurchasePrice(),
                item.getUnit(),
                item.getQuantity());
    }
}
