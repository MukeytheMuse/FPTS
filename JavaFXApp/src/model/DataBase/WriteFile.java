package model.DataBase;

import gui.FPTS;
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

    FPTS fpts = FPTS.getSelf();

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
            FileWriter writerT = new FileWriter(transFile, true);
            FileWriter writerC = new FileWriter(cashFile, true);

            //this.transactionsWriter(user, writerT);
            this.cashAccountsWriter(writerC);
            this.holdingsWriter(holdingsFile);

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
    Public method that updates portfolio for given user.
     */
    public void updatePortfolioForUser(User user) {
        try {
            File directory = new File("JavaFXApp/src/model/Database/Portfolios/" + user.getLoginID());
            File transFile = new File(directory, "Trans.csv");
            File cashFile = new File(directory, "Cash.csv");
            File holdingsFile = new File(directory, "Holdings.csv");
            FileWriter writerT = new FileWriter(transFile, true);
            FileWriter writerC = new FileWriter(cashFile, true);

            cashAccountsWriter(writerC);
            holdingsWriter(holdingsFile);

            System.out.println("Has anything extra been written?");

        } catch (Exception e) {
        }
    }

    /*
    Private method for writing down holdings
     */
    private void holdingsWriter(File file) {

        try {
            FileReader reader = new FileReader(file);
            FileWriter writerH = new FileWriter(file, true);
            BufferedReader bufferedReader = new BufferedReader(reader);
            BufferedWriter bufferedWriter = new BufferedWriter(writerH);
            ArrayList<Holding> holding = fpts.getPortfolio().getHoldings();
            String line = "";

            for (int i = 0; i < holding.size(); i++) {
                if ((line = bufferedReader.readLine()) != null) {
                    line = line.substring(1, line.length() - 1);
                    String[] split = line.split("\",\"");
                    if (holding.get(i).getSymbol().equals(split[0])){
                        System.out.println("Did I ever make it here?");
                        holding.remove(i);
                    }
                }
            }
            bufferedReader.close();

            for (int i = 0; i < holding.size(); i++) {
                bufferedWriter.write("\"" + holding.get(i).getSymbol() + "\",\"" + holding.get(i).getHoldingName() + "\",\"" +
                        holding.get(i).getValuePerShare() + "\",\"" + holding.get(i).getNumOfShares() + "\",\"" +
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
    private void cashAccountsWriter(FileWriter writer) {
        try {
            BufferedWriter bufferedWriter = new BufferedWriter(writer);
            ArrayList<CashAccount> cashAccounts = fpts.getPortfolio().getCashAccounts();
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
