/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.PortfolioElements;

import java.util.ArrayList;
import java.util.List;
import model.Equities.EquityComponent;
import model.Equities.EquityComponents;

/**
 *
 * @author ericepstein
 */
public interface PortfolioVisitor {
  
    /**
     *
     * @param eqList
     */
    public void visit(EquityComponents equityComponents);

    /**
     *
     * @param holdingList
     */
    public void visit(Holdings holdings);
    
    /**
     * 
     * 
     */
    public void visit(CashAccounts cashAccounts);

    public void visit(Watchlist watchlist);

    public void visit(History history);
}
