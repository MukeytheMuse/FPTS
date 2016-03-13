package model;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

/**
 * Performs a withdrawal at a given amount on a given CashAccount when
 * called to do so
 *
 * @author Eric Epstein
 */
public class Withdrawal implements Transaction {

    private CashAccount c;
    private double amount;

    /**
     * Constructs a Withdrawal command
     *
     * @param c
     * @param amount
     */
    public Withdrawal(CashAccount c, double amount) {
        this.c = c;
        this.amount = amount;
    }

    /**
     * invokes operation
     */
    public void execute() {
        c.withdraw(amount);
    }

    /**
     * returns a String representation for display
     *
     * @return String
     */
    public String toString() {
        Date theDate = c.getDateAdded();
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

}
