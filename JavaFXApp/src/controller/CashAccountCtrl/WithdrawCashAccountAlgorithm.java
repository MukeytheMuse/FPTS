/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.CashAccountCtrl;

import model.PortfolioElements.CashAccount;
import model.PortfolioElements.Transaction;

/**
 * Implements final step in CashAccountAlgorithm by creating a Withdraw object.
 *
 * @author Eric Epstein
 */
public class WithdrawCashAccountAlgorithm extends ChangeCashAccountAlgorithm {

    /**
     * Creates a Withdraw object with validated CashAccount if the amount is validated.
     */
    @Override
    public void performTransaction() {

        double amount = amounts.get(0);

        /*
        * Validates whether amount withdrawn will lead to negative overall value.
        */
        if (c.getCurrentValue() >= amount) {
            CashAccount aC = theFPTS.getPortfolio().getCashAccount(c);
            Transaction t = new Transaction(amount, "dateMade", "Withdrawal", aC.getAccountName());
            theFPTS.getPortfolio().add(t, aC);
            theFPTS.getStage().setScene(theFPTS.getConfirmationScene());
        } else {
            theFPTS.getStage().setScene(theFPTS.getErrorScene());
        }
    }

}

