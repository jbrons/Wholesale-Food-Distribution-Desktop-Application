package GUI.ExpiredItems;

import src.Item.Items;
import src.Item.ItemsArray;
import src.Vendor.DateValidator;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Vector;

public class ExpiredItemsLogic {
    ItemsArray itemsArray;
    DateValidator dateValidator;
    DateTimeFormatter dateFormatter;
    ArrayList<Items> expiredItems;

    public ExpiredItemsLogic()
    {
        itemsArray = ItemsArray.getInstance();
        dateFormatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
        dateValidator = new DateValidator(dateFormatter);
    }

    public Items[] getAllExpiredItems()
    {
        expiredItems = new ArrayList<>();
        Items[] allItems = itemsArray.getArray();
        LocalDate today = LocalDate.now();

        for(Items item : allItems)
        {
            LocalDate expirationDate = dateValidator.getDate(item.getExpirationDate());

            if(today.isAfter(expirationDate))
                expiredItems.add(item);
        }

        return expiredItems.toArray(new Items[expiredItems.size()]);
    }

    public Items getExpiredItem(String expiredItemName)
    {
        if(expiredItems.isEmpty())
            return null;

        Items expiredItem = null;

        for (Items item : expiredItems)
        {
            if(item.getName().equals(expiredItemName)) {
                expiredItem = item;
                break;
            }
        }

        return expiredItem;
    }
}
