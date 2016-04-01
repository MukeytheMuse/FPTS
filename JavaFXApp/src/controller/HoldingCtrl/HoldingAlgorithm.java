package controller.HoldingCtrl;

import gui.FPTS;
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
import model.PortfolioElements.CashAccount;
import model.PortfolioElements.HoldingUpdatable;
import model.Searchers.CashAccountSearcher;
import model.Searchers.Searchable;
import model.Searchers.SearchedHoldingSearcher;
import model.Searchers.Searcher;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

/**
 * Defines steps that are common to buying Equity and selling Holding objects.
 *
 * @author Eric Epstein
 */
abstract public class HoldingAlgorithm implements Observer {

    /*
    * Reference to self
    */
    private HoldingAlgorithm self;

    /*
    * Display of matches
    */
    private VBox matchDisplay;

    /*
    * Input field from the user
    */
    protected TextField mainInput;

    /*
    * Context data and derived information
    */
    protected FPTS theFPTS;//TODO:error checking
    protected Stage theStage;
    private double HEIGHT;
    private double WIDTH;

    /*
    * Search algorithm
    */
    protected Searcher s;

    /*
    * user inputs to be processed in child algorithms
    */
    protected double pricePerShare;
    protected int numOfShares;
    protected double valuePerShare;

    /*
    * Computations from user inputs
    */
    protected HoldingUpdatable equityOfInterest;
    protected CashAccount cashAccountOfInterest;

    /*
    * Delegates obtaining relevant context data to child algorithms
    */
    abstract void establishContext();

    /*
    * Delegates what should be searched to child algorithms
    */
    abstract ArrayList<Searchable> getToBeSearched();

