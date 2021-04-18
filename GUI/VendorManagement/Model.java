package GUI.VendorManagement;

import javax.swing.*;

public abstract class Model {
    private DefaultListModel<String> model;

    public DefaultListModel<String> getDisplayListModel() {
        return model;
    }

    public void add(String element) {
        model.addElement(element);
    }

    public void update(String element, int index) {
        model.set(index, element);
    }

    public boolean isEmpty() {
        return model.isEmpty();
    }
}
