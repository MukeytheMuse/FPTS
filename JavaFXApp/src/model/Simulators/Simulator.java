package model.Simulators;

import model.PortfolioElements.Holding;

import java.util.ArrayList;

/**
 * authors: Kaitlin Brockway & Luke
 */
public interface Simulator {

    double simulate(int numberOfSteps);

    int getCurrentStep();

    void stepBack();

    void addPreviousValues(ArrayList<Double> lst);

    int getTotalSteps();

    double getCurrentValue();

    void resetSimulator();

    double getChangeInValue();

    ArrayList<Holding> getHoldings();

    ArrayList<Double> getValues();

}
