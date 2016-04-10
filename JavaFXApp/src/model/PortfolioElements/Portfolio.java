/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.PortfolioElements;

import javafx.scene.control.TextField;
import model.Equities.EquityComponent;
import model.Equities.LoadedEquity;
import model.Equities.EquityComponents;


import org.w3c.dom.Document;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


/**
 * Maintains multiple holdings in equities, and cash in one or more cash
 * accounts for the user, and maintains a history of all transactions.
 *
 * @author Eric Epstein and Kaitlin Brockway
 */
public class Portfolio {
    private ArrayList<CashAccount> cashAccounts;
    private ArrayList<Holding> holdings;

    private Holdings holdingsCollection;
    private CashAccounts cashAccountsCollection;
    private EquityComponents equityComponentsCollection;
    private Watchlist watchlist;
    private History history;
    
    private double currentValue;
    private ArrayList<Transaction> transactions;

    private ArrayList<EquityComponent> equityComponents = LoadedEquity.getEquityList();  // lists what you can buy

    //this is observable. I missed the first thing, as i enter a search from the FPTS (gui),
    // it will tell the controller (ActionEvent in the button) to empty the "matches' array
    // and add elements that match, then after it finishes, the function in "Portfolio"
    // notifies the observer FPTS to update the search results on the view
    //TODO: MAKE SURE TO ADD TO portfolioHoldings EACH TIME YOU ADD TO "existingEquities" or "existingCashAccounts" & update the value

    /**
     * When creating a new portfolio, the system shall allow the user to
     * import holdings and transactions to initialize the new portfolio. THIS IS NOT ALLOWED YET
     * <p>
     * Method called when a user is constructed.
     *
     * @author ericepstein & Kaitlin
     */
    public Portfolio() {
        cashAccountsCollection = new CashAccounts();
        holdingsCollection = new Holdings();
        equityComponentsCollection = new EquityComponents();
        watchlist = new Watchlist();
        history = new History();
        currentValue = 0.00;
    }


    /**
     * Called when the Users are being Filled to create all user objects in the system.
     *
     * @param readInholdings
     * @param readInCashAccounts Author(s): Kaitlin Brockway
     */
    public Portfolio(ArrayList<Holding> readInholdings, ArrayList<CashAccount> readInCashAccounts, ArrayList<Transaction> readInTransactions) {
        this.holdings = readInholdings;
        this.cashAccounts = readInCashAccounts;

        watchlist = new Watchlist();
        history = new History();
        
        try {
        holdingsCollection.add(readInholdings);
        cashAccountsCollection.add(readInCashAccounts);
            history.add(readInTransactions);
        } catch (Exception ex) {
            holdingsCollection = new Holdings();
            cashAccountsCollection = new CashAccounts();
                    holdingsCollection.add(readInholdings);
        cashAccountsCollection.add(readInCashAccounts);
        }
        
        currentValue = 0;
        //Calculates the portfolio's Total Value.
        if (!readInholdings.isEmpty()) {
            for (Holding h : holdings) {
                currentValue += h.getTotalValue();
            }
        }
        if (!readInCashAccounts.isEmpty()) {
            for (CashAccount c : cashAccounts) {
                currentValue += c.getValue();
            }
        }

        if(!readInTransactions.isEmpty()){
            this.transactions = readInTransactions;
            history.add(readInTransactions);
        } else {
            transactions = new ArrayList<>();
            history = new History();
        }
    }


    /**
     * When creating a new portfolio, the system shall allow the user to
     * import holdings and transactions to initialize the new portfolio.
     * Returns Holding that matches given name
     *
     * @param keyword - String
     * @return Holding
     */
    public Holding getHolding(String keyword) {
        for (Holding e : holdings) {
            if (e.getTickerSymbol().equals(keyword)) {
                return e;
            }
        }
        return null;
    }

    public ArrayList<EquityComponent> getEquityComponents() {
        return (ArrayList <EquityComponent>) equityComponentsCollection.getList();
    }

    public EquityComponent getEquityComponent(String tickerSymbol) {

        for (EquityComponent ec : equityComponents) {
            if (ec.getDisplayName().toUpperCase().equals(tickerSymbol.toUpperCase())) {

                return ec;
            }
        }
        return null;
    }

    /*
    * functions related to watchlist
     */
    public ArrayList<WatchedEquity> getWatchedEquities() {
        return (ArrayList<WatchedEquity>) watchlist.getWatchedEquities();
    }

    public Watchlist getWatchlist() {
        return watchlist;
    }

    public void addWatchedEquity(WatchedEquity w) {
        watchlist.add(w);
    }

    public void updateWatchlist() {
       watchlist.updateWatchlist();
    }

    
    public EquityComponents getEquityComponentsCollection() {
        return new EquityComponents();
    }

    public CashAccounts getCashAccountsCollection() {
        return cashAccountsCollection;
    }

    /**
     * Returns collection of Transaction objects
     *
     * @return ArrayList<Transaction>
     */
    public ArrayList<Transaction> getTransactions() {
        return (ArrayList<Transaction>) history.getList();
    }

    /**
     * Returns collection of CashAccount
     *
     * @return ArrayList<CashAccount>
     */
    public ArrayList<CashAccount> getCashAccounts() {
        return (ArrayList<CashAccount>) cashAccountsCollection.getList();
    }


    public CashAccount getCashAccount(CashAccount c) {
        return cashAccountsCollection.get(c);
    }

    /**
     * Returns collection of Holding
     *
     * @return ArrayList<Holding>
     */
    public ArrayList<Holding> getHoldings() {
        //return holdings;
        return (ArrayList<Holding>) holdingsCollection.getList();
    }


    public History getHistory() {
        return history;
    }

    public Holdings getHoldingsCollection() {
        return holdingsCollection;
    }

    /**
     * Adds EquityComponent object to portfolio
     *
     * @param e
     */
    public void add(EquityComponent e) {
        equityComponents.add(e);
    }

    /**
     * Adds CashAccount to portfolio
     *
     * @param e - CashAccount
     */
    public void add(CashAccount e) {
//        transactions.add((Transaction) new Deposit(e, e.getValue()));
        cashAccounts.add(e);
    }

    /**
     * Removes CashAccount from portfolio
     *
     * @param e - CashAccount
     */
    public void remove(CashAccount e) {
        cashAccounts.remove(e);
    }

    /**
     * Executes Transaction and adds to portfolio history whenever an Equity is bought.
     * Called by BuyHoldingAlgorithm processInsideFPTS()
     *
     * @param t - Transaction
     */
    public void add(Transaction t, CashAccount c) {
        c.addTransaction(t);
    }

    /**
     * Adds Holding to portfolio
     *
     * @param e - Holding
     */
    public void add(Holding e) {
//        portfolioElements.add((Searchable) e);
        holdings.add(e);
    }


    /**
     * Removes Holding from portfolio
     *
     * @param e - Holding
     */
    public void remove(Holding e) {
        holdings.remove(e);
        //TODO: add as a transaction for selling a holding. totalValueOfPortfolio remains the same
        //TODO: ask if they get to select a specific cash Account where the transaction and $$ will transfer to
        //Check "SellHoldingAlgorithm"
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
    private boolean isEmpty() {
        if (currentValue == 0) {
            return true;
        } else {
            return false;
        }
    }

}
