package controller.CashAccountCtrl;

import model.PortfolioElements.CashAccount;
import model.PortfolioElements.Deposit;
import model.PortfolioElements.Transaction;

import java.util.Date;

/**
 * Implements final step in CashAccountAlgorithm by creating a Deposit object.
 *
 * @author Eric Epstein
 */
public class DepositCashAccountAlgorithm extends ChangeCashAccountAlgorithm {
    /**
     * Creates a Deposit object with validated CashAccount at a validated amount.
     */
    @Override
    public void performTransaction() {
        double amount = amounts.get(0);
        Date date = new Date(2012 - 11 - 14);
        CashAccount aC = theFPTS.getPortfolio().getCashAccount(c);
        Transaction t = new Deposit(aC, amount); //TODO: ADD DATE*****
        t.execute();
        theFPTS.getPortfolio().add(t, aC);
        /*
        * Transitions to confirmation scene.
        */
        theFPTS.getStage().setScene(theFPTS.getConfirmationScene());
    }
}

