package controller;

import gui.FPTS;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import model.CashAccount;
import model.Holding;
import model.Portfolio;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by nveil_000 on 3/11/2016.
 */
public class PortfolioController extends MenuController {

    FPTS fpts = FPTS.getSelf();
    @FXML private Label cashAcctValue;
    @FXML private Label equitiesValue;
    @FXML private Label totalValue;
    @FXML private PieChart valueChart;

    @FXML
    protected void handleHomeButtonPressed(ActionEvent event) throws IOException {
        Parent parent = FXMLLoader.load(getClass().getResource("../gui/HomePage.fxml"));
        Scene scene = new Scene(parent);
        Stage app_stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

        app_stage.setScene(scene);
        app_stage.show();
    }

    @FXML
    protected void handleBuyEquityButtonPressed(ActionEvent event) throws IOException {
        HoldingAlgorithm eqUpdater = new BuyHoldingAlgorithm();
        eqUpdater.process(FPTS.getSelf());
    }

    @FXML
    protected void handleSimulateButtonPressed(ActionEvent event){
        //TODO
    }

    @FXML
    protected void handleLogoutButtonPressed(ActionEvent event) throws IOException {
        goToLoginPage(event);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Portfolio p = fpts.getPortfolio();
        double cashAccountValue = 0.0;
        for (CashAccount c : p.getCashAccounts()){
            cashAccountValue += c.getValue();
        }
        cashAcctValue.setText("$" + cashAccountValue);
        double equityTotalValue = 0.0;
        for (Holding h : p.getHoldings()){
            equityTotalValue += h.getValue();
        }
        equitiesValue.setText("$" + equityTotalValue);
        totalValue.setText("$" + (equityTotalValue + cashAccountValue));

        ObservableList<PieChart.Data> pieChartData =
                FXCollections.observableArrayList(
                        new PieChart.Data("Cash Accounts", cashAccountValue),
                        new PieChart.Data("Equities", equityTotalValue));
        valueChart.setData(pieChartData);


    }
}
