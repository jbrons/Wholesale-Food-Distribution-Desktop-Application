package GUI.VendorManagement;

import src.Vendor.VendorDatabase;
import javax.swing.*;

// make singleton?

/**
 *  This class implements the DefaultListModel of the list that displays the search results to purchaser users.
 *  It stores and updates only one Vendor profile at a time in the list.
 *
 * @author Jordan Bronstetter
 * @date 03/18/2021
 *
 */
public class SearchModel implements IModel {
    private DefaultListModel<String> searchModel;

    public SearchModel() {
        searchModel = new DefaultListModel();
        VendorDatabase vendorDatabase = VendorDatabase.getInstance();
        searchModel.addElement(null);
    }

    public DefaultListModel<String> getDisplayListModel() {
        return searchModel;
    }

    public void updateVendor(String vendor, int index) {
        searchModel.setElementAt(vendor, index);
    }

    public boolean isEmpty() {
        return searchModel.isEmpty();
    }
}
