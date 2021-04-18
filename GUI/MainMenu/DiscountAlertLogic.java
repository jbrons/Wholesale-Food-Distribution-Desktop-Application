package GUI.MainMenu;

import src.Vendor.Vendor;
import src.Vendor.VendorDatabase;

import java.time.LocalDate;
import java.util.ArrayList;

public class DiscountAlertLogic implements IDiscountAlertLogic{
    public ArrayList<Vendor> getCurrentDiscounts()
    {
        VendorDatabase vendorList = VendorDatabase.getInstance();
        ArrayList<Vendor> currentDiscounts = new ArrayList<>();

        if(!vendorList.isEmpty())
        {
            for(int i = 0; i < vendorList.size(); i++)
            {
                Vendor vendor = vendorList.getVendor(i);
                LocalDate discountStartDate = vendor.getSeasonalDiscDate();
                LocalDate today = LocalDate.now();

                if(today.isAfter(discountStartDate) || today.isEqual(discountStartDate))
                    currentDiscounts.add(vendor);
            }
        }

        return  currentDiscounts;
    }
}
