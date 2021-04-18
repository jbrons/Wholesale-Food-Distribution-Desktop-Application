package src.PurchaseOrder;

import src.Item.Items;
import src.Item.ItemsArray;
import src.Vendor.DateValidator;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class ItemDetails {
    ItemsArray itemsList = ItemsArray.getInstance();
    private LocalDate needByDate = null;
    /* can also change quantity value */
    private double subtotalCost;
    private int itemId;

    public ItemDetails(int itemId) {
        this.itemId = itemId;
        calculateSubtotalCost();
    }

    private DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("MM/dd/yyyy");
    private DateValidator validator = new DateValidator(dateFormat);

    public void setNeedByDate(LocalDate needByDate) {
        this.needByDate = needByDate;
    }

    private void calculateSubtotalCost() {
        // itemsList.get(itemId).getUn what do I use?
        this.subtotalCost = subtotalCost;
    }

    public double getSubtotalCost() {
        return subtotalCost;
    }

    public void setQuantity(double quantity) {
        itemsList.get(itemId).setQuantity(quantity);
    }
}