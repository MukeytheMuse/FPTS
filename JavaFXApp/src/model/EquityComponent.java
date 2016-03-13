/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.util.ArrayList;

/**
 * Defines the interface that manages and accesses the collection
 * of objects of which there are five accessible attributes.
 *
 * @author Eric Epstein
 */
public interface EquityComponent {

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

    /**
     * returns ticker symbol
     *
     * @return String
     */
    public String getTickerSymbol();

    /**
     * returns equity name
     *
     * @return String
     */
    public String getEquityName();

    /**
     * returns value per share
     *
     * @return double
     */
    public double getValuePerShare();

    /**
     * returns sectors
     *
     * @return ArrayList<String>
     */
    public ArrayList<String> getSectors();

    /**
     * returns indices
     *
     * @return ArrayList<String>
     */
    public ArrayList<String> getIndices();

}
