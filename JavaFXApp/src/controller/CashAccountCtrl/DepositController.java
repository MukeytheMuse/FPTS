/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.CashAccountCtrl;

import controller.MenuController;
import gui.FPTS;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.Equities.EquityComponent;
import model.Equities.EquityComponents;
import model.PortfolioElements.*;
import model.UndoRedo.*;
import model.UndoRedo.Deposit;

import java.io.IOException;
import java.net.URL;
import java.util.Iterator;
import java.util.ResourceBundle;

/**
 * FXML Controller class
 *
 * @author ericepstein
 */
public class DepositController extends MenuController {

    double pricePerShare;
    double valuePerShare;
    int numOfShares;
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
    @FXML
    private Label amountDescription;

    @FXML
    private TextField amountField;

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
        amountField.setVisible(false);
        amountDescription.setVisible(false);


        setSearchCashAccount();

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
     * Helper method to validate numerical value from user input
     *
     * @param inputAmount
     * @return
     */
    private boolean isValid(TextField inputAmount) {
        if (inputAmount == null || inputAmount.getText() == null || inputAmount.getText().equals("")) {
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
    
    private void setSearchCashAccount() {
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
                                   mainInput.setText(c.getDisplayName());
                                   cashAccountOfInterest = c;
                                   selectDescription.setText("Selected cash account is : " + c.getDisplayName());
                                   selectBtn.setVisible(true);
                                    amountField.setVisible(true);
                                    amountDescription.setVisible(true);

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

                                 if (isValid(amountField)) {

                                     double amount = Double.parseDouble(amountField.getText());


                                         results.getChildren().clear();
                                         searchBtn.setVisible(false);
                                         selectBtn.setVisible(false);
                                         selectDescription.setVisible(false);

                                         History history = fpts.getCurrentUser().getMyPortfolio().getHistory();
                                         UndoRedoManager undoRedoManager = fpts.getUndoRedoManager();
                                         Command aComm = new Deposit(cashAccountOfInterest, amount);
                                         aCommand.addChild(aComm);
                                         Transaction t = (Transaction) aComm;
                                         Command anotherComm = new HistoryAddition(t, history);
                                         aCommand.addChild(anotherComm);

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
                                         try {
                                             redirect();
                                         } catch (Exception ex) {
                                             ex.printStackTrace();
                                         }
                                     }
                                     else {
                                         amountField.setText("INVALID");
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

    
