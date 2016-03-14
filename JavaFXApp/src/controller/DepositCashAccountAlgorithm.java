package controller;

import model.CashAccount;
import model.Deposit;
import model.Transaction;

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
        
        CashAccount aC = theFPTS.getPortfolio().getCashAccount(c);

        Transaction t = new Deposit(aC, amount);
        theFPTS.getPortfolio().add(t);
        /*
        * Transitions to confirmation scene.
        */
        theFPTS.getStage().setScene(theFPTS.getConfirmationScene());
    }

}
