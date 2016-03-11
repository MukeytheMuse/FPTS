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
public class CashAccountSearcher extends Searcher {
        public ArrayList<ArrayList<String>> getHoldingStrings(Searchable s) {
        
        //ArrayList<ArrayList<ArrayList<String>>> allObjects = new ArrayList<ArrayList<ArrayList<String>>>();
        
        //ArrayList<ArrayList<String>> anObject = new ArrayList<ArrayList<String>>();
        
        ArrayList<String> anItem = new ArrayList<String>();
        
        ArrayList<String> holdingStrings = new ArrayList<String>();
        CashAccount ac = (CashAccount)(Object)s;
        //for (Simulatable eq : (ArrayList<Simulatable>)(Object)toBeSearched) {
            ArrayList<ArrayList<String>> anObject = new ArrayList<ArrayList<String>>(); 
            
            //String tickerSymbol = eq.getTickerSymbol();
            ArrayList<String> cashAccountItem = new ArrayList<String>();
            cashAccountItem.add(ac.getAccountName());
            anObject.add(cashAccountItem);
            
            /*
            ArrayList<String> equityNameItem = new ArrayList<String>();
            equityNameItem.add(ac.getEquityName());
            anObject.add(equityNameItem);
            
            ArrayList<String> indices = eq.getIndices();
            anObject.add(indices);
            ArrayList<String> sectors = eq.getSectors();
            anObject.add(sectors);
            //allObjects.add(anObject);
            */
        //}
        //return allObjects;
        return anObject;
    }
}
