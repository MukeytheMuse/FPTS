package model.DataBase;

import model.PortfolioElements.*;
import model.UndoRedo.Command;
import model.UndoRedo.Deposit;
import model.UndoRedo.Withdrawal;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Kimberly Sookoo on 4/2/16.
 */
public class ReadImports {

    private static String holdings = "Holdings";
    private static String transactions = "Transactions";
    private static String cashAccounts = "Cash Accounts";

    /**
     * Reads in external file that the user chooses to import.
     *
     * @param file - file that user chooses to upload.
     * @return - arraylist containing the user's imported holdings.
     * Created by: Kaitlin Brockway & Kimberly Sookoo
     */
    public static HashMap<String, ArrayList> readInImports(File file) {
        String path = file.getPath();

        Map<String, ArrayList<String[]>> splitFile = readIn(path);

        ArrayList<String[]> transactionImports = splitFile.get(transactions);
        ArrayList<String[]> holdingImports = splitFile.get(holdings);
        ArrayList<String[]> cashAccountsImports = splitFile.get(cashAccounts);

        ArrayList<Transaction> transactionArrayList = ReadTrans.readTransactionImports(transactionImports);
        ArrayList<Holding> holdingArrayList = ReadHoldings.read(holdingImports);
        ArrayList<CashAccount> cashAccountArrayList = ReadCash.read(cashAccountsImports);

        HashMap<String, ArrayList> returnedImports = new HashMap<>();
        returnedImports.put(holdings, holdingArrayList);
        returnedImports.put(transactions, transactionArrayList);
        returnedImports.put(cashAccounts, cashAccountArrayList);

        return returnedImports;
    }

    protected static Map<String, ArrayList<String[]>> readIn(String path) {

        ArrayList<String[]> splitHoldings = new ArrayList<String[]>();
        ArrayList<String[]> splitTransactions = new ArrayList<String[]>();
        ArrayList<String[]> splitCashAccounts = new ArrayList<String[]>();
        ArrayList<String[]> splitFile = ReadFile.readIn(path);
        Map<String, ArrayList<String[]>> importMap = new HashMap<>();

        for (String[] line : splitFile) {
            if (isDouble(line[2])) {
                splitHoldings.add(line);
            } else if (isTransaction(line)) {
                splitTransactions.add(line);
            } else {
                splitCashAccounts.add(line);
            }
        }

        importMap.put(holdings, splitHoldings);
        importMap.put(transactions, splitTransactions);
        importMap.put(cashAccounts, splitCashAccounts);

        return importMap;
    }


    /**
     * Checks to see if the first string is a double to decide between transaction
     * or holding.
     *
     * @param str - string to check if it can be parsed
     * @return - boolean value
     * @author: Kimberly Sookoo
     */
    private static boolean isDouble(String str) {
        try {
            Double.parseDouble(str);
            return true;
        } catch (NumberFormatException nfe) {
            return false;
        }
    }

    private static boolean isTransaction(String[] line) {
        if (line.length == 4) {
            return true;
        } else {
            return false;
        }
    }

}
