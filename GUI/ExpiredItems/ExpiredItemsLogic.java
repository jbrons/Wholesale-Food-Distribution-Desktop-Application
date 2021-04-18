package GUI.ExpiredItems;

import src.Item.Item;
import src.Item.ItemsDatabase;
import src.Vendor.DateValidator;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class ExpiredItemsLogic {
    ItemsDatabase itemsArray;
    DateValidator dateValidator;
    DateTimeFormatter dateFormatter;
    ArrayList<Item> expiredItems;

    public ExpiredItemsLogic()
    {
        itemsArray = ItemsDatabase.getInstance();
        dateFormatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
        dateValidator = new DateValidator(dateFormatter);
    }

    public Item[] getAllExpiredItems()
    {
        expiredItems = new ArrayList<>();
        Item[] allItems = itemsArray.getArray();
        LocalDate today = LocalDate.now();

        for(Item item : allItems)
        {
            LocalDate expirationDate = dateValidator.getDate(item.getExpirationDate());

            if(today.isAfter(expirationDate))
                expiredItems.add(item);
        }

        return expiredItems.toArray(new Item[expiredItems.size()]);
    }

    public Item getExpiredItem(String expiredItemName)
    {
        if(expiredItems.isEmpty())
            return null;

        Item expiredItem = null;

        for (Item item : expiredItems)
        {
            if(item.getName().equals(expiredItemName)) {
                expiredItem = item;
                break;
            }
        }

        return expiredItem;
    }
}
