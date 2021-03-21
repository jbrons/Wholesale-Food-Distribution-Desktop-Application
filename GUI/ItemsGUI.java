package GUI;

import Item.Items;
import Item.ItemsArray;

import javax.swing.*;
import java.awt.event.*;
import java.text.NumberFormat;
import java.text.DecimalFormat;
import java.awt.event.FocusListener;
import java.util.ArrayList;

public class ItemsGUI  implements FocusListener{

    private JFrame frame;
    private JPanel rootPanel;


    private JTextField searchField;
    private JButton searchButton;
    private JButton addButton;
    private JButton displayButton;
    private JButton leaveButton;
    private JButton logoutButton;
    private JList iList;
    private JLabel guiLabel;
    private JTextField focused = searchField;
    private ArrayList<Items> itemsListCopy;


    NumberFormat nf = new DecimalFormat();

    public ItemsGUI(){


        frame = new JFrame("Items Profile");
        frame.setTitle("Items profiles");

        searchButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                String searchedItem = searchField.getText();
                itemsListCopy = ItemsArray.getItemsList();
                ArrayList<Items> itemsListFinal = new ArrayList<Items>();
                boolean found = false;

                for(int i=0;i< itemsListCopy.size();i++){
                    if(String.valueOf(itemsListCopy.get(i).getId()).equals(searchedItem) ||
                            itemsListCopy.get(i).getName().equals(searchedItem) ||
                            itemsListCopy.get(i).getExpirationDate().equals(searchedItem)){
                            itemsListFinal.add(itemsListCopy.get(i));
                            found = true;
                    }
                }
                if(!found){
                    JOptionPane.showMessageDialog(null, searchedItem + " not found");
                }
                displayCatalog(itemsListFinal);
            }});
        addButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                AddItemsGUI add = new AddItemsGUI();
                add.displayAddItem();
            }});

        leaveButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                closeCatalog();
            }});
        displayButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                displayCatalog(ItemsArray.getItemsList());
            }});

        iList.addMouseListener(new MouseAdapter(){
            public void mouseClicked(MouseEvent evt) {
                JList jList = (JList)evt.getSource();
                if (evt.getClickCount() == 2) {
                    int index = iList.locationToIndex(evt.getPoint());
                    ItemDisplayGUI dis = new ItemDisplayGUI(index);
                    dis.window();
                }
            }
        });
    }

    private void window() {
        frame.setContentPane(new ItemsGUI().rootPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        frame.setResizable(false);
        frame.pack();

    }
    public static void main(String[] args) {
        ItemsGUI items = new ItemsGUI();
        items.window();
    }

    public void displayCatalog(ArrayList<Items> itemsListFinal) {
        iList.setListData(ItemsArray.itemsListToArray(itemsListFinal));
    }


    public void closeCatalog(){
        SwingUtilities.getWindowAncestor(rootPanel).setVisible(false);
    }

    //methods required for implementing focus
    public void focusGained(FocusEvent e) {
        if (e.getSource() instanceof JTextField) {
            focused = (JTextField) e.getSource();
        }
    }
    public void focusLost(FocusEvent e) {
    }


}
