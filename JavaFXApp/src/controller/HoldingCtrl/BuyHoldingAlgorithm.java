package controller.HoldingCtrl;

import model.PortfolioElements.CashAccount;
import model.PortfolioElements.Holding;
import model.PortfolioElements.Portfolio;
import model.PortfolioElements.Transaction;
import model.Searchers.Searchable;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;

/**
 * @author ericepstein
 *         <p>
 *         Extends HoldingAlgorithm
 */
public class BuyHoldingAlgorithm extends HoldingAlgorithm {

    private Portfolio p;
    private ArrayList<Searchable> toBeSearched;

    @Override
    public void establishContext() {
        p = theFPTS.getPortfolio();
        toBeSearched = p.getEquityComponentSearchables();
    }

    public ArrayList<Searchable> getToBeSearched() {
        System.out.println("getToBeSearched called");
        return toBeSearched;
    }

    /*
    * Creates, saves and performs operations to carry out the transaction to execute.
    *
    *    precondition - cashAccountOfInterest is already set up
    *
    * Author(s): Kaitlin Brockway & ..
    */

    public void processInsideFPTS() {

        String aTickerSymbol = equityOfInterest.getTickerSymbol();
        Holding e;
        if (p.getHolding(aTickerSymbol) != null) {
            e = p.getHolding(aTickerSymbol);
        } else {
            //TODO: change date field to Date type
            e = new Holding(equityOfInterest.getTickerSymbol(), equityOfInterest.getName(), equityOfInterest.getPricePerShare(), numOfShares,new Date() , equityOfInterest.getSectors(), equityOfInterest.getIndices());
        }

        double accountVal = cashAccountOfInterest.getValue();

        if (accountVal >= (numOfShares * pricePerShare)) {
            //CashAccount aC = theFPTS.getPortfolio().getCashAccount(cashAccountOfInterest);
            //TODO: check if its ok that I replaced the line above with the line below????
            CashAccount aC = cashAccountOfInterest;
            Transaction t = new Transaction(numOfShares * pricePerShare, "date", "Withdraw", aC);


            t.execute(aC, numOfShares * pricePerShare, "Withdraw");//operates on portfolio
            //Transaction(double amount, String dateMade, String type, String cashAccountName)

            p.add(t, aC);
            e.add(numOfShares);

            theStage.setScene(theFPTS.getConfirmationScene());
        } else {
            mainInput.setText("INVALID");
        }

    }

    public void processOutsideFPTS() {

        String keyword = mainInput.getText();

        for (Searchable s : toBeSearched) {
            if (keyword.equals(s.getDisplayName())) {

                //If the holding exists in the collection, increase # of shares
                if (p.getHolding(keyword) != null) {
                    Holding e = p.getHolding(keyword);
                    e.add(numOfShares);
                    //If equity does not exist in the collection, create a new Holding & add to collection
                } else {
                    Holding e = new Holding(equityOfInterest.getTickerSymbol(), equityOfInterest.getName(), equityOfInterest.getPricePerShare(), numOfShares, new Date(), equityOfInterest.getSectors(), equityOfInterest.getIndices());
                    p.add(e);
                }

                theStage.setScene(theFPTS.getConfirmationScene());

            } else {
                mainInput.setText("INVALID");
            }
        }

    }

}

