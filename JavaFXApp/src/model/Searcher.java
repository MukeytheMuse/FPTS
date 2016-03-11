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
import gui.FPTS;

/**
 *
 * @author ericepstein
 */
abstract public class Searcher extends Observable {
    
    ObservableList<Node> queries;
    ArrayList<Searchable> toBeSearched;
    ArrayList<Searchable> matches;
    ArrayList<ArrayList<String>> holdingStrings;
    //private T t;
    
    public void search(ObservableList<Node> queries, ArrayList<Searchable> toBeSearched) {
        //define(queries, toBeSearched);
        this.toBeSearched = toBeSearched;
        this.queries = queries;
        matches = new ArrayList<Searchable>();
        generateMatches();
    }
    
    //abstract void set(T t);
    
    //abstract ArrayList<T> getMatches();
    abstract ArrayList<ArrayList<String>> getHoldingStrings(Searchable e);
    //abstract void define(ObservableList<Node> queries, ArrayList<T> toBeSearched);
    
    public Searchable getMatch(String keyword) {
        if (toBeSearched != null) {
            for (Searchable s : toBeSearched) {
                if (s.getDisplayName().toUpperCase().equals(keyword.toUpperCase())) {
                    return s;
                }
            }
        }
        return null;
    }
      
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
          
        //System.out.println("SIZE OF HOLDING STIRNGS IS " + holdingStrings.size());
        //System.out.println("SIZE OF QUERIES IS " + queries.size());
        
            for (Searchable e : toBeSearched) {
                boolean isMatch = true;
                System.out.println("NEW SEARCHABLE");
                ArrayList<ArrayList<String>> allItems = getHoldingStrings(e);
               
                for (int i = 0; i < allItems.size(); i++) {
                    
                    ArrayList<String> anItem = allItems.get(i);
                    
                    Pane p = (Pane) queries.get(i);
                    ComboBox cond = (ComboBox) p.getChildren().get(1);
                    TextField content = (TextField) p.getChildren().get(2);
                    //String content = contentField.getText();
                    System.out.println("content is " + content.getText());
                    
                    if (!cond.getValue().equals("")) {
                        boolean tempMatch = false;
                        for (int j = 0; j < anItem.size(); j++) {
                            String testStr = anItem.get(j);
                            System.out.println("test Str : " + testStr);
                            //Switch statement
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
                                    System.out.println("has QUOTES");
                                    oneMatch = true;
                            }
                            System.out.println("oneMatch is " + oneMatch);
                            tempMatch = tempMatch || oneMatch;
                            System.out.println("temp match is now " + tempMatch);
                        }
                        System.out.println("is match was " + isMatch);
                        isMatch = isMatch && tempMatch;
                        System.out.println("is match is now " + isMatch);
                        if (!isMatch) {
                            break;
                        }   
                    }
                    System.out.println("isMatch is " + isMatch);
                    
                }
                if (isMatch) {
                    matches.add(e);
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
    
}
