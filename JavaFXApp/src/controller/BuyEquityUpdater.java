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
 */
public class BuyEquityUpdater extends EquityUpdater {
    
    private Portfolio p;
    private ArrayList<Searchable> toBeSearched;
    
    @Override
    public void establishContext() {
        //this.theFPTS = theFPTS;
        p = theFPTS.getPortfolio();
        toBeSearched = p.getLoadedSearchables();
        //System.out.println(""+theFPTS.getWidth());
    }
 
    public ArrayList<Searchable> getToBeSearched() {
        //Portfolio p = copyFPTS.getPortfolio();
        //return p.getLoadedSearchables();
        return toBeSearched;
    }
    
 
    
    /*
    *
    *    precondition - cashAccountOfInterest is already set up
    
    */
    
    public void processInsideFPTS() {
        
       String aTickerSymbol = equityOfInterest.getTickerSymbol();
       Equity e;
       if ( p.getEquity(aTickerSymbol) != null ) {
           e = p.getEquity(aTickerSymbol);
       } else {
           e = new Equity(equityOfInterest.getTickerSymbol(), equityOfInterest.getEquityName(), equityOfInterest.getSectors(), equityOfInterest.getIndices(), numOfShares, equityOfInterest.getValuePerShare(), new Date());
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
              
              //If the equity exists in the collection, increase # of shares  
              if(p.getEquity(keyword) != null) {
                  Equity e = p.getEquity(keyword);
                  e.addShares(numOfShares);
              //If equity does not exist in the collection, create a new Equity & add to collection
              } else {
                  Equity e = new Equity(equityOfInterest.getTickerSymbol(), equityOfInterest.getEquityName(), equityOfInterest.getSectors(), equityOfInterest.getIndices(), numOfShares, equityOfInterest.getValuePerShare(), new Date());
                  p.addEquity(e);
              }
              
              theStage.setScene(getConfirmationScene());
              
          } else {
              mainInput.setText("INVALID");
          }
       }
       
   }
    
}
