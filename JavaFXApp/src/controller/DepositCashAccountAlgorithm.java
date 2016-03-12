/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import model.Deposit;
import model.Transaction;

/**
 *
 * @author ericepstein
 */
public class DepositCashAccountAlgorithm extends ChangeCashAccountAlgorithm {
    
    public void performTransaction() {
        double amount = amounts.get(0);
        c.deposit(amount);
        
        Transaction t = new Deposit(c, amount);
        theFPTS.getPortfolio().add(t);
        theFPTS.getStage().setScene(theFPTS.getConfirmationScene());
    }
    
}
