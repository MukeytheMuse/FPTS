/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;


import java.util.Date;

import model.CashAccount;
import gui.FPTS;
import java.util.Observable;
import java.util.Observer;


/**
 *
 * @author ericepstein
 */
public class RemoveCashAccountAlgorithm extends CashAccountAlgorithm /* implements Observer */ {
    
    public void action() {
        theFPTS.getPortfolio().remove(c);
        theFPTS.getStage().setScene(theFPTS.getHomeScene());
    }
    
}
