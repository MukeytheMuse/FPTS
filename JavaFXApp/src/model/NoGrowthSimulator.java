package model;

import java.util.ArrayList;

/**
 * Created by Brockway on 3/10/16.
 */
public class NoGrowthSimulator implements Simulator {
    public static String name = "No Growth Simulator";

    private ArrayList<Holding> holdings;
    //TODO: holdings is never used
    private String interval;
    //TODO:Warning:(14, 20) [UnusedDeclaration] Private field 'interval' is assigned but never accessed
    private boolean hasSteps;
    //TODO:Warning:(15, 21) [UnusedDeclaration] Private field 'hasSteps' is assigned but never accessed
    private int numSteps;
    private int stepNumber;

    /**
     *
     */
    public NoGrowthSimulator(int numSteps, String interval, boolean hasSteps) {
        this.interval = interval;
        this.hasSteps = hasSteps;
        this.numSteps = numSteps;
        //this.holdings = FPTS.getSelf().getPortfolio().getHoldings();
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
