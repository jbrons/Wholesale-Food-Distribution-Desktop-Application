package GUI.CustomerInvoiceSearch;

import src.Customer.CustomerProfile;
import src.Customer.CustomerProfileDatabase;
import src.CustomerOrder.CustomerOrder;
import src.CustomerOrder.CustomerOrderDatabase;
import src.Invoice.Invoice;
import src.Invoice.InvoiceDatabase;

import java.util.ArrayList;

/**
 * Handles all the logic needed for
 * retrieving customer invoices.
 *
 * @author Jacob Price | ga4116
 */
public class CustomerInvoiceSearchLogic {

    InvoiceDatabase invoiceDatabase;
    CustomerProfileDatabase customerProfileDatabase;
    CustomerOrderDatabase customerOrderDatabase;

    public CustomerInvoiceSearchLogic()
    {
        invoiceDatabase = InvoiceDatabase.getInstance();
        customerProfileDatabase = CustomerProfileDatabase.getInstance();
        customerOrderDatabase = CustomerOrderDatabase.getInstance();
    }

    public Invoice[] getCustomerInvoices(String customerName)
    {
        int customerID = -1;

        for (CustomerProfile customerProfile : customerProfileDatabase.getAllProfiles()) {
            if(customerProfile.getCustomerName().equals(customerName))
                customerID = customerProfile.getCustomerID();
        }

        if(customerID == -1)
            return null;

        ArrayList<CustomerOrder> customerOrderList = new ArrayList<>();

        for (CustomerOrder customerOrder : customerOrderDatabase.getAllOrders())
        {
            if(customerOrder.getCustomerID() == customerID) {
                customerOrderList.add(customerOrder);
            }
        }

        if(customerOrderList.isEmpty())
            return null;

        ArrayList<Invoice> customerInvoiceList = new ArrayList<>();

        for(Invoice invoice : invoiceDatabase.getInvoiceList())
        {
            for(CustomerOrder customerOrder : customerOrderList)
            {
                if(customerOrder.getOrderID() == invoice.getOrderId())
                {
                    customerInvoiceList.add(invoice);
                    customerOrderList.remove(customerOrder);
                    break;
                }
            }
        }

        if(customerInvoiceList.isEmpty())
            return null;

        return customerInvoiceList.toArray(new Invoice[customerInvoiceList.size()]);
    }
}
