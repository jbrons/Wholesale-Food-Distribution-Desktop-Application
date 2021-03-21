package GUI;

import Application.Vendor;
import Application.VendorList;

import javax.swing.*;
import java.util.Vector;

public class DisplayList {

    private static DisplayList firstInstance = null;
    private DefaultListModel vendorsModel;

    private DisplayList() {
        vendorsModel = new DefaultListModel();
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

    public DefaultListModel getDisplayListModel() {
        return vendorsModel;
    }

    public void addVendor(Vendor vendor) {
        vendorsModel.addElement(vendor);
    }

    public void removeVendor(Vendor vendor) {
        vendorsModel.removeElement(vendor);
    }

    /*public void updateVendor(Vendor vendor) {
        vendorsModel.
    }*/
}
