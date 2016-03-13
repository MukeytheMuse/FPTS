/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import javafx.scene.control.TextField;

import java.util.ArrayList;
import java.util.Date;
import java.util.Observable;

/**
 * Portfolio is a collection of holdings.
 *
 * @author ericepstein & Kaitlin
 */
public class Portfolio extends Observable {
    //for the simulation
    //The system can simulate market conditions to show the effect on the user's portfolio.
    //TODO: ADD "private ArrayList<Trasaction> myTransactions;" B	REQ: The system shall persist holdings and transactions in a portfolio across invocations of the system.

    private ArrayList<Searchable> portfolioElements;
    private ArrayList<CashAccount> cashAccounts;
    private ArrayList<Holding> holdings;
    private ArrayList<Transaction> transactions;
    private ArrayList<EquityComponent> equityComponents = Equity.getEquityList();  // lists what you can buy

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
     * <p>
     * Method called when a user is constructed.
     *
     * @author ericepstein & Kaitlin
     */
    public Portfolio() {

        portfolioElements = new ArrayList<Searchable>();
        equityComponents = Equity.getEquityList();
        cashAccounts = new ArrayList<CashAccount>();
        holdings = new ArrayList<Holding>();
        transactions = new ArrayList<Transaction>();

        matches = new ArrayList<Searchable>();

        add(new Holding("ham", "haha", new ArrayList<String>(), new ArrayList<String>(), 2, (float) 3.0, new Date()));

        add(new CashAccount("rofl", 3, new Date()));

        add(new Holding("mo", "momo", new ArrayList<String>(), new ArrayList<String>(), 2, (float) 3, new Date()));
        add(new Holding("lol", "lolol", new ArrayList<String>(), new ArrayList<String>(), 2, (float) 3, new Date()));

        add(new CashAccount("lala", 3, new Date()));


        System.out.println(new Withdrawal(new CashAccount("lala", 3, new Date()), 3));

        add((EquityComponent) new Equity("lala", "moo", 300, new ArrayList<String>(), new ArrayList<String>()));

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

    public ArrayList<Transaction> getTransactions() {
        return transactions;
    }

    public ArrayList<Searchable> getPortfolioElements() {
        return portfolioElements;
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

    public void add(EquityComponent e) {
        equityComponents.add(e);
    }

    public void remove(EquityComponent e) {
        equityComponents.remove(e);
    }

    public void add(CashAccount e) {
        portfolioElements.add((Searchable) e);
        cashAccounts.add(e);
    }

    public void remove(CashAccount e) {
        portfolioElements.remove((Searchable) e);
        cashAccounts.remove(e);
    }

    public void add(Transaction t) {
        t.execute();
        transactions.add(t);
    }

    public void remove(Transaction t) {
        transactions.remove(t);
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

    public void add(Holding e) {
        portfolioElements.add((Searchable) e);
        holdings.add(e);
    }

    public void remove(Holding e) {
        portfolioElements.remove((Searchable) e);
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
    private boolean isEmpty() {
        if (currentValue == 0) {
            return true;
        } else {
            return false;
        }
    }
}
