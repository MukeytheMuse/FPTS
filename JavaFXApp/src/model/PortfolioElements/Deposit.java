package model.PortfolioElements;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

/**
 * Performs a deposit at a given amount on a given
 * CashAccount when called to do so
 *
 * @author Eric Epstein
 */
public class Deposit implements Transaction {
    private CashAccount c;
    private double amount;
    private Date date;
    private String cashAcctName;

    /**
     * Constructs a Deposit command
     *
     * @param c
     * @param amount
     */
    public Deposit(CashAccount c, double amount) {
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
    @Override
    public void setCashAccount(CashAccount ca) {
        c = ca;
        cashAcctName = c.getAccountName();
    }

    /**
     * Executes the deposit
     */
    public void execute() {
        c.deposit(amount);
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
     * returns associated CashAccount
     *
     * @return CashAccount
     */
    public CashAccount getCashAccount() {
        return c;
    }

}
