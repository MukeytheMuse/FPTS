/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

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
    
    public String toString() {
        Date theDate = c.getDateAdded();
        LocalDate localDate = theDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        String theDateString = ( localDate.getMonthValue() + "/" + 
                localDate.getDayOfMonth()+ 
                "/" + localDate.getYear() );
        return "Deposited " + amount + " to " + c.getAccountName() + " on " + theDateString;
    }
    
}
