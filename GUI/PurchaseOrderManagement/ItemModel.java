package GUI.PurchaseOrderManagement;

import GUI.VendorManagement.Model;
import src.Item.Item;
import java.util.Vector;

/**
 *  ItemModel extends Model and implements a DefaultListModel,
 *  focusing on initializing and updating values in the model.
 *
 * @author Jordan Bronstetter
 * @date 04/11/2021
 *
 */
public class ItemModel extends Model {
    public void initializeModel(Vector<Item> items) {
        if (!getDisplayListModel().isEmpty()) {
            clearModel();
        }

        for (Item item : items) {
            add(item.toString());
        }
    }
}
