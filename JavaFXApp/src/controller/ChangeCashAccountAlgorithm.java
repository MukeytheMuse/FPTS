/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.util.ArrayList;
import java.util.Observable;

/**
 *
 * @author ericepstein
 */
abstract public class ChangeCashAccountAlgorithm extends CashAccountAlgorithm {
    
    protected ArrayList<Double> amounts;
    
    public void action() {
        amounts = new ArrayList<Double>();
        AmountInput amountInput = new AmountInput(theFPTS, amounts);
        amountInput.addObserver(this);
    }
    
    public void update(Observable o, Object args) {
        if (amounts != null) {
            performTransaction();
            
        } else {
            super.update(o, args);
        }
    }
    
    abstract void performTransaction();
    
}
