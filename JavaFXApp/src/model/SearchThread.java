package model;

import controller.SaleController;
import controller.SearchController;
import model.DataBase.ReadFile;
import model.Equities.EquityComponent;
import model.PortfolioElements.Holding;
import model.PortfolioElements.HoldingUpdatable;

import java.util.ArrayList;
import java.util.stream.Collectors;

/**
 * Threads used to search through Equities in the system or holdings in a users portfolio for given search criteria.
 * Created by Luke Veilleux
 */
public class SearchThread extends Thread{
    /**
     * Lists that hold the list of Holdings, the matches found from the list of data, and search criteria.
     */
    private ArrayList<Holding> dataList;
    private ArrayList<HoldingUpdatable> matches;
    private String criteria, searchField, typeOfSearch;

    /**
     * Initialization method that sets variables to their given values.
     * @param criteria - String - Text to be searched for.
     * @param field - String - Field in the equity/holding to search through
     * @param type - Type of match to look for in comparisons
     * @param dataList - List of Holding objects that can be searched through. NULL if searching through equities.
     */
    public SearchThread(String criteria, String field, String type, ArrayList<Holding> dataList){
        this.dataList = dataList;
        matches = new ArrayList<>();
        this.criteria = criteria;
        this.searchField = field;
        this.typeOfSearch = type;
    }


    /**
     * Main method of this thread, finds what list to search through, Holdings (dataList) if it was sent it, or
     * Equities provided from ReadFile. Calls to compare the equity/holding with the search criteria, adds that to a list
     * of a match is found. At the end this list is sent to SearchController
     */
    @Override
    public void run() {
        ArrayList<EquityComponent> list;
        if (dataList == null) {
            list = ReadFile.readEquity();
            matches.addAll(list.stream().filter(eq -> criteria.equals("") || search((HoldingUpdatable) eq)).map(eq ->
                                    (HoldingUpdatable) eq).collect(Collectors.toList()));
            SearchController.addEquitiesToList(matches);
        } else {
            matches.addAll(dataList.stream().filter(holding ->
                                    criteria.equals("") || search(holding)).collect(Collectors.toList()));
            SaleController.addHoldingsToList(matches);
        }

    }

    /**
     * Private method that checks one object (Equity or Holding) and based on input search criteria, calls to see if
     * a match is found.
     * @param input - HoldingUpdatable - Equity or Holding to compare to.
     * @return - True if there is match was found, otherwise false.
     */
    private boolean search(HoldingUpdatable input){
        String fromInput = "";
        switch (searchField) {
            case "Ticker":
                fromInput = input.getTickerSymbol();
                break;
            case "Name":
                fromInput = input.getName();
                break;
            case "Index":
                fromInput = input.getIndices().toString();
                break;
            case "Sector":
                fromInput = input.getSectors().toString();
                break;
        }
        switch (typeOfSearch) {
            case "Contains":
                return strContains(fromInput, criteria);
            case "Starts with":
                return strStartsWith(fromInput, criteria);
            case "Exactly Matches":
                return strExactlyMatches(fromInput, criteria);
            case "Any":
                return strContains(fromInput, criteria);
        }
        return false;
    }


    /**
     * Compares the input criteria and the field from the object for a match.
     * @param field - Field from the Equity or Holding
     * @param criteria - Search criteria to match to.
     * @return - True if there is a match, otherwise false.
     */
    private boolean strContains(String field, String criteria) {
        return !field.equals("") && !criteria.equals("") && field.toUpperCase().contains(criteria.toUpperCase());
    }

    /**
     * Compares the input criteria and the field from the object for a match.
     * @param field - Field from the Equity or Holding
     * @param criteria - Search criteria to match to.
     * @return - True if there is a match, otherwise false.
     */
    private boolean strStartsWith(String field, String criteria) {
        return !field.equals("") && !criteria.equals("") && field.toUpperCase().startsWith(criteria.toUpperCase());
    }

    /**
     * Compares the input criteria and the field from the object for a match.
     * @param field - Field from the Equity or Holding
     * @param criteria - Search criteria to match to.
     * @return - True if there is a match, otherwise false.
     */
    private boolean strExactlyMatches(String field, String criteria) {
        return !field.equals("") && !criteria.equals("") && field.toUpperCase().equals(criteria.toUpperCase());
    }
}