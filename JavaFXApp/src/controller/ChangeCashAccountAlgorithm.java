package controller;

import java.util.ArrayList;
import java.util.Observable;

/**
 * Implements step defined in CashAccountAlgorithm by obtaining amount of money
 * from user. Defines next step to be implemented in subclasses and to be executed
 * upon notification.
 *
 * @author Eric Epstein
 */
abstract public class ChangeCashAccountAlgorithm extends CashAccountAlgorithm {

    /*
    * context data
    */
    protected ArrayList<Double> amounts;

    /**
     * Implements step defined in CashAccountAlgorithm. Adds itself as observer
     * for AmountInput.
     */
    @Override
    public void action() {
        amounts = new ArrayList<Double>();
        AmountInput amountInput = new AmountInput(theFPTS, amounts);
        amountInput.addObserver(this);
    }

    /**
     * Upon update of selected CashAccount, notifies the superclass.
     * Upon update of a defined input amount, calls next step to be defined in
     * subclasses.
     *
     * @param o    - Observable
     * @param args - Object
     */
    @Override
    public void update(Observable o, Object args) {
        if (amounts != null) {
            performTransaction();
        } else {
            super.update(o, args);
        }
    }

    /**
     * Defines abstract method representing the next step to be implemented in
     * subclasses.
     */
    abstract void performTransaction();

}

