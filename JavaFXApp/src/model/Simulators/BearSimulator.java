package model.Simulators;

import gui.FPTS;
import model.PortfolioElements.Holding;

import java.util.ArrayList;
import model.PortfolioElements.Holding;

/**
 * authors: Kaitlin Brockway & Luke
 */
public class BearSimulator implements Simulator {
    public static String name = "Bear Market Simulator";

    private ArrayList<Holding> holdings;//TODO: find out how to get the holdings******
    //TODO: holdings is never assigned and is accessed
    private String interval;
    private boolean hasSteps;
    //TODO: Warning:(15, 21) [UnusedDeclaration] Private field 'hasSteps' is assigned but never accessed
    private int numSteps;
    private double pricePerYear;
    private double currentPercentDecrease;
    //TODO: Warning:(18, 20) [UnusedDeclaration] Private field 'currentPercentDecrease' is assigned but never accessed
    private int stepNumber;

    /**
     * Bear Market Constructor. Input values are converted
     * to their appropriate types in the SimulationController and
     * then this constructor is called.
     *
     * @param numSteps - Number of steps in the simulation.
     * @param interval - The interval that is traversed with each step.
     * @param pricePerYearPercentage - Per Annum, the change in price of each holding.
     * @param holdings - ArrayList of holdings that are used in simulation
     */
    public BearSimulator(int numSteps, String interval, boolean hasSteps, double pricePerYearPercentage, ArrayList<Holding> holdings) {
        this.interval = interval;
        this.hasSteps = hasSteps;
        this.numSteps = numSteps;
        this.pricePerYear = pricePerYearPercentage;
        this.holdings = FPTS.getSelf().getPortfolio().getHoldings();
        //this.holdings = holdings;
        this.stepNumber = 0;
    }


    //TODO: CHECK IF IT HAS STEPS.

    /**
     * The Bear Market simulation will decrease the equities
     * based on the percentage that the user enters.
     * If any equity value is calculated to drop below zero
     * that equity will remain at 0 and will not decrease to
     * a negative value.
     *
     * @return
     */
    @Override
    public double simulate(int numberOfSteps) {
        double valueCount = 0;
        if (interval.equals("Day")) {
            currentPercentDecrease = pricePerYear / 365;
        } else if (interval.equals("Month")) {
            currentPercentDecrease = pricePerYear / 12;
        } else {
            currentPercentDecrease = pricePerYear;
        }
        double currentDecreaseValue;
        for (int i = 0; i < numberOfSteps; i++) {
            for (Holding h : holdings) {
                currentDecreaseValue = currentPercentDecrease * h.getTotalValue();
                //the system shall ensure that the simulation algorithm
                // keeps all equity prices greater than or equal to zero.
                if (h.getTotalValue() - currentDecreaseValue > 0) {
                    valueCount -= currentDecreaseValue;
                }
            }
        }
        stepNumber += numberOfSteps;
        return valueCount;
    }

    @Override
    public int getCurrentStep() {
        return stepNumber;
    }

    @Override
    public int getTotalSteps() {
        return numSteps;
    }


}