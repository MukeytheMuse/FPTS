package controller.CashAccountCtrl;


import gui.FPTS;
import model.PortfolioElements.CashAccount;

import java.util.Observable;
import java.util.Observer;

/**
 * Defines the general steps of managing CashAccount. Implements the
 * first step of obtaining a CashAccount object.
 *
 * @author Eric Epstein
 */
abstract public class CashAccountAlgorithm implements Observer {

    /*
    * CashAccount of interest, to be modified in further steps
    */
    protected CashAccount c;

    /*
    * context data
    */
    protected FPTS theFPTS;

    /**
     * Stalls algorithm until a CashAccount is obtained from CashAccountFinder
     * <p>
     * Called by Menu Controller methods
     *
     * @param theFPTS - FPTS
     */
    public void process(FPTS theFPTS) {
        c = new CashAccount("", 0, null, null);
        this.theFPTS = theFPTS;
        CashAccountFinder caFinder = new CashAccountFinder(theFPTS, c);
        caFinder.addObserver(this);
    }

    /**
     * Upon update from CashAccountFinder (in this context), delegates
     * next step to child algorithms
     *
     * @param o
     * @param args
     */
    public void update(Observable o, Object args) {
        action();
    }

    /*
    * Abstract method that is proceeded once the current algorithm is notified
    * of an update that assigns a CashAccount of interest.
    */
    abstract void action();

}

