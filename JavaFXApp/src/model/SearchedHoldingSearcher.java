/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.util.ArrayList;
import gui.FPTS;

/**
 *
 * @author ericepstein
 */
public class SearchedHoldingSearcher extends Searcher {
    
    public ArrayList<ArrayList<String>> getSearchableStrings(Searchable s) {
        
        //ArrayList<ArrayList<ArrayList<String>>> allObjects = new ArrayList<ArrayList<ArrayList<String>>>();
        
        //ArrayList<ArrayList<String>> anObject = new ArrayList<ArrayList<String>>();
        
        ArrayList<String> anItem = new ArrayList<String>();
        
        ArrayList<String> holdingStrings = new ArrayList<String>();
        HoldingUpdatable eq = (HoldingUpdatable)(Object)s;
        //for (Simulatable eq : (ArrayList<Simulatable>)(Object)toBeSearched) {
            ArrayList<ArrayList<String>> anObject = new ArrayList<ArrayList<String>>(); 
            
            //String tickerSymbol = eq.getTickerSymbol();
            ArrayList<String> tickerSymbolItem = new ArrayList<String>();
            tickerSymbolItem.add(eq.getTickerSymbol());
            anObject.add(tickerSymbolItem);
            
            ArrayList<String> holdingNameItem = new ArrayList<String>();
            holdingNameItem.add(eq.getHoldingName());
            anObject.add(holdingNameItem);
            
            ArrayList<String> indices = eq.getIndices();
            anObject.add(indices);
            ArrayList<String> sectors = eq.getSectors();
            anObject.add(sectors);
            
        return anObject;
    }
    
    
    
}
