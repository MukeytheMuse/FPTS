package model.Simulators;

import java.util.ArrayList;

/**
 * authors: Kaitlin Brockway & Luke
 */
public abstract class Simulator {

    public static ArrayList<Double> series;

    public abstract double simulate(int numberOfSteps);

    public abstract int getCurrentStep();

    public abstract int getTotalSteps();

    public abstract double getCurrentValue();

    public abstract double getChangeInValue();
}
