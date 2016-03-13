package model;

import java.util.ArrayList;
import model.DataBase.ReadFile;

/**
 * Searchable and available to be purchased by the user. 
 * Contains information about the ticker symbol, equity name, 
 * the value per share, the market sector, and the index.
 * 
 * @author Epstein & Ian London
 */
public class Equity implements Searchable, EquityComponent, HoldingUpdatable {
    
    /*
    * the identifying symbol
    */
    private String tickerSymbol;
    
    /*
    * the identifying name
    */
    private String equityName;
    
    /*
    * price per share
    */
    private double pricePerShare;
    
    /*
    * collection of indices
    */
    public ArrayList<String> indices;
    
    /*
    * collection of sectors
    */
    public ArrayList<String> sectors;

    /*
    * collection of Equity read from input
    */
    public static ArrayList<EquityComponent> EquityList = ReadFile.readEquity();

    /*
    * collection of Equity that matches a search criteria
    */
    private ArrayList<Equity> matches;
    
    /*
    * getEquityList() returns equities 
    */
    public static ArrayList<EquityComponent> getEquityList() {
        return EquityList;
    }

    /*
    * Equity constructor takes 5 parameters
    * 
    * @params : tickerSymbol - str
    *            equityName - str
    *           perShareValue - double
    *           indices - ArrayList<String>
    *           sectors - ArrayList<String>
    */
    public Equity(String tickerSymbol, String equityName, double perShareValue, ArrayList<String> indices, ArrayList<String> sectors) {
        this.tickerSymbol = tickerSymbol;
        this.equityName = equityName;
        this.pricePerShare = perShareValue;
        this.indices = indices;
        this.sectors = sectors;
    }
    
    /*
    * getDisplayName returns ticker symbol
    *
    * @return tickerSymbol - String
    */
    public String getDisplayName() {
        return tickerSymbol;
    }

    /**
    * getValuePerShare returns price per share
    *
    * @return pricePerShare - double
    */
    @Override
    public double getValuePerShare() {
        return pricePerShare;
    }
   
    /**
    * getEquityName returns equity name
    *
    * @return equityName - String
    */
    @Override
    public String getEquityName() {
        return equityName;
    }
    
    /**
     * 
     * @return equityName - String
     */
    @Override
    public String getHoldingName() {
        return equityName;
    }
    
    /**
     * 
     * @return tickerSymbol - String 
     */
    @Override
    public String getTickerSymbol() {
        return tickerSymbol;
    }
    
    /**
     * 
     * @return sectors - ArrayList<String>
     */
    @Override
    public ArrayList<String> getSectors() {
        return sectors;
    }
    
    /**
     * 
     * @return  indices - ArrayList<String>
     */
    @Override
    public ArrayList<String> getIndices() {
        return indices;
    }
    
    /**
     * 
     * @param e - EquityComponent
     * 
     * overrides EquityComponent but does nothing because
     * Equity is a leaf node
     */
    @Override
    public void add(EquityComponent e) { }
    
    /**
     * 
     * @param e - EquityComponent
     * 
     * overrides EquityComponent but does nothing because
     * Equity is a leaf node
     */
    @Override
    public void remove(EquityComponent e) { }
}
