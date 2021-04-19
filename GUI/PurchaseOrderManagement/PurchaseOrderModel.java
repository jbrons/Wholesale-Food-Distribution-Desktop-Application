package GUI.PurchaseOrderManagement;

import GUI.VendorManagement.Model;
import src.Item.Item;
import src.PurchaseOrder.PurchaseOrder;

import java.util.Vector;

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
