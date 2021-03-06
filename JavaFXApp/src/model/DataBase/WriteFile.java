package model.DataBase;

import model.PortfolioElements.CashAccount;
import model.PortfolioElements.Holding;
import model.PortfolioElements.Transaction;
import model.PortfolioElements.WatchedEquity;
import model.UndoRedo.Command;
import model.User;

import java.io.*;
import java.net.URL;
import java.net.URLDecoder;
import java.util.ArrayList;

public class WriteFile {
    public WriteFile() {
    }

    /*
     * @author: Ian London
     */
    public static void makeDB() {
        try {
            String path = getPath();
            String fileSeparator = System.getProperty("file.separator");
            //create database directory
            String newBase = path + fileSeparator + "lilBase" + fileSeparator;
            File lilB = new File(newBase);
            //create portfolios directory
            String newPort = newBase + "Portfolios" + fileSeparator;
            File lilPortfolios = new File(newPort);
            //create file for user data base
            File userDataFile = new File(lilB, "UserData.csv");

            if (!lilB.exists()) {
                lilB.mkdir();
                lilPortfolios.mkdir();
                userDataFile.createNewFile();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /*
     * Finds the jar file's path and returns it.
     *
     * @author: Ian London
     */
    public static String getPath() throws UnsupportedEncodingException {
        URL url = WriteFile.class.getProtectionDomain().getCodeSource().getLocation();
        String jarPath = URLDecoder.decode(url.getFile(), "UTF-8");
        String parentPath = new File(jarPath).getParentFile().getPath();
        return parentPath;
    }

    //temporarily out of use
    public void addUser(String un, String pw) {
        try {
            FileWriter fileWriter = new FileWriter((new File(getPath() + "/lilBase/UserData.csv")), true);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            bufferedWriter.write(un + ",");
            bufferedWriter.write(pw);
            bufferedWriter.newLine();
            bufferedWriter.close();
        } catch (Exception var8) {
            var8.printStackTrace();
        }
    }

    /**
     * @param user
     */
    public void updatePortfolioForUser(User user) {
        try {
            File e = new File(getPath() + "/lilBase/Portfolios/" + user.getLoginID());
            if (!e.exists()) {
                e.mkdir();
            }
            File transFile = new File(e, "Trans.csv");
            File cashFile = new File(e, "Cash.csv");
            File holdingsFile = new File(e, "Holdings.csv");
            File watchedEquitiesFile = new File(e, "Watch.csv");
            this.transactionsWriter(user.getMyPortfolio().getTransactions(), transFile);
            this.cashAccountsWriter(user.getMyPortfolio().getCashAccounts(), cashFile);
            this.holdingsWriter(user.getMyPortfolio().getHoldings(), holdingsFile);
            this.equityWriter(user.getMyPortfolio().getWatchedEquities(), watchedEquitiesFile);
        } catch (Exception var8) {
            var8.printStackTrace();
        }

    }

    /**
     * @param user
     */
    public void exportPortfolioForUser(User user) {
        try {
            File e = new File(getPath() + "/" + user.getLoginID() + "'s exports");
            if (!e.exists()) {
                e.mkdir();
            }
            File transFile = new File(e, "Trans.csv");
            File cashFile = new File(e, "Cash.csv");
            File holdingsFile = new File(e, "Holdings.csv");
            this.transactionsWriter(user.getMyPortfolio().getTransactions(), transFile);
            this.cashAccountsWriter(user.getMyPortfolio().getCashAccounts(), cashFile);
            this.holdingsWriter(user.getMyPortfolio().getHoldings(), holdingsFile);
        } catch (Exception var8) {
            var8.printStackTrace();
        }

    }


    /**
     * @param file
     */
    public void holdingsWriter(ArrayList<Holding> holdings, File file) {
        try {

            file.delete();
            FileWriter writerH = new FileWriter(file, true);
            BufferedWriter bufferedWriter = new BufferedWriter(writerH);

            if (holdings.size() > 1) {
                for (int i = 0; i < holdings.size(); i++) {
                    for (int j = (i + 1); j < holdings.size(); j++) {
                        if (holdings.get(i).getTickerSymbol().equals(holdings.get(j).getTickerSymbol())) {
                            Holding iHolding = holdings.get(i);
                            Holding jHolding = holdings.get(j);
                            int numberOfShares = (iHolding.getNumOfShares() + jHolding.getNumOfShares());
                            Holding h = new Holding(iHolding.getTickerSymbol(), iHolding.getDisplayName(),iHolding.getPricePerShare(), numberOfShares, iHolding.getAcquisitionDate(), iHolding.getIndices(), iHolding.getSectors());
                            holdings.add(h);
                            holdings.remove(iHolding);
                            holdings.remove(jHolding);
                        }
                    }
                }
            }

            for (Holding holding : holdings) {
                bufferedWriter.write("\"" + holding.getTickerSymbol() + "\",\"" +
                        holding.getDisplayName() + "\",\"" + holding.getPricePerShare() + "\",\""
                        + holding.getNumOfShares() + "\",\"" + holding.getAcquisitionDate() + "\"");
                for (String s : holding.getIndices()) {
                    bufferedWriter.write(",\"" + s + "\"");
                }
                for (String se : holding.getSectors()) {
                    bufferedWriter.write(",\"" + se + "\"");
                }
                bufferedWriter.newLine();
            }
            bufferedWriter.close();

        } catch (Exception var6) {
            var6.printStackTrace();
        }

    }


    /**
     *
     */
    public void equityWriter(ArrayList<WatchedEquity> watchedEquities, File file) {
        try {
            file.delete();
            FileWriter fileWriterE = new FileWriter(file, true);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriterE);

            for (WatchedEquity watchedEquity : watchedEquities) {
                bufferedWriter.write("\"" + watchedEquity.getSymbol() + "\",\"" + watchedEquity.getHighTrigger()
                        + "\",\"" + watchedEquity.getLowTrigger() + "\",\"" + watchedEquity.isExceedsTrigger() +
                        "\",\"" + watchedEquity.isHasExceededTrigger() + "\",\"" + watchedEquity.isNotMeetsTrigger()
                        + "\",\"" + watchedEquity.isHasNotMetTrigger() + "\"");
                bufferedWriter.newLine();
            }
            bufferedWriter.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * @param cashAccounts
     */
    private void cashAccountsWriter(ArrayList<CashAccount> cashAccounts, File cashFile) {

        try {
            cashFile.delete();
            FileWriter fileWriterC = new FileWriter(cashFile, true);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriterC);

            for (CashAccount cashAccount : cashAccounts) {
                bufferedWriter.write("\"" + cashAccount.getAccountName() + "\",\"" + cashAccount.getValue() + "\",\"" +
                        cashAccount.getDateAdded() + "\"");
                bufferedWriter.newLine();
            }
            bufferedWriter.close();
        } catch (Exception e) {

        }
    }


    /**
     * Writes the users holding content to a file called Cash.csv in the form
     * "CashAccountForUser1","345.00","yyyy/mm/dd"
     *
     * @param transactions
     * @author: Kimberly Sookoo
     */
    private void transactionsWriter(ArrayList<Transaction> transactions, File importedTransactions) {
        FileWriter writerT = null;
        try {
            importedTransactions.delete();
            writerT = new FileWriter(importedTransactions, true);
            BufferedWriter bufferedWriter = new BufferedWriter(writerT);

            for (Transaction transaction : transactions) {
                bufferedWriter.write("\"" + transaction.getCashAccountName() + "\",\"" + transaction.getAmount() +
                        "\",\"" + transaction.getDateMade() + "\",\"" + transaction.getType() + "\"");
                bufferedWriter.newLine();
            }
            bufferedWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}

