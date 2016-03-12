/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import model.SearchedHoldingSearcher;
import model.Searchable;
import model.Searcher;

import gui.FPTS;
import java.util.Date;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import model.CashAccountSearcher;
import model.Holding;
import model.HoldingUpdatable;
import model.CashAccount;

/**
 *
 * @author ericepstein
 */
abstract public class HoldingAlgorithm implements Observer { 
     
     private HoldingAlgorithm self;
     
     private VBox matchDisplay;
     protected TextField mainInput;
     
     protected FPTS theFPTS;
     private double HEIGHT;
     private double WIDTH;
     protected Stage theStage;
     
     protected Searcher s;
     
     protected float pricePerShare;
     protected int numOfShares;
     protected float valuePerShare;
     
     protected HoldingUpdatable equityOfInterest;
     protected CashAccount cashAccountOfInterest;
     
     abstract void establishContext();
     abstract ArrayList<Searchable> getToBeSearched();
     
     public void process(FPTS anFPTS) {
         theFPTS = anFPTS;
         establishContext();
         theStage = theFPTS.getStage();
         self = this;
         mainInput = new TextField ();
         matchDisplay = new VBox();
         HEIGHT = theFPTS.getHeight();
         WIDTH = theFPTS.getWidth();
         
         theStage.setScene(getFirstSearchScene());
         theStage.show();
         
     }
    
    /*
     *
     * Defines abstract methods to be implemented in subclasses
     * 
     */
     
    abstract void processInsideFPTS();
    abstract void processOutsideFPTS();
     
    /*
    *
    * Defines views used
    *
    */
    
    public Scene getFirstSearchScene() {
        ArrayList<Searchable> toBeSearched = getToBeSearched();
        s = new SearchedHoldingSearcher();
        s.addObserver(self);
        VBox queries = getHoldingQueries();
        return getSearchScene(toBeSearched, queries, getTransitionAfterHolding());
     }
     
    public Scene getSecondSearchScene() {
        s = new CashAccountSearcher();
        s.addObserver(self);
        matchDisplay.getChildren().clear();
        mainInput.setText("");
        ArrayList<Searchable> toBeSearched = theFPTS.getPortfolio().getCashAccountSearchables();
        VBox queries = getCashAccountQueries();
        return getSearchScene(toBeSearched, queries, getTransitionAfterCashAccount());
        
    }
    
    public Scene getSearchScene(ArrayList<Searchable> toBeSearched, VBox queries, Button actionBtn) {
        VBox splitPage = new VBox();
        
        VBox searchPane = new VBox();

        actionBtn.setVisible(false);

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

     
    //abstract HoldingUpdatable getHoldingOfInterest(String keyword);
    
    public Scene getAdditionalInfoScene() {
        
        VBox splitPage = new VBox();
        VBox searchPane = new VBox();
        VBox queries = new VBox();
        HBox aField = new HBox();
        TextField pricePerShareField = new TextField();
        pricePerShareField.setText(""+equityOfInterest.getValuePerShare());
        aField.getChildren().addAll(new Label("Price per share: "), pricePerShareField);
        queries.getChildren().add(aField);
       
        aField = new HBox();
        TextField numOfSharesField = new TextField();
        aField.getChildren().addAll(new Label("Number of shares: "), numOfSharesField);
        queries.getChildren().add(aField);
       
        aField = new HBox();
        ObservableList<String> attributes = 
            FXCollections.observableArrayList(
                "Outside FPTS",
                "Use existing cash account"
        );
        ComboBox searchConditions = new ComboBox(attributes);
        searchConditions.getSelectionModel().select(0);
        aField.getChildren().addAll(new Label("Target source: "), searchConditions);
        
        queries.getChildren().add(aField);
        
        Button submitButton = new Button(); 
        submitButton.setText("Proceed");
        submitButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                String pricePerShareString = pricePerShareField.getText();
                String numOfSharesString = numOfSharesField.getText();
            
                try {
                    Double.parseDouble(pricePerShareString);
                } catch (Exception ex) {
                       
                } 
                
                //pricePerShare = Double.parseDouble
                numOfShares = Integer.parseInt(numOfSharesString);
;                /*
                
                CHECK FOR VALIDITY IN BOTH, THEN ADD TO GLOBAL VARIABLE
                
                if ( isFloat(pricePerShareString) && isInt(numOfSharesString) ) {
                    
                }
                
                    */
                
                pricePerShare = 3;
                numOfShares= 5;
                
                switch (searchConditions.getValue().toString()) {
                    case ("Outside FPTS"):
                        //place buy/sell command to be factored in, EXECUTE COMMAND
                        processOutsideFPTS();
                        theStage.setScene(getConfirmationScene());
                        break;
                    case("Use existing cash account"):
                        theStage.setScene(getSecondSearchScene());
                        break;
                }
                
            
            }
        });
        
        
        searchPane.getChildren().addAll(queries, submitButton);
        splitPage.getChildren().addAll(theFPTS.getNav(), searchPane);
        
       
        Scene additionalInfoScene = new Scene(splitPage, WIDTH, HEIGHT);
        return additionalInfoScene;
        
    }
    
    public Scene getConfirmationScene() {
        Label confirmation = new Label("Update completed");
        VBox split = new VBox();
        split.getChildren().addAll(theFPTS.getNav(), confirmation);
        return new Scene(split, WIDTH, HEIGHT);
    }
    
    public Scene getErrorScene() {
        Label confirmation = new Label("Error");
        VBox split = new VBox();
        split.getChildren().addAll(theFPTS.getNav(), confirmation);
        return new Scene(split, WIDTH, HEIGHT);
    }
    
    /*
    *
    * Defines Button controls
    *
    */
    
    public Button getTransitionAfterCashAccount() {
        Button actionBtn = new Button();
        actionBtn.setText("Proceed");
        
        actionBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                if (mainInput.getText() != null && s.getMatch(mainInput.getText()) != null) {
                    cashAccountOfInterest = new CashAccount("lala", 3, new Date());
                    processInsideFPTS();
               } else {
                   mainInput.setText("INVALID");  
               }
            }
        });
        
        return actionBtn;
        
    }
    
    //Defines button action to update equityOfInterest and 
    public Button getTransitionAfterHolding() {
        Button actionBtn = new Button();
        actionBtn.setText("Proceed");
        
        actionBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                if (mainInput.getText() != null && s.getMatch(mainInput.getText()) != null) {
                    equityOfInterest = (HoldingUpdatable) s.getMatch(mainInput.getText());
                    theStage.setScene(getAdditionalInfoScene());
               } else {
                   mainInput.setText("INVALID");  
               }
            }
        });
        
        return actionBtn;
    }
    
    /*
    *
    * Defines methods related to the Observer pattern
    *
    */
    
    @Override
    public void update(Observable o, Object arg) {
        //displayMatches(ArrayList<)
        //displayMatches(this.p.getMatches());
        System.out.println("SIZE IS : " + s.getMatches().size());
        displayMatches(s.getMatches());
        System.out.println("UPDATED");
        //isplayMatches(this.s.getMatches());
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
     
    public VBox getHoldingQueries() {
        VBox queries = new VBox();
        queries.getChildren().add(createInputField("Ticker symbol: ", mainInput));
        queries.getChildren().add(createInputField("Holding name: ", new TextField()));
        queries.getChildren().add(createInputField("Index: ", new TextField()));
        queries.getChildren().add(createInputField("Sector: ", new TextField()));
        return queries;
    }
    
   public VBox getCashAccountQueries() {
        VBox queries = new VBox();
        queries.getChildren().add(createInputField("Account name: ", mainInput));
        return queries;
    }
   
}
