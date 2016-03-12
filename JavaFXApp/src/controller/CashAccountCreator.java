/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import gui.FPTS;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import model.CashAccount;

/**
 *
 * @author ericepstein
 */
public class CashAccountCreator {
   
    private FPTS theFPTS;
    
    public CashAccountCreator(FPTS theFPTS) {
        
        this.theFPTS = theFPTS;
        
        theFPTS.getStage().setScene(getCashAccountCreatorScene());
    }
        
    public Scene getCashAccountCreatorScene() {
        VBox split = new VBox();
        
        HBox aField = new HBox();
        TextField nameInputField = new TextField();
        Label mainInput = new Label("Account name: ");
        aField.getChildren().addAll(mainInput, nameInputField);
        
        TextField amountInputField = new TextField();
        Label aLabel = new Label("Amount: ");
        aField.getChildren().addAll(aLabel, amountInputField);
        
        DatePicker dateField = new DatePicker();
        aLabel = new Label("Date: ");
        aField.getChildren().addAll(aLabel, dateField);
        
        Button submitBtn = new Button();
        submitBtn.setText("Submit");
        submitBtn.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent e) {
                    
                    
                    boolean isValid = isValidAccountName(nameInputField) && isValidDouble(amountInputField); 
                    
                    Date theDate = Date.from(dateField.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant());
                    
                    CashAccount c = new CashAccount(nameInputField.getText(), Double.parseDouble(amountInputField.getText()), theDate); 
                    
                    if ( isValid ) {
                        boolean isStillValid = theFPTS.getPortfolio().getCashAccounts().contains(c);
                        if ( isStillValid ) {
                            theFPTS.getPortfolio().add(c);
                        }
                        else mainInput.setText("EXISTING ACCOUNT NAME");
                    } else {
                        mainInput.setText("INVALID");
                    }  
                    
               
                }
            });
        
        VBox inputArea = new VBox();
        inputArea.getChildren().addAll(aField, submitBtn);
        split.getChildren().addAll(theFPTS.getNav(), inputArea);
        return new Scene(split, theFPTS.getWidth(), theFPTS.getHeight());
        
    }
    
    public boolean isValidAccountName(TextField inputAmount) {
        if (inputAmount.getText() == null || inputAmount.getText().equals("")) {
            return false;
        }
        
        String inputAmountString = inputAmount.getText();
        
        try {
            Double.parseDouble(inputAmountString);
        } catch (Exception e) {
            return false;
        }
        
        Double inputAmountDouble = Double.parseDouble(inputAmountString);
        
        if (inputAmountDouble < 0) {
            return false;
        }
        
        return true;    
    }
    
    public boolean isValidDouble(TextField inputAmount) {
        if (inputAmount.getText() == null || inputAmount.getText().equals("")) {
            return false;
        }
        
        String inputAmountString = inputAmount.getText();
        
        try {
            Double.parseDouble(inputAmountString);
        } catch (Exception e) {
            return false;
        }
        
        Double inputAmountDouble = Double.parseDouble(inputAmountString);
        
        if (inputAmountDouble < 0) {
            return false;
        }
        
        return true;
         
    }
    
}
