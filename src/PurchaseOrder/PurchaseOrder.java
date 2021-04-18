package src.PurchaseOrder;

import src.Item.Item;
import src.Vendor.VendorDatabase;

import java.util.ArrayList;
import java.util.Vector;

public class PurchaseOrder {
    private Vector<Item> itemsList = new Vector<>();
    private static ArrayList<Integer> idList;
    VendorDatabase vendorDatabase = VendorDatabase.getInstance();
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

    public void addItem(Item item) {
        if (itemsList.size() < 5) {
            itemsList.add(getItemDetails(item));
        } else {
            // complain
        }
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
        while (idList.contains(idCount)) {
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
