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
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import model.Transaction;

/**
 *
 * @author ericepstein
 */
public class TransactionDisplayer {
    
    FPTS theFPTS;
    ArrayList<Transaction> transactions;
    VBox results;
    
    public TransactionDisplayer(FPTS theFPTS) {
        this.theFPTS = theFPTS;
        transactions = theFPTS.getPortfolio().getTransactions();
        theFPTS.getStage().setScene(getTransactionDisplayScene());
        
    }
    
    public Scene getTransactionDisplayScene() {
        
        VBox split = new VBox();
        
        
        VBox queries = new VBox();
        HBox aField = new HBox();
        DatePicker startDate = new DatePicker(); 
        Label aLabel = new Label("Start date: ");
        aField.getChildren().addAll(aLabel, startDate);
        
        queries.getChildren().add(aField);
        
        aField = new HBox();
        DatePicker endDate = new DatePicker();
        aLabel = new Label("End date: ");
        aField.getChildren().addAll(aLabel, endDate);
        
        queries.getChildren().add(aField);
        
        VBox results = new VBox();
        for (Transaction t : transactions) {
            results.getChildren().add(new Label(t.toString()));
        }
        
        Button submitBtn = new Button();
        submitBtn.setText("Search");
        submitBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                results.getChildren().clear();
                if (startDate.getValue() != null && endDate.getValue() != null) {
                    Date start = Date.from(startDate.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant());
                    Date end = Date.from(endDate.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant());
                    for (Transaction t : transactions) {
                        Date aDate = t.getCashAccount().getDateAdded();
                        if (aDate.after(start) && aDate.before(end)) {
                            results.getChildren().add(new Label(t.toString()));
                        }
                    }
                }
            }
        });
        
        
        split.getChildren().addAll(theFPTS.getNav(), queries, submitBtn, results);
        Scene transactionDisplayScene = new Scene(split, theFPTS.getWidth(), theFPTS.getHeight());
        return transactionDisplayScene;
    }
}
