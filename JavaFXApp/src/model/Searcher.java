/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;

import java.util.ArrayList;
import java.util.Observable;

/**
 * Provides general algorithm to produce matches and notifies observers
 * once the matching algorithm is executed
 *
 * @author Eric Epstein
 */
abstract public class Searcher extends Observable {

    /*
    * collection of fields with user input
    */
    ObservableList<Node> queries;

    /*
    * collection of Searchables to be traversed
    */
    ArrayList<Searchable> toBeSearched;

    /*
    * collection of Searchables that match the queries
    */
    ArrayList<Searchable> matches;

    /*
    * collection of collection of strings that are converted from queries
    */
    ArrayList<ArrayList<String>> searchableStrings;

    /**
     * Assigns values to global variables before executing the getMatches()
     * algorithm
     *
     * @param queries
     * @param toBeSearched
     */
    public void search(ObservableList<Node> queries, ArrayList<Searchable> toBeSearched) {
        this.toBeSearched = toBeSearched;
        this.queries = queries;
        matches = new ArrayList<Searchable>();
        generateMatches();
    }

    /**
     * abstract method where subclasses convert the Searchable object
     * in a format such that can be tested against queries
     *
     * @param e
     * @return ArrayList<ArrayList<String>>
     */
    abstract ArrayList<ArrayList<String>> getSearchableStrings(Searchable e);

    /**
     * getMatch is common to all subclasses in that the keyword is used to
     * find the object of interest in the list of toBeSearched
     *
     * @param keyword
     * @return
     */
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

    /**
     * returns matches
     *
     * @return ArrayList<Searchable>
     */
    public ArrayList<Searchable> getMatches() {
        return matches;
    }

    /**
     * Algorithm to iterate through the list of possible matches and compare
     * each one against the fields that the user filled out where these fields
     * have search conditions like "exactly matches", "starts with", or "contains"
     * <p>
     * The algorithm delegates methods to subclasses when context is required.
     */
    public void generateMatches() {
        //Iterates through the list to be searched
        for (Searchable e : toBeSearched) {
            boolean isMatch = true;

            //Delegates to subclass to obtain string representation of object
            ArrayList<ArrayList<String>> allItems = getSearchableStrings(e);

            //Iterates through each field that a user filled out
            for (int i = 0; i < allItems.size(); i++) {

                ArrayList<String> anItem = allItems.get(i);

                Pane p = (Pane) queries.get(i);

                //Access the search condition for that field
                ComboBox cond = (ComboBox) p.getChildren().get(1);

                //Access what the user inputted in that field
                TextField content = (TextField) p.getChildren().get(2);

                //Enter inspection if the user inputted something
                if (!cond.getValue().equals("")) {
                    boolean tempMatch = false;
                    for (int j = 0; j < anItem.size(); j++) {
                        String testStr = anItem.get(j);

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

                    if (!isMatch) {
                        break;
                    }
                }

            }
            //Adds a match to the collection
            if (isMatch) {
                matches.add(e);
            }
        }
        //Notifies observers
        setChanged();
        notifyObservers();
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
