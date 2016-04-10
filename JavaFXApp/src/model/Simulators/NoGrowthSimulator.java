package model.Simulators;


import javafx.scene.chart.XYChart;
import model.PortfolioElements.Holding;

import java.util.ArrayList;

/**
 * Created by Brockway
 */
public class NoGrowthSimulator implements Simulator {
    public static String name = "No Growth Simulator";

    private int numSteps;
    private int stepNumber;
    private double currentValue;
    private ArrayList<Holding> holdings;
    private ArrayList<Double> portfolioValues;

    /**
     *
     */
    public NoGrowthSimulator(int numSteps, ArrayList<Holding> lst) {
        this.numSteps = numSteps;
        this.stepNumber = 0;
        this.holdings = lst;
        this.currentValue = 0;
        this.portfolioValues = new ArrayList<>();
    }

    //TODO: CHECK IF IT HAS STEPS.

    /**
     * @return - the change in the portfolio's value.
     */
    public double simulate(int numberOfSteps) {
        for(int i = 0; i < numberOfSteps; i++) {
            currentValue = 0;
            for (Holding h : holdings) {
                currentValue += h.getTotalValue();
            }
            if (portfolioValues.size() == 0)
                portfolioValues.add(currentValue);
            portfolioValues.add(currentValue);
            stepNumber += 1;
        }
        return 0;
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
    public double getChangeInValue() { return 0; }

    @Override
    public void resetSimulator() { portfolioValues.clear(); stepNumber = 0;}

    @Override
    public ArrayList<Holding> getHoldings() { return holdings; }

    @Override
    public ArrayList<Double> getValues() { return portfolioValues; }
}
