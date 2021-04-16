package src.PurchaseOrder;

import java.util.HashMap;

public class ItemsStock{
    private static ItemsStock stockInstance = null;
    private HashMap<Integer, Double> stock;
    private int outOfStock = 0;

    private ItemsStock()
    {
        stock = new HashMap<>();
    }

    public static ItemsStock getInstance() {
        if (stockInstance == null) {
            stockInstance = new ItemsStock();
        }
        return stockInstance;
    }

    public void setItem(int id, double quantity) {
       stock.put(id, quantity);
    }

    public double getItem(int id) {
       return stock.get(id);
    }

    // fix delete function
    public void deleteItem(int id) {
        stock.remove(id);
    }

    public boolean isInInventory(int id) {
        return stock.containsKey(id);
    }
}
