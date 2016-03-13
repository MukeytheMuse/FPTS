package model;
import java.util.ArrayList;
import java.util.Date;

/**
 *
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
    private Date acquisitionDate;
    
    /*
    * indices
    */
    private ArrayList<String> indices;
    
    /*
    * sectors
    */
    private ArrayList<String> sectors;
    
    /**
    * Constructor used when a user manually adds a Holding.
    * 
    * @author Eric Epstein and Kaitlyn Brockway
    */
    public Holding(String tickerSymbol, String equityName, ArrayList<String> indices, ArrayList<String> sectors, int numOfShares, double valuePerShare, Date acquisitionDate){
        this.tickerSymbol = tickerSymbol;
        this.holdingName = equityName;
        this.numOfShares = numOfShares;
        this.valuePerShare = valuePerShare;
        this.currentValue = numOfShares * valuePerShare;
        this.acquisitionDate = acquisitionDate;
        //this.cashAccount = cashAccount;
        //extras = new ArrayList<String>();
    }
    
    /**
     * 
     * returns value per share
     * 
     * @return double
     */
    @Override
    public double getValuePerShare() {
        return valuePerShare;
    }
    
    /**
     * 
     * returns display name
     * 
     * @return String 
     */
    @Override
    public String getDisplayName() {
        return tickerSymbol;
    }
    
    /**
     * 
     * returns symbol
     * 
     * @return String
     */
    public String getSymbol() {
        return tickerSymbol;
    }
    
     /**
     * 
     * returns symbol, overrides HoldingUpdatable interface
     * 
     * @return String
     */
    @Override
    public String getTickerSymbol(){
        return tickerSymbol;
    }
    
    /**
     * 
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
    public String getHoldingName(){
        return holdingName;
    }
    
  /**
   * 
   * returns number of shares
   * 
   * @return int
   */
    public int getNumOfShares(){
        return numOfShares;
    }
    
 /**
  * 
  * returns overall value
  * 
  * @return double
  */
    public double getValue(){
        return currentValue;
    }

    public Date getAcquisitionDate() {return acquisitionDate;}
    
/**
 * 
 * returns overall value
 * 
 * @return double
 */
    public double getCurrentValue() {
        return currentValue;
    }
    
    /**
     * 
     * returns sectors
     * 
     * @return ArrayList<String>
     */
    @Override
    public ArrayList<String> getSectors() {
        return new ArrayList<String>();
    }
    
    /**
     * 
     * returns indices
     * 
     * @return ArrayList<String>
     */
    @Override
    public ArrayList<String> getIndices() {
        return new ArrayList<String>();
    }
    
    /**
     * 
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
