/**
 * Customer profile database
 */
package src.Customer;


import src.CustomerOrder.CustomerOrder;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

public class CustomerProfileDatabase {

    private static CustomerProfileDatabase databaseInstance = null;
    HashMap<Integer, CustomerProfile> database;

    private CustomerProfileDatabase()
    {
        database = new HashMap<>();
        addProfile(new CompanyCustomerProfile("John Smith", "Lincoln", "New York",
                "New York", "123456789", 12, 10, "12/12/2020"));
        addProfile(new CompanyCustomerProfile("Jane Lin", "Walt", "Chicago",
                "Illinois", "123456789", 23, 13, "12/12/2020"));
    }

    public static CustomerProfileDatabase getInstance()
    {
        if(databaseInstance == null)
            databaseInstance = new CustomerProfileDatabase();

        return databaseInstance;
    }

    public boolean addProfile(CustomerProfile profile) {
        if(database.containsKey(profile.getCustomerID()))
            return false;
        else
            database.put(profile.getCustomerID(), profile);

        return true;
    }

    public boolean deleteProfile(CustomerProfile profile) {
        return database.remove(profile.getCustomerID(), profile);
    }

    public CustomerProfile[] getAllProfiles()
    {
        List<CustomerProfile> list = new ArrayList<>(database.values());
        list.sort(Comparator.comparingInt(CustomerProfile::getCustomerID));
        return list.toArray(new CustomerProfile[0]);
    }

    public CustomerProfile getProfile(int customerID) {
        return database.get(customerID);
    }
}
