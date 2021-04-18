package GUI.PurchaseOrderManagement;

import GUI.VendorManagement.Model;
import GUI.VendorManagement.VendorModel;
import src.Item.ItemsDatabase;
import src.Vendor.VendorDatabase;

import javax.swing.*;

public class ItemModel extends Model {
    private DefaultListModel<String> model;

    public ItemModel(Item items) {
        model = new DefaultListModel();


        for (String vendor : vendorDatabase.getVendorDatabaseDetails()) {
            listModel.addElement(vendor);
        }

        for (String item : items.getItemsForVendor()) {
            model.addElement(vendor);
        }
    }

    public int getI
}
