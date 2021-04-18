package GUI.VendorManagement;

import src.Vendor.VendorDatabase;
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
public class VendorModel extends Model {
    private static VendorModel firstInstance = null;

    private VendorModel() {
        super();
        VendorDatabase vendorDatabase = VendorDatabase.getInstance();

        for (String vendor : vendorDatabase.getVendorDatabaseDetails()) {
            add(vendor);
        }
    }

    public static VendorModel getInstance() {
        if (firstInstance == null) {
            firstInstance = new VendorModel();
        }
        return firstInstance;
    }

    public void remove(int index) {
        getDisplayListModel().remove(index);
    }
}
