package src.Vendor;

import java.util.*;

public class VendorList {
    private static VendorList firstInstance = null;
    private Vector<Vendor> vendorList = null;

    private VendorList() {
        vendorList = new Vector <Vendor>();
    }

    public static VendorList getInstance() {
        if (firstInstance == null) {
            firstInstance = new VendorList();
        }
        return firstInstance;
    }

    public boolean addVendor(Vendor vendor) {
        if (searchVendorList(vendor.getName()) == -1) {
            vendorList.add(vendor);
            return true;
        }
        return false;
    }

    public boolean updateVendor(Vendor vendor) {
        // how to implement?
        return true;
    }

    public boolean deleteVendor(Vendor vendor) {
        if (vendor.getBalance() == 0) {
            vendorList.remove(vendor);
            return true;
        }
        return false;
    }

    public Vendor getVendor(int id) {
        if (searchVendorList(id) > -1) {
            return vendorList.get(id - 1);
        }
        return null;
    }

    public Vector<Vendor> getVendorList() {
        return vendorList;
    }

    public Vector<Integer> getIdList() {
        Vector<Integer> idList = new Vector<Integer>();
        for (Vendor vendor : vendorList) {
            idList.add(vendor.getId());
        }
        return idList;
    }

    public int searchVendorList(int id) {
        for (Vendor vendor : vendorList) {
            if (id == vendor.getId()) {
                return vendorList.indexOf(vendor);
            }
        }
        return -1;
    }

    public int searchVendorList(String name) {
        for (Vendor vendor : vendorList) {
            if (name == vendor.getName()) {
                return vendorList.indexOf(vendor);
            }
        }
        return -1;
    }

    @Override
    public String toString() {
        String list = null;
        for (Vendor vendor : vendorList) {
            list += vendor + "\n";
        }
        return list;
    }
    // toString
}
