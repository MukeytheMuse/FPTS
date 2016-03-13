package model;

import gui.FPTS;

import java.util.ArrayList;

/**
 * Created by Brockway on 3/12/16.
 */
public class NoGrowthSimulator implements Simulator {
    public static String name = "No Growth Simulator";

    private ArrayList<Holding> holdings;
    private String interval;
    private boolean hasSteps;
    private int numSteps;

    /**
     *
     */
    public NoGrowthSimulator(int numSteps, String interval, boolean hasSteps){
        this.interval = interval;
        this.hasSteps = hasSteps;
        this.numSteps = numSteps;
        this.holdings = FPTS.getSelf().getPortfolio().getHoldings();
    }

    //TODO: CHECK IF IT HAS STEPS.
    /**
     *
     * @return
     */
    public double simulate() {
        return 0;
    }
}
