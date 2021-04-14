package src.Invoice;


import src.Item.Items;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Vector;

public class Invoice {
    //header
    private String invoiceDate;
    private double totalInvoiceAmount;

    //details
    //items?
    //might not need
    private String orderDate; //header
    private int customerOrderNumber; //header

    //constructors
    public Invoice(){
        invoiceDate ="";

    }
    /*
    public Invoice() {
    }
*/
    //set methods

    //get methods


    //toString() override which displays Item profile details
    @Override
    public String toString(){
        DecimalFormat df = new DecimalFormat("#.00");
        return "";
    }
}
