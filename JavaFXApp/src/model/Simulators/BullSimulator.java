package model.Simulators;

import gui.FPTS;
import model.PortfolioElements.Holding;

import java.util.ArrayList;

/**
 * authors: Kaitlin Brockway & Luke
 */
public class BullSimulator implements Simulator {
    public static String name = "Bull Market Simulator";

    private ArrayList<Holding> holdings;//TODO: prices increase
    //TODO: holdings is never assigned
    private String interval;
    private boolean hasSteps;
    //TODO:Warning:(15, 21) [UnusedDeclaration] Private field 'hasSteps' is assigned but never accessed
    private int numSteps;
    private double pricePerYear;
    private double currentPercentIncrease;
    //TODO:Warning:(18, 20) [UnusedDeclaration] Private field 'currentPercentIncrease' is assigned but never accessed
    private double valueChangePerStep;//may not need
    //TODO:Warning:(19, 20) [UnusedDeclaration] Private field 'valueChangePerStep' is never used
    private int stepNumber;

    /**
     * @param numSteps - Number of steps in the simulation.
     * @param interval - The interval that is traversed with each step.
     * @param pricePerYearPercentage - Per Annum, the change in price of each holding.
     * @param holdings - ArrayList of holdings that are used in simulation
     */
    public BullSimulator(int numSteps, String interval, boolean hasSteps, double pricePerYearPercentage, ArrayList<Holding> holdings) {
        this.interval = interval;
        this.numSteps = numSteps;
        this.pricePerYear = pricePerYearPercentage;
        this.holdings = FPTS.getSelf().getPortfolio().getHoldings();
        //this.holdings = holdings;
        this.stepNumber = 0;
    }

    /**
     * @return
     */
    @Override
    public double simulate(int numberOfSteps) {
        double valueCount = 0;
        if (interval.equals("Day")) {
            currentPercentIncrease = pricePerYear / 365;
        } else if (interval.equals("Month")) {
            currentPercentIncrease = pricePerYear / 12;
        } else {
            currentPercentIncrease = pricePerYear;
        }
        for (int i = 0; i < numberOfSteps; i++) {
            for (Holding h : holdings) {
                valueCount += currentPercentIncrease * h.getTotalValue();
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

