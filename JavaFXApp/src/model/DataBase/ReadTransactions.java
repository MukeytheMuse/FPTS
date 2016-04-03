package model.DataBase;

import model.PortfolioElements.CashAccount;
import model.PortfolioElements.Transaction;

import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Kimberly Sookoo on 4/2/16.
 */
public class ReadTransactions {

    private static ArrayList<CashAccount> cashAccount;
    private static ArrayList<Transaction> allTransactions;


    /**
     * Reads in external transaction file that the user chooses to import.
     * @param file - file that user chooses to upload.
     * @return - arraylist containing the user's imported holdings.
     * Created by: Kimberly Sookoo.
     */
    public static ArrayList<Transaction> readTransactionImports(File file) {
        ArrayList<String[]> splitFile = new ArrayList<>();
        allTransactions = new ArrayList<>();

        BufferedReader reader = null;
        String splitLine;

        try {
            reader = new BufferedReader(new FileReader(file));
            while ((splitLine = reader.readLine()) != null) {
                splitLine = splitLine.substring(1, splitLine.length() - 1);
                String[] split = splitLine.split("\",\"");
                splitFile.add(split);
            }
        } catch (FileNotFoundException e) {
            System.out.println("File not found! Please try again.");
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        for (String[] line : splitFile) {
            Date date = null;
            try {
                date = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy").parse(line[1]);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            double amount = Double.parseDouble(line[0]);
            Transaction trans = new Transaction(amount, date, line[2], line[3]);
            allTransactions.add(trans);
        }

        return allTransactions;
    }

    /**
     * Allows access to populated cash account.
     * @return cashAccount
     */
    public static ArrayList<CashAccount> getCashAccount(ArrayList<Transaction> transactions) {
        cashAccount = new ArrayList<CashAccount>();

        for (Transaction t : transactions) {
            cashAccount.add(new CashAccount(t.getCashAccountName(), t.getAmount(), t.getDateMade(), transactions));
        }

        return cashAccount;
    }
}
