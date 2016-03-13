/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.util.ArrayList;

/**
 * Defines one step in the Searcher that converts the object being
 * searched into a string representation of a CashAccount.
 *
 * @author Eric Epstein
 */
public class CashAccountSearcher extends Searcher {

    /**
     * Casts the Searchable object into CashAccount and provides information
     * as strings in ArrayList of one-element ArrayLists.
     *
     * @param s - Searchable
     * @return
     */
    public ArrayList<ArrayList<String>> getSearchableStrings(Searchable s) {
        ArrayList<String> searchableStrings = new ArrayList<String>();
        CashAccount ac = (CashAccount) (Object) s;
        ArrayList<ArrayList<String>> anObject = new ArrayList<ArrayList<String>>();
        ArrayList<String> cashAccountItem = new ArrayList<String>();
        cashAccountItem.add(ac.getAccountName());
        anObject.add(cashAccountItem);
        return anObject;
    }
}
