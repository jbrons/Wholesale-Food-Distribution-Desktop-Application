package GUI.VendorManagement;

import javax.swing.*;

/**
 * Model creates and handles a DefaultListModel
 *
 * @author Jordan Bronstetter
 * @date 03/17/2021
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

    public int size() {
        return model.size();
    }

    public boolean isEmpty() {
        return model.isEmpty();
    }
}
