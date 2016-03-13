package model;

import gui.FPTS;

import java.util.ArrayList;

/**
 * Created by Brockway on 3/12/16.
 */
public class BullSimulator implements Simulator {
    public static String name = "Bull Market Simulator";

    private ArrayList<Holding> holdings;//TODO: prices increase
    private String interval;
    private boolean hasSteps;
    private int numSteps;
    private double pricePerYear;
    private double currentPercentIncrease;

    /**
     *
     * @param numSteps
     * @param interval
     * @param hasSteps
     * @param pricePerYearPercentage
     */
    public BullSimulator(int numSteps, String interval, boolean hasSteps, double pricePerYearPercentage){
        this.interval = interval;
        this.hasSteps = hasSteps;
        this.numSteps = numSteps;
        this.pricePerYear = pricePerYearPercentage;
        this.holdings = FPTS.getSelf().getPortfolio().getHoldings();
    }


    //TODO: CHECK IF IT HAS STEPS.
    /**
     *
     * @return
     */
    public double simulate() {
        double valueCount = 0;
        //Finds the percentage increase based on the time interval.
        if(interval.equals("Day")){
            currentPercentIncrease = pricePerYear / 365;
        } else if(interval.equals("Month")){
            currentPercentIncrease = pricePerYear / 12;
        } else {
            currentPercentIncrease = pricePerYear;
        }


        if(hasSteps){

        } else {//This simulation has no steps
            for(Holding h: holdings){
                valueCount += currentPercentIncrease * h.getValue();
            }
        }
        return valueCount;
    }
}
