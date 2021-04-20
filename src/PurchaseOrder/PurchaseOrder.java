package src.PurchaseOrder;

import src.Item.Item;
import src.Vendor.VendorDatabase;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;

/**
 *  PurchaseOrder allows purchaser users to create purchase orders for a given Vendor
 *  Purchase Orders may contain 1-5 items listed by a given Vendor.
 *
 * @author Jordan Bronstetter
 * @date 04/06/2021
 *
 */
public class PurchaseOrder {
    /* HashMap<Need by Date and Quantity, Item> */
    private HashMap<PurchaseOrderDetails, Item> purchaseOrder = new HashMap<>();
    private static VendorDatabase vendorDatabase = VendorDatabase.getInstance();
    private int purchaseID;
    private double totalCost = 0;
    private static int idCount;
    private static int maxIDSize = 999999;
    private static int minimumItems = 1;
    private static int maximumItems = 5;

    public PurchaseOrder() {
        setPurchaseID();
    }

    public boolean addItem(PurchaseOrderDetails details, Item item) {
        if (purchaseOrder.size() < 5) {
            purchaseOrder.put(details, getItemDetails(item));
            calculateTotalCost(details.getSubtotalCost());
            return true;
        }
        return false;
    }
    public boolean containsItem(int itemId) {
        for (Map.Entry<PurchaseOrderDetails, Item> entry : purchaseOrder.entrySet()) {
            if (itemId == entry.getValue().getId()) {
                return true;
            }
        }
        return false;
    }

    private int getPurchaseID() {
        return purchaseID;
    }

    private void setPurchaseID() {
        if (idCount < maxIDSize) {
            purchaseID = ++idCount;
        } else {
            idCount = 1;
            purchaseID = findNewPurchaseID();
        }
    }

    private int findNewPurchaseID() {
        while (purchaseOrder.containsKey(idCount)) {
            ++idCount;
        }
        if (idCount < maxIDSize) {
            return idCount;
        } else {
            return -1;
        }
    }

    public boolean isFull() {
        return purchaseOrder.size() == maximumItems;
    }

    public boolean isAlmostFull() {
        return purchaseOrder.size() == maximumItems - 1;
    }

    public int size() {
        return purchaseOrder.size();
    }

    public boolean isEmpty() {
        return purchaseOrder.isEmpty();
    }

    public double getTotalCost() {
        return totalCost;
    }

    public void calculateTotalCost(double subCost) {
        totalCost += subCost;
    }

    public void updateBalance(int vendorId) {
        int index = vendorDatabase.getIndex(vendorId);
        double newBalance = vendorDatabase.getVendor(index).getBalance() + totalCost;
        vendorDatabase.getVendor(index).setBalance(newBalance);
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

    @Override
    public String toString() {
        DecimalFormat df = new DecimalFormat("$#.00");
        String details = "<html>";
        String format = "<br>&nbsp;&nbsp;&nbsp;";
        for (Map.Entry<PurchaseOrderDetails, Item> item : purchaseOrder.entrySet()) {
            details += "Item Name: " + item.getValue().getName() + format + item.getKey().toString();
        }
        details += "Total Cost: " + df.format(totalCost) + "</html>";
        return details;
    }
}
