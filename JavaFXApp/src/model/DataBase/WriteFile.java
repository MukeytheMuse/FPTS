package model.DataBase;

import model.CashAccount;
import model.Holding;
import model.Transaction;
import model.User;

import java.io.*;
import java.util.ArrayList;

/**
 * Created by Kimberly Sookoo on 3/2/16.
 */
public class WriteFile {

    /*
    Public method that checks to see if customer has a portfolio
    */
    public boolean hasPortfolio(User user) {
        File directory = new File("JavaFXApp/src/model/Database/Portfolios/" + user.getLoginID());
        return directory.exists();
    }

    /*
 Public method that creates portfolio for customer.
 */
    public void createPortfolioForUser(User user) {
        try {
            File directory = new File("JavaFXApp/src/model/Database/Portfolios/" + user.getLoginID());
            if (!directory.exists()) {
                directory.mkdir();
            }
            File transFile = new File(directory, "Trans.csv");
            File cashFile = new File(directory, "Cash.csv");
            File holdingsFile = new File(directory, "Holdings.csv");
            transFile.createNewFile();
            cashFile.createNewFile();
            holdingsFile.createNewFile();
            FileWriter writerT = new FileWriter(transFile,true);
            FileWriter writerC = new FileWriter(cashFile, true);
            FileWriter writerH = new FileWriter(holdingsFile, true);

            //this.transactionsWriter(user, writerT);
            this.cashAccountsWriter(user, writerC);
            this.holdingsWriter(user, writerH);

        } catch (Exception e1) {
            e1.printStackTrace();
        }

        System.out.println("Created");
    }

    /*
  Public method that removes portfolio for customer.
  */
    public void removePortfolioForUser(User user) {
        File directory = new File("JavaFXApp/src/model/Database/Portfolios/" + user.getLoginID());
        File transFile = new File(directory, "/Trans.csv");
        File cashFile = new File(directory, "/Cash.csv");
        File holdingsFile = new File(directory, "/Holdings.csv");
        transFile.delete();
        cashFile.delete();
        holdingsFile.delete();
        directory.delete();
    }

    /*
    Private method for writing down holdings
     */
    private void holdingsWriter(User user, FileWriter writer) {
        try {
            BufferedWriter bufferedWriter = new BufferedWriter(writer);
            ArrayList<Holding> holding = user.getMyPortfolio().getHoldings();
            for (int i = 0; i < holding.size(); i++) {
                bufferedWriter.write("\"" + holding.get(i).getSymbol() + "\",\"" + holding.get(i).getHoldingName() + "\",\"" +
                holding.get(i).getValuePerShare() + "\"," + holding.get(i).getNumOfShares() + "\",\"" +
                holding.get(i).getAcquisitionDate() + "\",\"" + holding.get(i).getIndices() + "\",\"" +
                        holding.get(i).getSectors() + "\"");
                bufferedWriter.newLine();
            }
            bufferedWriter.close();
        } catch (Exception e) {

        }
    }

    /*
    Private method for writing down cash accounts
     */
    private void cashAccountsWriter(User user, FileWriter writer) {
        try {
            BufferedWriter bufferedWriter = new BufferedWriter(writer);
            ArrayList<CashAccount> cashAccounts = user.getMyPortfolio().getCashAccounts();
            for (int i = 0; i < cashAccounts.size(); i++) {
                bufferedWriter.write("\"" + cashAccounts.get(i).getAccountName() + "\",\"" + cashAccounts.get(i).getValue() +
                "\",\"" + cashAccounts.get(i).getDateAdded() + "\"");
                bufferedWriter.newLine();
            }
            bufferedWriter.close();
        } catch (Exception e) {

        }
    }

    /*
    Private method for writing down cash accounts
     */
    private void transactionsWriter(User user, FileWriter writer) {
        try {
            BufferedWriter bufferedWriter = new BufferedWriter(writer);
            ArrayList<Transaction> transactions = user.getMyPortfolio().getTransactions();
            for (int i = 0; i < transactions.size(); i++) {

                bufferedWriter.newLine();
            }
            bufferedWriter.close();
        } catch (Exception e) {

        }
    }
}
