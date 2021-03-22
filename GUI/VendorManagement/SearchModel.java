package GUI.VendorManagement;

import src.Vendor.Vendor;
import src.Vendor.VendorList;

import javax.swing.*;

/**
 *
 */
public class SearchModel {
    private static SearchModel firstInstance = null;
    private DefaultListModel<String> searchModel;

    private SearchModel() {
        searchModel = new DefaultListModel();
        VendorList vendorList = VendorList.getInstance();

        searchModel.addElement(null);
    }

    public static SearchModel getInstance() {
        if (firstInstance == null) {
            firstInstance = new SearchModel();
        }
        return firstInstance;
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
