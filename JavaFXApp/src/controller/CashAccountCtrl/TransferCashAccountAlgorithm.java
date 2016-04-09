package controller.CashAccountCtrl;

import controller.AmountInput;
import model.PortfolioElements.CashAccount;
import model.PortfolioElements.Deposit;
import model.PortfolioElements.Transaction;
import model.PortfolioElements.WithdrawalOld;

import java.util.ArrayList;
import java.util.Observable;

/**
 * Implements final step in CashAccountAlgorithm by specifying amount and another
 * CashAccount to which the previously specified CashAccount is transferred.
 *
 * @author ericepstein
 */
public class TransferCashAccountAlgorithm extends CashAccountAlgorithm {

    /*
    * context data
    */
    CashAccount c2;
    protected ArrayList<Double> amounts;

    /*
    * number of times notified
    */
    static int numCalled = 0;

    /*
    * Implements the action() step by creating a new CashAccountFinder to establish
    * the second CashAccount
    *
    * TODO: error check this
    */
    public void action() {
        c2 = new CashAccount("", 0, null, null);
        CashAccountFinder caFinder = new CashAccountFinder(theFPTS, c2);
        caFinder.addObserver(this);
    }

    /*
    * Processes notifications by calling specific methods based on number of
    * current notification.
    */
    public void update(Observable o, Object args) {

        numCalled++;

        switch (numCalled) {
            /*
            * At first notification, the first CashAccount is already set.
            * Allow superclass to handle update.
            */
            case (1):
                super.update(o, args);
                break;
            /*
            * At second notification, the second CashAccount is already set
              Call method to transition to numerical input page.
            */
            case (2):
                getAmountInput();
                break;
            /*
            * At third notification, the amount input is already set.
            * Call method to perform transaction.
            */
            case (3):
                performTransaction();
                break;
        }
    }

    /*
    * Creates AmountInput to manage user interface and input to
    * receive validated numerical value.
    */
    public void getAmountInput() {
        amounts = new ArrayList<Double>();
        AmountInput amountInput = new AmountInput(theFPTS, amounts);
        amountInput.addObserver(this);
    }

    /*
    * Validates amount withdrawn before creating Deposit and Withdraw objects
    * for respective CashAccount objects.
    *
    * Supports Transactions done between two accounts within the system.
    */
    public void performTransaction() {

        double amount = amounts.get(0);
        /*
        * If the current value exceeds or equals the amount withdrawn, create
        * respective Transaction objects
        */
        if (c.getValue() >= amount) {
            //TODO: change "date" to an actual date.
            CashAccount aC = theFPTS.getPortfolio().getCashAccount(c);
            Transaction t = new WithdrawalOld(aC, amount);//TODO: add date********
            t.execute();
            theFPTS.getPortfolio().add(t, c);
            aC = theFPTS.getPortfolio().getCashAccount(c2);
            t = new Deposit(aC, amount);//TODO: add date********
            t.execute();
            theFPTS.getPortfolio().add(t, c2);
            theFPTS.getStage().setScene(theFPTS.getConfirmationScene());
        } else {
            theFPTS.getStage().setScene(theFPTS.getErrorScene());
        }

        /*
        * Resets the number of notifications to 0 for later invocations.
        */
        numCalled = 0;

    }

}

