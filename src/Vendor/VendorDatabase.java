package src.Vendor;

import java.util.*;

/**
 *  VendorDatabase implements a list to store and modify all the created Vendor profiles.
 *  It uses a singleton design pattern so that all users share the same list of Vendors
 *
 * @author Jordan Bronstetter
 * @date 03/18/2021
 *
 */
public class VendorDatabase {
    private static VendorDatabase firstInstance = null;
    private Vector<Vendor> vendorDatabase = null;

    private VendorDatabase() {
        vendorDatabase = new Vector<Vendor>();
    }

    public static VendorDatabase getInstance() {
        if (firstInstance == null) {
            firstInstance = new VendorDatabase();
        }
        return firstInstance;
    }

    public boolean addVendor(Vendor vendor) {
        if (getIndex(vendor.getId()) == -1) {
            vendorDatabase.add(vendor);
            return true;
        }
        return false;
    }

    public boolean updateVendor(Vendor vendor) {
        vendorDatabase.set(vendorDatabase.indexOf(vendor), vendor);
        return true;
    }

    public boolean deleteVendor(int index) {
        if (vendorDatabase.get(index).getBalance() == 0) {
            vendorDatabase.removeElementAt(index);
            return true;
        }
        return false;
    }

    public String getVendorDetails(int index) {
        if (index > -1) {
            return vendorDatabase.get(index).toString();
        }
        return null;
    }

    public String getVendorDetails(String name) {
        int index = searchVendorDatabase(name);
        if (index > -1) {
            return vendorDatabase.get(index).toString();
        }
        return null;
    }

    public Vector<String> getVendorDatabaseDetails() {
        Vector<String> vendors = new Vector<String>();
        for (Vendor vendor : vendorDatabase) {
            vendors.add(vendor.toString());
        }
        return vendors;
    }

    public Vector<Integer> getIdList() {
        Vector<Integer> idList = new Vector<Integer>();
        for (Vendor vendor : vendorDatabase) {
            idList.add(vendor.getId());
        }
        return idList;
    }

    public int getIndex(int id) {
        return searchVendorDatabase(id);
    }

    public int getIndex(String name) {
        return searchVendorDatabase(name);
    }

    public int getId(int index) {
        return getVendor(index).getId();
    }

    public int searchVendorDatabase(int id) {
        for (Vendor vendor : vendorDatabase) {
            if (id == vendor.getId()) {
                return vendorDatabase.indexOf(vendor);
            }
        }
        return -1;
    }

    public int searchVendorDatabase(String name) {
        for (Vendor vendor : vendorDatabase) {
            if (name.equals(vendor.getName())) {
                return vendorDatabase.indexOf(vendor);
            }
        }
        return -1;
    }

    public Vendor getVendor(int index) {
        return vendorDatabase.get(index);
    }

    public boolean canDelete(int index) {
        return getVendor(index).getBalance() == 0;
    }

    public boolean isEmpty() {return vendorDatabase.isEmpty();}
    public int size() { return vendorDatabase.size();}
}
