package model.Searchers;

import model.PortfolioElements.HoldingUpdatable;

import java.util.ArrayList;

/**
 * Defines one step in the Searcher that converts the object being
 * searched into a string representation of an Equity or Holding, as both
 * are treated the same under the HoldingUpdatable interface.
 *
 * @author Eric Epstein
 */
public class SearchedHoldingSearcher extends Searcher {

    /**
     * Casts Searchable into HoldingUpdatable to get information for either
     * Equity or Holding. The ArrayList contains one-element and multi-
     * element ArrayList, the latter representing indices and sectors.
     *
     * @param s - Searchable
     * @return ArrayList<ArrayList<String>>
     */
    public ArrayList<ArrayList<String>> getSearchableStrings(Searchable s) {
        HoldingUpdatable eq = (HoldingUpdatable) s;
        ArrayList<ArrayList<String>> anObject = new ArrayList<ArrayList<String>>();
        ArrayList<String> tickerSymbolItem = new ArrayList<String>();
        tickerSymbolItem.add(eq.getTickerSymbol());
        anObject.add(tickerSymbolItem);

        ArrayList<String> holdingNameItem = new ArrayList<String>();
        holdingNameItem.add(eq.getName());
        anObject.add(holdingNameItem);

        ArrayList<String> indices = eq.getIndices();
        anObject.add(indices);
        ArrayList<String> sectors = eq.getSectors();
        anObject.add(sectors);

        return anObject;
    }
}
