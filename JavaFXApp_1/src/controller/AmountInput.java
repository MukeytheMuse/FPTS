/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import gui.FPTS;
import java.util.ArrayList;
import java.util.Observable;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

/**
 *
 * @author ericepstein
 */
public class AmountInput extends Observable {
    
    ArrayList<Double> amounts;
    private FPTS theFPTS;
    
    public AmountInput(FPTS theFPTS, ArrayList<Double> amounts) {
        
        this.theFPTS = theFPTS;
        this.amounts = amounts;
        
        theFPTS.getStage().setScene(getAmountInputScene());
    }
        
    public Scene getAmountInputScene() {
        VBox split = new VBox();
        
        HBox aField = new HBox();
        TextField inputAmount = new TextField();
        Label aLabel = new Label("Input amount: ");
        aField.getChildren().addAll(aLabel, inputAmount);
        
        ArrayList<TextField> inputAmounts = new ArrayList<TextField>();
        inputAmounts.add(inputAmount);
        
        Button submitBtn = new Button();
        submitBtn.setText("Submit");
        submitBtn.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent e) {
                    
                    boolean isValid = true;
                    for (TextField inputAmount : inputAmounts ) {
                        isValid = isValid && isValid(inputAmount);
                    }
                    
                    if ( isValid ) {
                        
                        for (TextField inputAmount : inputAmounts) {
                            double anAmount = Double.parseDouble(inputAmount.getText());
                            amounts.add(anAmount);
                        }
                    
                        setChanged();
                        notifyObservers();
                    } else {
                        inputAmount.setText("INVALID");
                    }
               
                }
            });
        
        VBox inputArea = new VBox();
        inputArea.getChildren().addAll(aField, submitBtn);
        split.getChildren().addAll(theFPTS.getNav(), inputArea);
        return new Scene(split, theFPTS.getWidth(), theFPTS.getHeight());
        
    }
    
    public boolean isValid(TextField inputAmount) {
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
