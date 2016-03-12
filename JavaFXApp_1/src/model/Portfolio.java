/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;
import java.util.List;
import java.util.ArrayList;
import java.util.Date;
import java.util.Observable;
import javafx.collections.ObservableList;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.scene.Node;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;

/**
 * Portfolio is a collection of holdings.
 * 
 * @author ericepstein & Kaitlin
 */
public class Portfolio extends Observable {
    //for the simulation
    //The system can simulate market conditions to show the effect on the user's portfolio.
    //TODO: ADD "private ArrayList<Trasaction> myTransactions;" B	REQ: The system shall persist holdings and transactions in a portfolio across invocations of the system.
    
    private ArrayList<CashAccount> cashAccounts;
    private ArrayList<Holding> holdings;
    //private ArrayList<Transaction> Transaction;
    private ArrayList<EquityComponent> equityComponents;  // lists what you can buy
    
    private double currentValue;
    
    private ArrayList<Searchable> matches;
    //this is observable. I missed the first thing, as i enter a search from the FPTS (gui),
    // it will tell the controller (ActionEvent in the button) to empty the "matches' array
    // and add elements that match, then after it finishes, the function in "Portfolio"
    // notifies the observer FPTS to update the search results on the view
    //TODO: MAKE SURE TO ADD TO portfolioHoldings EACH TIME YOU ADD TO "existingEquities" or "existingCashAccounts" & update the value

    /**
     * When creating a new portfolio, the system shall allow the user to
     * import holdings and transactions to initialize the new portfolio. THIS IS NOT ALLOWED YET
     *
     * Method called when a user is constructed.
     *
     * @author ericepstein & Kaitlin
     */    
    public Portfolio(){
        
        equityComponents = new ArrayList<EquityComponent>();
        cashAccounts = new ArrayList<CashAccount>();        
        holdings = new ArrayList<Holding>();
        
        matches = new ArrayList<Searchable>();

        holdings.add(new Holding("ham", "haha", new ArrayList<String>(), new ArrayList<String>(), 2, (float) 3.0, new Date()));
        holdings.add(new Holding("mo", "momo", new ArrayList<String>(), new ArrayList<String>(), 2, (float) 3, new Date()));
        holdings.add(new Holding("lol", "lolol", new ArrayList<String>(), new ArrayList<String>(), 2, (float) 3, new Date()));
     
        cashAccounts.add(new CashAccount("lala", 3, new Date()));
        cashAccounts.add(new CashAccount("rofl", 3, new Date()));
        
        equityComponents.add((EquityComponent) new Equity("lala","moo",300,new ArrayList<String>(), new ArrayList<String>()));
        
    }
    

    /**
     * When creating a new portfolio, the system shall allow the user to
     * import holdings and transactions to initialize the new portfolio.
     *
     * @return
     */

    
    /**
     * When creating a new portfolio, the system shall allow the user to
     * import holdings and transactions to initialize the new portfolio.
     *
     * @return
     */
    
    public ArrayList<Searchable> getHoldingSearchables() {
        ArrayList<Searchable> temp = new ArrayList<Searchable>();
        for (Holding h : holdings) {
            temp.add((Searchable) h);
        }
        return temp;
    }
    
    public ArrayList<Searchable> getEquityComponentSearchables() {
        ArrayList<Searchable> temp = new ArrayList<Searchable>();
        for (EquityComponent ec : equityComponents) {
            temp.add((Searchable) ec);
        }
        return temp;
    }
    
    public ArrayList<Searchable> getCashAccountSearchables() {
        ArrayList<Searchable> temp = new ArrayList<Searchable>();
        for (CashAccount c : cashAccounts) {
            temp.add((Searchable) c);
        }
        return temp;
    }
    
    public ArrayList<CashAccount> getCashAccounts() {
        return cashAccounts;
    }
    
    public ArrayList<Holding> getHoldings() {
        return holdings;
    }
    
    public ArrayList<EquityComponent> getEquityComponents() {
        return equityComponents;
    }
    
    public void add(CashAccount e) {
        cashAccounts.add(e);
    }
    
    public void remove(CashAccount e) {
        cashAccounts.remove(e);
    }
    
    public ArrayList<Searchable> getMatches() {
        return matches;
    }
    
    public Holding getHolding(String keyword) {
        for (Holding e : holdings) {
            if (e.getTickerSymbol().equals(keyword)) {
                return e;
            }
        }
        return null;
    }
    
    public void addHolding(Holding e) {
        holdings.add(e);
    }
    
    public void removeHolding(Holding e) {
        holdings.remove(e);
    }
    
    //Overloading fieldHasContent for TextField
    private boolean fieldHasContent(TextField aField) {
        return (aField.getText() != null && !aField.getText().isEmpty());
    }

    /**
     * Check to see if the Portfolio is Empty.
     *
     * @return
     */
    private boolean isEmpty(){
        if(currentValue == 0){
            return true;
        } else {
            return false;
        }
    }
    
    //public
}
