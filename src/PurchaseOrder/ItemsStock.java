package src.PurchaseOrder;

import java.util.HashMap;

/**
 *  ItemsStock keeps inventory of all out-of-stock items.
 *  It implements a singleton design pattern so that all purchaser users share the same stock.
 *
 * @author Jordan Bronstetter
 * @date 04/06/2021
 *
 */
public class ItemsStock {
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

    public void setQuantity(int id, double quantity) {
       stock.put(id, quantity);
    }

    public double getQuantity(int id) {
       return stock.get(id);
    }

    // fix delete function
    public void deleteQuantity(int id) {
        stock.remove(id);
    }

    public boolean isInInventory(int id) {
        return stock.containsKey(id);
    }

    public int size() {
        return stock.size();
    }
}
