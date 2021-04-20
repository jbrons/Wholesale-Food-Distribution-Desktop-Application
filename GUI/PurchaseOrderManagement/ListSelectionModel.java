package GUI.PurchaseOrderManagement;

import javax.swing.*;

/**
 *  ListSelectionModel implements a DefaultListSelectionModel that sets its cells as unselectable
 *
 * @author Jordan Bronstetter
 * @date 04/06/2021
 *
 */
public class ListSelectionModel extends DefaultListSelectionModel {
    @Override
    public void setSelectionMode(int selectionMode) {
        super.setSelectionMode(SINGLE_SELECTION);
    }

    public void setSelectionInterval(int index0, int index01) {}

    public void addSelectionInterval(int i, int j) {}

    public void setLeadSelectionIndex(int i) {}

    public void setAnchorSelectionIndex(int i) {}
}
