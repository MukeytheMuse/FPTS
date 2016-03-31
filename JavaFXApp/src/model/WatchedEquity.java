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
    }
    
    public String toString() {
        return assocEquity.getDisplayName();
    }
    
}
