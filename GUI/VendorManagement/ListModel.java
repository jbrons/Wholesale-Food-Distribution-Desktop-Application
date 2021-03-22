package GUI.VendorManagement;

import src.Vendor.VendorList;
import javax.swing.*;

/**
 *  This class implements the DefaultListModel of the list that displays all of the Vendor profiles to owner users.
 *  It handles the storing and modifying of any Vendor profiles for the list.
 *  It uses a singleton design patter so that all owner users share the same list of Vendors.
 *
 * @author Jordan Bronstetter
 * @date 03/18/2021
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
