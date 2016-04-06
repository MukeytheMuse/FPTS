package model.DataBase;

import model.PortfolioElements.CashAccount;
import model.PortfolioElements.Deposit;
import model.PortfolioElements.Transaction;
import model.PortfolioElements.Withdrawal;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Map;

/**
 * Created by Kimberly Sookoo on 4/2/16.
 */
public class ReadTransactions {

    /**
     * Reads in external holdings file that the user chooses to import.
     *
     * @param file - file that user chooses to upload.
     * @return - arraylist containing the user's imported holdings.
     * Created by: Kaitlin Brockway & Kimberly Sookoo
     */
    public static ArrayList<Transaction> readInImports(File file) {
        String path = file.getPath();
        ArrayList<String[]> splitFile = ReadFile.readIn(path);
        return readTransactionImports(splitFile);
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
            if(type.equals("Withdrawal")){
                trans = new Withdrawal(amount, date);
            } else {//if(stringType.equals("Deposit")){
                trans = new Deposit(amount, date);
            }

            allTransactions.add(trans);
        }

        return allTransactions;
    }
}
