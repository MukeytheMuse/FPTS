/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.util.ArrayList;
import java.util.Observable;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;

/**
 *
 * @author ericepstein
 */
abstract public class Searcher extends Observable {
    
    ObservableList<Node> queries;
    ArrayList<Searchable> toBeSearched;
    ArrayList<Searchable> matches;
    ArrayList<String> holdingStrings;
    //private T t;
    
    public void search(ObservableList<Node> queries, ArrayList<Searchable> toBeSearched) {
        //define(queries, toBeSearched);
        toBeSearched = getToBeSearched();
        holdingStrings = getHoldingStrings();
        generateMatches();
    }
    
    //abstract void set(T t);
    
    //abstract ArrayList<T> getMatches();
    abstract ArrayList<Searchable> getToBeSearched();
    abstract ArrayList<String> getHoldingStrings();
    //abstract void define(ObservableList<Node> queries, ArrayList<T> toBeSearched);
    
    /*
    public void redefine(ObservableList<Node> queries, ArrayList<Searchable> toBeSearched) {
        this.queries = queries;
        this.toBeSearched = toBeSearched;
        matches = new ArrayList<>();
    }
*/
    public ArrayList<Searchable> getMatches() {
        return matches;
    }
    
    //abstract public ArrayList<Searchable> getMatches();
    
    public void generateMatches() { 
            for (Searchable e : toBeSearched) {          
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
                    matches.add(e);
                }
                holdingStrings.clear();
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
    
}
