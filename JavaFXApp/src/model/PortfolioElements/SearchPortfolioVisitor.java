/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.PortfolioElements;

import java.time.ZoneId;
import java.util.ArrayList;

import com.sun.tools.doclets.formats.html.SourceToHTMLConverter;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import model.Equities.EquityComponent;

import java.util.Date;
import java.util.Iterator;
import java.util.List;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import model.Equities.EquityComponents;

/**
 *
 * @author ericepstein
 */
public class SearchPortfolioVisitor implements PortfolioVisitor {

    ObservableList<Node> queries;
    
    public SearchPortfolioVisitor(ObservableList<Node> queries) {
        this.queries = queries; 
    }
    
        @Override
    public void visit(CashAccounts cashAccounts) {
        
        List<CashAccount> cashAccList = cashAccounts.getResults();
        ArrayList<ArrayList<ArrayList<String>>> allItems = new ArrayList<ArrayList<ArrayList<String>>>();
                
        //for (int i = eqList.size() - 1; i >= 0; i--) {
           // EquityComponent eq = eqList.get(i);
        for (CashAccount c : cashAccList) {
        //BEGINNING OF GET SEARCHABLE STRING
            ArrayList<ArrayList<String>> anObject = new ArrayList<ArrayList<String>>();
        
            ArrayList<String> nameItem = new ArrayList<String>();
            nameItem.add(c.getDisplayName());
            
            anObject.add(nameItem);
            
            allItems.add(anObject);
        }
        
        //ENDING OF GET SEARCHABLE STRING
        
        //BEGINNING OF ASSESSING MATCHES
            //Iterates through the list to be searched


            //Delegates to subclass to obtain string representation of object
            //ArrayList<ArrayList<String>> allItems = anObject;

            //Iterates through each field that a user filled out
            for (int l = allItems.size() - 1; l >= 0; l--) {
                ArrayList<ArrayList<String>> anItem = allItems.get(l);
                boolean isMatch = true;

                for (int i = 0; i < queries.size(); i++) {

                    Pane p = (Pane) queries.get(i);

                    //Access the search condition for that field
                    ComboBox cond = (ComboBox) p.getChildren().get(1);

                    //Access what the user inputted in that field
                    TextField content = (TextField) p.getChildren().get(2);


                        //ComboBox co = (ComboBox) aHbox.getChildren().get(1);

                    /*

                    try {
                        System.out.println("QUERIES SIZE IS :");
                        System.out.println(queries.size());
                        ComboBox co = (ComboBox) queries.get(i);
                    } catch (Exception ex) {
                        System.out.println("FAILED AT CO FOR QUERIES i = " + i);
                        ex.printStackTrace();

                    }

                    //System.out.println("SIZE OF QUERIES IS " + queries.size());
                    //System.out.println("SIZE OF PANE IS " + p.getChildren().size());
                    //System.out.println("SIZE OF first child of pane is " + p.getChildren().get(0));

                    int index = -1;
                    try {
                        p = (Pane) queries.get(i);

                        System.out.println("p SIZE IS :");
                        System.out.println(p.getChildren().size());
                        for (int j = 0; j < queries.size(); j++) {
                            index = j;
                            ComboBox co = (ComboBox) p.getChildren().get(j);
                        }
                    } catch (Exception ex) {
                        System.out.println("FAILED AT CO FOR PANE j = " + index);
                        ex.printStackTrace();
                    }
                    */


                    //Access the search condition for that field
                    //ComboBox cond = (ComboBox) p.getChildren().get(1);

                    //Access what the user inputted in that field
                    //TextField content = (TextField) p.getChildren().get(2);

                    List<String> validInfo = anItem.get(i);
                    //Enter inspection if the user inputted something
                    if (!cond.getValue().equals("")) {
                        boolean tempMatch = false;
                        for (int j = 0; j < validInfo.size(); j++) {
                            
                            String testStr = validInfo.get(j);

                            //Tests the condition of the search
                            boolean oneMatch = false;
                            switch (cond.getValue().toString()) {
                                case "exactly matches":
                                    oneMatch = strExactlyMatches(content, testStr);
                                    break;
                                case "starts with":
                                    oneMatch = strStartsWith(content, testStr);
                                    break;
                                case "contains":
                                    oneMatch = strContains(content, testStr);
                                    break;
                                case "":
                                    oneMatch = true;
                            }
                            /*
                            tempMatch reflects whether there was at least one match
                            for one field
                            */
                            tempMatch = tempMatch || oneMatch;

                        }
                        //If there were no matches for one field, terminate
                        isMatch = isMatch && tempMatch;
                        //TODO: check Warning:(147, 31) Condition 'isMatch' is always 'true' 
                    }
                    if (!isMatch) {
                        cashAccList.remove(cashAccList.get(l));
                        break;
                    }
                }
            }
    }

