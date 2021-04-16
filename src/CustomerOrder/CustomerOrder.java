/**
 *
 */

package src.CustomerOrder;

import src.Item.Items;

import java.util.HashMap;
import java.util.Map;

public class CustomerOrder {

    /**
     * static ID variable
     */
    public static int ID = 100000;

    /**
     * order ID
     */
    private int orderID;
    /**
     * customer ID
     */
    private int customerID;
    /**
     * items - max 5
     */
    private Map<Items, Integer> items;
    /**
     * need by date - format MM/dd/yyyy
     */
    private String needByDate;
    /**
     * order date - format MM/dd/yyyy
     */
    private String orderDate;

    /**
     * Constructor
     * @param customerID customerid
     * @param needByDate need by date
     * @param orderDate order date
     */
    public CustomerOrder(int customerID, String needByDate, String orderDate) {
        orderID = ++ID;
        this.customerID = customerID;
        this.needByDate = needByDate;
        this.orderDate = orderDate;
        this.items = new HashMap<>();
    }

    /**
     * get order id
     * @return
     */
    public int getOrderID() {
        return orderID;
    }

    /**
     * get customer id
     * @return
     */
    public int getCustomerID() {
        return customerID;
    }

    /**
     * get items
     * @return
     */
    public Map<Items, Integer> getItems() {
        return items;
    }

    /**
     * get need by date string
     * @return
     */
    public String getNeedByDate() {
        return needByDate;
    }

    /**
     * get order date string
     * @return
     */
    public String getOrderDate() {
        return orderDate;
    }

    /**
     * get price of all items
     * @return
     */
    public double getPrice() {
        double price = 0;

        // get all data
        for (Map.Entry<Items, Integer> e : items.entrySet()) {
            price += e.getKey().getSellingPrice() * e.getValue();
        }

        return price;
    }

    /**
     * Add new item with quantity
     * @param item item object
     * @param quantity quantity
     */
    public void addItem(Items item, Integer quantity) {
        if (items.size() < 5) {
            items.merge(item, quantity, Integer::sum);
        }
    }

    /**
     * get item count in order
     * @return
     */
    public int getItemCount() {
        return items.size();
    }

    @Override
    public String toString() {
        return orderID +
                ", customerID=" + customerID +
                ", needByDate='" + needByDate + '\'' +
                ", orderDate='" + orderDate + '\'' +
                ", price=" + getPrice() +
                ", items=" + getItemCount() +
                '\n';
    }
}
