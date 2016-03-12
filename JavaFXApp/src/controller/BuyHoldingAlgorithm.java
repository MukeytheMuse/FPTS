/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import model.*;
import gui.*;
import java.util.ArrayList;
import java.util.Date;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;

/**
 *
 * @author ericepstein
 
  Extends HoldingAlgorithm
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
       if ( p.getHolding(aTickerSymbol) != null ) {
           e = p.getHolding(aTickerSymbol);
       } else {
           e = new Holding(equityOfInterest.getTickerSymbol(), equityOfInterest.getHoldingName(), equityOfInterest.getSectors(), equityOfInterest.getIndices(), numOfShares, equityOfInterest.getValuePerShare(), new Date());
       }
       
       double accountVal = cashAccountOfInterest.getValue();

       if (accountVal >= (numOfShares * pricePerShare) ) {
            cashAccountOfInterest.withdraw(numOfShares * pricePerShare);
            e.addShares(numOfShares);
            theStage.setScene(getConfirmationScene());
       } else {
            mainInput.setText("INVALID");
       }  
       
    }
   
   public void processOutsideFPTS() {
       
       String keyword = mainInput.getText();
       
       for (Searchable s : toBeSearched ){
          if (keyword.equals(s.getDisplayName())) {
              
              //If the holding exists in the collection, increase # of shares  
              if(p.getHolding(keyword) != null) {
                  Holding e = p.getHolding(keyword);
                  e.addShares(numOfShares);
              //If equity does not exist in the collection, create a new Holding & add to collection
              } else {
                  Holding e = new Holding(equityOfInterest.getTickerSymbol(), equityOfInterest.getHoldingName(), equityOfInterest.getSectors(), equityOfInterest.getIndices(), numOfShares, equityOfInterest.getValuePerShare(), new Date());
                  p.addHolding(e);
              }
              
              theStage.setScene(getConfirmationScene());
              
          } else {
              mainInput.setText("INVALID");
          }
       }
       
   }
    
}