/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;
import java.util.List;
import java.util.ArrayList;
import java.util.Date;
import java.util.Observable;
import javafx.collections.ObservableList;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.scene.Node;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;

/**
 * Portfolio is a collection of holdings.
 * 
 * @author ericepstein & Kaitlin
 */
public class Portfolio extends Observable {
    private ArrayList<Holding> portfolioHoldings;
    private double currentValue;
    
    private ArrayList<Holding> matches;
    
    
    /**
     * 
     * @author ericepstein & Kaitlin
     */    
    public Portfolio(){
        

        //public CashAccount(String AccountName, float initialAmount, Date dateAdded) 
        portfolioHoldings = new ArrayList<Holding>();
        matches = new ArrayList<Holding>();
        //portfolioHoldings.add(new CashAccount("lala",200,new Date()));
        //portfolioHoldings.add(new CashAccount("moo",2,new Date()));
        
//(String tickerSymbol, int sharesHeld, double currentPricePerShare, double currentValue, Date acquisitionDate, boolean cashAccount )
        portfolioHoldings.add(new Equity("ha", "haha", 2, 3, 4.3, new Date(), false));
        portfolioHoldings.add(new Equity("mo", "momo", 2, 3, 5, new Date(), false));
        portfolioHoldings.add(new Equity("lol", "lolol", 4, 4, 5, new Date(), false));
    }    
    
    public void addHolding(Holding h) {
        portfolioHoldings.add(h);
    }
    
    public void removeHolding(Holding h) {
        portfolioHoldings.remove(h);
    }
    
    public ArrayList<Holding> getMatches() {
        return matches;
    }
    
    public void setMatches(ObservableList<Node> queries) {
    
        //matches = new ArrayList<Holding>();
        matches.clear();
        //if (queries.size() == 2) {
        if (true) {
            Pane p = (Pane) queries.get(0);
            TextField symbol = (TextField) p.getChildren().get(1);
            p = (Pane) queries.get(1);
            TextField equityName = (TextField) p.getChildren().get(1);
            for (Holding h : portfolioHoldings) {
                if (strContains(symbol, h.getSymbol()) /* && strContains(equityName, h.getEquityName())*/) {    
                //if (h.getSymbol().contains(symbol.getText())) {
                    matches.add(h);
                    System.out.println(symbol.getText());
                }
                
            }
        }
        setChanged();
        notifyObservers();
        System.out.println("Notified");
        
    }
    
    private boolean strContains(TextField testField, String str) {
        return fieldHasContent(testField) && str.contains(testField.getText());
    }
    
    //Overloading fieldHasContent for TextField
    private boolean fieldHasContent(TextField aField) {
        return (aField.getText() != null && !aField.getText().isEmpty());
    }
    
    //public
}
