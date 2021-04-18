package src.PurchaseOrder;

import GUI.PurchaseOrderManagement.PurchaseOrderGUI;
import src.Invoice.Invoice;
import src.Item.Item;
import src.Vendor.VendorDatabase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Vector;

public class PurchaseOrder {
    private HashMap<Integer, Item> items = new HashMap<>();
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
    }

    public boolean addItem(Item item) {
        if (items.size() < 5) {
            items.put(item.getId(), getItemDetails(item));
            return true;
        }
        return false;
    }

    public boolean containsItem(int itemId) {
        return items.containsKey(itemId);
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
