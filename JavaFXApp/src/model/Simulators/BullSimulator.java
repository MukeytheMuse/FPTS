package model.Simulators;

import javafx.scene.chart.XYChart;
import model.PortfolioElements.Holding;

import java.util.ArrayList;

/**
 * authors: Kaitlin Brockway & Luke
 */
public class BullSimulator implements Simulator {
    public static String name = "Bull Market Simulator";

    private ArrayList<Holding> holdings;
    private ArrayList<Double> portfolioValues;
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
        this.portfolioValues = new ArrayList<>();
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
            valueCount = 0;
            for (Holding h : holdings) {
                double change = 0;
                change = currentPercentIncrease * h.getPricePerShare();
                h.setHoldingValue(h.getPricePerShare() + change);
                currentValue += h.getTotalValue();
                valueCount += change;
            }
            if (portfolioValues.size() == 0)
                portfolioValues.add(currentValue - valueCount);
            portfolioValues.add(currentValue);
            stepNumber += 1;
        }
        return valueCount;
    }

    @Override
    public void stepBack() {
        stepNumber --;
        currentValue = portfolioValues.get(stepNumber);
        portfolioValues.remove(stepNumber + 1);
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
    public void addPreviousValues(ArrayList<Double> lst) {
        if (lst != null)
            portfolioValues.addAll(lst);
    }

    @Override
    public double getChangeInValue() {
        if(portfolioValues.size() == 0)
            return 0;
        return portfolioValues.get(portfolioValues.size() - 1) - portfolioValues.get(0);
    }

    @Override
    public void resetSimulator() {
        currentValue -= valueCount;
        valueCount = 0;
        portfolioValues.clear();
        stepNumber = 0;
    }

    @Override
    public ArrayList<Holding> getHoldings() { return holdings; }

    @Override
    public ArrayList<Double> getValues() { return portfolioValues; }
}

