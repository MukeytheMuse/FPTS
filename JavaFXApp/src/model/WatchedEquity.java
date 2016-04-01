/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

/**
 *
 * @author ericepstein
 */
public class WatchedEquity {
    
    EquityComponent assocEquity;
    double highTrigger;
    double lowTrigger;
    
    double previousPrice;
   
    boolean exceedsTrigger;
    boolean hasExceededTrigger;
    boolean notMeetsTrigger;
    boolean hasNotMetTrigger;
    
    
    public WatchedEquity(EquityComponent ec, double highTrigger, double lowTrigger) {
        assocEquity = ec;
        this.highTrigger = highTrigger;
        this.lowTrigger = lowTrigger;
        
        System.out.println("HIGH TRIGGER IS " + highTrigger);
        
        previousPrice = ec.getValuePerShare();
        
        System.out.println("PREVIOUS PRICE IS " + previousPrice);
        
        if (highTrigger == -1) {
            exceedsTrigger = false;
            hasExceededTrigger = false;
        } else if (previousPrice > highTrigger) {
            exceedsTrigger = true;
            hasExceededTrigger = true;
        } else {
            exceedsTrigger = false;
            hasExceededTrigger = false;
        }
        
        if (lowTrigger == -1) {
            notMeetsTrigger = false;
            hasNotMetTrigger = false;
        } else if (previousPrice < lowTrigger) {
            notMeetsTrigger = true;
            hasNotMetTrigger = true;
        } else {
            notMeetsTrigger = false;
            hasNotMetTrigger = false;
        }
        
    }
    
    public String toString() {
        String status = "";
        if (exceedsTrigger) { 
            status += " | Currently exceeds trigger";
        } else if (hasExceededTrigger) {
            status += " | Had exceeded trigger";
        }
        
        if (notMeetsTrigger) {
            status += " | Currently below low trigger";
        } else if (hasNotMetTrigger) {
            status += " | Had been below low trigger";
        }
        
        return assocEquity.getDisplayName() + status;
    }
    
}
