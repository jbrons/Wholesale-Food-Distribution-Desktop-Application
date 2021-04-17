package GUI.CustomerOrderSearch;

import src.Customer.CustomerProfile;
import src.Customer.CustomerProfileDatabase;
import src.CustomerOrder.CustomerOrder;
import src.CustomerOrder.CustomerOrderDatabase;

import java.util.ArrayList;

public class CustomerOrderSearchLogic {
    CustomerOrderDatabase customerOrderDatabase;
    CustomerProfileDatabase customerProfileDatabase;

    public CustomerOrderSearchLogic()
    {
        customerOrderDatabase = CustomerOrderDatabase.getInstance();
        customerProfileDatabase =  CustomerProfileDatabase.getInstance();
    }

    public CustomerOrder[] getCustomerOrders(String customerName)
    {
        int customerID = -1;

        for (CustomerProfile customerProfile : customerProfileDatabase.getAllProfiles()) {
            if(customerProfile.getCustomerName().equals(customerName))
                customerID = customerProfile.getCustomerID();
        }

        if(customerID == -1)
            return null;

        ArrayList<CustomerOrder> customerOrderList = new ArrayList<>();

        for (CustomerOrder customerOrder : customerOrderDatabase.getAllOrders())
        {
            if(customerOrder.getCustomerID() == customerID)
                customerOrderList.add(customerOrder);
        }

        return customerOrderList.toArray(new CustomerOrder[customerOrderList.size()]);
    }
}
