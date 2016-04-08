package controller.WatchlistCtrl;

import controller.MenuController;
import gui.FPTS;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import model.PortfolioElements.Portfolio;
import model.PortfolioElements.WatchedEquity;
import model.WebServiceReader;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;
import java.util.ResourceBundle;

public class WatchlistController extends MenuController implements Observer {
    FPTS fpts = FPTS.getSelf();//TODO: get rid of the god class

    @FXML
    private Label watchlistLabel = new Label();

    /**
     * @param location
     * @param resources
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        WebServiceReader ws = fpts.getWebServiceReader();
        ws.addObserver(this);
        fillWatchlist();

        //TODO: Warning:(49, 33) Unchecked assignment: 'javafx.collections.ObservableList' to 'javafx.collections.ObservableList<javafx.scene.chart.PieChart.Data>'    
    }


    @Override
    public void update(Observable o, Object arg) {
        Portfolio p = FPTS.getCurrentUser().getMyPortfolio();
        p.updateWatchlist();

    }

    private void fillWatchlist() {
        Portfolio p = FPTS.getCurrentUser().getMyPortfolio();
        ArrayList<WatchedEquity> watchedEquities = p.getWatchedEquities();
        String list = "";
        if (watchedEquities != null) {
            for (WatchedEquity w : watchedEquities) {
                list += w + "\n";
            }
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
        Parent parent = FXMLLoader.load(this.getClass().getClassLoader().getResource("res/Watchlist/AddToWatchlistPage.fxml"));
        Scene scene = new Scene(parent);
        Stage stage = (Stage) this.myMenuBar.getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    protected void handleGoToRemoveEquityFromWatchlistButtonPressed(ActionEvent event) throws IOException {
        Parent parent = FXMLLoader.load(this.getClass().getClassLoader().getResource("res/Watchlist/RemoveFromWatchlistPage.fxml"));
        Scene scene = new Scene(parent);
        Stage stage = (Stage) this.myMenuBar.getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    protected void handleGoToEditedWatchedEquityButtonPressed(ActionEvent event) throws IOException {
        Parent parent = FXMLLoader.load(this.getClass().getClassLoader().getResource("res/Watchlist/EditWatchedEquityPage.fxml"));
        Scene scene = new Scene(parent);
        Stage stage = (Stage) this.myMenuBar.getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }


}

