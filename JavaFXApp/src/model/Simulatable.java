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
public interface Simulatable extends Searchable {
    
    public void add(Simulatable s);
    public void delete(Simulatable s);
    public double getValue();
    public String getSymbol();
    
    public String getEquityName();
    public ArrayList<String> getIndices();
    public ArrayList<String> getSectors();
    public String getTickerSymbol();
    public double getPricePerShare();
    public double getCurrentValue();
    public int getSharesHeld();
    
    
}
