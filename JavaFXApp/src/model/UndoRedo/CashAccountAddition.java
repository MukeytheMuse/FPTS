/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.UndoRedo;

import java.util.ArrayList;
import java.util.Date;
import model.Equities.EquityComponent;
import model.PortfolioElements.CashAccount;
import model.PortfolioElements.CashAccounts;
import model.PortfolioElements.Holding;
import model.PortfolioElements.Holdings;

/**
 *
 * @author ericepstein
 */
public class CashAccountAddition implements Command {

    CashAccounts cashAccounts;
    CashAccount c;
    
    public CashAccountAddition(CashAccounts cashAccounts, CashAccount c) {
        this.c = c;
        this.cashAccounts = cashAccounts;
    }
    
    @Override
    public void execute() {
        if (cashAccounts.contains(c)) {
            double amount = c.getValue();
            c.deposit(amount);
        } else {
            cashAccounts.add(c);
        }
    }

    @Override
    public void undo() {
        cashAccounts.remove(c);
  
    }

    @Override
    public void addChild(Command c) {
        throw new UnsupportedOperationException("Not supported."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void removeChild(Command c) {
        throw new UnsupportedOperationException("Not supported."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
