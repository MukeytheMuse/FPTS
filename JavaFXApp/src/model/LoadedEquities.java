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
public class LoadedEquities {
    private ArrayList<LoadedEquity> loadedEquities;
    
    public LoadedEquities() {
        //convert each row to Equity objects
        loadedEquities = new ArrayList<LoadedEquity>();
        
    }
    
    public ArrayList<LoadedEquity> getLoadedEquities() {
        return loadedEquities;
    }
    
    
    
}
