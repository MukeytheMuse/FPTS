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
public class Holding implements Searchable, HoldingUpdatable {
    
    private String tickerSymbol;//index 0 in the csv file
    private String holdingName;//index 1 in the csv file
    private double initialPricePerShare;//index 2 in the csv file
    //index 3 in the csv file: 0 or more market indices or sectors that the equity is in. (aka may not be there)

    private int numOfShares;
    private double valuePerShare;
    private double currentValue;
    private boolean cashAccount;
    private Date acquisitionDate;
    private ArrayList<String> indices;
    private ArrayList<String> sectors;
    
    /**
    * Constructor used when a user manually adds a Holding.
    * 
    * @author ericepstein & kaitlin
    */
    public Holding(String tickerSymbol, String equityName, ArrayList<String> indices, ArrayList<String> sectors, int numOfShares, double valuePerShare, Date acquisitionDate){
        this.tickerSymbol = tickerSymbol;
        this.holdingName = equityName;
        this.numOfShares = numOfShares;
        this.valuePerShare = valuePerShare;
        this.currentValue = numOfShares * valuePerShare;
        this.acquisitionDate = acquisitionDate;
        //this.cashAccount = cashAccount;
        //extras = new ArrayList<String>();
    }
    
    
    /**
    * Constructor that is used when this object is created from reading the CSV file.
    * 
    * 
    * THIS IS FOR LOADEDEQUITY OBJECT
    * 
    * @author ericepstein & kaitlin
    */
    /*
    public Holding(String tickerSymbol, String equityName, float currentPricePerShare, double currentValue){
        this.tickerSymbol = tickerSymbol;
        this.equityName = equityName;
        this.currentPricePerShare = currentPricePerShare;
        this.currentValue = currentValue;
    }
    */
    
    public double getValuePerShare() {
        return valuePerShare;
    }
    
    public String getDisplayName() {
        return tickerSymbol;
    }
    
    public String getSymbol() {
        return tickerSymbol;
    }
    
    public String toString() {
        return tickerSymbol + "\t" + holdingName + "\t" + valuePerShare; 
    }
    
    public String getTickerSymbol(){
        return tickerSymbol;
    }
    
    public String getHoldingName(){
        return holdingName;
    }
    
  
    public int getNumOfShares(){
        return numOfShares;
    }
    
 
    public double getValue(){
        return currentValue;
    }
    

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
    
    public void addShares(int numOfSharesAdded) {
        numOfShares += numOfSharesAdded;
        currentValue += (numOfSharesAdded * valuePerShare);
    }
    
    public void subtractShares(int numOfSharesSubtracted) {
        numOfShares -= numOfSharesSubtracted;
        currentValue -= (numOfSharesSubtracted * valuePerShare);
    }
    
    
}
