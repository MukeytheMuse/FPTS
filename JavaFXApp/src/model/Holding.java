/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

/**
 *
 * @author ericepstein
 * 
 * Equity, SurrogateEquity and CashAccount implement this interface.
 */
public interface Holding {
    public void add(Holding h);
    public void delete(Holding h);
    public double getValue();
}

