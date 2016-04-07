package model.DataBase;


import model.PortfolioElements.Deposit;
import model.PortfolioElements.Transaction;
import model.PortfolioElements.Withdrawal;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Brockway on 4/5/16.
 */
public class ReadTrans {

    public static Map<String, ArrayList<Transaction>> readDB(String un) {
        ArrayList<String[]> splitFile = ReadFile.readInUser(un + "/Trans.csv");
        return read(splitFile);
    }

    /**
     * @return list of all Holding objects
     * <p>
     * Author(s): Kaitlin Brockway & Ian
     */
    public static Map<String, ArrayList<Transaction>> read(ArrayList<String[]> file) {
        ArrayList<String[]> splitFile = file;
        String stringCashAccountNameAssociatedWith;
        String stringAmount;
        Date dateMade;
        Map<String, ArrayList<Transaction>> cashAccountNameTransactionsMap = new HashMap<>();


        // iterate through each line representing a Transaction
        for (String[] line : splitFile) {
            stringCashAccountNameAssociatedWith = line[3];
            stringAmount = line[0];
            double amount = Double.parseDouble(stringAmount);
            String stringType;
            try {
                //***********ADD THE DATE TO THE CONSTRUCTOR of withdrawal & deposit***********


                dateMade = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy").parse(line[1]);
                stringType = line[2];
                //TODO: ADD CHECK TO SEE IF "stringAmount" is in the format 90809890.99 with only numbers as parts of the string.
                Transaction newTransactionToAdd;
                if (stringType.equals("Withdrawal")) {
                    newTransactionToAdd = new Withdrawal(amount, dateMade);
                } else {//if(stringType.equals("Deposit")){
                    newTransactionToAdd = new Deposit(amount, dateMade);
                }
                if (cashAccountNameTransactionsMap.containsKey(stringCashAccountNameAssociatedWith)) {
                    ArrayList<Transaction> newTransactionsList = cashAccountNameTransactionsMap.get(stringCashAccountNameAssociatedWith);
                    newTransactionsList.add(newTransactionToAdd);
                    cashAccountNameTransactionsMap.replace(stringCashAccountNameAssociatedWith, newTransactionsList);
                } else {
                    ArrayList<Transaction> transactionListToAdd = new ArrayList<>();
                    transactionListToAdd.add(newTransactionToAdd);
                    cashAccountNameTransactionsMap.put(stringCashAccountNameAssociatedWith, transactionListToAdd);
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

        return cashAccountNameTransactionsMap;
    }
}
