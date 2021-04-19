package GUI.VendorManagement;

import javax.swing.*;

/**
 *  This class implements the Vendor profile for the owner
 *  and purchaser users to create, update, and delete Vendors
 *
 * @author Jordan Bronstetter
 * @date 04/06/2021
 *
 */
public abstract class Model {
    private DefaultListModel<String> model;

    public Model() {
        model = new DefaultListModel<>();
    }

    public DefaultListModel<String> getDisplayListModel() {
        return model;
    }

    public void add(String element) {
        model.addElement(element);
    }

    public void update(String element, int index) {
        model.set(index, element);
    }

    public void clearModel() {
        getDisplayListModel().removeAllElements();
    }

    public boolean isEmpty() {
        return model.isEmpty();
    }
}
