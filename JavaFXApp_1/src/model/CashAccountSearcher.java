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
        public ArrayList<ArrayList<String>> getSearchableStrings(Searchable s) {
            ArrayList<String> searchableStrings = new ArrayList<String>();
            CashAccount ac = (CashAccount)(Object)s;
            ArrayList<ArrayList<String>> anObject = new ArrayList<ArrayList<String>>(); 
            ArrayList<String> cashAccountItem = new ArrayList<String>();
            cashAccountItem.add(ac.getAccountName());
            anObject.add(cashAccountItem);
            return anObject;
    }
}
