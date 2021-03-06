/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.HoldingCtrl;

import controller.MenuController;
import java.net.URL;
import java.util.Iterator;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import model.Equities.EquityComponent;
import model.PortfolioElements.*;

import model.Equities.EquityComponents;
import gui.FPTS;
import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.stage.Stage;
import model.UndoRedo.*;

/**
 * FXML Controller class
 *
 * @author ericepstein
 */
public class BuyHoldingController extends MenuController {

    double pricePerShare;
    double valuePerShare;
    int numOfShares;
    EquityComponent equityOfInterest;
    CashAccount cashAccountOfInterest;
    
    @FXML
    private VBox searchBoxes;
    @FXML
    private VBox results;
    @FXML
    private Button searchBtn;
    
    
    @FXML
    private Label selectDescription;
    @FXML
    private Button selectBtn;
    
    private TextField mainInput;

    private FPTS fpts;
    
    private CommandComposite aCommand;
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        
        aCommand = new CommandComposite();
        
        fpts = FPTS.getSelf();
       
        System.out.println("IN INITIALIZE");
        
        mainInput = new TextField();
        
        selectBtn.setVisible(false);
        
        /*searchBoxes.getChildren(); */
        searchBoxes.getChildren().addAll(getHoldingQueries().getChildren()); //= getHoldingQueries();
        System.out.println("UPDATED SEARCH BOXES");
        
