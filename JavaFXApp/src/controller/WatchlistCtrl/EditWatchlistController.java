/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.WatchlistCtrl;

import controller.MenuController;
import gui.FPTS;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.Equities.EquityComponent;
import model.Portfolio;
import model.PortfolioElements.WatchedEquity;

/**
 *
 * @author ericepstein
 */
public class EditWatchlistController extends MenuController {
    
    @FXML 
    private TextField tickerSymbolField = new TextField();
    
    @FXML
    private TextField lowTriggerField = new TextField();
    
    @FXML
    private TextField highTriggerField = new TextField();
    
    @FXML
    private VBox watchedEquitiesBox = new VBox();
    
    FPTS fpts;
    
    WatchedEquity watchedEq; 
    
        /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        System.out.print("INITIALIZED AGAIN!!!!");
        // TODO
        
        //watchedEquitiesBox = new VBox();
        fpts = FPTS.getSelf();
        Portfolio p = fpts.getCurrentUser().getMyPortfolio();
        ArrayList<WatchedEquity> watchlist = p.getWatchedEquities();
        
        for (WatchedEquity w : watchlist) {
            System.out.println("LOOP");
            Button aButton = new Button(w.getSymbol());
            aButton.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent e) {
                    
                    try { 
                        System.out.println("HANDLE");
                        watchedEq = w;
                        respond();
                        //redirect();
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
            });
            watchedEquitiesBox.getChildren().add(aButton);
        }
    } 
    
    public void redirect() throws IOException {
        System.out.println("IN REDIRECT");
        Parent parent = FXMLLoader.load(this.getClass().getResource("/gui/Watchlist/WatchlistPage.fxml"));
        Scene scene = new Scene(parent);
        Stage stage = (Stage) this.myMenuBar.getScene().getWindow();
        stage.setScene(scene);
        System.out.println("SET SCENE");
        stage.show(); 
    }
   
    protected void respond() throws IOException {
        boolean validInput = true;
        /*
        if (this.tickerSymbolField == null || this.tickerSymbolField.getText().isEmpty()) {
            tickerSymbolField.setText("INVALID");
            validInput = false;
        } else {
            String tickerSymbol = this.tickerSymbolField.getText(); 
            Portfolio p = fpts.getCurrentUser().getMyPortfolio();
            if (p.getEquityComponent(tickerSymbol) == null) {
                tickerSymbolField.setText("INVALID");
                validInput = false;
            } else {
        */
                double lowTrigger = -1;
                double highTrigger = -1;
                
                //EquityComponent e = p.getEquityComponent(tickerSymbol);
                EquityComponent e = watchedEq.getAssocEquity();
                
                if (lowTriggerField != null && !lowTriggerField.getText().isEmpty()) {
                    System.out.println("TESTING FIRST INPUT: " + lowTriggerField.getText());
                    try {
                        lowTrigger = Double.parseDouble(lowTriggerField.getText());
                    } catch (Exception ex) {
                           lowTriggerField.setText("INVALID");
                           validInput = false;
                    }
                }
                
                if (highTriggerField != null && !highTriggerField.getText().isEmpty()) {
                    System.out.println("TESTING SECOND INPUT: " + highTriggerField.getText());
                    try {
                        highTrigger = Double.parseDouble(highTriggerField.getText());
                    } catch (Exception ex) {
                           highTriggerField.setText("INVALID");
                           validInput = false;
                    }
                }
               
                if (validInput) {
                    System.out.println("IN VALID INPUT");
                    //WatchedEquity w = new WatchedEquity(e, lowTrigger, highTrigger);
                    //p.addWatchedEquity(w);
                    watchedEq.setHighTrigger(highTrigger);
                    watchedEq.setLowTrigger(lowTrigger);
                    Parent parent = FXMLLoader.load(this.getClass().getResource("/gui/Watchlist/WatchlistPage.fxml"));
                    Scene scene = new Scene(parent);
                    Stage stage = (Stage) this.myMenuBar.getScene().getWindow();
                    stage.setScene(scene);
                    stage.show(); 
                }
 
    }
    
}
