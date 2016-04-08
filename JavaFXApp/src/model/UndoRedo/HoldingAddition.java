/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.UndoRedo;

import java.util.ArrayList;
import java.util.Date;
import model.Equities.EquityComponent;
import model.PortfolioElements.Holding;
import model.PortfolioElements.Holdings;

/**
 *
 * @author ericepstein
 */
public class HoldingAddition implements Command {

    Holdings holdings;
    EquityComponent ec;
    int numShares;
    Holding holding;
    
    public HoldingAddition(Holdings holdings, EquityComponent ec, int numShares) {
        
        this.numShares = numShares;
        this.holdings = holdings;
        this.ec = ec;
        
    }
    
    @Override
    public void execute() {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        if (!holdings.contains(ec)) {
            String tickerSymbol = ec.getTickerSymbol();
            String equityName = ec.getDisplayName();
            double valuePerShare = ec.getPricePerShare();
            ArrayList<String> indices = ec.getIndices();
            ArrayList<String> sectors = ec.getSectors();
            
            holding = new Holding(tickerSymbol, equityName, valuePerShare, numShares, new Date(), indices, sectors);
            
            holdings.add(holding);
            
        } else {
            Holding h = holdings.getHoldingGivenEquityComponent(ec);
            h.add(numShares);
        }
    }

    @Override
    public void undo() {
        holding.remove(numShares);
        if (holding.getNumOfShares() == 0) {
            holdings.remove(holding);
        }
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void addChild(Command c) {
        throw new UnsupportedOperationException("Not supported."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void removeChild(Command c) {
        throw new UnsupportedOperationException("Not supported."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
