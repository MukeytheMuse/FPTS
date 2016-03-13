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
public class Equity implements Searchable, EquityComponent, HoldingUpdatable {
    
    private String tickerSymbol;
    private String equityName;
    double pricePerShare; //TODO: mark this private or public
    public ArrayList<String> indices;
    public ArrayList<String> sectors;

    //ArrayList of Equities for use within search functionality
    public static ArrayList<EquityComponent> EquityList = ReadFile.readEquity();

    private ArrayList<Equity> matches;

    //Return list of Equities to search through
    public static ArrayList<EquityComponent> getEquityList() {
        return EquityList;
    }

    
    public Equity(String tickerSymbol, String equityName, double perShareValue, ArrayList<String> indices, ArrayList<String> sectors) {
        this.tickerSymbol = tickerSymbol;
        this.equityName = equityName;
        this.pricePerShare = perShareValue;
        this.indices = indices;
        this.sectors = sectors;
    }
    
    public String getDisplayName() {
        return tickerSymbol;
    }

    public double getValuePerShare() {
        return pricePerShare;
    }
    
    public String getEquityName() {
        return equityName;
    }
    
    public String getHoldingName() {
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
    
    public void add(EquityComponent e) {
        //do nothing
    }
    
    public void remove(EquityComponent e) {
        //do nothing
    }
}
