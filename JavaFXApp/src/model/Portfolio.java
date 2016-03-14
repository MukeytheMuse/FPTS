/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import javafx.scene.control.TextField;
import java.text.ParseException;
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
import model.DataBase.ReadFile;

import static model.DataBase.ReadFile.readCash;
import static model.DataBase.ReadFile.readHoldings;

/**
 * Maintains multiple holdings in equities, and cash in one or more cash
 * accounts for the user, and maintains a history of all transactions.
 *
 * @author Eric Epstein and Kaitlyn Brockway
 */
public class Portfolio {

    private ArrayList<Searchable> portfolioElements;
    private ArrayList<CashAccount> cashAccounts;
    private ArrayList<Holding> holdings;
    private ArrayList<Transaction> transactions;
    private ArrayList<EquityComponent> equityComponents = Equity.getEquityList();  // lists what you can buy
    private double currentValue;

    /**
     * When creating a new portfolio, the system shall allow the user to
     * import holdings and transactions to initialize the new portfolio. THIS IS NOT ALLOWED YET
     *
     * Method called when a user is constructed.
     *
     * @author ericepstein & Kaitlin
     */
    public Portfolio() {

        portfolioElements = new ArrayList<Searchable>();
        equityComponents = Equity.getEquityList();
        cashAccounts = readCash(); //new ArrayList<CashAccount>(); <--replaced
        holdings = readHoldings(); //new ArrayList<Holding>(); <--replaced
        transactions = new ArrayList<Transaction>();

    }

     /**
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


    /**
     * Returns a collection of Holding objects that are cast to Searchable
     *
     * @return ArrayList<Searchable>
     */

    public ArrayList<Searchable> getHoldingSearchables() {
        ArrayList<Searchable> temp = new ArrayList<Searchable>();
        for (Holding h : holdings) {
            temp.add((Searchable) h);
        }
        return temp;
    }

    /**
     * Returns collection of EquityComponent objects that are cast to Searchable
     *
     * @return ArrayList<Searchable>
     */
    public ArrayList<Searchable> getEquityComponentSearchables() {
        ArrayList<Searchable> temp = new ArrayList<Searchable>();
        for (EquityComponent ec : equityComponents) {
            temp.add((Searchable) ec);
        }
        return temp;
    }

    /**
     * Returns collection of CashAccount objects that are cast to Searchable
     *
     * @return ArrayList<Searchable>
     */
    public ArrayList<Searchable> getCashAccountSearchables() {
        ArrayList<Searchable> temp = new ArrayList<Searchable>();
        for (CashAccount c : cashAccounts) {
            temp.add((Searchable) c);
        }
        return temp;
    }

    /**
     * Returns collection of Transaction objects
     *
     * @return ArrayList<Transaction>
     */
    public ArrayList<Transaction> getTransactions() {
        return transactions;
    }

    /**
    *
    * Returns collection of portfolio elements
    *
    * @return ArrayList<Searchable>
    */
    public ArrayList<Searchable> getPortfolioElements() {
        return portfolioElements;
    }

    /**
     * Returns collection of CashAccount
     *
     * @return ArrayList<CashAccount>
     */
    public ArrayList<CashAccount> getCashAccounts() {
        return cashAccounts;
    }

    /**
     * Returns collection of Holding
     *
     * @return ArrayList<Holding>
     */
    public ArrayList<Holding> getHoldings() {
        return holdings;
    }

    /**
     * Returns collection of EquityComponent
     *
     * @return ArrayList<EquityComponent>
     */
    public ArrayList<EquityComponent> getEquityComponents() {
        return Equity.getEquityList();
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
     * Removes EquityCompoonent object from portfolio
     *
     * @param e
     */
    public void remove(EquityComponent e) {
        equityComponents.remove(e);
    }

    /**
     * Adds CashAccount to portfolio
     *
     * @param e - CashAccount
     */
    public void add(CashAccount e) {
        portfolioElements.add((Searchable) e);
        transactions.add((Transaction) new Deposit(e, e.getValue()));
        cashAccounts.add(e);
    }

    /**
     * Removes CashAccount from portfolio
     *
     * @param e - CashAccount
     */
    public void remove(CashAccount e) {
        portfolioElements.remove((Searchable) e);
        cashAccounts.remove(e);
    }

    /**
     * Executes Transaction and adds to portfolio history
     *
     * @param t - Transaction
     */
    public void add(Transaction t) {
        transactions.add(t);
        t.execute();
    }

    /**
     * Removes Transaction from history list
     *
     * @param t
     */
    public void remove(Transaction t) {
        transactions.remove(t);
    }


    /**
     * Adds Holding to portfolio
     *
     * @param e - Holding
     */
    public void add(Holding e) {
        portfolioElements.add((Searchable) e);
        holdings.add(e);
    }

    /**
     * Removes Holding from portfolio
     *
     * @param e - Holding
     */
    public void remove(Holding e) {
        portfolioElements.remove((Searchable) e);
        holdings.remove(e);
    }
   
    public CashAccount getCashAccount(CashAccount c) {
        for (CashAccount aC : cashAccounts) {
            if (aC.equals(c)) {
                return aC;
            }
        }
        return null;
    }
    
}
