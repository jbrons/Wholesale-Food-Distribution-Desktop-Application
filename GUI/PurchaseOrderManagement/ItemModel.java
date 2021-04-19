package GUI.PurchaseOrderManagement;

import GUI.VendorManagement.Model;
import GUI.VendorManagement.VendorModel;
import src.Item.Item;
import src.Item.ItemsDatabase;
import src.Vendor.VendorDatabase;

import javax.swing.*;
import java.util.Vector;

/**
 *  This class implements the Vendor profile for the owner
 *  and purchaser users to create, update, and delete Vendors
 *
 * @author Jordan Bronstetter
 * @date 04/06/2021
 *
 */
public class ItemModel extends Model {
    public void updateModel(Vector<Item> items) {
        if (!getDisplayListModel().isEmpty()) {
            clearModel();
        }
        for (Item item : items) {
            add(item.toString());
        }
    }
}
