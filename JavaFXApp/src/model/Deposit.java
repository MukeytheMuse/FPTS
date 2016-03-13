package model;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

/**
 *
 * Performs a deposit at a given amount on a given 
 * CashAccount when called to do so 
 * 
 * @author Eric Epstein
 */
public class Deposit implements Transaction {
    private CashAccount c;
    private double amount;
    
    /**
     * 
     * Constructs a Deposit command
     * 
     * @param c
     * @param amount 
     */
    public Deposit(CashAccount c, double amount) {
        this.c = c;
        this.amount = amount;
    }
    
    /**
     * Executes the deposit
     */
    public void execute() {
        c.deposit(amount);
    }
    
    /**
     * 
     * returns a String representation for display
     * 
     * @return String
     */
    public String toString() {
        Date theDate = c.getDateAdded();
        LocalDate localDate = theDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        String theDateString = ( localDate.getMonthValue() + "/" + 
                localDate.getDayOfMonth()+ 
                "/" + localDate.getYear() );
        return "Deposited " + amount + " to " + c.getAccountName() + " on " + theDateString;
    }
    
    /**
     * 
     * returns associated CashAccount
     * 
     * @return CashAccount
     */
    public CashAccount getCashAccount() {
        return c;
    }
    
}
