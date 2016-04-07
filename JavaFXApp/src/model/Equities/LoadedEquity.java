package model.Equities;

import model.DataBase.ReadFile;
import model.PortfolioElements.HoldingUpdatable;
import model.Searchers.Searchable;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.util.ArrayList;

/**
 * Searchable and available to be purchased by the user.
 * Contains information about the ticker symbol, equity name,
 * the value per share, the market sector, and the index.
 *
 * @author Epstein & Ian London
 */
public class LoadedEquity implements Searchable, EquityComponent, HoldingUpdatable {

    /*
    * the identifying symbol
    */
    private String tickerSymbol;

    /*
    * the identifying name
    */
    private String equityName;

    /*
    * price per share
    */
    private double pricePerShare;

    /*
    * collection of indices
    */
    public ArrayList<String> indices;

    /*
    * collection of sectors
    */
    public ArrayList<String> sectors;


    //SHOULD NOT NEED THIS OR THE METHOD IN ReadFile called readEquity.
    /*
    * collection of Equity read from input
    */
    public static ArrayList<EquityComponent> LoadedEquityList = ReadFile.readEquity();

    /*
    * collection of Equity that matches a search criteria
    */
    private ArrayList<LoadedEquity> matches;

    /*
    * getEquityList() returns equities
    */
    public static ArrayList<EquityComponent> getEquityList() {
        return LoadedEquityList;
    }

    /*
    * Equity constructor takes 5 parameters
    *
    * @params : tickerSymbol - str
    *            equityName - str
    *           perShareValue - double
    *           indices - ArrayList<String>
    *           sectors - ArrayList<String>
    */
    public LoadedEquity(String tickerSymbol, String equityName, double perShareValue, ArrayList<String> indices, ArrayList<String> sectors) {
        this.tickerSymbol = tickerSymbol;
        this.equityName = equityName;
        this.pricePerShare = perShareValue;
        this.indices = indices;
        this.sectors = sectors;
    }

    public void updatePrice(Document document) {
        NodeList nodeList = document.getDocumentElement().getChildNodes().item(0).getChildNodes();

        for (int i = 0; i < nodeList.getLength(); i++) {
            Node node = nodeList.item(i);
            if (node instanceof Element && node.getNodeName().equals("quote")) {
                if (node.getAttributes().getNamedItem("symbol").getNodeValue().toUpperCase().equals(tickerSymbol.toUpperCase())) {
                    NodeList childNodes = node.getChildNodes();

                    for (int j = 0; j < childNodes.getLength(); j++) {
                        Node cNode = childNodes.item(j);

                        if (cNode.getNodeName().equals("LastTradePriceOnly")) {
                            try {
                                String priceStr = cNode.getTextContent();
                                double price = Double.parseDouble(priceStr);
                                pricePerShare = price;
                            } catch (Exception e) {
                                //e.printStackTrace();
                            }
                        }
                    }
                }
            }
        }
    }

    /*
    * getDisplayName returns ticker symbol
    *
    * @return tickerSymbol - String
    */
    public String getDisplayName() {
        return tickerSymbol;
    }
    //**************************************Searchable method

    /**
     * getValuePerShare returns price per share
     *
     * @return pricePerShare - double
     */
    @Override
    public double getPricePerShare() {
        return pricePerShare;
    }


    /**
     * @return equityName - String
     */
    @Override
    public String getName() {
        return equityName;
    }

    /**
     * @return tickerSymbol - String
     */
    @Override
    public String getTickerSymbol() {
        return tickerSymbol;
    }//TODO: get rid of one of these methods that both do the same thing.

    /**
     * @return sectors - ArrayList<String>
     */
    @Override
    public ArrayList<String> getSectors() {
        return sectors;
    }

    /**
     * @return indices - ArrayList<String>
     */
    @Override
    public ArrayList<String> getIndices() {
        return indices;
    }


    //TODO: find out if we even need add & remove in here (other than the fact that this class implements EquityComponent)

    /**
     * @param e - EquityComponent
     *          <p>
     *          overrides EquityComponent but does nothing because
     *          Equity is a leaf node
     */
    @Override
    public void add(EquityComponent e) {
    }

    /**
     * @param e - EquityComponent
     *          <p>
     *          overrides EquityComponent but does nothing because
     *          Equity is a leaf node
     */
    @Override
    public void remove(EquityComponent e) {
    }

    public boolean equals(EquityComponent e) {
        return e instanceof EquityComposite && this.getName().equals(e.getName()) && this.getTickerSymbol().equals(e.getTickerSymbol());
    }

}
