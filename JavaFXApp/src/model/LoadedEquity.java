/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.util.ArrayList;
import model.DataBase.ReadFile;
/**
 *
 * @author ericepstein & Ian
 */
public class LoadedEquity implements Searchable, EquityUpdatable {
    
    private String tickerSymbol;
    private String equityName;
    double perShareValue; 
    ArrayList<String> indices; 
    ArrayList<String> sectors;

    //ArrayList of Equities for use within search functionality
    public static ArrayList<LoadedEquity> loadedEquityList;
    private ArrayList<LoadedEquity> matches;

    //Populate the loadedEquityList
    public static void makeEquityList() {
        loadedEquityList = ReadFile.loadEquityList();
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
    
    
    public float getValuePerShare() {
        return (float) perShareValue;
    }
    
    public String getEquityName() {
        return equityName;
    }
    
    public String getTickerSymbol() {
        return tickerSymbol;
    }
    
    public ArrayList<String> getSectors() {
        return sectors;
    }
    
    public ArrayList<String> getIndices() {
        return indices;
    }
    
    public boolean isMatch(String tickerSymbol, String equityName, String index, String sector) {
        boolean matched = true;
        
        boolean tickerSymbolMatched = false;
        if (! tickerSymbol.isEmpty()) {
            this.tickerSymbol.contains(tickerSymbol);//RIGHT NOW .contains is ignored
            tickerSymbolMatched = true;
        }
        
        boolean equityNameMatched = false;
        if (! equityName.isEmpty()) {
            this.equityName.contains(equityName);//RIGHT NOW .contains is ignored
            equityNameMatched = true;
        }
        if (! index.isEmpty()) {
            for (String anIndex : indices) {
                anIndex.contains(index);//RIGHT NOW .contains is ignored
            }
        }
        if (! sector.isEmpty()) {
            for (String aSector : sectors) {
                aSector.contains(sector);//RIGHT NOW .contains is ignored
            }
        }
        
        return matched;
        //TODO: Right now matched always returns true. We need to change this.
    }
}
