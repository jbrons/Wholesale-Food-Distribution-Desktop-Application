package src.PurchaseOrder;

import GUI.PurchaseOrderManagement.*;
import src.User.EnumUserRoles;
import src.User.UserDatabase;

/**
 *  This class implements the Vendor profile for the owner
 *  and purchaser users to create, update, and delete Vendors
 *
 * @author Jordan Bronstetter
 * @date 04/06/2021
 *
 */
public class ItemsAlert  {
    private static ItemsStock stock = ItemsStock.getInstance();
    private static int outOfStock = 0;
    private static UserDatabase database = UserDatabase.getInstance();

    // private ItemsAlert() {}

    public static void alertStock(int id, double quantity) {
        if (database.getCurrentUser().getRole() != EnumUserRoles.PURCHASER) {
            return;
        }

        /* the item has been accounted for already */
        if (stock.isInInventory(id)) {
            if (stock.getQuantity(id) == quantity) {
                return;
            } else if (stock.getQuantity(id) == 0) {
                inStock();
                stock.deleteQuantity(id);
                return;
            }
        }
        if (quantity == 0) {
            outOfStock();
            stock.setQuantity(id, quantity);

            if (outOfStock > 2) {
                DialogDisplay.displayStockAlert("More than two items are out of Stock.");
                outOfStock = 0;
            }
        }
    }

    public static void alertStock() {
        if (database.getCurrentUser().getRole() != EnumUserRoles.PURCHASER) {
            return;
        }
        if (outOfStock > 2) {
            DialogDisplay.displayStockAlert("More than two items are out of Stock.");
            outOfStock = 0;
        }
    }

    private static void inStock() {
            --outOfStock;
    }

    private static void outOfStock() {
            ++outOfStock;
    }
}
