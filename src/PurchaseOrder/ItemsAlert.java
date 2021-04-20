package src.PurchaseOrder;

import GUI.PurchaseOrderManagement.*;
import src.User.EnumUserRoles;
import src.User.UserDatabase;

/**
 *  ItemsAlert alerts purchaser users when more than two items go out of stock.
 *  It stores out-of-stock items in ItemsStock and alerts using the alert dialog in DialogDisplay.
 *
 * @author Jordan Bronstetter
 * @date 04/06/2021
 *
 */
public class ItemsAlert  {
    private static ItemsStock stock = ItemsStock.getInstance();
    private static UserDatabase database = UserDatabase.getInstance();

    public static void alertStock(int id, double quantity) {
        /* the item has been accounted for already */
        if (stock.isInInventory(id)) {
            if (stock.getQuantity(id) == quantity) {
                return;
            } else if (stock.getQuantity(id) == 0) {
                stock.deleteQuantity(id);
                return;
            }
        }

        if (quantity == 0) {
            stock.setQuantity(id, quantity);
            if (database.getCurrentUser().getRole() == EnumUserRoles.PURCHASER && stock.size() > 2) {
                DialogDisplay.displayStockAlert("More than two items are out of Stock.");
            }
        }
    }

    public static void alertStock() {
        if (database.getCurrentUser().getRole() == EnumUserRoles.PURCHASER && stock.size() > 2) {
            DialogDisplay.displayStockAlert("More than two items are out of Stock.");
        }
    }
}
