/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.UndoRedo;

import model.PortfolioElements.CashAccount;
import model.PortfolioElements.Transaction;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

/**
 *
 * @author Eric Epstein
 */
public class Deposit implements Command, Transaction {

    private CashAccount c;
    double amount;

    Date date;
    String cashAcctName;
    
    public Deposit(CashAccount c, double amount) {
        this.c = c;
        this.amount = amount;
        this.date = new Date();
        this.cashAcctName = c.getAccountName();
    }
    
    @Override
    public void execute() {
        c.deposit(amount);
    }

    @Override
    public void undo() {
        c.withdraw(amount);
    }

    @Override
    public void addChild(Command c) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void removeChild(Command c) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }


    /**
     * Used when fillUsers method is called.
     *
     * @param amount Author(s): Kaitlin Brockway
     */
    public Deposit(double amount, Date date) {
        this.amount = amount;
        this.date = date;
    }

    /**
     * Used when fillUsers method is called.
     * To set the associated cash account if a cash account still exists.
     *
     * @param ca
     */
    //@Override
    public void setCashAccount(CashAccount ca) {
        c = ca;
        cashAcctName = c.getAccountName();
    }

    @Override
    public void setCashAccountName(String cashAccountName) {
        cashAcctName = cashAccountName;
    }

    @Override
    public double getAmount() {
        return amount;
    }

    @Override
    public Date getDateMade() {
        return date;
    }

    @Override
    public String getType() {
        return "Deposit";
    }

    @Override
    public String getCashAccountName() {
        return cashAcctName;
    }

    /**
     * returns a String representation for display
     *
     * @return String
     */
    public String toString() {
        //Date theDate = c.getDateAdded();//TODO CHANGE THIS TO DATE
        Date theDate = new Date();//TODO Get rid of this.
        LocalDate localDate = theDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        String theDateString = (localDate.getMonthValue() + "/" +
                localDate.getDayOfMonth() +
                "/" + localDate.getYear());
        return "Deposited " + amount + " to " + c.getAccountName() + " on " + theDateString;
    }

    /**
     * returns CashAccount associated with Withdrawal
     *
     * @return CashAccount
     */
    public CashAccount getCashAccount() {
        return c;
    }



    
}
