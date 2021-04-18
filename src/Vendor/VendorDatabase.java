package src.Vendor;

import java.util.*;

/**
 *  This class implements a list to store and modify all the created Vendor profiles.
 *  It uses a singleton design patter so that all users share the same list of Vendors
 *
 * @author Jordan Bronstetter
 * @date 03/18/2021
 *
 */
public class VendorDatabase {
    private static VendorDatabase firstInstance = null;
    private Vector<Vendor> vendordatabase = null;

    private VendorDatabase() {
        vendordatabase = new Vector<Vendor>();
    }

    public static VendorDatabase getInstance() {
        if (firstInstance == null) {
            firstInstance = new VendorDatabase();
        }
        return firstInstance;
    }

    public boolean addVendor(Vendor vendor) {
        if (getIndex(vendor.getId()) == -1) {
            vendordatabase.add(vendor);
            return true;
        }
        return false;
    }

    public boolean updateVendor(Vendor vendor) {
        vendordatabase.set(vendordatabase.indexOf(vendor), vendor);
        return true;
    }

    public boolean deleteVendor(int index) {
        if (vendordatabase.get(index).getBalance() == 0) {
            vendordatabase.removeElementAt(index);
            return true;
        }
        return false;
    }

    public String getVendorDetails(int index) {
        if (index > -1) {
            return vendordatabase.get(index).toString();
        }
        return null;
    }

    public String getVendorDetails(String name) {
        int index = searchVendorDatabase(name);
        if (index > -1) {
            return vendordatabase.get(index).toString();
        }
        return null;
    }

    public Vector<String> getVendorListDetails() {
        Vector<String> vendors = new Vector<String>();
        for (Vendor vendor : vendordatabase) {
            vendors.add(vendor.toString());
        }
        return vendors;
    }

    public Vector<Integer> getIdList() {
        Vector<Integer> idList = new Vector<Integer>();
        for (Vendor vendor : vendordatabase) {
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

    public int searchVendorDatabase(int id) {
        for (Vendor vendor : vendordatabase) {
            if (id == vendor.getId()) {
                return vendordatabase.indexOf(vendor);
            }
        }
        return -1;
    }

    public int searchVendorDatabase(String name) {
        for (Vendor vendor : vendordatabase) {
            if (name.equals(vendor.getName())) {
                return vendordatabase.indexOf(vendor);
            }
        }
        return -1;
    }

    public Vendor getVendor(int index) {
        return vendordatabase.get(index);
    }

    public boolean isEmpty() {return vendordatabase.isEmpty();}
    public int size() { return vendordatabase.size();}
}
