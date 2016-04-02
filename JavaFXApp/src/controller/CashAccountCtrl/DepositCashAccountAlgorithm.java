package controller.CashAccountCtrl;
import model.PortfolioElements.CashAccount;
import model.PortfolioElements.Transaction;

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
        Transaction t = new Transaction(amount, "date", "Deposit", c);
        theFPTS.getPortfolio().add(t, aC);
        t.execute(c, amount,"Deposit");
        /*
        * Transitions to confirmation scene.
        */
        theFPTS.getStage().setScene(theFPTS.getConfirmationScene());
    }
}

