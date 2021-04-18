package src.PurchaseOrder;

import GUI.PurchaseOrderManagement.*;
import src.User.EnumUserRoles;
import src.User.UserDatabase;

import javax.swing.*;

public class ItemsAlert  {
    private static ItemsStock stock = ItemsStock.getInstance();
    private static int outOfStock = 0;  /* may hold value between 0-3 */
    private static UserDatabase database = UserDatabase.getInstance();

    // private ItemsAlert() {}

    public static void alertStock(int id, double quantity) {
        if (database.getCurrentUser().getRole() != EnumUserRoles.PURCHASER) {
            return;
        }

        // the item has been accounted for already
        if (stock.isInInventory(id)) {
            if (stock.getItem(id) == quantity) {
                return;
            } else if (stock.getItem(id) == 0) {
                decrementOutOfStock();
                stock.deleteItem(id);
                return;
            }
        }
        if (quantity == 0) {
            incrementOutOfStock();  // fix NAMESSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSS
            stock.setItem(id, quantity);

            if (outOfStock == 3) {
                ItemsAlertGUI.displayAlert("Two Items have gone out of Stock");
                outOfStock = 0;
            }
        }
    }

    private static void decrementOutOfStock() {  // rename to something less stupid
        if (outOfStock > 0) {
            --outOfStock;
        } else {
            // throw error
        }
    }

    private static void incrementOutOfStock() {
        if (outOfStock < 3) {
            ++outOfStock;
        }
    }
}
