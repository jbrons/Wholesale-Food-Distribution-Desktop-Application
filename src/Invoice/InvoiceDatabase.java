package src.Invoice;

import src.Vendor.DateValidator;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Vector;

public class InvoiceDatabase {
    private static InvoiceDatabase firstInstance = null;
    private Vector<Invoice> invoiceList = null;

    private InvoiceDatabase(){
        invoiceList = new Vector<Invoice>();
    }
    public static InvoiceDatabase getInstance() {
        if (firstInstance == null) {
            firstInstance = new InvoiceDatabase();
        }
        return firstInstance;
    }

    public boolean addInvoice(Invoice invoice){
        invoiceList.add(invoice);
        return true;
    }
    public boolean removeInvoice(int index){
        invoiceList.removeElementAt(index);
        return true;
    }

    //probably wont need
    public Vector<String> getAllInvoiceDetails() {
        Vector<String> invoices = new Vector<String>();
        for (Invoice invoice : invoiceList) {
            invoices.add(invoice.toString());
        }
        return invoices;
    }
    
    public boolean invoiceAlreadyExists(int orderId) {
        Vector<String> invoices = new Vector<String>();
        for (Invoice invoice : invoiceList) {
            if(invoice.getOrderId() == orderId ){
                return false;
            }
        }
        return true;
    }

    public int getId(String s){
        int i=0;
        for (Invoice invoice : invoiceList) {
            if(s.equals(invoice.toString())){
                return i;
            }
            i++;
        }
        return -1;
    }
    public Invoice get(int index) {
        return invoiceList.get(index);
    }

    public Vector<Invoice> getInvoiceList() {return invoiceList; }

    public Vector<Invoice> getInvoiceList(LocalDate startDate, LocalDate endDate) {
        if(invoiceList.isEmpty())
            return invoiceList;

        Vector<Invoice> invoices = new Vector<>();

        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
        DateValidator dateValidator = new DateValidator(dateFormatter);

        for (Invoice invoice : invoiceList) {

            LocalDate orderDate = dateValidator.getDate(invoice.getOrderDate());

            boolean inBetweenDates = startDate.isBefore(orderDate) && endDate.isAfter(orderDate);
            boolean equalToStartDate = startDate.isEqual(orderDate);
            boolean equalToEndDate = endDate.isEqual(orderDate);

            if(inBetweenDates || equalToStartDate || equalToEndDate) {
                invoices.add(invoice);
            }
        }

        return invoices;
    }
}
