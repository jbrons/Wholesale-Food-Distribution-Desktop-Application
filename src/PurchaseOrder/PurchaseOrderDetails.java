package src.PurchaseOrder;

import src.Item.Item;
import src.Item.ItemsDatabase;
import src.Vendor.DateValidator;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 *  This class implements the Vendor profile for the owner
 *  and purchaser users to create, update, and delete Vendors
 *
 * @author Jordan Bronstetter
 * @date 04/06/2021
 *
 */
public class PurchaseOrderDetails {
    private LocalDate needByDate = null;
    private double quantity;
    private double subtotalCost;

    public PurchaseOrderDetails(double purchasingPrice, LocalDate needByDate, double quantity) {
        setNeedByDate(needByDate);
        setQuantity(quantity);
        calculateSubtotalCost(purchasingPrice);
    }

    private DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("MM/dd/yyyy");
    private DateValidator validator = new DateValidator(dateFormat);

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
        String nL = "<br>";
        return needByDate.format(DateTimeFormatter.ofPattern("MM/dd/yyyy"))
                + nL + quantity + nL + subtotalCost + nL ;
    }
}