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
    public boolean hasPortfolio(User user)   {
        boolean created = false;
        String line;

        try {
            String directory = "JavaFXApp/src/model/Database/Portfolios/" + user.getLoginID() + "/Trans.csv";
            System.out.println(directory);
            BufferedReader reader = new BufferedReader(new FileReader(directory));
            while ((line = reader.readLine()) != null) {
                if (line.length() != 0) {
                    String[] splitLine = line.split(",");
                    System.out.println(splitLine[0]);
                    if (splitLine[0].equals(user.getLoginID())) {
                        created = true;
                    }
                }
            }

        } catch (Exception e) {
            System.out.println("Not found! Creating new file!");
        }

        return created;
    }

    /*
 Public method that creates portfolio for customer.
 */
    public void createPortfolioForUser(User user) {
        BufferedWriter bufferedWriter = null;

        try {
            File directory = new File("JavaFXApp/src/model/Database/Portfolios");
            if (!directory.exists()) {
                directory.mkdir();
            }
            File userDir = new File(directory, "/" + user.getLoginID());
            if (!userDir.exists()) {
                userDir.mkdir();
            }
            File transFile = new File(userDir, "Trans.csv");
            File cashFile = new File(userDir, "Cash.csv");
            File holdingsFile = new File(userDir, "Holdings.csv");
            transFile.createNewFile();
            cashFile.createNewFile();
            holdingsFile.createNewFile();
            FileWriter writer = new FileWriter(transFile,true);
            bufferedWriter = new BufferedWriter(writer);
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

        File directory = new File("JavaFXApp/src/model/Database/Portfolios/");
        File userDir = new File(directory, user.getLoginID());
        File transFile = new File(userDir, "/Trans.csv");
        File cashFile = new File(userDir, "/Cash.csv");
        File holdingsFile = new File(userDir, "/Holdings.csv");
        transFile.delete();
        cashFile.delete();
        holdingsFile.delete();
        userDir.delete();

    }
}
