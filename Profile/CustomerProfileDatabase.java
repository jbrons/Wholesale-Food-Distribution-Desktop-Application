package Profile;

import java.util.HashMap;

public class CustomerProfileDatabase {

    private static CustomerProfileDatabase databaseInstance = null;
    HashMap<Integer, CustomerProfile> database;

    private CustomerProfileDatabase()
    {
        database = new HashMap<>();
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
        return database.values().toArray(new CustomerProfile[0]);
    }

}
