/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.util.ArrayList;

/**
 *
 * @author ericepstein
 */
public class LoadedEquity implements Searchable {
    
    private String tickerSymbol;
    private String equityName;
    double perShareValue; 
    ArrayList<String> indices; 
    ArrayList<String> sectors;

    //ArrayList of Equities for use within search functionality
    private static ArrayList<LoadedEquity> loadedEquityList;
    private ArrayList<LoadedEquity> matches;

    //Populate the Load
    public ArrayList<LoadedEquity> makeEquityList() {
        //TODO: PLEASE FILL ME IN
        
        //placeholder
        
        //
        return loadedEquityList;
    }

    //Return list of Equities to search through
    public ArrayList<LoadedEquity> getEquityList() {
        return loadedEquityList;
    }
    
    public LoadedEquity(String tickerSymbol, String equityName, double perShareValue, ArrayList<String> indices, ArrayList<String> sectors) {
        this.tickerSymbol = tickerSymbol;
        this.equityName = equityName;
        this.perShareValue = perShareValue;
        this.indices = indices;
        this.sectors = sectors;
    }
    
    public String getDisplayName() {
        return tickerSymbol;
    }
    
    public String getEquityName() {
        return equityName;
    }
    
    public String getTickerSymbol() {
        return tickerSymbol;
    }
    
    public boolean isMatch(String tickerSymbol, String equityName, String index, String sector) {
        boolean matched = true;
        
        boolean tickerSymbolMatched = false;
        if (! tickerSymbol.isEmpty()) {
            this.tickerSymbol.contains(tickerSymbol);
            tickerSymbolMatched = true;
        }
        
        boolean equityNameMatched = false;
        if (! equityName.isEmpty()) {
            this.equityName.contains(equityName);
            equityNameMatched = true;
        }
        if (! index.isEmpty()) {
            for (String anIndex : indices) {
                anIndex.contains(index);
            }
        }
        if (! sector.isEmpty()) {
            for (String aSector : sectors) {
                aSector.contains(sector);
            }
        }
        
        return matched;
    }
}
