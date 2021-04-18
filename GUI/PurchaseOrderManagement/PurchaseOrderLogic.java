package GUI.PurchaseOrderManagement;

import src.Vendor.Vendor;
import src.Vendor.VendorDatabase;

public class PurchaseOrderLogic {
    private static VendorDatabase vendorDatabase = VendorDatabase.getInstance();

    public static int searchForVendor(String name) {
        if (vendorDatabase.size() == 0) {
            return 0;
        }

        int index = vendorDatabase.getIndex(name);
        if (index < 0) {
            return -1;
        } else {
            return vendorDatabase.getId(index);
        }
    }
}
