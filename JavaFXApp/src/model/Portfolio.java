/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;
import java.util.List;
import java.util.ArrayList;
import java.util.Date;

/**
 * Portfolio is a collection of holdings.
 * 
 * @author ericepstein & Kaitlin
 */
public class Portfolio {
    private ArrayList<Holding> portfolioHoldings;
    private double currentValue;
    
    /**
     * 
     * @author ericepstein & Kaitlin
     */    
    public Portfolio(){
        //public CashAccount(String AccountName, float initialAmount, Date dateAdded) 
        portfolioHoldings = new ArrayList<Holding>();
        portfolioHoldings.add(new CashAccount("lala",200,new Date()));
        portfolioHoldings.add(new CashAccount("moo",2,new Date()));
        //(String tickerSymbol, int sharesHeld, double currentPricePerShare, double currentValue, Date acquisitionDate, boolean cashAccount )
        portfolioHoldings.add(new Equity("ha", 2, 3, 4.3, new Date(), false));
        portfolioHoldings.add(new Equity("mo", 2, 3, 5, new Date(), false));
        portfolioHoldings.add(new Equity("lol", 4, 4, 5, new Date(), false));
    }    
    
    public void addHolding(Holding h) {
        portfolioHoldings.add(h);
    }
    
    public void removeHolding(Holding h) {
        portfolioHoldings.remove(h);
    }
    
    //public
}
