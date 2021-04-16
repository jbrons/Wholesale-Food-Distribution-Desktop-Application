package src.CustomerOrder;

import src.Item.Items;

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
     * delete order from db
     * @param order existring order
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
    public int getIndex(String s){
        int i=0;
        List<CustomerOrder> list = new ArrayList<>(database.values());
        for (CustomerOrder order : list) {
            if(s.equals(order.toString())){
                return i;
            }
            i++;
        }
        return -1;
    }

    public CustomerOrder get(int index) {
        return database.get(index);
    }
}
