/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.util.ArrayList;
import javafx.collections.ObservableList;
import javafx.scene.Node;

/**
 *
 * @author ericepstein
 */
public class LoadedEquitySearcher extends Searcher {
    
    /*
    public void define(ObservableList<Node> queries, ArrayList<T> toBeSearched) {
        redefine(queries, toBeSearched);
    }
    */
    public ArrayList<Searchable> getToBeSearched() {
        //change such thatit may get the toBeSearched
        return new ArrayList<Searchable>();
    }
    
   
    
    public ArrayList<String> getHoldingStrings() {
        ArrayList<String> holdingStrings = new ArrayList<String>();
        for (LoadedEquity eq : (ArrayList<LoadedEquity>)(Object)toBeSearched) {
            holdingStrings.add(eq.getTickerSymbol());
            holdingStrings.add(eq.getEquityName());
        }
        return holdingStrings;
    }
    
    /*
    public ArrayList<LoadedEquity> getMatches() {
        return matches;
    }
    */
    
    
    
    
}
