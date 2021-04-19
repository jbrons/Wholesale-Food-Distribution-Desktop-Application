package GUI.PurchaseOrderManagement;

import GUI.VendorManagement.Model;
import src.PurchaseOrder.PurchaseOrder;

import java.util.Vector;

/**
 *  This class implements the Vendor profile for the owner
 *  and purchaser users to create, update, and delete Vendors
 *
 * @author Jordan Bronstetter
 * @date 04/06/2021
 *
 */
public class PurchaseOrderModel extends Model {

    public void updateModel(Vector<PurchaseOrder> purchaseOrders) {
        if (!getDisplayListModel().isEmpty()) {
            clearModel();
        }

        for (PurchaseOrder purchaseOrder : purchaseOrders) {
            add("</body></html>" + purchaseOrder.toString() + "<br>" +
                    purchaseOrder.getTotalCost() + "</body></html>");
        }
    }

    public void clearModel() {
        getDisplayListModel().removeAllElements();
    }
}
