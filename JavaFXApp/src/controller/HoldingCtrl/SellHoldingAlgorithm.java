package controller.HoldingCtrl;

import model.PortfolioElements.*;
import model.Searchers.Searchable;

import java.util.ArrayList;
import java.util.Date;

/**
 * Extends the Holding Algorithm by defining methods to sell a Holding
 * from outside and inside the FPTS.
 *
 * @author Eric Epstein
 */
public class SellHoldingAlgorithm extends HoldingAlgorithm {

    /*
    * context data
    */
    private Portfolio p;

    /*
    * collection of interest from which element of interest is identified
    */
    private ArrayList<Searchable> toBeSearched;

    /**
     * defines step in HoldingAlgorithm to establish context
     */
    @Override
    public void establishContext() {
        p = theFPTS.getPortfolio();
        toBeSearched = p.getHoldingSearchables();
    }

    /**
     * returns a list of Holding that is searchable
     * for possible sale
     */
    public ArrayList<Searchable> getToBeSearched() {
        return toBeSearched;
    }

    /**
     * Implements algorithm of a sale that is made inside FPTS.
     * <p>
     * Precondition - the following variables have already been assigned:
     * equityOfInterest
     * numOfShares
     * pricePerShare
     * cashAccountOfInterest
     */
    @Override
    public void processInsideFPTS() {
        Holding e = (Holding) equityOfInterest;

        /*
        * Validates the following conditions:
        *   - number of shares are positive
        *   - price per share is positve
        *   - the number of shares sold is less than or equal to the number of shares
        *      in the Holding
        */
        if (numOfShares > 0 && pricePerShare > 0 && e.getNumOfShares() >= numOfShares) {
            /*
            * Creates Transaction that adds value to cashAccountOfInterest
            */
            Date date = new Date(2012 - 11 - 14);
            CashAccount aC = theFPTS.getPortfolio().getCashAccount(cashAccountOfInterest);
            Transaction t = new Deposit(aC, numOfShares * pricePerShare);//TODO: ADD DATE
            t.execute();
            p.add(t, aC);//add to transactions list in the Portfolio class.
            e.remove(numOfShares);
            /*
            * Portfolio removes Holding if the number of shares is equal to zero
            */
            if (e.getNumOfShares() == 0) {
                p.remove(e);
                theStage.setScene(theFPTS.getConfirmationScene());
            }
        } else {
            mainInput.setText("INVALID");
        }
    }

    /**
     * Implements algorithm of a sale that is made outside FPTS.
     */
    @Override
    public void processOutsideFPTS() {
        Holding e = (Holding) equityOfInterest;
        Date date = new Date(2012 - 11 - 14);
        /*
        * Validates that the number of shares subtracted is less than
        * the current number of shares before subtraction.
        */
        if (e.getNumOfShares() > numOfShares) {
            e.remove(numOfShares);
        /*
        * Removes Holding if the number of shares subtracted is equal
        * to the current number of shares.
        */
        } else if (e.getNumOfShares() == numOfShares) {
            p.remove(e);
            double pricePerShare = e.getPricePerShare();
            double totalAmountTransaction = (pricePerShare * numOfShares);
            Transaction newTransaction = new Deposit(p.getCashAccounts().get(0), totalAmountTransaction);
            newTransaction.execute();
            //TODO: doesn't account for the useR not having a cash account
            p.add(newTransaction, p.getCashAccounts().get(0));
            theStage.setScene(theFPTS.getConfirmationScene());
        /*
        /*
        * Warns the user of an invalid input.
        */
        } else {
            mainInput.setText("INVALID");
        }
    }
}

