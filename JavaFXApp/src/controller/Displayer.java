package controller;

import gui.FPTS;

/**
 * 
 * Defines the interface for displaying contents in a portfolio.
 * 
 * @author Eric Epstein
 */
public interface Displayer {

    /**
     * 
     * Displays information given context data.
     * 
     * @param theFPTS - the FPTS
     */
    public void display(FPTS theFPTS);

}
