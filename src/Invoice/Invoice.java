package src.Invoice;


import src.CustomerOrder.CustomerOrder;
import src.Item.Item;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Map;
import java.util.Random;


public class Invoice {

    public static int invoiceId = 100;
    private String invoiceDate;
    private String orderDate;
    private double totalInvoiceAmount;
    private CustomerOrder customerOrder;
    private String customerName;
    private int orderId;
    private Map<Item, Double> items;


    public Invoice(CustomerOrder order) {
        this.customerOrder = order;
        this.orderDate = order.getOrderDate();
        this.totalInvoiceAmount = order.getPrice();
        this.items = order.getItems();
        setInvoiceDate();
        setInvoiceID();
        this.orderId = order.getOrderID();
    }

    //set methods
    public void set(CustomerOrder order){
        this.customerOrder = order;
    }
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
    public void setCustomerName(){}
    public void setTotalInvoiceAmount(double total){ this.totalInvoiceAmount = total; }
    public void setOrderDate(double od){ this.orderDate = orderDate; }

    //get methods
    public String getInvoiceDate(){ return this.invoiceDate; }
    public String getCustomerName(){ return ""; }
    public int getInvoiceID(){ return this.getInvoiceID(); }
    public Map<Item, Double> getItems() {
        return this.items;
    }
    public double getTotalInvoiceAmount(){ return this.totalInvoiceAmount; }
    public String getOrderDate(){ return this.orderDate; }
    public int getOrderId(){return this.orderId;}




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

    //toString() override which displays Item profile details
    @Override
    public String toString(){
        DecimalFormat df = new DecimalFormat("#.00");
        return "Invoice Date: " + this.invoiceDate + " Order Date: " + this.orderDate + " Customer Order Number: " +
                customerOrder.getOrderID() + " Total Invoice Price: $" + df.format(totalInvoiceAmount);
    }
}
