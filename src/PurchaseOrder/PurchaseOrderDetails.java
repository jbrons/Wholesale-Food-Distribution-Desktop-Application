package src.PurchaseOrder;

import src.Vendor.DateValidator;

import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 *  PurchaseOrderDetails stores and handles the details of Purchase Orders.
 *
 * @author Jordan Bronstetter
 * @date 04/06/2021
 *
 */
public class PurchaseOrderDetails {

    private static DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("MM/dd/yyyy");

    private LocalDate needByDate = null;
    private double quantity;
    private double subtotalCost;

    public PurchaseOrderDetails(double purchasingPrice, LocalDate needByDate, double quantity) {
        setNeedByDate(needByDate);
        setQuantity(quantity);
        calculateSubtotalCost(purchasingPrice);
    }

    public LocalDate getNeedByDate() {
        return needByDate;
    }

    public void setNeedByDate(LocalDate needByDate) {
        this.needByDate = needByDate;
    }

    private void calculateSubtotalCost(double purchasingPrice) {
        subtotalCost = quantity * purchasingPrice;
    }

    public double getSubtotalCost() {
        return subtotalCost;
    }

    public void setQuantity(double quantity) {
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        DecimalFormat df = new DecimalFormat("$#.00");
        String format = "<br>&nbsp;&nbsp;&nbsp;";
        String date = needByDate.format(DateTimeFormatter.ofPattern("MM/dd/yyyy"));
        String q = Double.toString(quantity);;

        return "Need by Date: " + date + format + "Quantity: " + q
                + format + "Subtotal Cost: " + df.format(subtotalCost) + "<br>";
    }
}