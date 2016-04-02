package model.DataBase;

import model.User;

import java.io.*;

public class WriteFile {
    public WriteFile() {
    }

    public boolean hasPortfolio(User user) {
        File directory = new File("model/Database/Portfolios/" + user.getLoginID());
        return directory.exists();
    }

    public void createPortfolioForUser(User user) {
        try {
            File e1 = new File("model/Database/Portfolios/" + user.getLoginID());
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
            this.holdingsWriter(holdingsFile);
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
            File e = new File("model/Database/Portfolios/" + user.getLoginID());
            File transFile = new File(e, "Trans.csv");
            File cashFile = new File(e, "Cash.csv");
            File holdingsFile = new File(e, "Holdings.csv");
            new FileWriter(transFile, true);
            FileWriter writerC = new FileWriter(cashFile, true);
            this.cashAccountsWriter(writerC);
            this.holdingsWriter(holdingsFile);
            System.out.println("Has anything extra been written?");
        } catch (Exception var8) {
            //TODO: check Warning:(73, 11) Empty 'catch' block
        }

    }

    /**
     *
     * @param file
     */
    private void holdingsWriter(File file) {
        try {
            FileReader e = new FileReader(file);
            FileWriter writerH = new FileWriter(file, true);
            BufferedReader bufferedReader = new BufferedReader(e);
            new BufferedWriter(writerH);
            bufferedReader.close();
        } catch (Exception var6) {
            //TODO: check Warning:(90, 11) Empty 'catch' block
        }

    }

    /**
     *
     * @param writer
     */
    private void cashAccountsWriter(FileWriter writer) {
    }
}

