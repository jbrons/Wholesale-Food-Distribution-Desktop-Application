package src.PurchaseOrder;

import src.Item.Items;
import src.User.EnumUserRoles;
import src.User.User;
import src.User.UserDatabase;

import java.util.HashMap;

public class ItemsAlert extends Items {
    private static ItemsAlert alertInstance = null;
    private HashMap<Integer, Integer> stock;
    private int outOfStock = 0;

    private ItemsAlert()
    {
        stock = new HashMap<>();
    }

    public static ItemsAlert getInstance() {
        if (alertInstance == null) {
            alertInstance = new ItemsAlert();
        }
        return alertInstance;
    }

    public void updateStock(int quantity, int id) {
        // the item has been accounted for already
        if (quantity == 0 && stock.containsKey(id)) {
            if (stock.get(id) == quantity) {
                /* do nothing */
            } else if (stock.get(id) == 0) {
                decrementOutOfStock();
                setItem(id, quantity);
            } else if (quantity == 0) {
                ++outOfStock;
                setItem(id, quantity);
            }
        } else {
            setItem(id, quantity);
        }
    }

    private void decrementOutOfStock() {
        if (outOfStock > 0) {
            --outOfStock;
        }
    }

    private void incrementOutOfStock() {
        if (outOfStock < 2) {
            ++outOfStock;
        }
    }

    public void setItem(int id, int quantity) {
        stock.put(id, quantity);
    }

    public boolean deleteItem(User user) {
        return stock.remove(user.getUserID(), user);
    }
}
