package src.OverdueCustomerInvoice;

public class OverdueCustomerInvoice {

    String customerName;
    String invoiceDate;
    int invoiceID;
    double invoiceTotalDue;

    public OverdueCustomerInvoice(String customerName, String invoiceDate,
                                  int invoiceID, double invoiceTotalDue)
    {
        this.customerName = customerName;
        this.invoiceDate = invoiceDate;
        this.invoiceID = invoiceID;
        this.invoiceTotalDue = invoiceTotalDue;
    }

    @Override
    public String toString()
    {
        return "Customer: " + customerName + ", "
                + "Invoice ID: " + invoiceID + ", "
                + "Invoice Date: " + invoiceDate + ", "
                + "Total Due: $" + invoiceTotalDue;
    }

}
