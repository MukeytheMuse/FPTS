/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

/**
 *
 * @author ericepstein
 */
public class User {
        private String loginID;
        private String password;
        
        public User(String loginID, String password) {
            this.loginID = loginID;
            this.password = hash(password);
        }
        
        public String hash(String password) {
            String encryptedPW = "";
            for (int i = 0; i < password.length(); i++) {
                char encryptedChar = (char) (password.charAt(i) + 1);
                encryptedPW += encryptedChar;
            }
            return encryptedPW;
        }
        
        public boolean equals(User u) {
            return u.getPassword().equals(password) && u.getLoginID().equals(loginID);
        }
        
        public String getLoginID() {
            return loginID;
        }
        
        private String getPassword() {
            return password;
        }
    }
