package model;

/**
 * Created by Brockway on 3/12/16.
 */
public interface Simulator {
    public double simulate(int numberOfSteps);

    public int getCurrentStep();

    public int getTotalSteps();
}
