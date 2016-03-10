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
public class Equity implements Holding {
    
    private String tickerSymbol;
    private String equityName;
    private int sharesHeld;
    private double currentPricePerShare;
    private double currentValue;
    private boolean cashAccount;
    private Date acquisitionDate;
    private ArrayList<String> extras;
    
    /**
    * Constructor used when a user manually adds a Equity.
    * 
    * @author ericepstein & kaitlin
    */
    public Equity(String tickerSymbol, String equityName, int sharesHeld, double currentPricePerShare, double currentValue, Date acquisitionDate, boolean cashAccount ){
        this.tickerSymbol = tickerSymbol;
        this.equityName = equityName;
        this.sharesHeld = sharesHeld;
        this.currentPricePerShare = currentPricePerShare;
        this.currentValue = currentValue;
        this.acquisitionDate = acquisitionDate;
        this.cashAccount = cashAccount;
        extras = new ArrayList<String>();
    }
    
    /**
    * Constructor that is used when this object is created from reading the CSV file.
    * 
    * @author ericepstein & kaitlin
    */
    public Equity(String tickerSymbol, String equityName, double currentPricePerShare, double currentValue){
        this.tickerSymbol = tickerSymbol;
        this.equityName = equityName;
        this.currentPricePerShare = currentPricePerShare;
        this.currentValue = currentValue;
    }
    
    public String getSymbol() {
        return tickerSymbol;
    }
    
    public String toString() {
        return tickerSymbol + "\t" + equityName + "\t" + currentPricePerShare; 
    }
    
    public String getTickerSymbol(){
        return tickerSymbol;
    }
    
    public String getEquityName(){
        return equityName;
    }
    
    public int getSharesHeld(){
        return sharesHeld;
    }
    
    public double getCurrentPricePerShare(){
        return currentPricePerShare;
    }
    
    public double getValue(){
        return currentValue;
    }
    
    public void add(Holding h){
        //this is a leaf node so this method is not applicable to this class        
    }
    
    public void delete(Holding h){
        //this is a leaf node so this method is not applicable to this class
        
    }
    
    
}
