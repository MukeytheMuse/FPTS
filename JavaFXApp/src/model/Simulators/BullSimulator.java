package model.Simulators;

import javafx.scene.chart.XYChart;
import model.PortfolioElements.Holding;

import java.util.ArrayList;

/**
 * authors: Kaitlin Brockway & Luke
 */
public class BullSimulator extends Simulator {
    public static String name = "Bull Market Simulator";

    private ArrayList<Holding> holdings;
    private String interval;
    private int numSteps;
    private double pricePerYear;
    private double valueCount;
    private int stepNumber;
    private double currentValue;

    /**
     * Runs a Bull simulation. This simulation increases the holdings in value as time goes on.
     * @param numSteps - Number of steps in the simulation.
     * @param interval - The interval that is traversed with each step.
     * @param pricePerYearPercentage - Per Annum, the change in price of each holding.
     * @param holdings - ArrayList of holdings that are used in simulation
     */
    public BullSimulator(int numSteps, String interval, double pricePerYearPercentage, ArrayList<Holding> holdings) {
        this.interval = interval;
        this.numSteps = numSteps;
        this.pricePerYear = pricePerYearPercentage;
        this.holdings = holdings;
        this.stepNumber = 0;
        this.currentValue = 0;
        if (Simulator.series == null) {
            Simulator.series = new ArrayList<Double>();
        }
    }

    /**
     * @return the change in value in the portfolio.
     */
    @Override
    public double simulate(int numberOfSteps) {
        double currentPercentIncrease = 0;
        if (interval.equals("Day")) {
            currentPercentIncrease = pricePerYear / 365;
        } else if (interval.equals("Month")) {
            currentPercentIncrease = pricePerYear / 12;
        } else {
            currentPercentIncrease = pricePerYear;
        }
        for (int i = 0; i < numberOfSteps; i++) {
            currentValue = 0;
            for (Holding h : holdings) {
                double change = 0;
                change = currentPercentIncrease * h.getPricePerShare();
                h.setHoldingValue(h.getPricePerShare() + change);
                currentValue += h.getTotalValue();
                valueCount += change;
            }
            series.add(stepNumber, currentValue);
            stepNumber += 1;
        }
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

    @Override
    public double getCurrentValue() { return currentValue; }

    @Override
    public double getChangeInValue() { return valueCount; }
}

