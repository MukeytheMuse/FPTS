

package model;

import gui.FPTS;
import java.io.*;
import java.nio.file.*;
import java.nio.file.attribute.FileAttribute;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class User implements Serializable {
    private String loginID;
    private String password;
    private Portfolio myPortfolio;
    private static Map<String, User> allUsersMap = new HashMap();
    //TODO: Warning:(17, 52) Unchecked assignment: 'java.util.HashMap' to 'java.util.Map<java.lang.String,model.User>'


    public Map<String, User> getAllUsersMap(){
        return allUsersMap;
    }


    //private final String dateFormatPattern = "yyyy/MM/dd";

//    public User(String loginID, String password) {
//        this.loginID = loginID;
//        this.password = hash(password);
//    }


    /**
     * Acts as a temporary user for accessing static methods.
     *
     * @param uid
     *
     * Author(s): Kaitlin Brockway
     */
    public User(String uid){
        this.loginID = uid;
    }

    /**
     * Used by fillUsers method.
     *
     * @param loginID
     * @param password
     * @param portfolio
     *
     * Author(s): Kaitlin Brockway
     */
    public User(String loginID, String password, Portfolio portfolio){
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
     *
     * Private in order to protect any outside sources from obtaining the
     * hashing method.
     *
     * @param password
     * @return
     */
    private static String hash(String password) {
        String encryptedPW = "";

        for(int i = 0; i < password.length(); ++i) {
            char encryptedChar = (char)(password.charAt(i) + 1);
            encryptedPW = encryptedPW + encryptedChar;
        }

        return encryptedPW;
    }

    private static String unHash(String password) {
        String textPass = "";

        for(int i = 0; i < password.length(); ++i) {
            char encryptedChar = (char)(password.charAt(i) - 1);
            textPass = textPass + encryptedChar;
        }

        return textPass;
    }

    public boolean equals(Object o) {
        if(!(o instanceof User)) {
            return false;
        } else {
            User cur_user = (User)o;
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
     *
     * TODO: ask if this should be case sensitive???****
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
        if(allUsersMap.containsKey(username)) {
            String hashedPasswordMappedTo = ((User) allUsersMap.get(username)).getPassword();
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
     *     from the UserData.csv file.
     */
    public static void fillUsers() {
        String user_csv = "JavaFXApp/src/model/DataBase/UserData.csv";
        BufferedReader reader = null;
        String line;

        try {
            reader = new BufferedReader(new FileReader(user_csv));
            while ((line = reader.readLine()) != null) {
                String[] split = line.split(",");
                String un = split[0];
                String pwd = split[1];
                ArrayList<CashAccount> usersCashAccounts = readInCashFile(un);
                //line above also utilizes readInTransFile(String userID) method
                //ArrayList<Holding> usersHoldings = ReadHoldings.read();//THIS CAUSED ERRORS FOR SOME REASON
                ArrayList<Holding> usersHoldings = readInHoldingsFile(un);

                Portfolio userPortfolio = new Portfolio(usersHoldings, usersCashAccounts);
                User newUser = new User(un, unHash(pwd), userPortfolio);

                System.out.println(un);
                //have to unhash before creating a user because the password is hashed during creation
                allUsersMap.put(un, newUser);
            }
        } catch (FileNotFoundException e) {
            System.out.println("JavaFXApp/src/model/DataBase/UserData.csv not found! Please try again.");
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
     * Reads Cash.csv and Trans.csv in order to created a populated list of Cash Accounts
     * with their corresponding transaction history.
     *
     * @param userID: Current User being created and populated with their information
     *              from the database when the system first starts up.
     * @return
     *
     * Author(s): Ian London and Kaitlin Brockway
     */
    private static ArrayList<CashAccount> readInCashFile(String userID){
        String cash_csv = "JavaFXApp/src/model/DataBase/Portfolios/" + userID + "/Cash.csv";
        String line;
        BufferedReader reader = null;
        String cashAccountName;
        String cashAccountTotalValue;
        String cashAccountDateAdded;
        ArrayList<CashAccount> usersCashAccounts = new ArrayList<>();
        Map<String, ArrayList<Transaction>> cashAccountNameTransactionsMap;

        try {
            reader = new BufferedReader(new FileReader(cash_csv));
            cashAccountNameTransactionsMap = readInTransFile(userID);//get the transactions from Trans.csv
            //Each line in the file is formatted: AccountName, currentValue, dateAdded
            //loops through while there are still lines with information left in the file
            while ((line = reader.readLine()) != null ){
                String[] split = line.split(",");
                cashAccountName = split[0];
                cashAccountName = cashAccountName.substring(1, (cashAccountName.length() - 1));//strips the first @ last "
                cashAccountTotalValue = split[1];
                cashAccountTotalValue = cashAccountTotalValue.substring(1, (cashAccountTotalValue.length() - 1));//strips the first @ last "
                double doubleCashATotalValue = Double.parseDouble(cashAccountTotalValue);
                cashAccountDateAdded = split[2];
                cashAccountDateAdded = cashAccountDateAdded.substring(1, (cashAccountDateAdded.length() - 1));//strips the first @ last "
                //TODO: figure out why date conversion throws parsing errors and fix and change types in class constructor.
                //DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy MM dd");
                //LocalDate parsedDate = LocalDate.parse(cashAccountDateAdded, formatter);

                CashAccount cashAccountToAdd = new CashAccount(cashAccountName, doubleCashATotalValue , cashAccountDateAdded, cashAccountNameTransactionsMap.get(cashAccountName));
                usersCashAccounts.add(cashAccountToAdd);
            }
        } catch (FileNotFoundException e){
            System.out.println("JavaFXApp/src/model/DataBase/Portfolios/" + userID + "/Cash.csv not found! Please try again.");
        } catch(IOException e) {
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
        return usersCashAccounts;
    }

    /**
     *
     * Holdings.csv file is in the format:
     * "tickerSymbol","holdingName","valuePricePerShare","numOfShares","aquisitionDate","index1","sector1"
     * where there may be multiple indicies and sectors.
     *
     * @param userID
     * @return
     *
     * Author(s): Kaitlin Brockway
     */
    private static ArrayList<Holding> readInHoldingsFile(String userID){
        String holdings_csv = "JavaFXApp/src/model/DataBase/Portfolios/" + userID + "/Holdings.csv";
        String line;
        BufferedReader reader = null;
        String tickerSymbol;
        String holdingName;
        String stringPricePerShare;
        String stringNumOfShares;
        String stringAcquisitionDate;
        ArrayList<String> indicies = new ArrayList<>();
        String cur_indexORsector;
        ArrayList<String> sectors = new ArrayList<>();
        ArrayList<Holding> allHoldings = new ArrayList<>();

        try {
            reader = new BufferedReader(new FileReader(holdings_csv));
            while ((line = reader.readLine()) != null ){
                String[] split = line.split(",");
                int splitLength = split.length;
                System.out.println(splitLength);
                tickerSymbol = split[0];
                tickerSymbol = tickerSymbol.substring(1, (tickerSymbol.length() - 1));//strips the first @ last "
                holdingName = split[1];
                holdingName = holdingName.substring(1, (holdingName.length() - 1));//strips the first @ last "
                stringPricePerShare = split[2];
                stringPricePerShare = stringPricePerShare.substring(1, (stringPricePerShare.length() - 1));//strips the first @ last "
                //TODO: check if the stringPricePerShare can be parsed into a double. catch
                double doublePricePerShareValue = Double.parseDouble(stringPricePerShare);
                stringNumOfShares = split[3];
                stringNumOfShares = stringNumOfShares.substring(1, (stringNumOfShares.length() - 1));//strips the first @ last "
                //TODO: check to see if stringNumOfShares can be converted to an int. catch
                int intNumOfShares = Integer.parseInt(stringNumOfShares);
                stringAcquisitionDate = split[4];
                stringAcquisitionDate = stringAcquisitionDate.substring(1, (stringAcquisitionDate.length() - 1));
                //TODO: change to date format here and for the corresponding class attribute and its constructor. catch
                // WE DON'T NEED THE TOTAL VALUE BECAUSE IT IS CALCULATED IN THE CONSTRUCTOR.
                int counter = 5;
                while(counter < splitLength){
                    cur_indexORsector = split[counter];
                    cur_indexORsector = cur_indexORsector.substring(1, cur_indexORsector.length() - 1);
                    if(FPTS.allIndicies.contains(cur_indexORsector)){
                        indicies.add(cur_indexORsector);
                    } else if(FPTS.allSectors.contains(cur_indexORsector)){
                        sectors.add(cur_indexORsector);
                    } else {
//                        System.out.println("The current index or sector being read is not included in the possibilities.");
//                        System.out.println("Check to make sure allSectors and allIndicies in the FPTS class have included all possibilities");
                        System.out.println("The current string being ignored is: " + cur_indexORsector);
                        System.out.println("This is being printed from the method readInHoldingsFile in the User Class.");
                    }
                    counter += 1;
                }
                Holding cur_holding = new Holding(tickerSymbol, holdingName, doublePricePerShareValue, intNumOfShares, stringAcquisitionDate, indicies, sectors);
                allHoldings.add(cur_holding);
            }
        } catch (FileNotFoundException e){
            System.out.println("JavaFXApp/src/model/DataBase/Portfolios/" + userID + "/Trans.csv not found! Please try again.");
        } catch(IOException e) {
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
        return allHoldings;
    }


    /**
     * BE AWARE: IF THE USER HAS TRANSACTIONS WITH A CASH ACCOUNT NAME THAT DOES NOT EXIST
     * IT WILL NOT BE SAVED IN THE PORTFOLIO. ALSO IF THE CASH ACCOUNT NAME DOES NOT HAVE ANY TRANSACTIONS
     * THE TRANSACTIONS WILL BE "null"
     *
     *
     * Reads the Trans.csv file for the userID specified as a parameter and returns
     * a map that contains a key represented as the CashAccountName with the values
     * represented as a list of the transactions associated with the cash account.
     *
     * Called by "readInCashFile" method because the cash objects need to be created with
     * the preexisting transactions.
     *
     * Private method for safety purposes.
     *
     * @param userID
     * @return
     *
     * Author(s): Kaitlin Brockway
     */
    private static Map<String, ArrayList<Transaction>> readInTransFile(String userID){
        String transactions_csv = "JavaFXApp/src/model/DataBase/Portfolios/" + userID + "/Trans.csv";
        Map<String, ArrayList<Transaction>> cashAccountNameTransactionsMap = new HashMap<>();
        String line;
        BufferedReader reader = null;
        String stringCashAccountNameAssociatedWith;
        String stringAmount;
        String stringDateMade;

        String stringType;
        // Line format:
        // cashAccountNameAssociatedWith, amount, year, month, day, typeWithdrawalTransferOrDeposit
        try {
            reader = new BufferedReader(new FileReader(transactions_csv));
            while ((line = reader.readLine()) != null ){
                String[] split = line.split(",");
                stringCashAccountNameAssociatedWith = split[0];
                stringCashAccountNameAssociatedWith = stringCashAccountNameAssociatedWith.substring(1, (stringCashAccountNameAssociatedWith.length() - 1));//strips the first @ last "
                stringAmount = split[1];
                stringAmount = stringAmount.substring(1, (stringAmount.length() - 1));//strips the first @ last "
                stringDateMade = split[2];
                stringDateMade = stringDateMade.substring(1, (stringDateMade.length() - 1));//strips the first @ last "
                //System.out.println(stringDateMade);
                stringType = split[3];
                stringType = stringType.substring(1, (stringType.length() - 1));//strips the first @ last "
                //convert certain fields to their appropriate types for the constructor.
                //TODO: ADD CHECK TO SEE IF "stringAmount" is in the format 90809890.99 with only numbers as parts of the string.
                double amount = Double.parseDouble(stringAmount);//need the amount in double format to use the transaction constructor
                //TODO: figure out why date conversion throws parsing errors and fix and change types in class constructor.
//                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy MM dd");
//                LocalDate parsedDate = LocalDate.parse(stringDateMade, formatter);
                Transaction newTransactionToAdd = new Transaction(amount, stringDateMade , stringType, stringCashAccountNameAssociatedWith);
                if (cashAccountNameTransactionsMap.containsKey(stringCashAccountNameAssociatedWith)) {
                    ArrayList<Transaction> newTransactionsList = cashAccountNameTransactionsMap.get(stringCashAccountNameAssociatedWith);
                    newTransactionsList.add(newTransactionToAdd);
                    cashAccountNameTransactionsMap.replace(stringCashAccountNameAssociatedWith, newTransactionsList);
                } else {
                    ArrayList<Transaction> transactionListToAdd = new ArrayList<>();
                    transactionListToAdd.add(newTransactionToAdd);
                    cashAccountNameTransactionsMap.put(stringCashAccountNameAssociatedWith, transactionListToAdd);
                }
            }
        } catch (FileNotFoundException e){
            System.out.println("JavaFXApp/src/model/DataBase/Portfolios/" + userID + "/Trans.csv not found! Please try again.");
        } catch(IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
//                    System.out.print("Closed Successfully. ");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return cashAccountNameTransactionsMap;
    }

    /**
     * Adds the user to UserDate.csv that holds all the users usernames and associated passwords.
     *
     * @param usr
     * @param pw1
     *
     * Author(s): Kaitlin Brockway &
     *
     */
    public void addUser(User usr, String pw1, ArrayList<Holding> holdings, ArrayList<Transaction> transactions) {
        FileWriter fileWriter = null;
        BufferedWriter bufferedWriter = null;
        try {
            fileWriter = new FileWriter((new File("JavaFXApp/src/model/Database/UserData.csv")).getAbsolutePath(), true);
            bufferedWriter = new BufferedWriter(fileWriter);
            bufferedWriter.write(usr.getLoginID() + ",");
            bufferedWriter.write(hash(pw1));
            bufferedWriter.newLine();
            bufferedWriter.close();
            //create a new user directory for the new user registering.
            File newUserDir = new File("JavaFXApp/src/model/Database/Portfolios/" + usr.loginID + "/");
            newUserDir.mkdir();//not sure why IntelliJ says that this is ignored because it works
            //create 3 new files inside the newUserDir
            File newTransFile = new File(newUserDir.getAbsolutePath() + "/Trans.csv");
            File newCashFile = new File(newUserDir.getAbsolutePath() + "/Cash.csv");
            File newHoldingsFile = new File(newUserDir.getAbsolutePath() + "/Holdings.csv");
            try{
                newCashFile.createNewFile();
                newTransFile.createNewFile();
                newHoldingsFile.createNewFile();
            } catch (IOException e){
                e.printStackTrace();
            }
        } catch (IOException var6) {
            var6.printStackTrace();
        }
        //if there are holdings to import
        if(!holdings.isEmpty()){
            addHoldings(usr, holdings);//TODO:
        }
        //if there are transactions to import
        if(!transactions.isEmpty()){
            addCash(usr, transactions);//TODO:
        }
    }


    //TODO: write to a new file for when user is registering
    /**
     * Writes the users holding content to a file called Holdings.csv.
     *
     * @param user
     */
    private void addHoldings(User user, ArrayList<Holding> holdings){
        FileWriter fileWriter = null;
        BufferedWriter bufferedWriter = null;
//        try {
//
//        }

    }



    /**
     * Writes the users holding content to a file called Cash.csv in the form
     * "CashAccountForUser1","345.00","yyyy/mm/dd"
     *
     * //TODO:
     *
     * @param user
     */
    private void addCash(User user, ArrayList<Transaction> transactions){
        FileWriter fileWriter = null;
        BufferedWriter bufferedWriter = null;
//        if(!transactions.isEmpty()){
//            for(Transaction t: transactions){
//
//            }
//        }
//            //fileWriter = new FileWriter((new File("JavaFXApp/src/model/Database/UserData.csv")).getAbsolutePath(), true);
//            bufferedWriter = new BufferedWriter(fileWriter);
//            bufferedWriter.write(user.getLoginID() + ",");
//            bufferedWriter.write(user.getPassword());
//            bufferedWriter.newLine();
//            bufferedWriter.close();
//        } catch (IOException var6) {
//            var6.printStackTrace();
//        }
//

    }
}
