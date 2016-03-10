/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

/**
 *
 * @author ericepstein
 */
public class User {
    private String loginID;
    private String password;

    private static ArrayList<User> userList = new ArrayList<User>();

    public User(String loginID, String password) {
        this.loginID = loginID;
        this.password = hash(password);
        userList.add(this);

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

    /*
     * Private method used to populate the users ArrayList<User> from the UserData.txt file.
     */
    public static void fillUsers(){
        try {
            Scanner scanner = new Scanner(new File("UserData.txt"));

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
                return true;
            }
        }
        return false;
    }

}
