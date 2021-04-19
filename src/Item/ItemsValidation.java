/**
 * This class ensures that Item input from user is valid. The class has a validation method
 * which calls on other methods in the class to ensure that all item input is correct.
 *
 * @Author Zachary Nicolai
 * @Date 03/15/2021
 */

package src.Item;

import javax.swing.*;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Date;



public class ItemsValidation {
    ItemsDatabase itemsList = ItemsDatabase.getInstance();
    public ItemsValidation(){
    }
    //validation method that returns true assuming all user input is correct
    public boolean validation(int id,String name, int vID, double sPrice,String date, double pPrice,double quantity){
        //check sPrice
        if(twoDecimals(sPrice)){
        JOptionPane.showMessageDialog(null, "your selling price input is invalid");
        return false;
         }
        //check pPrice
        if(twoDecimals(pPrice)){
            JOptionPane.showMessageDialog(null, "your purchase price input is invalid");
            return false;
        }
        //check quantity
        if(neg(quantity)){
            JOptionPane.showMessageDialog(null, "your quantity is invalid");
            return false;
        }
        //check that there are no duplicate names for a given vendor ID
        if(nameDuplicate(id, name, vID)){
            JOptionPane.showMessageDialog(null, "Vendor: " + vID + " already has Item: " + name);
            return false;
        }
        if(checkDate(date)){
            JOptionPane.showMessageDialog(null, "Invalid Date" );
            return false;
        }
        if(nameLength(name)){
            JOptionPane.showMessageDialog(null, "Name too long or empty" );
            return false;
        }
    return true;
}
    public boolean twoDecimals(double d){
        if(BigDecimal.valueOf(d).scale() > 2){
        return true;
        }
        return false;
}
    public boolean neg(double q){
        if (q<0){
            return true;
        }
            return false;
}
    public boolean nameDuplicate(int id,String name,int vID){
        if(itemsList.nameDupe(id,name,vID)){
            return true;
        }
        return false;
        }

    public boolean checkDate(String d){

        String f = "MM/dd/yyyy" ;
        DateTimeFormatter format = DateTimeFormatter.ofPattern(f);
        LocalDate date = LocalDate.now();
        String td = date.format(format);
        Date givenDate = new Date();
        SimpleDateFormat smf = new SimpleDateFormat(f);
        try {
            LocalDate dt = LocalDate.parse(d, format);
            Date today = smf.parse(td);
            givenDate = smf.parse(d);

            if(givenDate.after(today) || givenDate.equals(today)){
                return false;
            }
            else{
                return true;
            }
        }
        catch(DateTimeParseException | ParseException p){
            return true;
        }
    }
    public boolean nameLength(String n){
        if(n.length()>20 || n.length() <= 0){
            return true;
        }
        return false;
    }
}
