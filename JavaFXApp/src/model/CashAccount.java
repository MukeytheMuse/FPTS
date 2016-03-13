/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;
import java.util.Date;

/**
 *
 * @author ericepstein
 */
public class CashAccount implements  Searchable {
    private String accountName;
    private double currentValue;
    private Date dateAdded;
    
    /**
    * The system shall allow the user to specify a new cash account. 
    * A user defines a cash account by specifying an account name, 
    * initial amount, and the date it was added.
    * 
    * @author ericepstein & kaitlin
    */
    public CashAccount(String AccountName, double initialAmount, Date dateAdded) {
        this.accountName = AccountName;
        currentValue = initialAmount;
        this.dateAdded = dateAdded;
    }
    
    /**
    * Getter for the account name.
    * 
    * @author ericepstein & kaitlin
    * returns: String accountName
    */
    public String getAccountName(){
        return accountName;
    }
    
    public String toString() {
        return "" + currentValue;
    }
    
    public String getSymbol() {
        return "";
    }
    
    public String getDisplayName() {
        return accountName;
    }
    
    /**
    * Getter for the current balance
    * in the cash account.
    * 
    * @author ericepstein & kaitlin
    */
    public double getValue(){
        return currentValue;
    }
    
    @Override
    public boolean equals(java.lang.Object obj) {
        //TODO: should check to make sure this can be casted.
        CashAccount c = (CashAccount) obj;
        return (accountName.equals(c.getAccountName()));
    }
    
    /**
    * Getter for the date that the cash account was created/added.
    * 
    * @author ericepstein & kaitlin
    */
    public Date getDateAdded(){
        return dateAdded;
    }
    
    
    /**
    *
    * @author ericepstein & kaitlin
    */
    public void withdraw(double amount){
        
        if((currentValue - amount) >= 0){
            currentValue -= amount;
        } else if( amount < 0){
            System.out.println("Withdrawal amount cannot be negative.");
        } else {
            System.out.println("Insufficient funds in account. You do not have enough money to make this withdrawal.");
        }
    }
    
    
    /**
    *
    * @author ericepstein & kaitlin
    */
    public void deposit(double amount) {
        if(amount > 0) {
            currentValue += amount;
        } else {
            System.out.println("Deposit amount cannot be negative.");
        }
    }
    
    public void overwrite(CashAccount c) {
        this.accountName = c.getAccountName();
        this.currentValue = c.getValue();
        this.dateAdded = c.getDateAdded();
    }
    

}
