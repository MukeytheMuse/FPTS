package model.DataBase;

import model.PortfolioElements.Holding;
import model.User;

import javax.swing.*;
import java.io.*;
import java.net.URL;
import java.net.URLDecoder;
import java.util.ArrayList;

public class WriteFile {
    public WriteFile() {
    }

    public boolean hasPortfolio(User user) {
        //File directory = new File("model/Database/Portfolios/" + user.getLoginID());
        File directory = new File("model/Database/Portfolios/" + user.getLoginID());
        return directory.exists();
    }

    /*
     * @author: Ian London
     */
    public static void makeDB() {
        try {
            String path = getPath();
            //System.out.println("PATH ATTEMPT: " + path);

            String fileSeparator = System.getProperty("file.separator");
            //create database directory
            String newBase = path + fileSeparator + "lilBase" + fileSeparator;
            File lilB = new File(newBase);
            //create portfolios directory
            String newPort = newBase + "Portfolios" + fileSeparator;
            File lilPortfolios = new File(newPort);
            //create file for user data base
            File userDataFile = new File(lilB, "UserData.csv");

            if(!lilB.exists()) {
                //JOptionPane.showMessageDialog(null, newDir);
                lilB.mkdir();
                lilPortfolios.mkdir();
                userDataFile.createNewFile();

            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /*
     * @author: Ian London
     */
    public static String getPath() throws UnsupportedEncodingException {
        URL url = WriteFile.class.getProtectionDomain().getCodeSource().getLocation();
        String jarPath = URLDecoder.decode(url.getFile(), "UTF-8");
        String parentPath = new File(jarPath).getParentFile().getPath();
        return parentPath;
    }


    public void createPortfolioForUser(User user) {
        try {
            File e1 = new File(getPath() + "/lilBase/Portfolios/" + user.getLoginID());
            if(!e1.exists()) {
                e1.mkdir();
                //TODO: check Warning:(23, 20) Result of 'File.mkdir()' is ignored
            }

            File transFile = new File(e1, "Trans.csv");
            File cashFile = new File(e1, "Cash.csv");
            File holdingsFile = new File(e1, "Holdings.csv");
            transFile.createNewFile();
            //TODO: check Warning:(31, 26) Result of 'File.createNewFile()' is ignored
            cashFile.createNewFile();
            //TODO: check Warning:(31, 26) Result of 'File.createNewFile()' is ignored
            holdingsFile.createNewFile();
            //TODO: check Warning:(31, 26) Result of 'File.createNewFile()' is ignored
            new FileWriter(transFile, true);
            FileWriter writerC = new FileWriter(cashFile, true);
            this.cashAccountsWriter(writerC);
            //this.holdingsWriter(holdingsFile);
        } catch (Exception var8) {
            var8.printStackTrace();
        }

        System.out.println("Created");
    }



    //TODO: check Warning:(47, 17) Method 'removePortfolioForUser(model.User)' is never used
    /**
     *
     * @param user
     */
    public void removePortfolioForUser(User user) {
        File directory = new File("model/Database/Portfolios/" + user.getLoginID());
        File transFile = new File(directory, "/Trans.csv");
        File cashFile = new File(directory, "/Cash.csv");
        File holdingsFile = new File(directory, "/Holdings.csv");
        transFile.delete();
        //TODO: check Warning:(52, 19) Result of 'File.delete()' is ignored
        cashFile.delete();
        //TODO: check Warning:(52, 19) Result of 'File.delete()' is ignored
        holdingsFile.delete();
        //TODO: check Warning:(52, 19) Result of 'File.delete()' is ignored
        directory.delete();
        //TODO: check Warning:(52, 19) Result of 'File.delete()' is ignored
    }

    /**
     *
     * @param user
     */
    public void updatePortfolioForUser(User user) {
        try {
            File e = new File(getPath() + "/lilBase/Portfolios/" + user.getLoginID());
            if(!e.exists()) { e.mkdir(); }
            File transFile = new File(e, "Trans.csv");
            File cashFile = new File(e, "Cash.csv");
            File holdingsFile = new File(e, "Holdings.csv");
            new FileWriter(transFile, true);
            FileWriter writerC = new FileWriter(cashFile, true);
            this.cashAccountsWriter(writerC);
            this.holdingsWriter(user.getMyPortfolio().getHoldings(), holdingsFile);
            System.out.println("made it through WriteFile.updatePortfolioForUser!!!");
        } catch (Exception var8) {
            System.out.println("updatePortfolioForUser within WriteFile threw an exception");
        }

    }

    /**
     *
     * @param file
     */
    public void holdingsWriter(ArrayList<Holding> holdings, File file) {
        try {
            file.delete();
            FileWriter writerH = new FileWriter(file, true);
            BufferedWriter bufferedWriter = new BufferedWriter(writerH);

            for (Holding holding : holdings) {
                bufferedWriter.write("\"" + holding.getTickerSymbol() + "\",\"" +
                        holding.getName() + "\",\"" + holding.getPricePerShare() + "\",\""
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
            /*FileReader e = new FileReader(file);
            FileWriter writerH = new FileWriter(file, true);
            BufferedReader bufferedReader = new BufferedReader(e);
            new BufferedWriter(writerH);
            bufferedReader.close();*/
        } catch (Exception var6) {
            System.out.println("holdingsWriter within WriteFile threw an exception");
        }

    }

    /**
     *
     * @param writer
     */
    private void cashAccountsWriter(FileWriter writer) {
    }
}