        searchBtn.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent e) {
                    results.getChildren().clear();
                    System.out.println("CLICKING NOW");
                    ObservableList<Node> queries = searchBoxes.getChildren();// getHoldingQueries().getChildren();
                    PortfolioVisitor searchPortfolioV = new SearchPortfolioVisitor(queries);
                    EquityComponents eqComponents = new EquityComponents(); // FPTS.getCurrentUser().getMyPortfolio().getEqComponents();
                    Iterator eqIterator = eqComponents.iterator(searchPortfolioV);
                   //eqIterator.accept(searchPortfolioV);
                    while (eqIterator.hasNext()) {
                        
                        EquityComponent ec = (EquityComponent) eqIterator.next();
                        //Object ec = eqIterator.next();
                        
                        Button resultBtn = new Button(ec.getDisplayName());
                     
                        resultBtn.setOnAction(new EventHandler<ActionEvent>() {
                             @Override
                            public void handle(ActionEvent e) {
                                   mainInput.setText(ec.getDisplayName());
                                   equityOfInterest = ec;
                                   selectDescription.setText("Selected equity is " + ec.getDisplayName());
                                   selectBtn.setVisible(true);
                            }
                        });
                        results.getChildren().add(resultBtn);
                    }
                        
                        //results.getChildren().add(new Button(eqIterator.next().toString()));
                }
        });
        
        selectBtn.setOnAction(new EventHandler<ActionEvent>() {
                             @Override
                            public void handle(ActionEvent e) {
                                results.getChildren().clear();
                                searchBtn.setVisible(false);
                                selectBtn.setVisible(false);
                                selectDescription.setVisible(false);
                                getAdditionalInfo();
                            }
                        });
                        
                        
                        //results.getChildren().add(new Button(eqIterator.next().toString()));
               
         
    }
    
    public VBox getHoldingQueries() {
        VBox queries = new VBox();
        queries.getChildren().add(createInputField("Ticker symbol: ", mainInput)); //replace lala with mainInput
        queries.getChildren().add(createInputField("Holding name: ", new TextField()));
        queries.getChildren().add(createInputField("Index: ", new TextField()));
        queries.getChildren().add(createInputField("Sector: ", new TextField()));
        return queries;
    }
    
    /**
     * Helper method to create uniquely formatted fields for user input.
     * These fields have a description, search options, and a TextField.
     *
     * @param description
     * @param input
     * @return HBox
     */
    private HBox createInputField(String description, TextField input) {
        HBox aField = new HBox();
        Label descriptionLabel = new Label(description);
        /**
         * Search options are "" (ignore), "contains," "starts with," and "exactly
         * matches"
         */
        ObservableList<String> attributes =
                FXCollections.observableArrayList(
                        "",
                        "contains",
                        "starts with",
                        "exactly matches"
                );
        ComboBox searchConditions = new ComboBox(attributes);
        //TODO: check Warning:(454, 37) Unchecked call to 'ComboBox(ObservableList<T>)' as a member of raw type 'javafx.scene.control.ComboBox'

        searchConditions.getSelectionModel().select(0);
        aField.getChildren().addAll(descriptionLabel, searchConditions, input);
        System.out.println("Size of HBXO is " + aField.getChildren().size());
        aField.setSpacing(10);
        return aField;
    }
    
     /**
     * Constructs Scene to get the following user input:
     * -price per share
     * -number of shares
     * -whether purchase/sale was made outside or inside the FPTS
     *
     * @return Scene
     */
    public void getAdditionalInfo() {

        //VBox splitPage = new VBox();
        VBox searchPane = new VBox();
        VBox queries = new VBox();
        HBox aField = new HBox();

        /*
        * Defines first field : price per share
        */
        TextField pricePerShareField = new TextField();
        pricePerShareField.setText("" + equityOfInterest.getPricePerShare());
        aField.getChildren().addAll(new Label("Price per share: "), pricePerShareField);
        queries.getChildren().add(aField);

        /*
        * Defines second field : number of shares
        */
        aField = new HBox();
        TextField numOfSharesField = new TextField();
        aField.getChildren().addAll(new Label("Number of shares: "), numOfSharesField);
        queries.getChildren().add(aField);

        /*
        * Defines third field : whether or not the sale is made outside or inside FPTS.
        */
        aField = new HBox();
        ObservableList<String> attributes =
                FXCollections.observableArrayList(
                        "Outside FPTS",
                        "Use existing cash account"
                );
        ComboBox searchConditions = new ComboBox(attributes);
        //TODO: check Warning:(235, 37) Unchecked call to 'ComboBox(ObservableList<T>)' as a member of raw type 'javafx.scene.control.ComboBox'
        searchConditions.getSelectionModel().select(0);
        aField.getChildren().addAll(new Label("Target source: "), searchConditions);

        queries.getChildren().add(aField);

        Button submitButton = new Button();
        submitButton.setText("Proceed");
        submitButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {

                /*
                * Validates user input with helper methods
                */
                boolean isValid = isValid(pricePerShareField) && isValid(numOfSharesField);

                /*
                * If input is valid, analyze response and determine which step in the
                * algorithm to go.
                */
                                 
                if (isValid) {
                    pricePerShare = Double.parseDouble(pricePerShareField.getText());
                    numOfShares = Integer.parseInt(numOfSharesField.getText());

                    UndoRedoManager undoRedoManager = fpts.getUndoRedoManager();
                    Holdings holdings = fpts.getCurrentUser().getMyPortfolio().getHoldingsCollection();
                    HoldingAddition holdingAddition = new HoldingAddition(holdings, equityOfInterest, numOfShares);
                    aCommand.addChild(holdingAddition);
                    
                    switch (searchConditions.getValue().toString()) {
                        case ("Outside FPTS"):
                            /*
                            * Delegates purchase/sale outside FPTS in respective subclass
                            */
                            
                            //processOutsideFPTS(null, 0, 0);
                            //HoldingPurchase()
                            
                            //MAKE COMMAND TO BUY/SELL HOLDING

                            undoRedoManager.execute(aCommand);
                           
                            
                            try { 
                                redirect();
                            } catch (Exception ex) {
                                ex.printStackTrace();
                            }
                            
                            //theStage.setScene(theFPTS.getConfirmationScene());
                            
                            break;
                        case ("Use existing cash account"):
                            /*
                            * Constructs another scene to get CashAccount if purchase/sale
                            * is made inside FPTS.
                            */
                            
                            submitButton.setVisible(false);
                            setSearchCashAccountView();
                            
                            //theStage.setScene(getSecondSearchScene());
                            
                            break;
                    }
                } else {
                    pricePerShareField.setText("INVALID");
                }
            }
        });

        searchPane.getChildren().addAll(queries, submitButton);
        searchBoxes.getChildren().clear();
        searchBoxes.getChildren().addAll(queries, submitButton);

    }

    /**
     * Helper method to validate numerical value from user input
     *
     * @param inputAmount
     * @return
     */
    private boolean isValid(TextField inputAmount) {
        if (inputAmount.getText() == null || inputAmount.getText().equals("")) {
            return false;
        }

        String inputAmountString = inputAmount.getText();

        try {
            Double.parseDouble(inputAmountString);
            //TODO: check Warning:(303, 20) Result of 'Double.parseDouble()' is ignored
        } catch (Exception e) {
            return false;
        }

        Double inputAmountDouble = Double.parseDouble(inputAmountString);

        return inputAmountDouble >= 0;

    }
    
    private void setSearchCashAccountView() {
        selectBtn.setVisible(false);
        mainInput.setText("");
        

        /*searchBoxes.getChildren(); */
        searchBoxes.getChildren().clear();
        searchBoxes.getChildren().addAll(getCashAccountQueries().getChildren()); //= getHoldingQueries();
        System.out.println("UPDATED SEARCH BOXES");

        
        searchBtn.setVisible(true);
        searchBtn.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent e) {
                    results.getChildren().clear();
                    System.out.println("CLICKING NOW");
                    ObservableList<Node> queries = searchBoxes.getChildren(); // getHoldingQueries().getChildren();
                    PortfolioVisitor searchPortfolioV = new SearchPortfolioVisitor(queries);
                    CashAccounts cashAccounts = fpts.getCurrentUser().getMyPortfolio().getCashAccountsCollection();
                    Iterator cashAccountIterator = cashAccounts.iterator(searchPortfolioV);
                   //eqIterator.accept(searchPortfolioV);
                    while (cashAccountIterator.hasNext()) {
                        
                        CashAccount c = (CashAccount) cashAccountIterator.next();
                        //Object ec = eqIterator.next();
                        
                        Button resultBtn = new Button(c.getDisplayName());
                     
                        resultBtn.setOnAction(new EventHandler<ActionEvent>() {
                             @Override
                            public void handle(ActionEvent e) {
                                 if (c.getValue() >= numOfShares * pricePerShare) {
                                     mainInput.setText(c.getDisplayName());
                                     cashAccountOfInterest = c;
                                     selectDescription.setText("Selected cash account is " + c.getDisplayName());
                                     selectBtn.setVisible(true);
                                 } else {
                                     mainInput.setText("INSUFFICIENT BALANCE");
                                 }
                             }
                        });
                        results.getChildren().add(resultBtn);

                    }
                        
                        //results.getChildren().add(new Button(eqIterator.next().toString()));
                }
        });
        
        selectBtn.setOnAction(new EventHandler<ActionEvent>() {
                             @Override
                            public void handle(ActionEvent e) {
                                
                                if (cashAccountOfInterest.getValue() >= (numOfShares * pricePerShare)) {
            
       
                                results.getChildren().clear();
                                searchBtn.setVisible(false);
                                selectBtn.setVisible(false);
                                selectDescription.setVisible(false);


                                UndoRedoManager undoRedoManager = fpts.getUndoRedoManager();
                                Command aComm = new Withdrawal(cashAccountOfInterest, pricePerShare * numOfShares);
                                    aCommand.addChild(aComm);
                                    Transaction t = (Transaction) aComm;
                                    aComm = new HistoryAddition(t, fpts.getCurrentUser().getMyPortfolio().getHistory());
                                    aCommand.addChild(aComm);

                                undoRedoManager.execute(aCommand);
                                //Buy Equity command
                                //create withdrawal command
                                //create command to buy shares, so have Holdings, 
                                
                                //command to buy share will
                                // Holdings, EquityOfInterest, numShares
                                
                                
                                //TO insert holding
                                //EquityComponent, get all info, transfer to Holding
                                //add number of shares
                                
                                //
                                    try { redirect(); } catch (Exception ex) { ex.printStackTrace(); }
                                }
                                
                                
                                //getAdditionalInfo();

                            }
                        });
                                            
                        //results.getChildren().add(new Button(eqIterator.next().toString()));
               
    }
    

 
        /**
     * This helper method returns queries in relation to searching/selecting a
     * CashAccount.
     *
     * @return VBox
     */
    private VBox getCashAccountQueries() {
        VBox queries = new VBox();
        queries.getChildren().add(createInputField("Account name: ", mainInput));
        System.out.println("SIZE OF A QUERY IS " + queries.getChildren().size());
        return queries;
    }
    
    public void redirect() throws IOException {
        Parent parent = FXMLLoader.load(this.getClass().getResource("/HomePage.fxml"));
        Scene scene = new Scene(parent);
        Stage stage = (Stage) this.myMenuBar.getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }
    
}

    
