package model.Simulators;

/**
 * authors: Kaitlin Brockway & Luke
 */
public interface Simulator {
    double simulate(int numberOfSteps);

    int getCurrentStep();

    int getTotalSteps();
}
