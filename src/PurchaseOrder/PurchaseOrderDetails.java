package src.PurchaseOrder;

import src.Item.Item;
import src.Item.ItemsDatabase;
import src.Vendor.DateValidator;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class PurchaseOrderDetails {
    private LocalDate needByDate = null;
    private double quantity;
    private double subtotalCost;

    public PurchaseOrderDetails(double purchasingPrice, LocalDate needByDate, double quantity) {
        setNeedByDate(needByDate);
        setQuantity(quantity);
        calculateTotalCost(purchasingPrice);
    }

    private DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("MM/dd/yyyy");
    private DateValidator validator = new DateValidator(dateFormat);

    public void setNeedByDate(LocalDate needByDate) {
        this.needByDate = needByDate;
    }

    private void calculateTotalCost(double purchasingPrice) {
        subtotalCost = quantity * purchasingPrice;
    }

    public double getSubtotalCost() {
        return subtotalCost;
    }

    public void setQuantity(double quantity) {
        this.quantity = quantity;
    }
}