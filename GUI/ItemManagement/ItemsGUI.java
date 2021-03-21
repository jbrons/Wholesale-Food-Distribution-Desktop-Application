/*
 * Name:Zachary Nicolai
 * Class Name: ItemsGUI
 * Class Description: This class controls the ItemsGUI. it gives instructions for all of the buttons and text fields. It
 * allows users to double click an item to access ItemDisplayGUI, access addItemsGUI if the add button is pressed, or search
 * for an Item. it ensures that only the correct types of users can do specific tasks.
 * */


package GUI.ItemManagement;

import GUI.Login.LoginGUI;
import GUI.MainMenu.MainMenuGUI;
import src.Item.Items;
import src.Item.ItemsArray;
import src.User.UserDatabase;
import src.User.EnumUserRoles;
import GUI.MainWindow.MainWindowGUI;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.text.NumberFormat;
import java.text.DecimalFormat;
import java.awt.event.FocusListener;
import java.util.ArrayList;




public class ItemsGUI implements FocusListener{

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
    private MainWindowGUI mainWindowGUI;


    UserDatabase dataBase = UserDatabase.getInstance();
    NumberFormat nf = new DecimalFormat();

    public ItemsGUI(){
        mainWindowGUI = MainWindowGUI.getInstance();
        setupGUI();
    }

    public void setupGUI()
    {
        //button actions
        searchButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                if(dataBase.getCurrentUser().getRole() == EnumUserRoles.OWNER ||dataBase.getCurrentUser().getRole() == EnumUserRoles.PURCHASER) {
                    String searchedItem = searchField.getText();
                    itemsListCopy = ItemsArray.getItemsList();
                    ArrayList<Items> itemsListFinal = new ArrayList<Items>();
                    boolean found = false;

                    for (int i = 0; i < itemsListCopy.size(); i++) {
                        if (String.valueOf(itemsListCopy.get(i).getId()).equals(searchedItem) ||
                                itemsListCopy.get(i).getName().equals(searchedItem) ||
                                itemsListCopy.get(i).getExpirationDate().equals(searchedItem)) {
                            itemsListFinal.add(itemsListCopy.get(i));
                            found = true;
                        }
                    }
                    if (!found) {
                        JOptionPane.showMessageDialog(null, searchedItem + " not found");
                    }
                    displayCatalog(itemsListFinal);
                }
                else{
                    JOptionPane.showMessageDialog(null,  "Must be Owner user or Purchase user");
                }
            }});
        addButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                if (dataBase.getCurrentUser().getRole() == EnumUserRoles.OWNER || dataBase.getCurrentUser().getRole() ==
                        EnumUserRoles.PURCHASER ||dataBase.getCurrentUser().getRole() == EnumUserRoles.INVENTORY_MANAGER) {
                    mainWindowGUI.setJPanel(new AddItemsGUI().getPanel());
                }
                else{
                    JOptionPane.showMessageDialog(null, "You must be a Owner, Purchaser, or Inventory Manager");
                }
            }
            });
        leaveButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                closeCatalog();
            }});
        displayButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                if (dataBase.getCurrentUser().getRole() == EnumUserRoles.OWNER) {
                    displayCatalog(ItemsArray.getItemsList());
                }
                else{
                    JOptionPane.showMessageDialog(null, "Only Owners can view a list of all items in the system");
                }
            }});

        iList.addMouseListener(new MouseAdapter(){
            public void mouseClicked(MouseEvent evt) {
                JList jList = (JList)evt.getSource();
                if (evt.getClickCount() == 2) {
                    //index of selected jList is stored and then ItemsGUI displays selectedIndex
                    int index = iList.locationToIndex(evt.getPoint());
                    ItemDisplayGUI dis = new ItemDisplayGUI(index);
                    mainWindowGUI.setJPanel(dis.getPanel());
                }
            }
        });
        logoutButton.addActionListener(e ->
        {
            mainWindowGUI.setJPanel(new LoginGUI().getPanel());
        });
    }

    public void displayCatalog(ArrayList<Items> itemsListFinal) {
        iList.setListData(ItemsArray.itemsListToArray(itemsListFinal));
        iList.setFont(new Font("Arial",Font.BOLD,12));
    }

    public void closeCatalog(){
        mainWindowGUI.setJPanel(new MainMenuGUI().getPanel());
    }

    //methods required for implementing focusListener
    public void focusGained(FocusEvent e) {
        if (e.getSource() instanceof JTextField) {
            focused = (JTextField) e.getSource();
        }
    }
    public void focusLost(FocusEvent e) {
    }

    public JPanel getPanel()
    {
        return rootPanel;
    }
}
