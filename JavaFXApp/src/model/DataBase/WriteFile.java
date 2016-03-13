package model.DataBase;

import model.User;

import java.io.*;

/**
 * Created by Kimberly Sookoo on 3/12/16.
 */
public class WriteFile {

    /*
    Private method that checks to see if customer has a portfolio
    */
    public boolean hasPortfolio(User user) {
        File directory = new File("JavaFXApp/src/model/Database/Portfolios/" + user.getLoginID());
        return directory.exists();
    }

    /*
 Public method that creates portfolio for customer.
 */
    public void createPortfolioForUser(User user) {
        BufferedWriter bufferedWriter = null;

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

            this.holdingsWriter(user, writerH);
            bufferedWriter = new BufferedWriter(writerT);
            bufferedWriter.write(user.getLoginID() + ",");
            bufferedWriter.write("true");
            bufferedWriter.newLine();
            bufferedWriter.close();
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
            bufferedWriter.write(user.getMyPortfolio().getHoldings() + ",");
        } catch (Exception e) {

        }
    }
}
