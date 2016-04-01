package controller;

import gui.FPTS;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;

import javafx.scene.control.Label;

import javafx.stage.Stage;
import model.WatchedEquity;
import model.Portfolio;


import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class WatchlistController extends MenuController {
    FPTS fpts = FPTS.getSelf();//TODO: get rid of the god class

    @FXML
    private Label watchlistLabel = new Label();

    /**
     * @param location
     * @param resources
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Portfolio p = FPTS.getCurrentUser().getMyPortfolio();
        ArrayList<WatchedEquity> watchlist = p.getWatchedEquities();
        String watchlistStr = "";
        for (WatchedEquity w : watchlist) {
            watchlistStr = watchlistStr + w + "\n";
        }
        //ObservableList pieChartData = FXCollections.observableArrayList(new Data("Holdings", equityTotalValue), new Data("Cash Accounts", cashAccountValue));
        //TODO: Warning:(49, 33) Unchecked assignment: 'javafx.collections.ObservableList' to 'javafx.collections.ObservableList<javafx.scene.chart.PieChart.Data>'
        
        this.watchlistLabel.setText(watchlistStr);
        
    }
    
    public void handleUpdate(ActionEvent event) {
        System.out.println("IN UPDATE");
        Portfolio p = fpts.getCurrentUser().getMyPortfolio();
        ArrayList<WatchedEquity> watchedEquities = p.getWatchedEquities();
        String list = "";
        for (WatchedEquity w : watchedEquities) {
            list += w;
        }
        this.watchlistLabel.setText(list);
        
    }



    /**
     * Method that is called if the BuyEquities button is pressed on the HomePage.
     * Loads the buy equities page.
     *
     * @param event - ActionEvent - the event that caused this method to run.
     */
    /*
    @FXML
    protected void handleBuyEquityButtonPressed(ActionEvent event) {
        HoldingAlgorithm eqUpdater = new BuyHoldingAlgorithm();
        eqUpdater.process(FPTS.getSelf());
    }
    */
    
    @FXML
    protected void handleGoToAddEquityToWatchlistButtonPressed(ActionEvent event) throws IOException {
        Parent parent = FXMLLoader.load(this.getClass().getResource("/gui/AddToWatchlistPage.fxml"));
        Scene scene = new Scene(parent);
        Stage stage = (Stage) this.myMenuBar.getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }
   
   
}

