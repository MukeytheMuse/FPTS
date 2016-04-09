package controller.HoldingCtrl;

import gui.FPTS;
import model.Equities.EquityComponent;
import model.PortfolioElements.*;
import model.Searchers.Searchable;

import java.util.ArrayList;
import java.util.Date;

/**
 * @author ericepstein
 *         <p>
 *         Extends HoldingAlgorithm
 */
public class BuyHoldingAlgorithm extends HoldingAlgorithm {

    private Portfolio p;
    private ArrayList<Searchable> toBeSearched;

    @Override
    public void establishContext() {
        p = FPTS.getSelf().getPortfolio();
        toBeSearched = p.getEquityComponentSearchables();
    }

    public ArrayList<Searchable> getToBeSearched() {
        return toBeSearched;
    }

    /*
    * Creates, saves and performs operations to carry out the transaction to execute.
    *
    *    precondition - cashAccountOfInterest is already set up
    *
    * Author(s): Kaitlin Brockway & ..
    */

    public void processInsideFPTS(EquityComponent equity, int numOfShares, CashAccount account, double price) {

        String aTickerSymbol = equity.getTickerSymbol();
        Holding e;
        if (p.getHolding(aTickerSymbol) != null) {
            e = p.getHolding(aTickerSymbol);
        } else {
            //TODO: change date field to Date type
            e = new Holding(equity.getTickerSymbol(), equity.getName(), price, numOfShares, new Date(), equity.getSectors(), equity.getIndices());
        }

        double accountVal = account.getValue();

        if (accountVal >= (numOfShares * price)) {
            //CashAccount aC = theFPTS.getPortfolio().getCashAccount(cashAccountOfInterest);
            //TODO: check if its ok that I replaced the line above with the line below????
            //Date date = new Date(2012 - 11 - 14);
            Transaction t = new WithdrawalOld(account, numOfShares * price);//TODO: add date***
            t.execute();//operates on portfolio
            p.add(t, account);
            p.add(e);
        }
    }

    public void processOutsideFPTS(EquityComponent equity, int numOfShares, double price) {

        String keyword = equity.getTickerSymbol();

        for (Searchable s : toBeSearched) {
            if (keyword.equals(s.getDisplayName())) {

                //If the holding exists in the collection, increase # of shares
                if (p.getHolding(keyword) != null) {
                    Holding e = p.getHolding(keyword);
                    e.add(numOfShares);
                    //If equity does not exist in the collection, create a new Holding & add to collection
                } else {
                    Holding e = new Holding(equity.getTickerSymbol(), equity.getName(), price, numOfShares, new Date(), equity.getSectors(), equity.getIndices());
                    p.add(e);
                }

            }
        }

    }

}

