package GUI.ExpiredItems;

import GUI.Login.LoginGUI;
import GUI.MainMenu.MainMenuGUI;
import GUI.MainWindow.MainWindowGUI;
import src.Item.Item;
import src.Item.ItemsDatabase;

import javax.swing.*;

public class ExpiredItemsGUI {
    private JPanel rootPanel;
    private JPanel mainPanel;
    private JPanel mainTopBarPanel;
    private JList<Item> expiredItemsList;
    private JTextField searchTextField;
    private JButton searchButton;
    private JPanel buttonPanel;
    private JButton mainMenuButton;
    private JButton logoutButton;
    private JButton viewAllButton;

    MainWindowGUI mainWindowGUI;
    ItemsDatabase itemsArray;
    ExpiredItemsLogic expiredItemsLogic;

    public ExpiredItemsGUI()
    {
        mainWindowGUI = MainWindowGUI.getInstance();
        itemsArray = ItemsDatabase.getInstance();

        expiredItemsLogic = new ExpiredItemsLogic();

        setupGUI();
        initializeList();
    }

    private void setupGUI()
    {
        searchButton.addActionListener(e -> {
            String searchQuery = searchTextField.getText();
            searchList(searchQuery);
        });

        viewAllButton.addActionListener(e ->{
            initializeList();
        });

        mainMenuButton.addActionListener(e -> {
            mainWindowGUI.setJPanel(new MainMenuGUI().getPanel());
        });

        logoutButton.addActionListener(e -> {
            mainWindowGUI.setJPanel(new LoginGUI().getPanel());
        });
    }

    private void initializeList()
    {
        expiredItemsList.setListData(expiredItemsLogic.getAllExpiredItems());
    }

    private void searchList(String expiredItemName)
    {
        Item[] list = new Item[1];
        list[0] = expiredItemsLogic.getExpiredItem(expiredItemName);

        if(list[0] == null)
        {
            displayError("The item [" + expiredItemName + "] was not found.");
            return;
        }

        expiredItemsList.setListData(list);
    }

    private void displayError(String message) {
        JOptionPane.showMessageDialog(JOptionPane.getRootFrame(), message, "Error", JOptionPane.ERROR_MESSAGE);
    }

    public JPanel getPanel()
    {
        return rootPanel;
    }
}
