package GUI.ProfitSearch;

import src.Invoice.Invoice;
import src.Invoice.InvoiceDatabase;
import src.Item.Items;
import src.Item.ItemsDatabase;

import javax.swing.*;
import java.time.LocalDate;
import java.util.Map;
import java.util.Vector;

public class ProfitSearchLogic {
    double totalProfit;
    ItemsDatabase itemsArray;
    InvoiceDatabase invoiceListDatabase;

    public ProfitSearchLogic()
    {
        totalProfit = 0.0;
        itemsArray = ItemsDatabase.getInstance();
        invoiceListDatabase = InvoiceDatabase.getInstance();
    }

    public double getProfit(Items item, LocalDate startDate, LocalDate endDate)
    {
        Vector<Invoice> invoiceList = invoiceListDatabase.getInvoiceList(startDate, endDate);

        if(invoiceList.isEmpty())
            return totalProfit;

        double itemMargin = item.getSellingPrice() - item.getPurchasePrice();

        for(Invoice invoice : invoiceList)
        {
            Map<Items, Double> itemsList = invoice.getItems();

            if(itemsList.get(item) != null) {
                double quantitySold = itemsList.get(item);

                totalProfit += quantitySold * itemMargin;
            }

        }

        return totalProfit;
    }

    public Items checkItemExists(String itemName)
    {
        if(itemsArray.isEmpty())
            return null;

        Vector<String> itemList = itemsArray.getSearchDetails(itemName);
        if(itemList.isEmpty())
            return null;

        // Set the item to be used in future methods
        Items item = itemsArray.get(itemsArray.getId(itemList.get(0)));

        return item;
    }

    boolean formValidated(String itemName, LocalDate startDate, LocalDate endDate)
    {
        LocalDate today = LocalDate.now();

        // Check that the text fields have valid data
        if(itemName.isEmpty()) {
            displayError("Item Name cannot be blank!");
            return false;
        }

        if(startDate == null) {
            displayError("Incorrect Start Date!");
            return false;
        }
        else if(today.isBefore(startDate)) {
            displayError("Start Date cannot be after today's date! (" + today.toString() + ")");
            return false;
        }

        if(endDate == null) {
            displayError("Incorrect End Date!");
            return false;
        }

        return true;
    }

    private void displayError(String message) {
        JOptionPane.showMessageDialog(JOptionPane.getRootFrame(), message, "Error", JOptionPane.ERROR_MESSAGE);
    }
}
