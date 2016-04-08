package model;

import model.DataBase.ReadFile;
import model.DataBase.WriteFile;
import model.PortfolioElements.*;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class User {
    private String loginID;
    private String password;
    private Portfolio myPortfolio;
    private static Map<String, User> allUsersMap = new HashMap();

    public Map<String, User> getAllUsersMap() {
        return allUsersMap;
    }

    public void setMyPortfolio(Portfolio p) {
        myPortfolio = p;
    }


    //private final String dateFormatPattern = "yyyy/MM/dd";
    //ToDO: either use this or delete it
//    public User(String loginID, String password) {
//        this.loginID = loginID;
//        this.password = hash(password);
//    }


    /**
     * Acts as a temporary user for accessing static methods.
     *
     * @param uid Author(s): Kaitlin Brockway
     */
    public User(String uid) {
        this.loginID = uid;
        myPortfolio = new Portfolio();
    }

    /**
     * Used by fillUsers method.
     *
     * @param loginID
     * @param password
     * @param portfolio Author(s): Kaitlin Brockway
     */
    public User(String loginID, String password, Portfolio portfolio) {
        this.loginID = loginID;
        this.password = hash(password);
        this.myPortfolio = portfolio;
    }

    public Portfolio getMyPortfolio() {
        return this.myPortfolio;
    }


    /**
     * Static method allows "validateUser" to call this method
     * when checking if login input is valid.
     * <p>
     * Private in order to protect any outside sources from obtaining the
     * hashing method.
     *
     * @param password
     * @return
     */
    private static String hash(String password) {
        String encryptedPW = "";

        for (int i = 0; i < password.length(); ++i) {
            char encryptedChar = (char) (password.charAt(i) + 1);
            encryptedPW = encryptedPW + encryptedChar;
        }

        return encryptedPW;
    }

    private static String unHash(String password) {
        String textPass = "";

        for (int i = 0; i < password.length(); ++i) {
            char encryptedChar = (char) (password.charAt(i) - 1);
            textPass = textPass + encryptedChar;
        }

        return textPass;
    }

    public boolean equals(Object o) {
        if (!(o instanceof User)) {
            return false;
        } else {
            User cur_user = (User) o;
            return cur_user.getLoginID().equals(this.loginID) && cur_user.getPassword().equals(this.password);
        }
    }

    public String getLoginID() {
        return this.loginID;
    }

    private String getPassword() {
        return this.password;
    }


    /**
     * Checks to see if a username already exists in the system
     * when a user is registering with a new username and password.
     * <p>
     *
     * @param id
     * @return
     */
    public static boolean ValidLoginID(String id) {
        return !allUsersMap.containsKey(id);
    }

    /**
     * Upon clicking "Login" on the LoginPage, this method is called to validate
     * that the User has entered in a correct username and password combination
     * that exists in all the registered users. (in the allUsersMap)
     *
     * @param username: input field
     * @param password: input field
     * @return: true if the user and password combination exists in the system.
     */
    public static boolean validateUser(String username, String password) {
        if (allUsersMap.containsKey(username)) {
            String hashedPasswordMappedTo = allUsersMap.get(username).getPassword();
            String hashedPasswordEntered = hash(password);
            return hashedPasswordEntered.equals(hashedPasswordMappedTo);
        }
        return false;
    }


    public static void addToUserMap(User u) {
        allUsersMap.put(u.getLoginID(), u);
    }

    public String toString() {
        return this.loginID;
    }

    /**
     * Public method used to populate the users ArrayList<User>
     * from the UserData.csv file.
     */
    public static void fillUsers() {
        BufferedReader reader = null;
        String line;
        ReadFile readFile = new ReadFile();

        try {
            File user_csv = new File(WriteFile.getPath() + "/lilBase/UserData.csv");
            reader = new BufferedReader(new FileReader(user_csv));
            while ((line = reader.readLine()) != null) {
                String[] split = line.split(",");
                String un = split[0];
                String pwd = split[1];
                ArrayList<CashAccount> usersCashAccounts = readFile.readCash(un);
                Map<String, ArrayList<Transaction>> cashAccountNameTransactionsMap = readFile.readInTransFile(un);


                for (CashAccount cashAccountToAssociate : usersCashAccounts) {
                    //if transactions exist for this cash account
                    if (cashAccountNameTransactionsMap.containsKey(cashAccountToAssociate.getAccountName())) {
                        ArrayList<Transaction> curTransactions = cashAccountNameTransactionsMap.get(cashAccountToAssociate.getAccountName());
                        cashAccountToAssociate.setTransactions(curTransactions);//add transactions to this current cashAccount object
                        //iterate through transactions (that have a cash account association with a cash account that still exists)
                        // and add association to the transaction objects
                        for (Transaction t : curTransactions) {
                            t.setCashAccount(cashAccountToAssociate);
                        }
                    }//if not it will maintain an empty array list for its transactions
                }
                //ArrayList<Holding> usersHoldings = readInHoldingsFile(un);
                ArrayList<Holding> usersHoldings = readFile.readHoldings(un);
                ArrayList<WatchedEquity> watchedEquities = readFile.readWatchedEquities(un);
                Portfolio userPortfolio = new Portfolio(usersHoldings, usersCashAccounts);
                User newUser = new User(un, unHash(pwd), userPortfolio);

                if (watchedEquities.size() != 0) {
                    for (WatchedEquity w : watchedEquities) {
                        newUser.getMyPortfolio().addWatchedEquity(w);
                    }
                }

                //have to unhash before creating a user because the password is hashed during creation
                allUsersMap.put(un, newUser);
            }
        } catch (FileNotFoundException e) {
            System.out.println("UserData.csv not found! Please try again.");
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * Adds the user to UserDate.csv that holds all the users usernames and associated passwords.
     *
     * @param usr
     * @param pw1 Author(s): Kimberly Sookoo & Kaitlin Brockway & Ian London
     */
    public void addUser(User usr, String pw1, ArrayList<Holding> holdings, ArrayList<Transaction> transactions) {
        FileWriter fileWriter = null;
        BufferedWriter bufferedWriter = null;
        //WriteFile writeFile = new WriteFile();
        File newHoldingsFile;
        File newTransFile;
        File newCashFile;

        /* temporary quick fix to add user to lilBase
        String un = usr.getLoginID();
        writeFile.addUser(un, hash(pw1));
        writeFile.updatePortfolioForUser(usr);*/

        try {
            fileWriter = new FileWriter((new File(WriteFile.getPath() + "/lilBase/UserData.csv")).getAbsolutePath(), true);
            bufferedWriter = new BufferedWriter(fileWriter);
            bufferedWriter.write(usr.getLoginID() + ",");
            bufferedWriter.write(hash(pw1));
            bufferedWriter.newLine();
            bufferedWriter.close();
            //creates portfolio directory to store all user information
            File portfolioDir = new File(WriteFile.getPath() + "/lilBase/Portfolios/");
            //TODO: most of this code already exist in writefile, reuse please
            if (!portfolioDir.exists()) {
                portfolioDir.mkdir();
            }
            //create a new user directory for the new user registering.
            File newUserDir = new File(WriteFile.getPath() + "/lilBase/Portfolios/" + usr.loginID + "/");
            newUserDir.mkdir();
            //create 3 new files inside the newUserDir
            newTransFile = new File(newUserDir.getAbsolutePath() + "/Trans.csv");
            newCashFile = new File(newUserDir.getAbsolutePath() + "/Cash.csv");
            newHoldingsFile = new File(newUserDir.getAbsolutePath() + "/Holdings.csv");
            try {
                newCashFile.createNewFile();
                newTransFile.createNewFile();
                newHoldingsFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }

            //if there are holdings to import
            if (!holdings.isEmpty()) {
                addHoldings(holdings, newHoldingsFile);
            }
            //if there are transactions to import
            if (!transactions.isEmpty()) {
                addTrans(transactions, newTransFile);
            }

        } catch (IOException var6) {
            var6.printStackTrace();
        }
    }


    /**
     * Writes the user's holding content to a file called Holdings.csv.
     * Written by: Kimberly Sookoo
     *
     * @param holdings
     * @param importedHoldings
     */
    public void addHoldings(ArrayList<Holding> holdings, File importedHoldings) {
        WriteFile writeFile = new WriteFile();
        writeFile.holdingsWriter(holdings, importedHoldings);
    }

    //TODO: If neither of these are being used please delete them and their  ^ v

    /**
     * Writes the users holding content to a file called Cash.csv in the form
     * "CashAccountForUser1","345.00","yyyy/mm/dd"
     *
     * @param transactions
     * @author: Kimberly Sookoo
     */
    private void addTrans(ArrayList<Transaction> transactions, File importedTransactions) {
        FileWriter writerT = null;
        try {
            writerT = new FileWriter(importedTransactions, true);
            BufferedWriter bufferedWriter = new BufferedWriter(writerT);

            for (Transaction transaction : transactions) {
                bufferedWriter.write("\"" + transaction.getAmount() + "\",\"" + transaction.getDateMade() +
                        "\",\"" + transaction.getType() + "\",\"" + transaction.getCashAccountName() + "\"");
                bufferedWriter.newLine();
            }
            bufferedWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
