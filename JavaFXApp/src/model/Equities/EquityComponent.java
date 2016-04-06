package model.Equities;

import org.w3c.dom.Document;

import java.util.ArrayList;

/**
 * Defines the interface that manages and accesses the collection
 * of objects of which there are five accessible attributes.
 *
 * @author Eric Epstein & Kaitlin Brockway
 */
public interface EquityComponent {

    public String getDisplayName();

    public String getName();

    public double getPricePerShare();

    public String getTickerSymbol();

    public ArrayList<String> getIndices();

    public ArrayList<String> getSectors();

    public void updatePrice(Document d);

    /**
     * adds child to the composite
     *
     * @param e - EquityComponent
     */
    public void add(EquityComponent e);

    /**
     * removes child from composite
     *
     * @param e - EquityComponent
     */
    public void remove(EquityComponent e);


    public boolean equals(EquityComponent e);
}
