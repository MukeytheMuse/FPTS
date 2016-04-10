package model.PortfolioElements;

import java.util.Date;

public interface Transaction {

    public void execute();

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 *

    import model.PortfolioElements.CashAccount;

    import java.util.Date;

    /**
     *
     * @author ericepstein
     */

        public double getAmount();

        public Date getDateMade();

        public String getType();

        public String getCashAccountName();

        /**
         * returns CashAccount unique to the realization
         *
         * @return CashAccount
         */
        public CashAccount getCashAccount();

        public void setCashAccount(CashAccount ca);

        public void setCashAccountName(String cashAccountName);

        //    private double amount;
//    private String dateMade;
//    private String cashAccountName;
//    private String type;

//    /**
//     * Used for reading in transactions from Trans.csv when fillUsers method is running.
//     * @param amount
//     * @param dateMade
//     * @param type
//     * @param cashAccountName
//     *
//     * Author(s): Kaitlin Brockway
//     */
//    public Transaction(double amount, String dateMade, String type, String cashAccountName) {
//        this.amount = amount;
//        this.dateMade = dateMade;
//        this.type = type;
//        this.cashAccountName = cashAccountName;
//    }

        /**
         * Run operation on object
         */

    }
