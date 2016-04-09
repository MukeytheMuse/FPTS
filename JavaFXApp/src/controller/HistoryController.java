/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import gui.FPTS;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import model.PortfolioElements.History;
import model.PortfolioElements.PortfolioVisitor;
import model.PortfolioElements.SearchPortfolioVisitor;
import model.PortfolioElements.Transaction;

import java.net.URL;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.ResourceBundle;

/**
 * Displays Transaction objects in one Scene.
 *
 * @author Eric Epstein
 */
public class HistoryController extends MenuController {

    @FXML
    private VBox mainVbox;


    /*
    * context data
    */
    FPTS theFPTS;
    //ArrayList<Transaction> transactions;
    History history;
    VBox results;//TODO: check


    VBox queries;

    /*
    * Establishes context data and overrides Displayer's display method
    * by calling a scene constructor.
    */



    /**
     * Helper method to construct Scene with controller functionality for
     * start and end dates.
     *
     * @return
     */
    private void getTransactionDisplayScene() {

        //VBox split = new VBox();
        VBox prompts = new VBox();
        HBox aField = new HBox();

        /**
         * Field to select start date
         */
        DatePicker startDate = new DatePicker();
        Label aLabel = new Label("Start date: ");
        aField.getChildren().addAll(aLabel, startDate);
        prompts.getChildren().add(aField);

        /*
        * Field to select end date
        */
        aField = new HBox();
        DatePicker endDate = new DatePicker();
        aLabel = new Label("End date: ");
        prompts.getChildren().addAll(aLabel, endDate);

        prompts.getChildren().add(aField);

        queries.getChildren().addAll(startDate, endDate);

        /*
        * Initially displays all Transaction objects.
        */
        VBox results = new VBox();

        ArrayList<Transaction> transactions = (ArrayList<Transaction>) history.getList();

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

                PortfolioVisitor aPv = new SearchPortfolioVisitor(queries.getChildren());

                results.getChildren().clear();
                Iterator resultsIterator = history.iterator(aPv);

                while (resultsIterator.hasNext()) {
                    results.getChildren().add(new Label(resultsIterator.next().toString()));
                }


            }
        });
        mainVbox.getChildren().addAll(queries, submitBtn, results);
        //Scene transactionDisplayScene = new Scene(split, theFPTS.getWidth(), theFPTS.getHeight());
        //return transactionDisplayScene;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.theFPTS = theFPTS;
        queries = new VBox();
        //transactions = theFPTS.getCurrentUser().getMyPortfolio().getTransactions();
        history = theFPTS.getCurrentUser().getMyPortfolio().getHistory();
        getTransactionDisplayScene();
    }
}

