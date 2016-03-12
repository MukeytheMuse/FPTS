/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.*;
import java.util.List;
import java.util.ArrayList;
import java.util.Scanner;

/**
 *
 * @author ericepstein
 */
public class User {
    private String loginID;
    private String password;
    private Portfolio myPortfolio;

    private static ArrayList<User> userList = new ArrayList<User>();

        /**
         * When creating a new portfolio, the system shall allow the user to
         * import holdings and transactions to initialize the new portfolio. THIS IS NOT ALLOWED YET
         * @param loginID - String - Login ID of of the User.
         * @param password - String - Password of the User, stored in a hashed setting.
         */
    public User(String loginID, String password) {
        this.loginID = loginID;
        this.password = hash(password);
        userList.add(this);


        this.myPortfolio = new Portfolio();//*****************************
    }

    public String hash(String password) {
        String encryptedPW = "";
        for (int i = 0; i < password.length(); i++) {
            char encryptedChar = (char) (password.charAt(i) + 1);
            encryptedPW += encryptedChar;
        }
        return encryptedPW;
    }

    public static String unHash(String password) {
        String textPass = "";
        for (int i = 0; i < password.length(); i++) {
            char encryptedChar = (char) (password.charAt(i) - 1);
            textPass += encryptedChar;
        }
        return textPass;
    }

    public boolean equals(User u) {
        return u.getLoginID().equals(loginID) && u.getPassword().equals(password);
    }

    public String getLoginID() {
        return loginID;
    }

    private String getPassword() {
        return password;
    }

    /**
     * Private method used to populate the users ArrayList<User> from the UserData.csv file.
     */
    public static void fillUsers(){
        try {
            Scanner scanner = new Scanner(new File("src/DataBase/UserData.csv"));

            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                if(line.length() != 0){
                    //System.out.println(line);
                    String[] splitLine = line.split(",");
                    //System.out.println(splitLine[0]);
                    User newUser = new User(splitLine[0], splitLine[1]);
                }
            }

        } catch (FileNotFoundException e1) {
            e1.printStackTrace();
        }
    }

    public boolean validateUser(){
        for(User usr : userList){
            if(this.equals(usr)){
                return true;
            }
        }
        return false;
    }

    public static boolean ValidLoginID(String id){
        for(User usr : userList){
            if(usr.getLoginID().equals(id)){
                return false;
            }
        }
        return true;
    }

    /*
    Private method that checks to see if customer has a portfolio
    */
    public boolean hasPortfolio() {
        boolean created = false;

        for (User user : userList) {

            if (this.equals(user)) {

                try {
                    Scanner scanner = new Scanner(new File("Portfolios.txt"));

                    while (scanner.hasNextLine()) {
                        String line = scanner.nextLine();
                        if (line.length() != 0) {
                            String[] splitLine = line.split(",");
                            if (splitLine[0].equals(user.getLoginID())) {
                                created = true;
                            }
                        }
                    }

                } catch (FileNotFoundException e1) {
                    e1.printStackTrace();
                }
            }
        }

        return created;
    }

}

    /*
    INVALID NOW
    //A	The system shall allow the user to add holdings to a portfolio.
    public void addHoldings(List<Holding> holdings){
        for(Holding cur_h: holdings ){
            myPortfolio.addHolding(cur_h);
        }
    }
    */
