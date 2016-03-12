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
public interface EquityComponent {
    public void add(EquityComponent e);
    public void remove(EquityComponent e);
    public String getTickerSymbol();
    public String getEquityName();
    public double getValuePerShare();
    public ArrayList<String> getSectors();
    public ArrayList<String> getIndices();
    
}
