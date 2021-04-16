package GUI.VendorManagement;

import src.Vendor.VendorList;

import javax.swing.*;

public interface IModel {
    public DefaultListModel<String> getDisplayListModel();

    public void updateVendor(String vendor, int index);

    public boolean isEmpty();
}
