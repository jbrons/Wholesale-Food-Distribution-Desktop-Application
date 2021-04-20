package GUI.PurchaseOrderManagement;

import GUI.VendorManagement.Model;
import src.PurchaseOrder.PurchaseOrder;

import java.util.Vector;
import java.util.stream.IntStream;

/**
 *  PurchaseOrderModel extends Model and implements a DefaultListModel,
 *  focusing on updating and clearing values in the model.
 *
 * @author Jordan Bronstetter
 * @date 04/06/2021
 *
 */
public class PurchaseOrderModel extends Model {

    public void updateModel(Vector<PurchaseOrder> purchaseOrders, int index) {
        IntStream.range(index, purchaseOrders.size()).mapToObj(i ->
                purchaseOrders.get(i).toString()).forEachOrdered(this::add);
    }

    public void clearModel() {
        getDisplayListModel().removeAllElements();
    }
}