    /**
     * Establishes context derived from child algorithms then constructs
     * search scene with specialized context.
     * <p>
     * Called when Buy Equities button is pressed and sets up the scene
     * for when a holding is searched for on the Buy Equities "page".
     *
     * @param anFPTS - FPTS
     */
    public void process(FPTS anFPTS) {
        theFPTS = anFPTS;
        establishContext();
        theStage = theFPTS.getStage();
        self = this;
        mainInput = new TextField();
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

    /*
    * Delegates method of purchase/sale inside FPTS to subclass
    */
    abstract void processInsideFPTS();

    /*
    * Delegates method of purchase/sale outside FPTS to sublcass
    */
    abstract void processOutsideFPTS();

    /*
    *
    * Defines views used throughout algorithm
    *
    */

    /**
     * Constructs Scene where the user searches and selects Equity or Holding,
     * depending on context established previously in given subclass.
     *
     * @return
     */
    public Scene getFirstSearchScene() {
        System.out.println("getFirstSearchScene() called");
        ArrayList<Searchable> toBeSearched = getToBeSearched();
        s = new SearchedHoldingSearcher();
        s.addObserver(self);
        VBox queries = getHoldingQueries();
        return getSearchScene(toBeSearched, queries, getTransitionAfterHolding());
    }

    /**
     * Constructs Scene where the user searches and selects CashAccount
     *
     * @return Scene
     */
    public Scene getSecondSearchScene() {
        System.out.println("getSecondSearchScene() called");
        s = new CashAccountSearcher();
        s.addObserver(self);
        matchDisplay.getChildren().clear();
        mainInput.setText("");
        ArrayList<Searchable> toBeSearched = theFPTS.getPortfolio().getCashAccountSearchables();
        VBox queries = getCashAccountQueries();
        return getSearchScene(toBeSearched, queries, getTransitionAfterCashAccount());
    }

    /**
     * Helper scene constructor for the user to select and search any given
     * Searchable type.
     * <p>
     * Precondition: Searcher algorithm is preset
     *
     * @param toBeSearched - ArrayList<Searchable>
     * @param queries      - VBox of text field
     * @param actionBtn    - controller that determines what to do next
     * @return
     */
    private Scene getSearchScene(ArrayList<Searchable> toBeSearched, VBox queries, Button actionBtn) {
        System.out.println("getSearchScene(p1 ,p2 ,p3 ) called");
        VBox splitPage = new VBox();

        VBox searchPane = new VBox();

        actionBtn.setVisible(false);

        Button searchBtn = new Button();
        searchBtn.setText("Search");
        searchBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                /*
                * Calls Searcher algorithm specified in the step of program execution.
                */
                s.search(queries.getChildren(), toBeSearched);
                actionBtn.setVisible(true);
            }
        });

        HBox forAction = new HBox();
        forAction.getChildren().addAll(queries, actionBtn);
        searchPane.getChildren().addAll(forAction, searchBtn, matchDisplay);
        splitPage.getChildren().addAll(theFPTS.getNav(), searchPane);
        return new Scene(splitPage, WIDTH, HEIGHT);
    }


    /**
     * Constructs Scene to get the following user input:
     * -price per share
     * -number of shares
     * -whether purchase/sale was made outside or inside the FPTS
     *
     * @return Scene
     */
    public Scene getAdditionalInfoScene() {

        VBox splitPage = new VBox();
        VBox searchPane = new VBox();
        VBox queries = new VBox();
        HBox aField = new HBox();

        /*
        * Defines first field : price per share
        */
        TextField pricePerShareField = new TextField();
        pricePerShareField.setText("" + equityOfInterest.getValuePerShare());
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

                    switch (searchConditions.getValue().toString()) {
                        case ("Outside FPTS"):
                            /*
                            * Delegates purchase/sale outside FPTS in respective subclass
                            */
                            processOutsideFPTS();
                            theStage.setScene(theFPTS.getConfirmationScene());
                            break;
                        case ("Use existing cash account"):
                            /*
                            * Constructs another scene to get CashAccount if purchase/sale
                            * is made inside FPTS.
                            */
                            theStage.setScene(getSecondSearchScene());
                            break;
                    }
                } else {
                    pricePerShareField.setText("INVALID");
                }
            }
        });

        searchPane.getChildren().addAll(queries, submitButton);
        splitPage.getChildren().addAll(theFPTS.getNav(), searchPane);
        Scene additionalInfoScene = new Scene(splitPage, WIDTH, HEIGHT);
        return additionalInfoScene;

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

    /*
    *
    * Defines Button controls
    *
    */

    /**
     * Defines behavior for submit button after selecting CashAccount
     *
     * @return Button
     */
    public Button getTransitionAfterCashAccount() {
        Button actionBtn = new Button();
        actionBtn.setText("Proceed");

        actionBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                /*
                * Validates CashAccount and calls subclass algorithm to process
                * sale/purchase inside FPTS.
                */
                if (mainInput.getText() != null && s.getMatch(mainInput.getText()) != null) {
                    cashAccountOfInterest = (CashAccount) s.getMatch(mainInput.getText());
                    processInsideFPTS();
                } else {
                    mainInput.setText("INVALID");
                }
            }
        });

        return actionBtn;

    }

    /**
     * Defines button action to update equityOfInterest, which may reflect
     * Equity or Holding to be bought or sold, respectively.
     *
     * @return Button
     */
    public Button getTransitionAfterHolding() {
        Button actionBtn = new Button();
        actionBtn.setText("Proceed");

        actionBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                /*
                * Validates HoldingUpdatable (Equity or Holding) then transitions
                * to next view.
                */
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

    /**
     * On update, calls method to display new set of matches.
     *
     * @param o
     * @param arg
     */
    @Override
    public void update(Observable o, Object arg) {
        displayMatches(s.getMatches());
    }

    /**
     * Helper method to display matches produced by Searcher algorithm
     *
     * @param matches
     */
    private void displayMatches(ArrayList<Searchable> matches) {
        /**
         * For each match in matches, produce button that may populate the input
         * field.
         */
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
        aField.setSpacing(10);
        return aField;
    }

    /**
     * This helper method returns queries in relation to searching/selecting an
     * Equity or a Holding.
     *
     * @return VBox
     */
    private VBox getHoldingQueries() {
        VBox queries = new VBox();
        queries.getChildren().add(createInputField("Ticker symbol: ", mainInput));
        queries.getChildren().add(createInputField("Holding name: ", new TextField()));
        queries.getChildren().add(createInputField("Index: ", new TextField()));
        queries.getChildren().add(createInputField("Sector: ", new TextField()));
        return queries;
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
        return queries;
    }

}

