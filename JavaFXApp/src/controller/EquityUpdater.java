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
import model.PortfolioEquitySearcher;
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
import model.Equity;
import model.EquityUpdatable;
import model.Simulatable;
import model.CashAccount;

/**
 *
 * @author ericepstein
 */
abstract public class EquityUpdater implements Observer {
    
     protected Searcher s;
     
     private EquityUpdater self;
     
     private ComboBox transactionSource;
     
     private VBox matchDisplay;
     protected TextField mainInput;
     
     protected FPTS theFPTS;
     private double HEIGHT;
     private double WIDTH;
     protected Stage theStage;
     
     //private Scene equitySearchScene;
     //private Scene additionalInfoScene;
     //private Scene cashAccountSearchScene;
     
     protected float pricePerShare;
     protected int numOfShares;
     protected float valuePerShare;
     
     protected EquityUpdatable equityOfInterest;
     protected CashAccount cashAccountOfInterest;
     
     public void process(FPTS anFPTS) {
         theFPTS = anFPTS;
         establishContext();
         theStage = theFPTS.getStage();
         //nav = theFPTS.getNav();
         self = this;
         mainInput = new TextField ();
         matchDisplay = new VBox();
         HEIGHT = theFPTS.getHeight();
         WIDTH = theFPTS.getWidth();
         
         //theStage.setScene(getFirstSearchScene());
         //equityOfInterest = new Equity("mo", "momo", new ArrayList<String>(), new ArrayList<String>(), 2, (float) 3.1, new Date());
         theStage.setScene(getFirstSearchScene());
         theStage.show();
         System.out.println("done");
         //theStage.setScene(new Scene(new Group(), 5000, 5000));
         
     }
   
     abstract void establishContext();
     abstract ArrayList<Searchable> getToBeSearched();
     
     public Scene getFirstSearchScene() {
        ArrayList<Searchable> toBeSearched = getToBeSearched();
        // p.getPortfolioSearchables();
        s = new PortfolioEquitySearcher();
        s.addObserver(self);
        VBox queries = getEquityQueries();
        //getSearchScene(toBeSearched, queries, goToSearchCashAccount());
        return getSearchScene(toBeSearched, queries, getTransitionAfterEquity());
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
    
    //abstract Button getTransitionAfterCashAccount();
       
    public Button getTransitionAfterEquity() {
        Button actionBtn = new Button();
        actionBtn.setText("Proceed");
        
        actionBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                if (mainInput.getText() != null && s.getMatch(mainInput.getText()) != null) {
                    //prepareSecondView();
                    //theStage.setScene();
                    equityOfInterest = (EquityUpdatable) s.getMatch(mainInput.getText());
                            // new Equity("mo", "momo", new ArrayList<String>(), new ArrayList<String>(), 2, (float) 3.1, new Date());
                    theStage.setScene(getAdditionalInfoScene());
               } else {
                   mainInput.setText("INVALID");  
               }
            }
        });
        
        return actionBtn;
    }
     
    //abstract EquityUpdatable getEquityOfInterest(String keyword);
    
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
            
                /*
                
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
        
       
        Scene additionalInfoScene = new Scene(splitPage, 500, 500);
        //return new Scene(splitPage, WIDTH, HEIGHT);
        return additionalInfoScene;
        
    }
    
    public Button getTransitionAfterCashAccount() {
        Button actionBtn = new Button();
        actionBtn.setText("Proceed");
        
        actionBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                if (mainInput.getText() != null && s.getMatch(mainInput.getText()) != null) {
                    //prepareSecondView();
                    //theStage.setScene();
                    cashAccountOfInterest = new CashAccount("lala", 3, new Date());
                    
                    processInsideFPTS();
                    
                    //theStage.setScene(getAdditionalInfoScene());
               } else {
                   mainInput.setText("INVALID");  
               }
            }
        });
        
        return actionBtn;
        
    }
    
    abstract void processInsideFPTS();
    abstract void processOutsideFPTS();
    
    public Scene getConfirmationScene() {
        Label confirmation = new Label("Update completed");
        VBox split = new VBox();
        split.getChildren().addAll(theFPTS.getNav(), confirmation);
        return new Scene(split, 300, 300);
    }
    
    public Scene getErrorScene() {
        Label confirmation = new Label("Error");
        VBox split = new VBox();
        split.getChildren().addAll(theFPTS.getNav(), confirmation);
        return new Scene(split, 300, 300);
    }
    
    public boolean isFloat(String s) {
        /*
        
        TYPE YOUR CODE!
        */
        return true;
    }
                
    /*
    public void handleFirstTransition() {
        if (mainInput.getText() != null && s.getMatch(mainInput.getText()) != null) {

            EquityOfInterest = (Simulatable) s.getMatch(mainInput.getText());
            //EquityOfInterest = (Simulatable) s.getMatch(mainInput.getText());
            ArrayList<Searchable> toBeSearched = p.getCashAccountSearchables();
            s = new CashAccountSearcher();
            s.addObserver(self);
            VBox queries = getCashAccountQueries();
            displayMatches(new ArrayList<Searchable>());
            mainInput.setText("");
            //Scene nextSearchScene = new Scene(new Group(), WIDTH, HEIGHT);
            //designSearchScene(nextSearchScene, toBeSearched, queries, buyEquity() );
            theStage.setScene(cashAccountSearch);
        } else {
            mainInput.setText("INVALID");
        }
    }
    */
    
     public Scene getSearchScene(ArrayList<Searchable> toBeSearched, VBox queries, Button actionBtn) {
        VBox splitPage = new VBox();
        
        VBox searchPane = new VBox();

        actionBtn.setVisible(false);

        Button searchBtn = new Button();
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
        
        return new Scene(splitPage, 300, 300);
     }
     
     
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
    
    public VBox getEquityQueries() {
        VBox queries = new VBox();
        queries.getChildren().add(createInputField("Ticker symbol: ", mainInput));
        queries.getChildren().add(createInputField("Equity name: ", new TextField()));
        queries.getChildren().add(createInputField("Index: ", new TextField()));
        queries.getChildren().add(createInputField("Sector: ", new TextField()));
        return queries;
    }
    
   public VBox getCashAccountQueries() {
        VBox queries = new VBox();
        queries.getChildren().add(createInputField("Account name: ", mainInput));
        return queries;
    }
    
    @Override
    public void update(Observable o, Object arg) {
        //displayMatches(ArrayList<)
        //displayMatches(this.p.getMatches());
        System.out.println("SIZE IS : " + s.getMatches().size());
        displayMatches(s.getMatches());
        System.out.println("UPDATED");
        //isplayMatches(this.s.getMatches());
    }
}
