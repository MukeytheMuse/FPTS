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
public class CashAccount implements Holding{
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
    public CashAccount(String AccountName, float initialAmount, Date dateAdded) {
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
        return accountName + "\t" + currentValue + "\t" + dateAdded;
    }
    
    public String getSymbol() {
        return "";
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
            //return error message that you cannot withdraw a negative amount
        } else {
            //The cash account does not have enouph funds to withdraw the amount requested.
            
            //RETURN AN ERROR HERE; INSUFFICIENT FUNDS
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
            //return an error message that deposit amount cannot be negative
        }
    }
    
    public void add(Holding h){
        //this is a leaf node so this method is not applicable to this class        
    }
    
    public void delete(Holding h){
        //this is a leaf node so this method is not applicable to this class
    }
    

}
