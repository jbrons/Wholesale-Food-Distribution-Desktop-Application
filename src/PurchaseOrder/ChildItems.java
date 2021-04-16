package src.PurchaseOrder;
import src.Item.Items;
import src.Item.ItemsArray;

import java.awt.*;
import java.beans.*;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;
import java.awt.event.*;

public class ChildItems {
    private String message; // the bean  property
    private final PropertyChangeSupport support = new PropertyChangeSupport(this);

    public ChildItems() {
        // default
    }

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        support.addPropertyChangeListener(listener);
    }

    public void removePropertyChangeListener(PropertyChangeListener listener) {
        support.removePropertyChangeListener(listener);
    }

    public String getMessage() {
        return this.message;
    }

    public void setMessage(String newValue) {
        String oldValue = this.message;
        this.message = newValue;
        // The parameter values of firePropertyChange method
        // constitute the PropertyChangeEvent object
        support.firePropertyChange("message", oldValue, newValue);
    }
}