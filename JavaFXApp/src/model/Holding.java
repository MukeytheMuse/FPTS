package model;

import java.util.ArrayList;
import java.util.Date;

//import static model.DataBase.ReadFile.readHoldings;

/**
 * Holds an individual equity acquisition made by the user of the application.
 * Stores the ticker symbol, name, sectors, and indices, value per share,
 * number of shares, and overall value.
 *
 * @author Eric Epstein
 */
public class Holding implements Searchable, HoldingUpdatable {

    /*
    * ticker symbol
    */
    private String tickerSymbol;

    /*
    * holding name
    */
    private String holdingName;

    /*
    * initial price per share
    */
    private double initialPricePerShare;
    //TODO: Warning:(30, 20) [UnusedDeclaration] Private field 'initialPricePerShare' is never used


    /*
    * number of shares
    */
    private int numOfShares;

    /*
    * value per share
    */
    private double valuePerShare;

    /*
    * current value
    */
    private double currentValue;

    /*
    * acquistion date
    */
    private String acquisitionDate;

    /*
    * indices
    */
    private ArrayList<String> indices;
    //TODO:Warning:(55, 31) [UnusedDeclaration] Private field 'indices' is assigned but never accessed



    /*
    * sectors
    */
    private ArrayList<String> sectors;
    //TODO:Warning:(60, 31) [UnusedDeclaration] Private field 'sectors' is assigned but never accessed



//    /*
//    *
//    */
//    public static ArrayList<Holding> holdingArrayList = readHoldings();


    /**
     * Constructor used when a user manually adds a Holding.
     *
     * @author Eric Epstein and Kaitlyn Brockway
     */
    public Holding(String tickerSymbol, String equityName, double valuePerShare, int numOfShares, String acquisitionDate, ArrayList<String> indices, ArrayList<String> sectors) {
        this.tickerSymbol = tickerSymbol;
        this.holdingName = equityName;
        this.numOfShares = numOfShares;
        this.valuePerShare = valuePerShare;
        this.currentValue = numOfShares * valuePerShare;
        this.acquisitionDate = acquisitionDate;
        this.indices = indices;
        this.sectors = sectors;
        //this.cashAccount = cashAccount;
        //extras = new ArrayList<String>();
    }

    /**
     * returns value per share
     *
     * @return double
     */
    @Override
    public double getValuePerShare() {
        return valuePerShare;
    }

    /**
     * returns display name
     *
     * @return String
     */
    public String getDisplayName() {
        return tickerSymbol;
    }

    /**
     * returns symbol
     *
     * @return String
     */
    public String getSymbol() {//TODO
        return tickerSymbol;
    }

    /**
     * returns symbol, overrides HoldingUpdatable interface
     *
     * @return String
     */
    @Override
    public String getTickerSymbol() {
        return tickerSymbol;
    }

    /**
     * returns String for display
     *
     * @return String
     */
    @Override
    public String toString() {
        return tickerSymbol + ", " + holdingName + ", " + numOfShares + " shares, $" + valuePerShare + " per share, $" + currentValue + " current value";
    }


    /**
     * returns holding name
     *
     * @return String
     */
    @Override
    public String getHoldingName() {
        return holdingName;
    }

    /**
     * returns number of shares
     *
     * @return int
     */
    public int getNumOfShares() {
        return numOfShares;
    }

    /**
     * returns overall value
     *
     * @return double
     */
    public double getValue() {
        return currentValue;
    }

    public String getAcquisitionDate() {//TODO
        return acquisitionDate;
    }

    /**
     * returns overall value
     *
     * @return double
     */
    public double getCurrentValue() {//TODO
        return currentValue;
    }

    /**
     * returns sectors
     *
     * @return ArrayList<String>
     */
    @Override
    public ArrayList<String> getSectors() {
        return new ArrayList<>();
    }

    /**
     * returns indices
     *
     * @return ArrayList<String>
     */
    @Override
    public ArrayList<String> getIndices() {
        return new ArrayList<>();
    }

    /**
     * subtracts the number of shares by a specified amount
     * and updates value
     *
     * @param numOfSharesAdded
     */
    public void addShares(int numOfSharesAdded) {
        numOfShares += numOfSharesAdded;
        currentValue += (numOfSharesAdded * valuePerShare);
    }

    /**
     * adds the number of shares by a specified amount
     * and updates value
     *
     * @param numOfSharesSubtracted
     */

    public void subtractShares(int numOfSharesSubtracted) {
        numOfShares -= numOfSharesSubtracted;
        currentValue -= (numOfSharesSubtracted * valuePerShare);
    }


}
