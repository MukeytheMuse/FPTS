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
public class HoldingRemoval implements Command {

    Holdings holdings;
    int numShares;
    Holding holding;
    
    public HoldingRemoval(Holdings holdings, EquityComponent ec, int numShares) {
        
        this.numShares = numShares;
        this.holding = holding;
        this.holdings = holdings;
       
        
    }
    
    @Override
    public void execute() {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        holding.remove(numShares);
        if (holding.getNumOfShares() == 0) {
            holdings.remove(holding);
        }
    }

    @Override
    public void undo() {
        
        holding.add(numShares);
        if (!holdings.contains(holding)) {
            holdings.add(holding);
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
