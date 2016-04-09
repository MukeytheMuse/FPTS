package model.Simulators;

import javafx.scene.chart.XYChart;
import model.PortfolioElements.Holding;

import java.util.ArrayList;
import model.PortfolioElements.Holding;

/**
 * authors: Kaitlin Brockway & Luke
 */
public class BearSimulator extends Simulator {
    public static String name = "Bear Market Simulator";

    private ArrayList<Holding> holdings;
    private String interval;
    private int numSteps;
    private double pricePerYear;
    private double valueCount;
    private int stepNumber;
    private double currentValue;

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
    public BearSimulator(int numSteps, String interval, double pricePerYearPercentage, ArrayList<Holding> holdings) {
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
        currentValue = 0;
        double currentPercentDecrease = 0;
        if (interval.equals("Day")) {
            currentPercentDecrease = pricePerYear / 365;
        } else if (interval.equals("Month")) {
            currentPercentDecrease = pricePerYear / 12;
        } else {
            currentPercentDecrease = pricePerYear;
        }
        for (int i = 0; i < numberOfSteps; i++) {
            currentValue = 0;
            for (Holding h : holdings) {
                double change = 0;
                change = currentPercentDecrease * h.getPricePerShare();
                if (h.getTotalValue() - change > 0) {
                    h.setHoldingValue(h.getPricePerShare() - change);
                    currentValue += h.getTotalValue();
                    valueCount += change;
                } else {
                    h.setHoldingValue(0);
                    valueCount += (h.getTotalValue());
                }
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
    public double getCurrentValue() {
        return currentValue;
    }

    @Override
    public double getChangeInValue() { return valueCount; }
}