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
 *
 * Extends the Holding Algorithm by defining methods to purchase a Holding 
 * from outside and inside the FPTS.
 * 
 * @author Eric Epstein
 *    
 */
public class BuyHoldingAlgorithm extends HoldingAlgorithm {

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
        toBeSearched = p.getEquityComponentSearchables();
    }

    /**
    * returns a list of EquityComponent that is searchable
    * for possible future purchase
    */
    public ArrayList<Searchable> getToBeSearched() {
        return toBeSearched;
    }
    
    /**
    * 
    * Implements algorithm of a purchase that is made inside FPTS.
    * 
    * Precondition - the following variables have already been assigned:
    *   equityOfInterest
    *   numOfShares
    *   pricePerShare
    *   cashAccountOfInterest
    *
    */
    @Override
    public void processInsideFPTS() {

        
        String aTickerSymbol = equityOfInterest.getTickerSymbol();
        Holding e;
        /*
        * Checks whether shares are added to an existing Holding or a new Holding.
        */
        if (p.getHolding(aTickerSymbol) != null) {
            e = p.getHolding(aTickerSymbol);
        /**
         *  Add shares to a new Holding if the Holding does not exist in the portfolio
         */
        } else {
            e = new Holding(equityOfInterest.getTickerSymbol(), equityOfInterest.getHoldingName(), equityOfInterest.getSectors(), equityOfInterest.getIndices(), numOfShares, equityOfInterest.getValuePerShare(), new Date());
        }

        double accountVal = cashAccountOfInterest.getValue();

        /**
         * Validates amount inputted by user before adding shares and performing 
         * transaction on CashAccount
         */
        if (accountVal >= (numOfShares * pricePerShare)) {
            /*
            * Creates a new Transaction
            */
            Transaction t = new Withdrawal(cashAccountOfInterest, numOfShares * pricePerShare);
            p.add(t);
            e.addShares(numOfShares);
            theStage.setScene(theFPTS.getConfirmationScene());
        } else {
            mainInput.setText("INVALID");
        }

    }

    /**
    * 
    * Implements algorithm of a purchase that is made outside FPTS.
    *
    */
    public void processOutsideFPTS() {

        String keyword = mainInput.getText();

        /**
         *  Validates user input by comparing user input against a list of 
         *  what can be bought, represented as a Searchable object 
         */
        for (Searchable s : toBeSearched) {
            if (keyword.equals(s.getDisplayName())) {
                /**
                * Checks whether shares are added to an existing Holding or a new Holding.
                */
                if (p.getHolding(keyword) != null) {
                    Holding e = p.getHolding(keyword);
                    e.addShares(numOfShares);
                /*
                * Instantiates a new Holding if it has not existed in the portfolio
                */
                } else {
                    Holding e = new Holding(equityOfInterest.getTickerSymbol(), equityOfInterest.getHoldingName(), equityOfInterest.getSectors(), equityOfInterest.getIndices(), numOfShares, equityOfInterest.getValuePerShare(), new Date());
                    p.add(e);
                }

                //Breaks the loop by transitioning user to confirmation scene
                theStage.setScene(theFPTS.getConfirmationScene());

            } else {
                mainInput.setText("INVALID");
            }
        }

    }

}
