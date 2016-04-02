package model.Simulators;

/**
 * authors: Kaitlin Brockway & Luke
 */
public interface Simulator {
    public double simulate(int numberOfSteps);

    public int getCurrentStep();

    public int getTotalSteps();
}
