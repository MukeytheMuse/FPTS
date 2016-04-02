package model.PortfolioElements;

import model.Searchers.Searchable;

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
    * value per share
    */
    private double valuePerShare;//Same as pricePerShare of the Equity.

    /*
    * number of shares
    */
    private int numOfShares;

    /*
    * current value
    */
    private double currentValue;

    /*
    * acquistion date
    */
    private Date acquisitionDate;

    /*
    * indices
    */
    private ArrayList<String> indices;

    /*
    * sectors
    */
    private ArrayList<String> sectors;



    //TODO: Fix this. Accidentally messed it up.
//    /*
//    *
//    */
//    public static ArrayList<Holding> holdingArrayList = readHoldings();


    /**
     * Constructor used when a user manually adds a Holding.
     *
     * @author Eric Epstein and Kaitlyn Brockway
     */
    public Holding(String tickerSymbol, String equityName, double valuePerShare, int numOfShares, Date acquisitionDate, ArrayList<String> indices, ArrayList<String> sectors) {
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
    public double getPricePerShare() {
        return valuePerShare;
    }

    /**
     * returns display name
     *
     * @return String
     */
    public String getDisplayName() {
        return tickerSymbol;
    }//***********************************************************GET RID OF WHEN COMBINING HoldingUpdatable


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
    public String getName() {
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
    public double getTotalValue() {
        return currentValue;
    }

    public Date getAcquisitionDate() {//TODO
        return acquisitionDate;
    }

    /**
     * returns sectors
     *
     * @return ArrayList<String>
     */
    @Override
    public ArrayList<String> getSectors() {
        return sectors;
    }

    /**
     * returns indices
     *
     * @return ArrayList<String>
     */
    @Override
    public ArrayList<String> getIndices() {
        return indices;
    }

    /**
     * subtracts the number of shares by a specified amount
     * and updates value
     *
     * @param numOfSharesAdded
     */
    public void add(int numOfSharesAdded) {
        numOfShares += numOfSharesAdded;
        currentValue += (numOfSharesAdded * valuePerShare);
    }

    /**
     * adds the number of shares by a specified amount
     * and updates value
     *
     * @param numOfSharesSubtracted
     */

    public void remove(int numOfSharesSubtracted) {
        numOfShares -= numOfSharesSubtracted;
        currentValue -= (numOfSharesSubtracted * valuePerShare);
    }

}
