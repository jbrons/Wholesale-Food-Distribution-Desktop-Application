package GUI.ProfitSearch;

import GUI.Login.LoginGUI;
import GUI.MainMenu.MainMenuGUI;
import GUI.MainWindow.MainWindowGUI;
import src.Item.Item;
import src.Vendor.DateValidator;

import javax.swing.*;
import javax.swing.text.DefaultFormatterFactory;
import javax.swing.text.MaskFormatter;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class ProfitSearchGUI {
    private JPanel rootPanel;
    private JPanel profitSearchPanel;
    private JPanel buttonPanel;
    private JButton searchButton;
    private JButton mainMenuButton;
    private JButton logoutButton;
    private JFormattedTextField itemNameTextField;
    private JFormattedTextField startDateTextField;
    private JFormattedTextField endDateTextField;

    MainWindowGUI mainWindowGUI;
    ProfitSearchLogic profitSearchLogic;
    DateValidator dateValidator;
    DateTimeFormatter dateFormatter;

    public ProfitSearchGUI()
    {
        mainWindowGUI = MainWindowGUI.getInstance();
        mainWindowGUI.setTitle("Profit Search");
        profitSearchLogic = new ProfitSearchLogic();

        dateFormatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
        dateValidator = new DateValidator(dateFormatter);

        setupGUI();
    }

    private void setupGUI()
    {
        String dateFormat = "##/##/####";

        startDateTextField.setFormatterFactory(new DefaultFormatterFactory(formatter(dateFormat, '-')));
        endDateTextField.setFormatterFactory(new DefaultFormatterFactory(formatter(dateFormat, '-')));

        searchButton.addActionListener(e -> {
            String itemName;
            LocalDate startDate;
            LocalDate endDate;
            Item item;

            itemName = itemNameTextField.getText();
            startDate = dateValidator.getDate(startDateTextField.getText());
            endDate = dateValidator.getDate(endDateTextField.getText());

            if(!profitSearchLogic.formValidated(itemName, startDate, endDate))
                return;

            // Check if item exists first
            item = profitSearchLogic.checkItemExists(itemName);

            if(item == null) {
                displayError("Item [" + itemName + "] does not exist in the inventory!");
                return;
            }

            String alertMessage = "=== Total Profit for [" + itemName + "] ===\n"
                    + "\nStart Date: " + startDate.toString() + "\nEnd Date: " + endDate.toString()
                    + "\n\nTotal Profit: $";

            double totalProfit = profitSearchLogic.getProfit(item, startDate, endDate);
            alertMessage += totalProfit;

            JOptionPane.showMessageDialog(null, alertMessage,
                    "Search Profit - " + itemName + " Profit", JOptionPane.INFORMATION_MESSAGE);
        });

        mainMenuButton.addActionListener(e -> {
            mainWindowGUI.setJPanel(new MainMenuGUI().getPanel());
        });

        logoutButton.addActionListener(e -> {
            mainWindowGUI.setJPanel(new LoginGUI().getPanel());
        });
    }

    private MaskFormatter formatter(String format, char placeHolder) {
        MaskFormatter formatter = null;
        try {
            formatter = new MaskFormatter(format);
            formatter.setPlaceholderCharacter(placeHolder);
        } catch (ParseException e) {
            System.out.println("Format Error");
        }
        return formatter;
    }

    private void displayError(String message) {
        JOptionPane.showMessageDialog(JOptionPane.getRootFrame(), message, "Error", JOptionPane.ERROR_MESSAGE);
    }

    public JPanel getPanel()
    {
        return rootPanel;
    }
}
