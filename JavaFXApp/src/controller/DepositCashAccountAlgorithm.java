/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

/**
 *
 * @author ericepstein
 */
public class DepositCashAccountAlgorithm extends ChangeCashAccountAlgorithm {
    
    public void performTransaction() {
        double amount = amounts.get(0);
        c.deposit(amount);
        
        theFPTS.getStage().setScene(theFPTS.getHomeScene());
    }
    
}
