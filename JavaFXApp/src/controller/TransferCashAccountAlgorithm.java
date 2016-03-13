/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Observable;
import model.CashAccount;
import model.Deposit;
import model.Transaction;
import model.Withdrawal;

/**
 *
 * @author ericepstein
 */
public class TransferCashAccountAlgorithm extends CashAccountAlgorithm {
    
    CashAccount c2;
    protected ArrayList<Double> amounts;
   
    static int numCalled = 0;
    
    public void action() {
        c2 = new CashAccount("",0,null);
        CashAccountFinder caFinder = new CashAccountFinder(theFPTS, c2);
        caFinder.addObserver(this);  
    }
    
    public void update(Observable o, Object args) {
        
        numCalled++;
        
        switch (numCalled) {
            case(1) :   super.update(o, args);
                        break;
            case(2) :   getAmountInput();
                        break;
            case(3) :   performTransaction();
                        break;
        }
    }
  
    public void getAmountInput() {
        amounts = new ArrayList<Double>();
        AmountInput amountInput = new AmountInput(theFPTS, amounts);
        amountInput.addObserver(this);
    }
    
    public void performTransaction() {
       
        double amount = amounts.get(0);
        if (c.getValue() >= amount) {
            Transaction t = new Withdrawal(c, amount);
            theFPTS.getPortfolio().add(t);
            t = new Deposit(c2, amount);
            theFPTS.getPortfolio().add(t);
            theFPTS.getStage().setScene(theFPTS.getConfirmationScene());
        } else {
            theFPTS.getStage().setScene(theFPTS.getErrorScene());
        }
    
        numCalled = 0;
        
        
    }
    
}
