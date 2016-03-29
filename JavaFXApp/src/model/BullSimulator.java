package model;

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
     * @param numSteps
     * @param interval
     * @param hasSteps
     * @param pricePerYearPercentage
     */
    public BullSimulator(int numSteps, String interval, boolean hasSteps, double pricePerYearPercentage) {
        this.interval = interval;
        this.hasSteps = hasSteps;
        this.numSteps = numSteps;
        this.pricePerYear = pricePerYearPercentage;
//        this.holdings = FPTS.getSelf().getPortfolio().getHoldings();
        this.stepNumber = 0;
    }


    //TODO: CHECK IF IT HAS STEPS.

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
                valueCount += currentPercentIncrease * h.getCurrentValue();
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

