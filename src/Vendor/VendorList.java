package src.Vendor;

import java.util.*;

public class VendorList {
    private static VendorList firstInstance = null;
    private Vector<Vendor> vendorList = null;

    private VendorList() {
        vendorList = new Vector<Vendor>();
    }

    public static VendorList getInstance() {
        if (firstInstance == null) {
            firstInstance = new VendorList();
        }
        return firstInstance;
    }

    public boolean addVendor(Vendor vendor) {
        if (getIndex(vendor.getId()) == -1) {
            vendorList.add(vendor);
            return true;
        }
        return false;
    }

    public boolean updateVendor(Vendor vendor) {
        vendorList.set(vendorList.indexOf(vendor), vendor);
        return true;
    }

    public boolean deleteVendor(int index) {
        if (vendorList.get(index).getBalance() == 0) {
            vendorList.removeElementAt(index);
            return true;
        }
        return false;
    }

    public String getVendorDetails(int index) {
        if (index > -1) {
            return vendorList.get(index).toString();
        }
        return null;
    }

    public String getVendorDetails(String name) {
        int index = searchVendorList(name);
        if (index > -1) {
            return vendorList.get(index).toString();
        }
        return null;
    }

    public Vector<String> getVendorListDetails() {
        Vector<String> vendors = new Vector<String>();
        for (Vendor vendor : vendorList) {
            vendors.add(vendor.toString());
        }
        return vendors;
    }

    public Vector<Integer> getIdList() {
        Vector<Integer> idList = new Vector<Integer>();
        for (Vendor vendor : vendorList) {
            idList.add(vendor.getId());
        }
        return idList;
    }

    public int getIndex(int id) {
        return searchVendorList(id);
    }

    public int getIndex(String name) {
        return searchVendorList(name);
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
            if (name.equals(vendor.getName())) {
                return vendorList.indexOf(vendor);
            }
        }
        return -1;
    }

    public Vendor getVendor(int index) {
        return vendorList.get(index);
    }
}
