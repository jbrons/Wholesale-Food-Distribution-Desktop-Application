package src.Vendor;

import javax.swing.text.MaskFormatter;
import javax.swing.text.NumberFormatter;
import java.text.NumberFormat;
import java.text.ParseException;

/**
 *  ParseStrings implements a variety of string pasring functions for dates and numbers.
 *
 * @author Jordan Bronstetter
 * @date 03/18/2021
 *
 */
public class ParseStrings {
    private static NumberFormat numberFormat = NumberFormat.getInstance();
    private static NumberFormatter numberFormatter = new NumberFormatter(numberFormat);

    public static MaskFormatter formatter(String format, char placeHolder) {
        MaskFormatter formatter = null;
        try {
            formatter = new MaskFormatter(format);
            formatter.setPlaceholderCharacter(placeHolder);
        } catch (ParseException e) {
            System.out.println("Format Error");
        }
        return formatter;
    }

    public static double getNumber(String numStr) {
        try {
            Number number = numberFormat.parse(numStr);
            return number.doubleValue();
        } catch (ParseException e) {
            return -1;
        }
    }

    public static NumberFormatter getNumberFormatter() {
        return numberFormatter;
    }
}
