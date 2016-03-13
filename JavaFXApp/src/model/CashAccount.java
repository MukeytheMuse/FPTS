package model;

import java.util.ArrayList;
import java.util.Date;

/**
 * Holds a user’s cash account details, including the account name,
 * creation date and total value that may change depending on various operations.
 *
 * @author Eric Epstein and Kaitlyn Brockway
 */
public class CashAccount implements Searchable {
    private String accountName;
    private double currentValue;
    private Date dateAdded;
    public static ArrayList<CashAccount> cashList = getCashList();

    /**
    * getCashList() returns cash accounts
    */
    public static ArrayList<CashAccount> getCashList() {
        return cashList;
    }

    /**
     * The system shall allow the user to specify a new cash account.
     * A user defines a cash account by specifying an account name,
     * initial amount, and the date it was added.
     *
     * @param AccountName   - String
     * @param initialAmount - double
     * @param dateAdded     - Date
     */
    public CashAccount(String AccountName, double initialAmount, Date dateAdded) {
        this.accountName = AccountName;
        currentValue = initialAmount;
        this.dateAdded = dateAdded;
    }

    /**
     * Returns the account name.
     * <p>
     * returns: String accountName
     */
    public String getAccountName() {
        return accountName;
    }

    /**
     * returns account name to override Searchable interface
     *
     * @return String
     */
    @Override
    public String getDisplayName() {
        return accountName;
    }

    /**
     * Returns value for display
     *
     * @return String
     */
    public String toString() {
        return "" + currentValue;
    }

    /**
     * Returns empty string indicating lack of symbol
     *
     * @return String
     */
    public String getSymbol() {
        return "";
    }

    /**
     * returns current value
     *
     * @return double
     */
    public double getValue() {
        return currentValue;
    }

    /**
     * overrides default equals() method
     * <p>
     * Precondition: Object obj can be casted to CashAccount
     *
     * @param obj
     * @return boolean
     */
    @Override
    public boolean equals(java.lang.Object obj) {
        CashAccount c = (CashAccount) obj;
        return (accountName.equals(c.getAccountName()));
    }

    /**
     * Returns date the cash account was added
     *
     * @return Date
     */
    public Date getDateAdded() {
        return dateAdded;
    }


    /**
     * subtracts current value by a specified amount
     * <p>
     * Precondition: the amount cannot exceed the total value of the cash account
     * and the amount is non-negative
     *
     * @param amount - double
     */
    public void withdraw(double amount) {
        currentValue -= amount;
    }


    /**
     * adds amount to current value
     * <p>
     * Precondition: the amount is a non-negative number
     *
     * @param amount - double
     */
    public void deposit(double amount) {
        currentValue += amount;
    }

    /*
    * mimics a copy function to maintain reference to the same cash account
    *
    * @param c - CashAccount
    */
    public void overwrite(CashAccount c) {
        this.accountName = c.getAccountName();
        this.currentValue = c.getValue();
        this.dateAdded = c.getDateAdded();
    }


}
