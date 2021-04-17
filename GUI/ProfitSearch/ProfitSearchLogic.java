package GUI.ProfitSearch;

import src.Invoice.Invoice;
import src.Invoice.InvoiceList;
import src.Item.Items;
import src.Item.ItemsArray;
import src.Vendor.Vendor;

import javax.swing.*;
import java.time.LocalDate;
import java.util.Map;
import java.util.Vector;

public class ProfitSearchLogic {
    double totalProfit;
    ItemsArray itemsArray;
    InvoiceList invoiceListDatabase;

    public ProfitSearchLogic()
    {
        totalProfit = 0.0;
        itemsArray = ItemsArray.getInstance();
        invoiceListDatabase = InvoiceList.getInstance();
    }

    public double getProfit(String itemName, LocalDate startDate, LocalDate endDate)
    {
        Vector<Invoice> invoiceList = invoiceListDatabase.getInvoiceList(startDate, endDate);

        if(invoiceList.isEmpty())
            return totalProfit;

        for(Invoice invoice : invoiceList)
        {
            Map<Items, Integer> itemsList;
            itemsList = invoice.getItems();

            itemsList.get(itemName);
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
        else if(today.isBefore(endDate)) {
            displayError("End Date cannot be after today's date! (" + today.toString() + ")");
            return false;
        }

        return true;
    }

    private void displayError(String message) {
        JOptionPane.showMessageDialog(JOptionPane.getRootFrame(), message, "Error", JOptionPane.ERROR_MESSAGE);
    }
}
