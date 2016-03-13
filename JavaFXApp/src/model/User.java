/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/**
 * 
 * Stores attributes of User, generates list of Users, and validates User
 * 
 * @author Kimberly Sookoo and Ian London
 *         
 */
public class User {
    private String loginID;
    private String password;
    private Portfolio myPortfolio;
    private static ArrayList<User> userList = new ArrayList<User>();//Holds All Registered Users

    /**
     * When creating a new portfolio, the system shall allow the user to
     * import holdings and transactions to initialize the new portfolio. THIS IS NOT ALLOWED YET
     *
     * @param loginID  - String - Login ID of of the User.
     * @param password - String - Password of the User, stored in a hashed setting.
     */
    public User(String loginID, String password) {
        this.loginID = loginID;
        this.password = hash(password);
        this.myPortfolio = new Portfolio();//TODO: check if user wants to import holdings and transactions
    }

    /**
    * 
    * Ensures encryption by incrementing each password character by 1
    * 
    * @param password - String
    * @return String of password
    */
    public String hash(String password) {
        String encryptedPW = "";
        for (int i = 0; i < password.length(); i++) {
            char encryptedChar = (char) (password.charAt(i) + 1);
            encryptedPW += encryptedChar;
        }
        return encryptedPW;
    }

    /**
     * 
     * Ensures decryption by decrementing each password character by 1
     * 
     * @param password
     * @return String of password
     */
    public static String unHash(String password) {
        String textPass = "";
        for (int i = 0; i < password.length(); i++) {
            char encryptedChar = (char) (password.charAt(i) - 1);
            textPass += encryptedChar;
        }
        return textPass;
    }

    /**
     * Equality function that matches user and password
     * 
     * @param u
     * @return boolean
     */
    public boolean equals(User u) {
        return u.getLoginID().equals(loginID) && u.getPassword().equals(password);
    }
    
    /**
     * 
     * Overrides equals() method
     * 
     * Precondition - object passed must be a User object
     * 
     * @param o
     * @return 
     */
    @Override
    public boolean equals(Object o) {
        return equals((User) o);        
    }

    /**
    * Returns login ID
    * 
    * @return String
    */
    public String getLoginID() {
        return loginID;
    }

    /**
     * Returns password 
     * 
     * @return String
     */
    private String getPassword() {
        return password;
    }

    /**
     * Public method used to populate the users ArrayList<User> from the UserData.csv file.
     */
    public static void fillUsers() {
        String csv = "JavaFXApp/src/model/DataBase/UserData.csv";
        BufferedReader reader = null;
        String line;

        try {
            reader = new BufferedReader(new FileReader(csv));
            while ((line = reader.readLine()) != null) {
                String[] split = line.split(",");
                User newUser = new User(split[0], unHash(split[1]));
                userList.add(newUser);
            }
        } catch (FileNotFoundException e) {
            System.out.println("src/model/DataBase/UserData.csv not found! Please try again.");
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
     * Returns validation by testing a User object against a list of existing
     * User objects
     * 
     * @return boolean
     */
    public boolean validateUser() {
        for (User usr : userList) {
            if (this.equals(usr)) {
                return true;
            }
        }
        return false;
    }

    /**
    * 
    * Returns whether the login ID exists in a collection of User objects
    * 
    * @return boolean
    */
    public static boolean ValidLoginID(String id) {
        for (User usr : userList) {
            if (usr.getLoginID().equals(id)) {
                return false;
            }
        }
        return true;
    }

    /**
     * 
     * Adds a User object to list of users
     * 
     * @param u 
     */
    public static void addToList(User u) {
        userList.add(u);
    }

    /**
     * 
     * returns Portfolio
     * 
     * @return Portfolio
     */
    public Portfolio getMyPortfolio() {
        return myPortfolio;
    }

    public void setMyPortfolio(Portfolio portfolio) {
        this.myPortfolio = portfolio;
    }

}
