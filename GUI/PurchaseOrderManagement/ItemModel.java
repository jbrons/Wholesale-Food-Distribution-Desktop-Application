package GUI.PurchaseOrderManagement;

import GUI.VendorManagement.Model;
import GUI.VendorManagement.VendorModel;
import src.Item.Item;
import src.Item.ItemsDatabase;
import src.Vendor.VendorDatabase;

import javax.swing.*;
import java.util.Vector;

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
