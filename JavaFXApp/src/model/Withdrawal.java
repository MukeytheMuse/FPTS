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
public class Withdrawal implements Transaction {
    
    private CashAccount c;
    private double amount;
    
    public Withdrawal(CashAccount c, double amount) {
        this.c = c;
        this.amount = amount;
    }
    
    public void execute() {
        c.withdraw(amount);
    }
    
    public String toString() {
        Date theDate = c.getDateAdded();
        LocalDate localDate = theDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        String theDateString = ( localDate.getMonthValue() + "/" + 
                localDate.getDayOfMonth()+ 
                "/" + localDate.getYear() );
        return "Withdrew " + amount + " from " + c.getAccountName() + " on " + theDateString;
    }
    
}
