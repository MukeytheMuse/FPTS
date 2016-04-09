package model.Simulators;


import javafx.scene.chart.XYChart;
import model.PortfolioElements.Holding;

import java.util.ArrayList;

/**
 * Created by Brockway on 3/10/16.
 */
public class NoGrowthSimulator extends Simulator {
    public static String name = "No Growth Simulator";

    private int numSteps;
    private int stepNumber;
    private double currentValue;
    private ArrayList<Holding> holdings;

    /**
     *
     */
    public NoGrowthSimulator(int numSteps, ArrayList<Holding> lst) {
        this.numSteps = numSteps;
        this.stepNumber = 0;
        this.holdings = lst;
        this.currentValue = 0;
        if (Simulator.series == null) {
            Simulator.series = new ArrayList<Double>();
        }
    }

    //TODO: CHECK IF IT HAS STEPS.

    /**
     * @return
     */
    public double simulate(int numberOfSteps) {
        for(int i = 0; i < numberOfSteps; i++) {
            currentValue = 0;
            for (Holding h : holdings) {
                currentValue += h.getTotalValue();
            }
            series.add(stepNumber, currentValue);
            stepNumber += 1;
        }
        return 0;
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
    public double getChangeInValue() { return 0; }
}
