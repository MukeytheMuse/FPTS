package model;

/**
 * Interface that defines execute behaviors in association with CashAccount
 *
 * @author Eric Epstein
 */
public interface Transaction {

    /**
     * Run operation on object
     */
    public void execute();

    /**
     * returns CashAccount unique to the realization
     *
     * @return CashAccount
     */
    public CashAccount getCashAccount();
}
