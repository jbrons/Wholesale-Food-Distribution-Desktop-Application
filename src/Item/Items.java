/*
 * Author: Zachary Nicolai
 * Class Name: Items
 * Class Description: This class class is used for creating a new Item object.
 * it contains all the item profile details as variables as well as get and set
 * methods to access them. It also contains a toString() override which is
 * called upon to display the item profile details.
*/
package src.Item;

import java.text.DecimalFormat;


public class Items {
    //variables
    private int id;
    private int vendorId;
    private String name;
    private double sellingPrice;
    private String category;
    private String expirationDate;
    private double purchasePrice;
    private String unit;
    private int quantity;

    //constructors
    public Items(){
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
    public Items(int i,int vi,String n,double sp,String c,String ed,double pp,String u,int q){
        id = i;
        vendorId = vi;
        name = n;
        sellingPrice = sp;
        category = c;
        expirationDate = ed;
        purchasePrice = pp;
        unit = u;
        quantity = q;
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
    public void setQuantity(int q){
        quantity = q;
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
    public int getQuantity(){
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
