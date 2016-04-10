package model.Simulators;

import javafx.scene.chart.XYChart;
import model.PortfolioElements.Holding;

import java.util.ArrayList;
import java.util.DoubleSummaryStatistics;

import model.PortfolioElements.Holding;

/**
 * authors: Kaitlin Brockway & Luke
 */
public class BearSimulator implements Simulator {
    public static String name = "Bear Market Simulator";

    private ArrayList<Holding> holdings;
    private ArrayList<Double> portfolioValues;
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
        this.portfolioValues = new ArrayList<>();
    }

    /**
     * The Bear Market simulation will decrease the equities
     * based on the percentage that the user enters.
     * If any equity value is calculated to drop below zero
     * that equity will remain at 0 and will not decrease to
     * a negative value.
     *
     * @return - the change in value in the Holdings
     */
    @Override
    public double simulate(int numberOfSteps) {
        currentValue = 0;
        double currentPercentDecrease = 0;
        switch (interval) {
            case "Day":
                currentPercentDecrease = pricePerYear / 365;
                break;
            case "Month":
                currentPercentDecrease = pricePerYear / 12;
                break;
            default:
                currentPercentDecrease = pricePerYear;
                break;
        }
        for (int i = 0; i < numberOfSteps; i++) {
            currentValue = 0;
            valueCount = 0;
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
            if (portfolioValues.size() == 0)
                portfolioValues.add(currentValue + valueCount);
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
    public double getCurrentValue() {
        return currentValue;
    }

    @Override
    public void addPreviousValues(ArrayList<Double> lst) {
        if (lst != null)
            portfolioValues.addAll(lst);
    }

    @Override
    public void resetSimulator() {
        currentValue += valueCount;
        valueCount = 0;
        portfolioValues.clear();
        stepNumber = 0;
    }

    @Override
    public double getChangeInValue() {
        if(portfolioValues.size() == 0)
            return 0;
        return portfolioValues.get(0) - portfolioValues.get(portfolioValues.size() - 1);
    }

    @Override
    public ArrayList<Holding> getHoldings() { return holdings; }

    public ArrayList<Double> getValues() { return portfolioValues; }
}