    @Override
    public void visit(Watchlist watchlist) {
        //Do nothing
    }

    @Override
    public void visit(History history) {
        DatePicker startDate = (DatePicker) queries.get(0);
        DatePicker endDate = (DatePicker) queries.get(1);

        List<Transaction> results = history.getResults();

        if (startDate.getValue() != null && endDate.getValue() == null) {
            Date start = Date.from(startDate.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant());
            for (int i = results.size() - 1; i >= 0; i--) {
                Transaction t = results.get(i);
                Date aDate = t.getCashAccount().getDateAdded();
                if (!aDate.after(start)) {
                    results.remove(i);
                }
            }
        } else if (startDate.getValue() == null && endDate.getValue() != null) {
                Date end = Date.from(endDate.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant());
                for (int i = results.size() - 1; i >= 0; i--) {
                    Transaction t = results.get(i);
                    Date aDate = t.getCashAccount().getDateAdded();
                    if (!aDate.before(end)) {
                        results.remove(i);
                    }
                }
            } else if (startDate.getValue() != null && endDate.getValue() != null) {
                Date start = Date.from(startDate.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant());//TODO: check
                Date end = Date.from(endDate.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant());//TODO: check
                for (int i = results.size() - 1; i >= 0; i--) {
                    Transaction t = results.get(i);
                    Date aDate = t.getCashAccount().getDateAdded();
                    if (!(aDate.after(start) && aDate.before(end))) {
                        results.remove(i);
                    }
                }
        }
    }

    @Override
    public void visit(EquityComponents equityComponents) {
        
        List<EquityComponent> eqList = equityComponents.getResults();
        ArrayList<ArrayList<ArrayList<String>>> allItems = new ArrayList<ArrayList<ArrayList<String>>>();
                
        //for (int i = eqList.size() - 1; i >= 0; i--) {
           // EquityComponent eq = eqList.get(i);
        for (EquityComponent eq : eqList) {
        //BEGINNING OF GET SEARCHABLE STRING
            ArrayList<ArrayList<String>> anObject = new ArrayList<ArrayList<String>>();
        
            ArrayList<String> tickerSymbolItem = new ArrayList<String>();
            tickerSymbolItem.add(eq.getTickerSymbol());
            anObject.add(tickerSymbolItem);

            ArrayList<String> holdingNameItem = new ArrayList<String>();
            holdingNameItem.add(eq.getName());
            anObject.add(holdingNameItem);

            ArrayList<String> indices = eq.getIndices();
            anObject.add(indices);
            ArrayList<String> sectors = eq.getSectors();
            anObject.add(sectors);
            
            allItems.add(anObject);
        }
        
        //ENDING OF GET SEARCHABLE STRING
        
        //BEGINNING OF ASSESSING MATCHES
            //Iterates through the list to be searched


            //Delegates to subclass to obtain string representation of object
            //ArrayList<ArrayList<String>> allItems = anObject;

            //Iterates through each field that a user filled out
            for (int l = allItems.size() - 1; l >= 0; l--) {
                ArrayList<ArrayList<String>> anItem = allItems.get(l);
                boolean isMatch = true;
                
                for (int i = 0; i < queries.size(); i++) {

                    Pane p = (Pane) queries.get(i);

                    //Access the search condition for that field
                    ComboBox cond = (ComboBox) p.getChildren().get(1);

                    //Access what the user inputted in that field
                    TextField content = (TextField) p.getChildren().get(2);

                    List<String> validInfo = anItem.get(i);
                    //Enter inspection if the user inputted something
                    if (!cond.getValue().equals("")) {
                        boolean tempMatch = false;
                        for (int j = 0; j < validInfo.size(); j++) {
                            
                            String testStr = validInfo.get(j);

                            //Tests the condition of the search
                            boolean oneMatch = false;
                            switch (cond.getValue().toString()) {
                                case "exactly matches":
                                    oneMatch = strExactlyMatches(content, testStr);
                                    break;
                                case "starts with":
                                    oneMatch = strStartsWith(content, testStr);
                                    break;
                                case "contains":
                                    oneMatch = strContains(content, testStr);
                                    break;
                                case "":
                                    oneMatch = true;
                            }
                            /*
                            tempMatch reflects whether there was at least one match
                            for one field
                            */
                            tempMatch = tempMatch || oneMatch;

                        }
                        //If there were no matches for one field, terminate
                        isMatch = isMatch && tempMatch;
                        //TODO: check Warning:(147, 31) Condition 'isMatch' is always 'true' 
                    }
                    if (!isMatch) {
                        eqList.remove(eqList.get(l));
                        break;
                    }
                }
            }
    }
    


