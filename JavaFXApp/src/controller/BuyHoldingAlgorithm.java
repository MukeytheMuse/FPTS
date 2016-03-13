/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import model.*;

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
        p = theFPTS.getPortfolio();
        toBeSearched = p.getEquityComponentSearchables();
    }

    public ArrayList<Searchable> getToBeSearched() {
        return toBeSearched;
    }
    
    /*
    *
    *    precondition - cashAccountOfInterest is already set up
    
    */

    public void processInsideFPTS() {

        String aTickerSymbol = equityOfInterest.getTickerSymbol();
        Holding e;
        if (p.getHolding(aTickerSymbol) != null) {
            e = p.getHolding(aTickerSymbol);
        } else {
            e = new Holding(equityOfInterest.getTickerSymbol(), equityOfInterest.getHoldingName(), equityOfInterest.getSectors(), equityOfInterest.getIndices(), numOfShares, equityOfInterest.getValuePerShare(), new Date());
        }

        double accountVal = cashAccountOfInterest.getValue();

        if (accountVal >= (numOfShares * pricePerShare)) {
            Transaction t = new Withdrawal(cashAccountOfInterest, numOfShares * pricePerShare);
            p.add(t);
            e.addShares(numOfShares);

            theStage.setScene(getConfirmationScene());
        } else {
            mainInput.setText("INVALID");
        }

    }

    public void processOutsideFPTS() {

        String keyword = mainInput.getText();

        for (Searchable s : toBeSearched) {
            if (keyword.equals(s.getDisplayName())) {

                //If the holding exists in the collection, increase # of shares
                if (p.getHolding(keyword) != null) {
                    Holding e = p.getHolding(keyword);
                    e.addShares(numOfShares);
                    //If equity does not exist in the collection, create a new Holding & add to collection
                } else {
                    Holding e = new Holding(equityOfInterest.getTickerSymbol(), equityOfInterest.getHoldingName(), equityOfInterest.getSectors(), equityOfInterest.getIndices(), numOfShares, equityOfInterest.getValuePerShare(), new Date());
                    p.add(e);
                }

                theStage.setScene(getConfirmationScene());

            } else {
                mainInput.setText("INVALID");
            }
        }

    }

}
