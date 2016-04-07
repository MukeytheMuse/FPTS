package model;

import controller.SearchController;
import model.DataBase.ReadFile;
import model.Equities.EquityComponent;
import model.PortfolioElements.Holding;
import model.PortfolioElements.HoldingUpdatable;

import java.util.ArrayList;

/**
 * Created by Luke Veilleux
 */
public class SearchThread extends Thread {
    private ArrayList<Holding> dataList;
    private ArrayList<HoldingUpdatable> matches;
    private String criteria, searchField, typeOfSearch;

    public SearchThread(String criteria, String field, String type, ArrayList<Holding> dataList) {
        this.dataList = dataList;
        matches = new ArrayList<>();
        this.criteria = criteria;
        this.searchField = field;
        this.typeOfSearch = type;
    }


    @Override
    public void run() {
        ArrayList<EquityComponent> list = new ArrayList<>();
        if (dataList == null) {
            list = ReadFile.readEquity();
            for (EquityComponent eq : list) {
                if (criteria.equals("") || search((HoldingUpdatable) eq)) {
                    matches.add((HoldingUpdatable) eq);
                }
            }
            SearchController.addEquitiesToList(matches);
        } else {
            for (Holding holding : dataList) {
                if (criteria.equals("") || search(holding)) {
                    matches.add(holding);
                }
            }
            SearchController.addEquitiesToList(matches);
        }

    }

    private boolean search(HoldingUpdatable input) {
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


    private boolean strContains(String field, String criteria) {
        if (!field.equals("") && !criteria.equals(""))
            return field.toUpperCase().contains(criteria.toUpperCase());
        return false;
    }

    private boolean strStartsWith(String field, String criteria) {
        if (!field.equals("") && !criteria.equals(""))
            return field.toUpperCase().startsWith(criteria.toUpperCase());
        return false;
    }

    private boolean strExactlyMatches(String field, String criteria) {
        if (!field.equals("") && !criteria.equals(""))
            return field.toUpperCase().equals(criteria.toUpperCase());
        return false;
    }
}