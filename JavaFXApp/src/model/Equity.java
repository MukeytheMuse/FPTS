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
public class Equity implements Holding, Searchable, Simulatable {
    
    private String tickerSymbol;//index 0 in the csv file
    private String equityName;//index 1 in the csv file
    private double initialPricePerShare;//index 2 in the csv file
    //index 3 in the csv file: 0 or more market indices or sectors that the equity is in. (aka may not be there)

    private String equityType;//STOCK, BOND OR MUTUAL FUND HOLDING
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
    public Equity(String tickerSymbol, String equityName, ArrayList<String> indices, ArrayList<String> sectors, int sharesHeld, double currentPricePerShare, Date acquisitionDate){
        this.tickerSymbol = tickerSymbol;
        this.equityName = equityName;
        this.sharesHeld = sharesHeld;
        this.currentPricePerShare = currentPricePerShare;
        this.currentValue = sharesHeld * currentPricePerShare;
        this.acquisitionDate = acquisitionDate;
        //this.cashAccount = cashAccount;
        //extras = new ArrayList<String>();
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
    
    public String getDisplayName() {
        return tickerSymbol;
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
    
    @Override
    public int getSharesHeld(){
        return sharesHeld;
    }
    
    public double getCurrentPricePerShare(){
        return currentPricePerShare;
    }
    
    @Override
    public double getValue(){
        return currentValue;
    }
    
    @Override
    public double getCurrentValue() {
        return currentValue;
    }
    
    @Override
    public ArrayList<String> getSectors() {
        return new ArrayList<String>();
    }
    
    @Override
    public ArrayList<String> getIndices() {
        return new ArrayList<String>();
    }
    
    
    public void add(Holding h){
        //this is a leaf node so this method is not applicable to this class        
    }
    
    public void delete(Holding h){
        //this is a leaf node so this method is not applicable to this class
        
    }
    
    public void delete(Simulatable s) {
        //none
    }
    
    public void add(Simulatable s) {
        //none
    }
    
    
}
