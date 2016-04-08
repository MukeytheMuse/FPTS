package model.Simulators;


/**
 * Created by Brockway on 3/10/16.
 */
public class NoGrowthSimulator implements Simulator {
    public static String name = "No Growth Simulator";

    private int numSteps;
    private int stepNumber;

    /**
     *
     */
    public NoGrowthSimulator(int numSteps) {
        this.numSteps = numSteps;
        this.stepNumber = 0;
    }

    //TODO: CHECK IF IT HAS STEPS.

    /**
     * @return
     */
    public double simulate(int numberOfSteps) {
        stepNumber += numberOfSteps;
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
}
