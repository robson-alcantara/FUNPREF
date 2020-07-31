/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package funpref.controller;

import java.time.Period;

/**
 *
 * @author robson
 */
public class Utils {
    
    public static String decodePeriod(Period period) {
        
        String periodString = "";
        
        if( period.getYears() > 0 ) {
            periodString += "" + period.getYears() + " anos";
        }
        
        if( period.getMonths() > 0 ) {
            
            if( !periodString.isEmpty() ) {
                
                if( ( period.getYears() > 0 ) && ( period.getDays() == 0 ) ) {
                    periodString += " e ";
                }
                
                else {                
                    periodString += ", ";
                }
            }
            
            periodString += "" + period.getMonths() + " meses";
        }    
        
        if( period.getDays() > 0 ) {
            
            if( !periodString.isEmpty() ) {
                periodString += " e ";
            }
            
            periodString += "" + period.getDays() + " dias";
        }         
        
        return periodString;
    }    
}
