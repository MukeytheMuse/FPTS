package model;

import java.util.ArrayList;
import java.util.List;

/**
 * Searchable and available to be purchased by the user.
 * Stores four attributes of an Equity in addition to an
 * ArrayList of equities from which value is calculated
 * <p>
 * Authors: Eric Epstein and Kaitlin Brockway
 */
public class EquityComposite implements Searchable, EquityComponent, HoldingUpdatable {


    private String tickerSymbol;

    //pricePerShare is calculated by the method each time it is called.
    //sectors always -> empty array list
    //indicies always -> empty array list


    /*
    * name
    */
    private String equityName;

    /*
    * type
    */
    private String type;

    /*
    * collection of child Equity objects
    */
    private List<HoldingUpdatable> childEquities;

    /**
     * EquityComposite structure takes two parameters before adding
     * more attributes
     *
     * @params name - String
     * type - String
     */
    public EquityComposite(String name, String type) {
        this.equityName = name;
        this.type = type;
        childEquities = new ArrayList<HoldingUpdatable>();
    }

    /**
     * getDisplayName returns name
     *
     * @return name - String
     */
    @Override
    public String getDisplayName() {
        return equityName;
    }

    /**
     * @return name - String
     */
    @Override
    public String getTickerSymbol() {
        return equityName;
    }



    /**
     * @return name - String
     */
    @Override
    public String getHoldingName() {
        return equityName;
    }

    /**
     * @return type - String
     */
    public String getEquityType() {
        return type;
    }


    /**
     * getValuePerShare calculates the average price of the composite
     *
     * @return valuePerShare - double
     */
    @Override
    public double getValuePerShare() {
        double count = 0;
        double curVal;
        for (HoldingUpdatable se : childEquities) {
            curVal = se.getValuePerShare();
            count += curVal;
        }
        return count / childEquities.size();
    }


    /**
     * Returns empty list because a composite
     * does not have indices
     *
     * @return sectors - ArrayList<String>
     */
    @Override
    public ArrayList<String> getSectors() {
        return new ArrayList<String>();
    }

    /**
     * Returns an empty list because a composite
     * does not have indices
     *
     * @return indices - ArrayList<String>
     */
    public ArrayList<String> getIndices() {
        return new ArrayList<String>();
    }

    /**
     * Removes child node from composite
     *
     * @param ec - EquityComponent
     */
    @Override
    public void remove(EquityComponent ec) {

        childEquities.remove((HoldingUpdatable) ec);
    }

    /**
     * Adds child node to composite
     *
     * @param ec - EquityComponent
     */
    @Override
    public void add(EquityComponent ec) {

        childEquities.add((HoldingUpdatable) ec);
    }

    /**
     * Adds a child equity to composite
     *
     * @param e - HoldingUpdateable
     */
    public void add(HoldingUpdatable e) {

        childEquities.add(e);
    }

}
