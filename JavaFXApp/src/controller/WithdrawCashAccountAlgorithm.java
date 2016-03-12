/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import model.Transaction;
import model.Withdrawal;

/**
 *
 * @author ericepstein
 */
public class WithdrawCashAccountAlgorithm extends ChangeCashAccountAlgorithm {
    
    public void performTransaction() {
        
        double amount = amounts.get(0);
        
        if ( c.getValue() >= amount) {
            Transaction t = new Withdrawal(c, amount);
            theFPTS.getPortfolio().add(t);
            
            theFPTS.getStage().setScene(theFPTS.getConfirmationScene());
        } else {
            theFPTS.getStage().setScene(theFPTS.getErrorScene());
        }
    }
    
}
