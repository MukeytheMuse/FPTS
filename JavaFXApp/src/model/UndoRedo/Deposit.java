/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.UndoRedo;

import model.PortfolioElements.CashAccount;

/**
 *
 * @author ericepstein
 */
public class Deposit implements Command {

    private CashAccount c;
    double amount;
    
    public Deposit(CashAccount c, double amount) {
        this.c = c;
        this.amount = amount;
    }
    
    @Override
    public void execute() {

    }

    @Override
    public void undo() {
        
    }

    @Override
    public void addChild(Command c) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void removeChild(Command c) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
