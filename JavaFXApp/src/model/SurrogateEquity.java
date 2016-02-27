/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

/**
 *
 * @author ericepstein
 */
public class SurrogateEquity implements Holding {
    public double currentValue;
    
    //leaf of a composite pattern
    public void add(Holding h){
        //this is a leaf node so this method is not applicable to this class        
    }
    
    public void delete(Holding h){
        //this is a leaf node so this method is not applicable to this class
    }
    public double getValue() {
        return currentValue;
    }
}
