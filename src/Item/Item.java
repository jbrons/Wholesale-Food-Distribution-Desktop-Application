
package src.Item;

import src.PurchaseOrder.ItemsAlert;

import java.text.DecimalFormat;

/**
 * This class class is used for creating a new Item object.
 * it contains all the item profile details as variables as well as get and set
 * methods to access them. It also contains a toString() override which is
 * called upon to display the item profile details.
 *
 * @Author Zachary Nicolai
 * @Date 03/15/2021
 *
 */

public class Item {
    //variables
    private int id;
    private int vendorId;
    private String name;
    private double sellingPrice;
    private String category;
    private String expirationDate;
    private double purchasePrice;
    private String unit;
    private double quantity;

    //constructors
    public Item(){
        id = 0;
        vendorId = 0;
        name  = "";
        sellingPrice = 0;
        category = "";
        expirationDate = "";
        purchasePrice = 0;
        unit = "";
        quantity = 0;
    }
    public Item(int i, int vi, String n, double sp, String c, String ed, double pp, String u, double q){
        id = i;
        vendorId = vi;
        name = n;
        sellingPrice = sp;
        category = c;
        expirationDate = ed;
        purchasePrice = pp;
        unit = u;
        quantity = q;
        ItemsAlert.alertStock(id, quantity);
    }

    //set methods
    public void setId(int i){
        id = i;
    }
    public void setVendorId(int vi){
        vendorId = vi;
    }
    public void setName(String n){ name = n; }
    public void setSellingPrice(double sp){
        sellingPrice = sp;
    }
    public void setCategory(String c){
        category = c;
    }
    public void setExpirationDate(String ed){
        expirationDate = ed;
    }
    public void setPurchasePrice(double pp){
        purchasePrice = pp;
    }
    public void setUnit(String u){
        unit = u;
    }
    public void setQuantity(double q){
        quantity = q;
        ItemsAlert.alertStock(id, quantity);
    }
    //get methods
    public int getId(){
        return id;
    }
    public int getVendorId(){
        return vendorId;
    }
    public String getName(){
        return name;
    }
    public double getSellingPrice(){
        return sellingPrice;
    }
    public String getCategory(){
        return category;
    }
    public String getExpirationDate(){
        return expirationDate;
    }
    public double getPurchasePrice(){
        return purchasePrice;
    }
    public String getUnit(){
        return unit;
    }
    public double getQuantity(){
        return quantity;
    }

    //toString() override which displays Item profile details
    @Override
    public String toString(){
        DecimalFormat df = new DecimalFormat("#.00");
        return "Item ID: " + this.getId() + ", Item Name: " + this.getName() + ", Quantity on Hand: " +
                this.getQuantity() + ", Selling Price: $" + df.format(this.getSellingPrice()) + ", Purchase Price $"
                + df.format(this.getPurchasePrice()) + ", Expiring Date " + this.getExpirationDate();
    }
}
