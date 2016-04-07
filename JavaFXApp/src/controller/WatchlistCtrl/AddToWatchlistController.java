package controller.WatchlistCtrl;

import controller.MenuController;
import gui.FPTS;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.Equities.EquityComponent;
import model.PortfolioElements.Portfolio;
import model.PortfolioElements.WatchedEquity;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class AddToWatchlistController extends MenuController {
    FPTS fpts = FPTS.getSelf();//TODO: get rid of the god class

    @FXML
    private TextField tickerSymbolField = new TextField();

    @FXML
    private TextField lowTriggerField = new TextField();

    @FXML
    private TextField highTriggerField = new TextField();

    /**
     * @param location
     * @param resources
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //Portfolio p = FPTS.getCurrentUser().getMyPortfolio();

        //ObservableList pieChartData = FXCollections.observableArrayList(new Data("Holdings", equityTotalValue), new Data("Cash Accounts", cashAccountValue));
        //TODO: Warning:(49, 33) Unchecked assignment: 'javafx.collections.ObservableList' to 'javafx.collections.ObservableList<javafx.scene.chart.PieChart.Data>'

        //this.valueChart.setTitle("Portfolio");
        //this.watchlistLabel.setText("TEST");
    }

    public void handleUpdate(ActionEvent event) {
        System.out.println("IN UPDATE");
        Portfolio p = new Portfolio(); // fpts.getCurrentUser().getMyPortfolio();
        ArrayList<WatchedEquity> watchedEquities = p.getWatchedEquities();
        String list = "";
        for (WatchedEquity w : watchedEquities) {
            list += w + "\n";
        }
        //this.watchlistLabel.setText(list);

    }


    @FXML
    protected void handleAddEquityToWatchlistButtonPressed(ActionEvent event) throws IOException {
        boolean validInput = true;
        if (this.tickerSymbolField == null || this.tickerSymbolField.getText().isEmpty()) {
            tickerSymbolField.setText("INVALID");
            //TODO: check Warning:(77, 13) Method invocation 'tickerSymbolField.setText("INVALID")' may produce 'java.lang.NullPointerException'
            validInput = false;
        } else {
            String tickerSymbol = this.tickerSymbolField.getText();
            Portfolio p = fpts.getCurrentUser().getMyPortfolio();
            if (p.getEquityComponent(tickerSymbol) == null) {
                tickerSymbolField.setText("INVALID");
                validInput = false;
            } else {
                double lowTrigger = -1;
                double highTrigger = -1;

                EquityComponent e = p.getEquityComponent(tickerSymbol);

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
                    WatchedEquity w = new WatchedEquity(e, highTrigger, lowTrigger);
                    p.addWatchedEquity(w);
                    Parent parent = FXMLLoader.load(this.getClass().getResource("/Watchlist/WatchlistPage.fxml"));
                    Scene scene = new Scene(parent);
                    Stage stage = (Stage) this.myMenuBar.getScene().getWindow();
                    stage.setScene(scene);
                    stage.show();
                }

            }
        }


    }

}

