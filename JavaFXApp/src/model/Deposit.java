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
public class Deposit implements Transaction {
    private CashAccount c;
    private double amount;
    
    public Deposit(CashAccount c, double amount) {
        this.c = c;
        this.amount = amount;
    }
    
    public void execute() {
        c.deposit(amount);
    }
    
}
