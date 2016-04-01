/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import gui.FPTS;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import model.PortfolioElements.Transaction;

import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;

/**
 * Displays Transaction objects in one Scene.
 *
 * @author Eric Epstein
 */
public class TransactionDisplayer implements Displayer {

    /*
    * context data
    */
    FPTS theFPTS;
    ArrayList<Transaction> transactions;
    VBox results;//TODO: check


    /*
    * Establishes context data and overrides Displayer's display method
    * by calling a scene constructor.
    */
    @Override
    public void display(FPTS theFPTS) {
        this.theFPTS = theFPTS;
        transactions = theFPTS.getPortfolio().getTransactions();
        theFPTS.getStage().setScene(getTransactionDisplayScene());
    }

    /**
     * Helper method to construct Scene with controller functionality for
     * start and end dates.
     *
     * @return
     */
    private Scene getTransactionDisplayScene() {

        VBox split = new VBox();
        VBox queries = new VBox();
        HBox aField = new HBox();

        /**
         * Field to select start date
         */
        DatePicker startDate = new DatePicker();
        Label aLabel = new Label("Start date: ");
        aField.getChildren().addAll(aLabel, startDate);
        queries.getChildren().add(aField);

        /*
        * Field to select end date
        */
        aField = new HBox();
        DatePicker endDate = new DatePicker();
        aLabel = new Label("End date: ");
        aField.getChildren().addAll(aLabel, endDate);

        queries.getChildren().add(aField);

        /*
        * Initially displays all Transaction objects.
        */
        VBox results = new VBox();
        for (Transaction t : transactions) {
            results.getChildren().add(new Label(t.toString()));
        }

        Button submitBtn = new Button();
        submitBtn.setText("Search");
        /*
         * Filters list of Transaction in case user inputs two valid start and end
         * dates.
         */
        submitBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                results.getChildren().clear();
                if (startDate.getValue() != null && endDate.getValue() != null) {
                    Date start = Date.from(startDate.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant());//TODO: check
                    Date end = Date.from(endDate.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant());//TODO: check
                    /*
                    * Update display to represent filtered Transaction objects
                    */
                    for (Transaction t : transactions) {
                        //TODO: change date to type date.
                        String aDate = t.getCashAccount().getDateAdded();//TODO: check
                        /*
                        * Add to display if Transaction object is after the start
                        * date and before the end date.
                        */
//                        if (aDate.after(start) && aDate.before(end)) {
//                            results.getChildren().add(new Label(t.toString()));
//                        }
                    }
                }
            }
        });
        split.getChildren().addAll(theFPTS.getNav(), queries, submitBtn, results);
        Scene transactionDisplayScene = new Scene(split, theFPTS.getWidth(), theFPTS.getHeight());
        return transactionDisplayScene;
    }
}

