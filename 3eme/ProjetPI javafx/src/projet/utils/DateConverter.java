/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projet.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;

/**
 *
 * @author Samplon
 */
public class DateConverter {
    
      private static SimpleDateFormat formater = null;
     private static Date Ddate=null;
     private static String Sdate=null;
     private static LocalDate LDdate=null;
    
    public static String Date_Now_As_String() {
        Ddate = new Date();
        formater = new SimpleDateFormat("yyyy-MM-dd");
        return formater.format(Ddate);
    }
    
    
    public static Date Date_Now_As_Date() {
        
        Ddate = new Date();
        return Ddate;
    }
    
    /**
     * Format must be like year-mounth-day
     * Exemple 2000-09-21
     * @param dateToConvert
     * @return 
     */
   public static Date String_To_Date(String dateToConvert) {
        try {
            formater = new SimpleDateFormat("yyyy-MM-dd");
            Ddate = formater.parse(dateToConvert);
      
    } catch (ParseException e) {      }
        return Ddate;
    }
   
   
    public static String Date_To_String(Date dateToConvert) {
        formater = new SimpleDateFormat("yyyy-MM-dd");
          Sdate = formater.format(dateToConvert);
        return Sdate;
    }
   
    public static String LocalDate_To_String(LocalDate dateToConvert) {
        formater = new SimpleDateFormat("yyyy-MM-dd");
          Sdate = formater.format(dateToConvert);
        return Sdate;
    }
    
    
    
    
    
}
