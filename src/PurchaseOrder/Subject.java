package src.PurchaseOrder;

import src.Item.Items;
import src.Item.ItemsArray;
import src.Vendor.DateValidator;
import src.Vendor.StateAbbrs;
import src.Vendor.Vendor;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class Subject  {
    ItemsArray itemsList = ItemsArray.getInstance();

    DateValidator validate = new DateValidator(DateTimeFormatter.ofPattern("MM/dd/yyyy"));

    if (validate.isValid("12/20/2021")) {

    }


    //itemsList.addPropertyChangeListener();

}