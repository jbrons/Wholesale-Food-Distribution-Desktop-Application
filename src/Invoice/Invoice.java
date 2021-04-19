package src.Invoice;

/**
 *  This class implements the Invoice profile so accountant users
 *  can create, search, and view invoices
 *
 * @author Zachary Nicolai
 * @date 04/18/2021
 */

import src.CustomerOrder.CustomerOrder;
import src.Item.Item;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Map;
import java.util.Random;


public class Invoice {

    //variables
    public static int invoiceId = 100;
    private String invoiceDate;
    private String orderDate;
    private double totalInvoiceAmount;
    private CustomerOrder customerOrder;
    private int orderId;
    private Map<Item, Double> items;

    //constructor
    public Invoice(CustomerOrder order) {
        setInvoiceID();
        setInvoiceDate();
        this.orderDate = order.getOrderDate();
        this.totalInvoiceAmount = order.getPrice();
        this.customerOrder = order;
        this.orderId = order.getOrderID();
        this.items = order.getItems();
    }

    //set methods
    public void set(CustomerOrder order){ this.customerOrder = order; }
    public void setTotalInvoiceAmount(double total){ this.totalInvoiceAmount = total; }
    public void setOrderDate(double od){ this.orderDate = orderDate; }
    public void setOrderId(int i){ this.orderId = i; }
    public void setItems(Map<Item, Double> items){ this.items = items; }
    public void setInvoiceDate(){
        LocalDate today = LocalDate.now();
        DateTimeFormatter format = DateTimeFormatter.ofPattern("MM/dd/yyyy");
        String date = today.format(format);
        this.invoiceDate = date;
    }
    public void setInvoiceID(){
        Random r = new Random();
        this.invoiceId += r.nextInt(10)+1;
    }


    //get methods
    public String getInvoiceDate(){ return this.invoiceDate; }
    public int getInvoiceID(){ return this.getInvoiceID(); }
    public Map<Item, Double> getItems() { return this.items; }
    public double getTotalInvoiceAmount(){ return this.totalInvoiceAmount; }
    public String getOrderDate(){ return this.orderDate; }
    public int getOrderId(){return this.orderId;}


    //method checks if an item is is in a invoice
    public boolean itemInObject(int id){

        for (Map.Entry<Item, Double> e : items.entrySet()) {
           if(e.getKey().getId() == id){
               return true;
           }
        }
        return false;
    }

    //returns all item details as a string
    public String getItemDetails(){
        DecimalFormat df = new DecimalFormat("#.00");
        String details = "<html><body>";
        for (Map.Entry<Item, Double> e : items.entrySet()) {
            details += "Item Name: " + e.getKey().getName() +" Quantity: " + e.getValue() +
                    " cost: $" +  df.format(e.getKey().getSellingPrice() * e.getValue()) + "<br>";
        }
        details+="</body></html>";
        return details;
    }

    //toString() override which displays invoice header details
    @Override
    public String toString(){
        DecimalFormat df = new DecimalFormat("#.00");
        return "Invoice Date: " + this.invoiceDate + " Order Date: " + this.orderDate + " Customer Order Number: " +
                customerOrder.getOrderID() + " Total Invoice Price: $" + df.format(totalInvoiceAmount);
    }
}
