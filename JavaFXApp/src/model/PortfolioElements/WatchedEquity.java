/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.PortfolioElements;

import model.Equities.EquityComponent;
import model.Equities.LoadedEquity;

import java.util.ArrayList;

/**
 * @author ericepstein
 */
public class WatchedEquity {

    EquityComponent assocEquity;
    private double highTrigger;
    private double lowTrigger;

    private double previousPrice;

    private boolean exceedsTrigger;
    private boolean hasExceededTrigger;
    private boolean notMeetsTrigger;
    private boolean hasNotMetTrigger;

    public WatchedEquity(String symbol, double highTrigger, double lowTrigger,
                         boolean exceedsTrigger, boolean hasExceededTrigger, boolean notMeetsTrigger,
                         boolean hasNotMetTrigger) {
        assocEquity = getAssocEquity(symbol);
        this.highTrigger = highTrigger;
        this.lowTrigger = lowTrigger;
        this.exceedsTrigger = exceedsTrigger;
        this.hasExceededTrigger = hasExceededTrigger;
        this.notMeetsTrigger = notMeetsTrigger;
        this.hasNotMetTrigger = hasNotMetTrigger;

        previousPrice = assocEquity.getPricePerShare();

        updateTriggers();
    }

    private EquityComponent getAssocEquity(String symbol) {
        ArrayList<EquityComponent> equityComponents = LoadedEquity.getEquityList();
        for (EquityComponent ec : equityComponents) {
            if (ec.getDisplayName().toUpperCase().equals(symbol.toUpperCase())) {

                return ec;
            }
        }
        return null;
        //return FPTS.getCurrentUser().getMyPortfolio().getEquityComponent(symbol);
    }

    public WatchedEquity(EquityComponent ec, double highTrigger, double lowTrigger) {
        assocEquity = ec;
        this.highTrigger = highTrigger;
        this.lowTrigger = lowTrigger;

        previousPrice = assocEquity.getPricePerShare();

        updateTriggers();
    }

    public void updateTriggers() {
        if (highTrigger < 0) {
            exceedsTrigger = false;
            hasExceededTrigger = false;
        } else if (previousPrice > highTrigger) {
            exceedsTrigger = true;
            hasExceededTrigger = true;
        } else {
            exceedsTrigger = false;
            hasExceededTrigger = false;
        }

        if (lowTrigger < 0) {
            notMeetsTrigger = false;
            hasNotMetTrigger = false;
        } else if (previousPrice < lowTrigger) {
            notMeetsTrigger = true;
            hasNotMetTrigger = true;
        } else {
            notMeetsTrigger = false;
            hasNotMetTrigger = false;
        }
    }

    public void handleNewPrice() {
        previousPrice = assocEquity.getPricePerShare();
        updateTriggers();
    }

    public String toString() {
        String status = "";
        if (exceedsTrigger) {
            status += " | Currently exceeds trigger" + " (" + highTrigger + ")";
        } else if (hasExceededTrigger) {
            status += " | Had exceeded trigger" + " (" + highTrigger + ")";
        }

        if (notMeetsTrigger) {
            status += " | Currently below low trigger" + " (" + lowTrigger + ")";
        } else if (hasNotMetTrigger) {
            status += " | Had been below low trigger" + " (" + lowTrigger + ")";
        }

        return assocEquity.getDisplayName() + " " + previousPrice + " " + status;
    }

    public String getSymbol() {
        return assocEquity.getDisplayName();
    }

    public EquityComponent getAssocEquity() {
        return assocEquity;
    }

    public double getPricePerShare() {
        return previousPrice;
    }

    public void setHighTrigger(double trigger) {
        highTrigger = trigger;
        updateTriggers();
    }

    public void setLowTrigger(double trigger) {
        lowTrigger = trigger;
        updateTriggers();
    }

    public double getHighTrigger() {
        return this.highTrigger;
    }

    public double getLowTrigger() {
        return lowTrigger;
    }

    public boolean isExceedsTrigger() {
        return exceedsTrigger;
    }

    public boolean isHasExceededTrigger() {
        return hasExceededTrigger;
    }

    public boolean isNotMeetsTrigger() {
        return notMeetsTrigger;
    }

    public boolean isHasNotMetTrigger() {
        return hasNotMetTrigger;
    }
}
