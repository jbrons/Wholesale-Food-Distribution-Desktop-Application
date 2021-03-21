package GUI.VendorManagement;

import src.Vendor.Vendor;
import src.Vendor.VendorList;

import javax.swing.*;

public class DisplayList {

    private static DisplayList firstInstance = null;
    private DefaultListModel<Vendor> vendorsModel;

    private DisplayList() {
        vendorsModel = new DefaultListModel<>();
        VendorList vendorList = VendorList.getInstance();

        for (Vendor vendor : vendorList.getVendorList()) {
            vendorsModel.addElement(vendor);
        }
    }

    public static DisplayList getInstance() {
        if (firstInstance == null) {
            firstInstance = new DisplayList();
        }
        return firstInstance;
    }

    public DefaultListModel<Vendor> getDisplayListModel() {
        return vendorsModel;
    }

    public void addVendor(Vendor vendor) {
        vendorsModel.addElement(vendor);
    }

    public void updateVendor(Vendor vendor, int index) {
        vendorsModel.setElementAt(vendor, index);
    }
    public void removeVendor(Vendor vendor) {
        vendorsModel.removeElement(vendor);
    }


    /*public void updateVendor(Vendor vendor) {
        vendorsModel.
    }*/
}
