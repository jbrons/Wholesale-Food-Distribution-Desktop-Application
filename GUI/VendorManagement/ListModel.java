package GUI.VendorManagement;

import src.Vendor.Vendor;
import src.Vendor.VendorList;

import javax.swing.*;

/**
 *
 */
public class ListModel {
    private static ListModel firstInstance = null;
    private DefaultListModel<String> listModel;

    private ListModel() {
        listModel = new DefaultListModel();
        VendorList vendorList = VendorList.getInstance();

        for (String vendor : vendorList.getVendorListDetails()) {
            listModel.addElement(vendor);
        }
    }

    public static ListModel getInstance() {
        if (firstInstance == null) {
            firstInstance = new ListModel();
        }
        return firstInstance;
    }

    public DefaultListModel<String> getDisplayListModel() {
        return listModel;
    }

    public void addVendor(String vendor) {
        listModel.addElement(vendor);
    }

    public void updateVendor(String vendor, int index) {
        listModel.setElementAt(vendor, index);
    }

    public void removeVendor(int index) {
        listModel.remove(index);
    }

    public boolean isEmpty() {
       return listModel.isEmpty();
    }
}
