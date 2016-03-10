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
import javafx.scene.control.ComboBox;
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
    private ArrayList<Equity> existingEquities;
    
    private ArrayList<Holding> matches;
    
    
    /**
     * 
     * @author ericepstein & Kaitlin
     */    
    public Portfolio(){
                       

        //public CashAccount(String AccountName, float initialAmount, Date dateAdded) 
        portfolioHoldings = new ArrayList<Holding>();
        existingEquities = new ArrayList<Equity>();
        matches = new ArrayList<Holding>();
        //portfolioHoldings.add(new CashAccount("lala",200,new Date()));
        //portfolioHoldings.add(new CashAccount("moo",2,new Date()));
        
//(String tickerSymbol, int sharesHeld, double currentPricePerShare, double currentValue, Date acquisitionDate, boolean cashAccount )
        portfolioHoldings.add(new Equity("ha", "haha", 2, 3, 4.3, new Date(), false));
        portfolioHoldings.add(new Equity("mo", "momo", 2, 3, 5, new Date(), false));
        portfolioHoldings.add(new Equity("lol", "lolol", 4, 4, 5, new Date(), false));
        
        existingEquities.add(new Equity("ha", "haha", 2, 3, 4.3, new Date(), false));
        existingEquities.add(new Equity("mo", "momo", 2, 3, 5, new Date(), false));
        existingEquities.add(new Equity("lol", "lolol", 4, 4, 5, new Date(), false));
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
        LoadedEquities loadedEqs = new LoadedEquities();
        //matches = new ArrayList<Holding>();
        matches.clear();
        //if (queries.size() == 2) {
        if (true) {
            
            ArrayList<String> holdingStrings = new ArrayList<String>();
            
            for (Equity e : existingEquities) {
                
                holdingStrings.add(e.getTickerSymbol());
                holdingStrings.add(e.getEquityName());
                
                boolean isMatch = false;
                for (int i = 0; i < holdingStrings.size(); i++) {
                    Pane p = (Pane) queries.get(i);
                    ComboBox cond = (ComboBox) p.getChildren().get(1);
                    TextField content = (TextField) p.getChildren().get(2);
                    
                    String testStr = holdingStrings.get(i);
                    
                    
                    //Switch statement
                    switch (cond.getValue().toString()) {
                        case "exactly matches":
                            isMatch = strExactlyMatches(content, testStr);
                            break;
                        case "starts with":
                            isMatch = strStartsWith(content, testStr);
                            break;
                        case "contains":
                            isMatch = strContains(content, testStr);
                            break;
                        case "":
                            isMatch = true;
                            break;
                    }
                    
                    if (!isMatch) {
                        break;
                    }
                    
                }
                if (isMatch) {
                    matches.add((Holding) e);
                }
                holdingStrings.clear();
            }
        }
        setChanged();
        notifyObservers();
        System.out.println("Notified");
    }
    
    private boolean strContains(TextField testField, String str) {
        return fieldHasContent(testField) && str.toUpperCase().contains(testField.getText().toUpperCase());
    }
    
    private boolean strStartsWith(TextField testField, String str) {
        return fieldHasContent(testField) && str.toUpperCase().startsWith(testField.getText().toUpperCase());
    }
    
    private boolean strExactlyMatches(TextField testField, String str) {
        return fieldHasContent(testField) && str.toUpperCase().equals(testField.getText().toUpperCase());
    }
    
    //Overloading fieldHasContent for TextField
    private boolean fieldHasContent(TextField aField) {
        return (aField.getText() != null && !aField.getText().isEmpty());
    }
    
    //public
}
