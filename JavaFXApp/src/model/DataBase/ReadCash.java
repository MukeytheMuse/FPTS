package model.DataBase;

import model.PortfolioElements.CashAccount;
import model.PortfolioElements.Holding;
import model.PortfolioElements.Transaction;
import model.User;

import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Map;

/**
 * Created by iLondon on 3/13/16.
 */
public class ReadCash {
    protected static ArrayList<String[]> readInFile() {
        return ReadFile.readInUser("/Cash.csv");
    }

    public static ArrayList<CashAccount> allCash;
    private static ArrayList<String[]> splitFile;




    public static ArrayList<CashAccount> readDB(String un) {
        ArrayList<String[]> splitFile = ReadFile.readInUser(un + "/Cash.csv");
        return read(splitFile);
    }
    //TODO: readInUser(String file)

    /**
     * Created by Ian
     *
     * @return list of all Holding objects
     */
    protected static ArrayList<CashAccount> read(ArrayList<String[]> file) {
        ArrayList<String[]> splitFile = file;
        ArrayList<CashAccount> allCashAccounts = new ArrayList<>();

        // iterate through each line representing a Cash Account
        for (String[] line : splitFile) {
            Date date = null;
            try {
                date = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy").parse(line[2]);
            } catch (ParseException e) {
                e.printStackTrace();
            }

            String cashAccountName = line[0];
            String stringCashValue = line[1];
            double doubleCashATotalValue = Double.parseDouble(stringCashValue);
            String cashAccountDateAdded = line[2];
            //Cash account is temporarily created with no associated transactions. Association is made in fillUsers method in the User class
            CashAccount cashAccountToAdd = new CashAccount(cashAccountName, doubleCashATotalValue , date, new ArrayList<>());
            allCashAccounts.add(cashAccountToAdd);
        }
        return allCashAccounts;
    }


    /**
     * Reads in external holdings file that the user chooses to import.
     *
     * @param file - file that user chooses to upload.
     * @return - arraylist containing the user's imported holdings.
     * Created by: Kaitlin Brockway & Kimberly.
     */
    public static ArrayList<CashAccount> readInCash(File file) {
        String path = file.getPath();
        ArrayList<String[]> splitFile = ReadFile.readIn(path);
        return read(splitFile);
    }
}
