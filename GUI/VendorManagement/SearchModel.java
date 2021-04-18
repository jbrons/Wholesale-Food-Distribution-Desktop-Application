package GUI.VendorManagement;

import javax.swing.*;

/**
 *  This class implements the DefaultListModel of the list that displays the search results to purchaser users.
 *  It stores and updates only one Vendor profile at a time in the list.
 *
 * @author Jordan Bronstetter
 * @date 03/18/2021
 *
 */
public class SearchModel extends Model {
    private DefaultListModel<String> searchModel;

    public SearchModel() {
        searchModel = new DefaultListModel();
        searchModel.addElement(null);
    }

}
