package model.UndoRedo;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import model.PortfolioElements.CashAccount;
import model.PortfolioElements.Transaction;
import model.UndoRedo.Command;

/**
 * Performs a withdrawal at a given amount on a given CashAccount when
 * called to do so
 *
 * @author Eric Epstein
 */
public class Withdrawal implements Command, Transaction {

    private CashAccount c;
    private double amount;
    private Date date;
    private String cashAcctName;

    /**
     * Constructs a Withdrawal command
     *
     * @param c
     * @param amount
     */
    public Withdrawal(CashAccount c, double amount) {
        this.c = c;
        this.amount = amount;
        this.date = new Date();
        this.cashAcctName = c.getAccountName();
    }


    /**
     * Used when fillUsers method is called.
     *
     * @param amount Author(s): Kaitlin Brockway
     */
    public Withdrawal(double amount, Date date) {
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


    /**
     * invokes operation
     */
    public void execute() {
        c.withdraw(amount);
    }

    //@Override
    public double getAmount() {
        return amount;
    }

    //@Override
    public Date getDateMade() {
        return date;
    }

    @Override
    public String getType() {
        return "Withdrawal";
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
        return "Withdrew " + amount + " from " + c.getAccountName() + " on " + theDateString;
    }

    /**
     * returns CashAccount associated with Withdrawal
     *
     * @return CashAccount
     */
    public CashAccount getCashAccount() {
        return c;
    }

    @Override
    public void undo() {
        c.deposit(amount);
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

