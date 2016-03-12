/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import gui.FPTS;
import java.util.ArrayList;
import java.util.Date;
import java.util.Observable;
import java.util.Observer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.CashAccount;
import model.CashAccountSearcher;
import model.Portfolio;
import model.Searchable;
import model.Searcher;

/**
 *
 * @author ericepstein
 */
public class CashAccountFinder extends Observable implements Observer {
    
    VBox matchDisplay;
    FPTS theFPTS;
    int WIDTH;
    int HEIGHT;
    TextField mainInput;
    Searcher s;
    CashAccount c;
    
    public CashAccountFinder(FPTS theFPTS, CashAccount c) {
        mainInput = new TextField();
        matchDisplay = new VBox();
        s = new CashAccountSearcher();
        this.s.addObserver(this);
        
        this.c = c;
        
        WIDTH = theFPTS.getWidth();
        HEIGHT = theFPTS.getHeight();
        this.theFPTS = theFPTS;
        Portfolio p = theFPTS.getPortfolio();
        Stage theStage = theFPTS.getStage();
        matchDisplay.getChildren().clear();
        mainInput.setText("");
        
        Scene searchScene = getSearchScene(p.getCashAccountSearchables(), getCashAccountQueries());
        theStage.setScene(searchScene);
        
    }
    
    public Scene getSearchScene(ArrayList<Searchable> toBeSearched, VBox queries) {
       
        VBox splitPage = new VBox();
        
        VBox searchPane = new VBox();
        
        Button actionBtn = new Button();
        actionBtn.setVisible(false);
        actionBtn.setText("Proceed");
        actionBtn.setOnAction(new EventHandler<ActionEvent>() {
               @Override
               public void handle(ActionEvent e) {
                if (mainInput.getText() != null && s.getMatch(mainInput.getText()) != null) {
                    
                    c.overwrite((CashAccount) s.getMatch(mainInput.getText()));
                    setChanged();
                    notifyObservers();
                }
            }
        });

        Button searchBtn = new Button();
        searchBtn.setText("Search");
        searchBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                s.search(queries.getChildren(), toBeSearched);
                actionBtn.setVisible(true);
                //p.setMatches(queries.getChildren());
            }
        });
        
        HBox forAction = new HBox();
        forAction.getChildren().addAll(queries, actionBtn);
        searchPane.getChildren().addAll(forAction, searchBtn, matchDisplay);
        splitPage.getChildren().addAll(theFPTS.getNav(), searchPane);
        return new Scene(splitPage, WIDTH, HEIGHT);
    }
    
    /*
    *
    * Define methods related to creation for parts of views
    *
    */
    
    public HBox createInputField(String description, TextField input) {
        HBox aField = new HBox();
        Label descriptionLabel = new Label(description);
        ObservableList<String> attributes = 
            FXCollections.observableArrayList(
                "",
                "contains",
                "starts with",
                "exactly matches"
        );
        ComboBox searchConditions = new ComboBox(attributes);
        searchConditions.getSelectionModel().select(0);
        aField.getChildren().addAll(descriptionLabel, searchConditions, input);
        aField.setSpacing(10);
        return aField;
    }
    
    public VBox getCashAccountQueries() {
        VBox queries = new VBox();
        queries.getChildren().add(createInputField("Account name: ", mainInput));
        return queries;
    }
    
    /*
    *
    * Defines methods related to the Observer pattern
    *
    */
    
    @Override
    public void update(Observable o, Object arg) {
        displayMatches(s.getMatches());
    }
    
    public void displayMatches(ArrayList<Searchable> matches) {
        //given the list
        matchDisplay.getChildren().clear();
        for (Searchable s : matches) {
            String symbol = s.getDisplayName();
            Button item = new Button(symbol);
            item.setStyle("-fx-background-color: white; -fx-text-fill: black;");
            item.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent e) {
                    mainInput.setText(symbol);
                }
            });
            matchDisplay.getChildren().add(item);
        }   
        
    }
}
