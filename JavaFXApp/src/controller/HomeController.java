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
 * Controller class for the HomePage. Extends MenuController which holds all of the functionality for the MenuBar
 * on the page. Controls the actions that occur when a button is pressed on the screen.
 * Created by Luke on 3/02/2016.
 */
public class HomeController extends MenuController {

    FPTS fpts = FPTS.getSelf();
    @FXML
    private PieChart valueChart = new PieChart();
    @FXML
    private Label valueLabel = new Label();

    /**
     * Method that is called if the BuyEquities button is pressed on the HomePage. Loads the buy equities page.
     * @param event - ActionEvent - the event that caused this method to run.
     */
    @FXML
    protected void handlePortfolioButtonPressed(ActionEvent event) {
        Displayer pd = new PortfolioDisplayer();
        pd.display(fpts);
    }

    /**
     * Method that is called if the BuyEquities button is pressed on the HomePage. Loads the buy equities page.
     * @param event - ActionEvent - the event that caused this method to run.
     */
    @FXML
    protected void handleBuyEquityButtonPressed(ActionEvent event) {
        HoldingAlgorithm eqUpdater = new BuyHoldingAlgorithm();
        eqUpdater.process(FPTS.getSelf());
    }

    /**
     * Method that is called when the Simulate button is pressed on the HomePage. Loads the Simulation page.
     * @param event - ActionEvent - the event that caused this method to run
     * @throws IOException - Exception is thrown if the SimulatePage.fxml is not found.
     */
    @FXML
    protected void handleSimulateButtonPressed(ActionEvent event) throws IOException {
        Parent parent = FXMLLoader.load(getClass().getResource("../gui/SimulatePage.fxml"));
        Scene scene = new Scene(parent);
        Stage app_stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        app_stage.setScene(scene);
        app_stage.show();
    }

    /**
     * Method that is called when the Logout button is pressed on the HomePage. Loads the Logout pop-up window
     * which in turn will logout the user depending on what option they select.
     * @param event - ActionEvent - the event that caused this method to run
     * @throws IOException - Exception is thrown if the LogoutPage.fxml is not found.
     */
    @FXML
    protected void handleLogoutButtonPressed(ActionEvent event) throws IOException {
        Stage stage = new Stage();
        Scene scene = new Scene(FXMLLoader.load(getClass().getResource("../gui/LogoutPage.fxml")));
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Method used to initialize items on the HomePage, mainly used to fill the PieChart on the page and
     * put the current value of the portfolio into the label on the page.
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        if(fpts.hasPortfolio(fpts.getCurrentUser())) {
            Portfolio p = fpts.getPortfolio();
            double cashAccountValue = 0.0;
            double equityTotalValue = 0.0;
            try {
                for (CashAccount c : p.getCashAccounts()) {
                    cashAccountValue += c.getValue();
                }
                for (Holding h : p.getHoldings()) {
                    equityTotalValue += h.getValue();
                }
            }
            catch(NullPointerException e){
            }
            ObservableList<PieChart.Data> pieChartData =
                    FXCollections.observableArrayList(
                            new PieChart.Data("Holdings", equityTotalValue),
                            new PieChart.Data("Cash Accounts", cashAccountValue));
            valueChart.setData(pieChartData);
            valueChart.setTitle("Portfolio");
            valueLabel.setText("Current Portfolio Value: $" + (cashAccountValue + equityTotalValue));

        }

    }
}
