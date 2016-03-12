/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import gui.FPTS;
import java.util.Observable;
import java.util.Observer;
import model.CashAccount;

/**
 *
 * @author ericepstein
 */
abstract public class CashAccountAlgorithm implements Observer {
    
    protected CashAccount c;
    protected FPTS theFPTS;
    
    public void process(FPTS theFPTS) {
        c = new CashAccount("",0,null);
        this.theFPTS = theFPTS;
        CashAccountFinder caFinder = new CashAccountFinder(theFPTS, c);
        caFinder.addObserver(this);
    }
  
    public void update(Observable o, Object args) {
        action();
    }
    
    abstract void action();
    
}