    /**
     * Algorithm to iterate through the list of possible matches and compare
     * each one against the fields that the user filled out where these fields
     * have search conditions like "exactly matches", "starts with", or "contains"
     * <p>
     * The algorithm delegates methods to subclasses when context is required.
     */
    public void generateMatches() {

        
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
    
    @Override
    public void visit(Holdings holdings) {
        
        List<Holding> holdingsList = holdings.getResults();
        ArrayList<ArrayList<ArrayList<String>>> allItems = new ArrayList<ArrayList<ArrayList<String>>>();
                
        //for (int i = eqList.size() - 1; i >= 0; i--) {
           // EquityComponent eq = eqList.get(i);
        for (Holding h : holdingsList) {
        //BEGINNING OF GET SEARCHABLE STRING
            ArrayList<ArrayList<String>> anObject = new ArrayList<ArrayList<String>>();
        
            ArrayList<String> tickerSymbolItem = new ArrayList<String>();
            tickerSymbolItem.add(h.getTickerSymbol());
            anObject.add(tickerSymbolItem);

            ArrayList<String> holdingNameItem = new ArrayList<String>();
            holdingNameItem.add(h.getName());
            anObject.add(holdingNameItem);

            ArrayList<String> indices = h.getIndices();
            anObject.add(indices);
            ArrayList<String> sectors = h.getSectors();
            anObject.add(sectors);
            
            allItems.add(anObject);
        }
        
        //ENDING OF GET SEARCHABLE STRING
        
        //BEGINNING OF ASSESSING MATCHES
            //Iterates through the list to be searched

            //Delegates to subclass to obtain string representation of object
            //ArrayList<ArrayList<String>> allItems = anObject;

            //Iterates through each field that a user filled out
            for (int l = allItems.size() - 1; l >= 0; l--) {
                ArrayList<ArrayList<String>> anItem = allItems.get(l);
                boolean isMatch = true;
                
                for (int i = 0; i < queries.size(); i++) {

                    Pane p = (Pane) queries.get(i);

                    //Access the search condition for that field
                    ComboBox cond = (ComboBox) p.getChildren().get(1);

                    //Access what the user inputted in that field
                    TextField content = (TextField) p.getChildren().get(2);

                    List<String> validInfo = anItem.get(i);
                    //Enter inspection if the user inputted something
                    if (!cond.getValue().equals("")) {
                        boolean tempMatch = false;
                        for (int j = 0; j < validInfo.size(); j++) {
                            
                            String testStr = validInfo.get(j);

                            //Tests the condition of the search
                            boolean oneMatch = false;
                            switch (cond.getValue().toString()) {
                                case "exactly matches":
                                    oneMatch = strExactlyMatches(content, testStr);
                                    break;
                                case "starts with":
                                    oneMatch = strStartsWith(content, testStr);
                                    break;
                                case "contains":
                                    oneMatch = strContains(content, testStr);
                                    break;
                                case "":
                                    oneMatch = true;
                            }
                            /*
                            tempMatch reflects whether there was at least one match
                            for one field
                            */
                            tempMatch = tempMatch || oneMatch;

                        }
                        //If there were no matches for one field, terminate
                        isMatch = isMatch && tempMatch;
                        //TODO: check Warning:(147, 31) Condition 'isMatch' is always 'true' 
                    }
                    if (!isMatch) {
                        holdingsList.remove(holdingsList.get(l));
                        break;
                    }
                }
            }
    }
   

    
    
}
