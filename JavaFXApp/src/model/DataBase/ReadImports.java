package model.DataBase;

import model.PortfolioElements.Deposit;
import model.PortfolioElements.Holding;
import model.PortfolioElements.Transaction;
import model.PortfolioElements.WithdrawalOld;

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
        ArrayList<Transaction> transactionArrayList = readTransactionImports(transactionImports);
        ArrayList<Holding> holdingArrayList = ReadHoldings.read(holdingImports);

        HashMap<String, ArrayList> returnedImports = new HashMap<>();
        returnedImports.put(holdings, holdingArrayList);
        returnedImports.put(transactions, transactionArrayList);

        return returnedImports;
    }

    protected static Map<String, ArrayList<String[]>> readIn(String path) {

        ArrayList<String[]> splitHoldings = new ArrayList<String[]>();
        ArrayList<String[]> splitTransactions = new ArrayList<String[]>();
        ArrayList<String[]> splitFile = ReadFile.readIn(path);
        Map<String, ArrayList<String[]>> importMap = new HashMap<>();

        for (String[] line : splitFile) {
            if (isDouble(line[0])) {
                splitTransactions.add(line);
            } else {
                splitHoldings.add(line);
            }
        }

        importMap.put(holdings, splitHoldings);
        importMap.put(transactions, splitTransactions);

        return importMap;
    }


    /**
     * Reads in external transaction file that the user chooses to import.
     *
     * @param splitFile - file that user chooses to upload.
     * @return - arraylist containing the user's imported holdings.
     * Created by: Kimberly Sookoo.
     */
    public static ArrayList<Transaction> readTransactionImports(ArrayList<String[]> splitFile) {

        ArrayList<Transaction> allTransactions = new ArrayList<>();

        for (String[] line : splitFile) {
            Date date = null;
            try {
                date = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy").parse(line[1]);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            double amount = Double.parseDouble(line[0]);
            //(double amount, String dateMade, String type, String cashAccountName) {

            String type = line[2];
            Transaction trans;
            if (type.equals("Withdrawal")) {
                trans = new WithdrawalOld(amount, date);
            } else {//if(stringType.equals("Deposit")){
                trans = new Deposit(amount, date);
            }

            allTransactions.add(trans);
        }

        return allTransactions;
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


}
