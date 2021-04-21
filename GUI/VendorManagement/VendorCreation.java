package GUI.VendorManagement;

import src.Vendor.DateValidator;
import src.Vendor.ParseStrings;

import javax.swing.*;
import java.time.LocalDate;

/**
 *  VendorHub handles the logic of VendorCreationGUI.
 *
 * @author Jordan Bronstetter
 * @date 04/08/2021
 *
 */
public class VendorCreation {
    public static String checkText(JTextField textField) {
        if (textField.getText().isEmpty()) {
            return null;
        } else {
            return textField.getText();
        }
    }

    public static double checkNumber(JFormattedTextField textField) {
        double number;
        if (!textField.getText().isEmpty()) {
            number = ParseStrings.getNumber(textField.getText());
            if (number == -1) {
                return -1;
            }
        } else {
            number = 0;
        }
        return number;
    }

    public static LocalDate checkDate(JFormattedTextField textField) {
        LocalDate date = null;
        if (textField.isEditValid()) {
            date = DateValidator.getDate(textField.getText());
        }
        return date;
    }
}
