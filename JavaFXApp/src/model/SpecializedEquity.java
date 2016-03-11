/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.util.ArrayList;
import java.util.Date;

/**
 *
 * @author ericepstein
 */
public class SpecializedEquity implements Simulatable {
    
    private String tickerSymbol;
    private String equityName;
    private int sharesHeld;
    private double currentPricePerShare;
    private double currentValue;
    private boolean cashAccount;
    private Date acquisitionDate;
    
    /**
    * Constructor used when a user manually adds a Equity.
    * 
    * @author ericepstein & kaitlin
    */
    public SpecializedEquity(String tickerSymbol, String equityName, int sharesHeld, double currentPricePerShare, double currentValue, Date acquisitionDate, boolean cashAccount ){
        this.tickerSymbol = tickerSymbol;
        this.equityName = equityName;
        this.sharesHeld = sharesHeld;
        this.currentPricePerShare = currentPricePerShare;
        this.currentValue = currentValue;
        this.acquisitionDate = acquisitionDate;
        this.cashAccount = cashAccount;
    }
    
    public double getPricePerShare() {
        return currentPricePerShare;
    }
    
    public String getEquityName() {
        return equityName;
    }
    
    public String getDisplayName() {
        return tickerSymbol;
    }
    
    public ArrayList<String> getIndices() {
        return new ArrayList<String>();
    }
    
    public ArrayList<String> getSectors() {
        return new ArrayList<String>();
    }
    
    public int getSharesHeld() {
        return sharesHeld;
    }
    
    public double getCurrentValue() {
        return currentValue;
    }
    
    public String getTickerSymbol() {
        return tickerSymbol;
    }
    
    public String getSymbol() {
        return "";
    }
    
    public double getValue() {
        return 0;
    }
    
    public void add(Simulatable s) {
        //none
    }
    
    public void delete(Simulatable s) {
        //none
    }
    
}
