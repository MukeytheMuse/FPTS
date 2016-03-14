package controller;

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
import model.*;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

/**
 * Defines the view and control for selecting CashAccount. Observes
 * ashAccountSearcher, updates display, then notifies algorithms when a
 * CashAccount has been selected.
 *
 * @author Eric Epstein
 */
public class CashAccountFinder extends Observable implements Observer {

    /*
    * display of matches
    */
    VBox matchDisplay;

    /*
    * context data
    */
    FPTS theFPTS;

    /*
    * text field that user types in
    */
    TextField mainInput;

    /*
    * searcher algorithm
    */
    Searcher s;

    /*
    * CashAccount being found
    */
    CashAccount c;

    /**
     * Establishes context data, adds itself as observer of CashAccountSearcher,
     * then calls for scene construction.
     *
     * @param theFPTS
     * @param c
     */
    public CashAccountFinder(FPTS theFPTS, CashAccount c) {
        mainInput = new TextField();
        matchDisplay = new VBox();
        s = new CashAccountSearcher();
        this.s.addObserver(this);

        this.c = c;
        this.theFPTS = theFPTS;
        Portfolio p = theFPTS.getPortfolio();
        Stage theStage = theFPTS.getStage();
        matchDisplay.getChildren().clear();
        mainInput.setText("");

        Scene searchScene = getSearchScene(p.getCashAccountSearchables(), getCashAccountQueries());
        theStage.setScene(searchScene);

    }

    /**
     * Constructs view and control for searching and selecting CashAccount.
     *
     * @param toBeSearched
     * @param queries
     * @return
     */
    public Scene getSearchScene(ArrayList<Searchable> toBeSearched, VBox queries) {

        VBox splitPage = new VBox();
        VBox searchPane = new VBox();

        Button actionBtn = new Button();
        actionBtn.setVisible(false);
        actionBtn.setText("Proceed");
        /*
        * Handles event of selecting a match
        */
        actionBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                if (mainInput.getText() != null && s.getMatch(mainInput.getText()) != null) {
                    CashAccount aCashAccount = (CashAccount) s.getMatch(mainInput.getText());
                    c.overwrite(aCashAccount);
                    setChanged();
                    notifyObservers();
                }
            }
        });

        /*
        * Handle event of searching for a match
        */
        Button searchBtn = new Button();
        searchBtn.setText("Search");
        searchBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                s.search(queries.getChildren(), toBeSearched);
                actionBtn.setVisible(true);
            }
        });

        HBox forAction = new HBox();
        forAction.getChildren().addAll(queries, actionBtn);
        searchPane.getChildren().addAll(forAction, searchBtn, matchDisplay);
        splitPage.getChildren().addAll(theFPTS.getNav(), searchPane);
        return new Scene(splitPage, theFPTS.getWidth(), theFPTS.getHeight());
    }
    
    /*
    *
    * Define helper methods related to creation for parts of views
    *
    */

    /**
     * Helper method to format text fields with description.
     *
     * @return HBox
     */
    private HBox createInputField(String description, TextField input) {
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

    /**
     * Helper method to define fields to be entered
     *
     * @return VBox
     */
    private VBox getCashAccountQueries() {
        VBox queries = new VBox();
        queries.getChildren().add(createInputField("Account name: ", mainInput));
        return queries;
    }
    
    /*
    *
    * Defines methods related to the Observer pattern
    *
    */

    /**
     * On update, the display of matches will change to reflect
     * the next matches.
     *
     * @param o   - Observable
     * @param arg - Object
     */
    @Override
    public void update(Observable o, Object arg) {
        displayMatches(s.getMatches());
    }

    /**
     * Displays the matches, one of which may be selected.
     *
     * @param matches
     */
    public void displayMatches(ArrayList<Searchable> matches) {
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
