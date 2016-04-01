package controller.CashAccountCtrl;


/**
 * Implements final step in CashAccountAlgorithm that removes specified CashAccount.
 *
 * @author Eric Epstein
 */
public class RemoveCashAccountAlgorithm extends CashAccountAlgorithm {

    /**
     * Overrides CashAccountAlgorithm's action() method to remove specified
     * CashAccount
     */
    @Override
    public void action() {
        theFPTS.getPortfolio().remove(c);
        theFPTS.getStage().setScene(theFPTS.getConfirmationScene());
    }

}
