package GUI.OverdueInvoicesSearch;

import src.Customer.CustomerProfile;
import src.Customer.CustomerProfileDatabase;
import src.CustomerOrder.CustomerOrder;
import src.CustomerOrder.CustomerOrderDatabase;
import src.Invoice.Invoice;
import src.Invoice.InvoiceDatabase;
import src.Vendor.DateValidator;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Vector;

public class OverdueInvoicesLogic {

    InvoiceDatabase invoiceDatabase;
    CustomerProfileDatabase customerProfileDatabase;
    CustomerOrderDatabase customerOrderDatabase;

    public OverdueInvoicesLogic()
    {
        invoiceDatabase = InvoiceDatabase.getInstance();
        customerProfileDatabase = CustomerProfileDatabase.getInstance();
        customerOrderDatabase = CustomerOrderDatabase.getInstance();
    }

    public CustomerProfile[] getOverdueCustomers()
    {
        ArrayList<CustomerProfile> overdueCustomers = new ArrayList<>();

        Invoice[] invoiceList = getOverdueInvoices();

        for(Invoice orderInvoice : invoiceList)
        {
            int orderNumber = orderInvoice.getOrderId();

            CustomerOrder customerOrder = customerOrderDatabase.get(orderNumber);
            int customerID = customerOrder.getCustomerID();

            CustomerProfile overdueCustomer = customerProfileDatabase.getProfile(customerID);

            if(!overdueCustomers.contains(overdueCustomer))
                overdueCustomers.add(overdueCustomer);
        }

        return overdueCustomers.toArray(new CustomerProfile[overdueCustomers.size()]);
    }

    public Invoice[] getOverdueInvoices()
    {
        Vector<Invoice> invoices = invoiceDatabase.getInvoiceList();
        ArrayList<Invoice> overdueInvoiceList = new ArrayList<>();

        LocalDate today = LocalDate.now();

        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
        DateValidator dateValidator = new DateValidator(dateFormatter);

        for(Invoice invoice : invoices)
        {
            LocalDate invoiceDate = dateValidator.getDate(invoice.getInvoiceDate());

            long numberOfDays = ChronoUnit.DAYS.between(invoiceDate, today);

            if(numberOfDays > 30)
                overdueInvoiceList.add(invoice);
        }

        return overdueInvoiceList.toArray(new Invoice[overdueInvoiceList.size()]);
    }
}
