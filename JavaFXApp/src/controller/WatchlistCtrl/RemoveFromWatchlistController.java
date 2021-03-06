/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.WatchlistCtrl;

import controller.MenuController;
import gui.FPTS;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.PortfolioElements.Portfolio;
import model.PortfolioElements.WatchedEquity;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

/**
 * FXML Controller class
 *
 * @author ericepstein
 */
public class RemoveFromWatchlistController extends MenuController implements Initializable {

    @FXML
    private MenuBar myMenuBar;
    @FXML
    private MenuItem home;
    @FXML
    private MenuItem save;
    @FXML
    private MenuItem Logout;
    @FXML
    private MenuItem Exit;
    @FXML
    private MenuItem showPortfolio;
    @FXML
    private MenuItem buyEquities;
    @FXML
    private MenuItem sellEquities;
    @FXML
    private MenuItem createCA;
    @FXML
    private MenuItem deposit;
    @FXML
    private MenuItem withdraw;
    @FXML
    private MenuItem transfer;
    @FXML
    private MenuItem remove;
    @FXML
    private MenuItem about;
    @FXML
    private Label error;
    @FXML
    private VBox watchedEquitiesBox = new VBox();

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        //watchedEquitiesBox = new VBox();
        FPTS fpts = FPTS.getSelf();
        Portfolio p = fpts.getCurrentUser().getMyPortfolio();
        ArrayList<WatchedEquity> watchlist = p.getWatchedEquities();

        for (WatchedEquity w : watchlist) {
            Button aButton = new Button(w.getSymbol());
            aButton.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent e) {
                    watchlist.remove(w);
                    try {
                        redirect();
                    } catch (Exception ex) {

                    }
                }
            });
            watchedEquitiesBox.getChildren().add(aButton);
        }
    }

    public void redirect() throws IOException {
        Parent parent = FXMLLoader.load(this.getClass().getResource("/Watchlist/WatchlistPage.fxml"));
        Scene scene = new Scene(parent);
        Stage stage = (Stage) this.myMenuBar.getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

}
