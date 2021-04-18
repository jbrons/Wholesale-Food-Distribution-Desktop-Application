package src.CustomerOrder;

import java.util.*;

public class CustomerOrderDatabase {
    /**
     * instance for singleton pattern
     */
    private static CustomerOrderDatabase databaseInstance = null;

    /**
     * map of id and customer order
     */
    HashMap<Integer, CustomerOrder> database;

    private CustomerOrderDatabase()
    {

        database = new HashMap<>();
    }

    /**
     * get instance of order db
     * @return
     */
    public static CustomerOrderDatabase getInstance()
    {
        if(databaseInstance == null)
            databaseInstance = new CustomerOrderDatabase();

        return databaseInstance;
    }

    /**
     * add new order to db
     * @param order new order
     * @return
     */
    public boolean addOrder(CustomerOrder order) {
        if(database.containsKey(order.getOrderID()))
            return false;
        else
            database.put(order.getOrderID(), order);

        return true;
    }

    /**
     * delete order from database
     * @param order
     * existing order
     * @return
     */
    public boolean deleteOrder(CustomerOrder order) {

        return database.remove(order.getOrderID(), order);
    }

    /**
     * get all orders
     * @return
     */
    public CustomerOrder[] getAllOrders()
    {
        List<CustomerOrder> list = new ArrayList<>(database.values());
        list.sort(Comparator.comparingInt(CustomerOrder::getOrderID));
        return list.toArray(new CustomerOrder[0]);
    }

    //I added the next two functions for me to use
    
    public CustomerOrder get(int index) {
        CustomerOrder order = database.get(index);
        return order;
    }
}
