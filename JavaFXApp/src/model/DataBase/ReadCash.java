package model.DataBase;

import model.PortfolioElements.CashAccount;
import model.PortfolioElements.Holding;
import model.PortfolioElements.Transaction;

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
        Map<String, ArrayList<Transaction>> cashAccountNameTransactionsMap;

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

            //cashAccountNameTransactionsMap = readInTransFile(userID);

            //CashAccount cashAccountToAdd = new CashAccount(cashAccountName, doubleCashATotalValue , new Date(), cashAccountNameTransactionsMap.get(cashAccountName));
//                        if( cashAccountNameTransactionsMap.containsKey(cashAccountToAdd.getAccountName())){
//                            ArrayList<Transaction> newTransactions = new ArrayList<>();
//                            ArrayList<Transaction> curTransactions = cashAccountNameTransactionsMap.get(cashAccountToAdd.getAccountName());
//                            for(Transaction t: curTransactions){
//                                t.setCashAccount(cashAccountToAdd);
//                                newTransactions.add(t);
//                            }
//                            cashAccountToAdd.setTransactions(newTransactions);
//                            usersCashAccounts.add(cashAccountToAdd);
//                        } else {
//                            usersCashAccounts.add(cashAccountToAdd);
//                        }
//                    }
//                } catch (FileNotFoundException e) {
//                    System.out.println("JavaFXApp/src/model/DataBase/Portfolios/" + userID + "/Cash.csv not found! Please try again.");
//                } catch (IOException e) {
//                    e.printStackTrace();
//                } finally {
//                    if (reader != null) {
//                        try {
//                            reader.close();
//                        } catch (IOException e) {
//                            e.printStackTrace();
//                        }
//                    }
//
//                }
//                return usersCashAccounts;
//            }

            //TODO: ADD BACK
//            CashAccount curCash = new CashAccount(line[0], Double.parseDouble(line[1]), date);
//
//            //    public CashAccount(String s, double v, Date date) {
//            //public CashAccount(String AccountName, double initialAmount, Date dateAdded, ArrayList< Transaction > existingTransactions) {
//
//
//                // add cash accounts iteratively
//            allCash.add(curCash);
        }
        return allCash;
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
