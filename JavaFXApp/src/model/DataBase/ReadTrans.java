package model.DataBase;


import model.PortfolioElements.*;
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
 * Class for reading in transactions.
 */
public class ReadTrans {

    public static ArrayList<Transaction> readDB(String un) {
        ArrayList<String[]> splitFile = ReadFile.readInUser(un + "/Trans.csv");
        return readTransactionImports(splitFile);
    }

    /**
     * Reads in external transaction file that the user chooses to import.
     *
     * @param splitFile - file that user chooses to upload.
     * @return - arraylist containing the user's imported holdings.
     */
    public static ArrayList<Transaction> readTransactionImports(ArrayList<String[]> splitFile) {

        ArrayList<Transaction> allTransactions = new ArrayList<>();

        for (String[] line : splitFile) {
            Date date = null;
            try {
                date = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy").parse(line[2]);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            double amount = Double.parseDouble(line[1]);
            //(double amount, String dateMade, String type, String cashAccountName) {

            String cashAccountName = line[0];
            String type = line[3];
            Transaction trans;
            if (type.equals("Withdrawal")) {
                trans = new Withdrawal(amount, date);
                trans.setCashAccountName(cashAccountName);
            } else {//if(stringType.equals("Deposit")){
                trans = new Deposit(amount, date);
                trans.setCashAccountName(cashAccountName);
            }



            allTransactions.add(trans);
        }

        return allTransactions;
    }
