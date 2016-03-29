package controller;
import gui.FPTS;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.PieChart.Data;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import model.CashAccount;
import model.Holding;
import model.Portfolio;

public class HomeController extends MenuController {
    FPTS fpts = FPTS.getSelf();//TODO: get rid of the god class

    @FXML
    private PieChart valueChart = new PieChart();
    @FXML
    private Label valueLabel = new Label();


    /**
     *
     * @param location
     * @param resources
     */
    public void initialize(URL location, ResourceBundle resources) {
        Portfolio p = FPTS.getCurrentUser().getMyPortfolio();

        double equityTotalValue = 0;
        for(Holding h: p.getHoldings()){
            equityTotalValue += h.getCurrentValue();
        }
        double cashAccountValue = 0;
        for(CashAccount c: p.getCashAccounts()){
            cashAccountValue += c.getValue();
        }

        ObservableList pieChartData = FXCollections.observableArrayList(new Data("Holdings", equityTotalValue), new Data("Cash Accounts", cashAccountValue));
        this.valueChart.setData(pieChartData);
        //TODO: Warning:(49, 33) Unchecked assignment: 'javafx.collections.ObservableList' to 'javafx.collections.ObservableList<javafx.scene.chart.PieChart.Data>'

        this.valueChart.setTitle("Portfolio");
        this.valueLabel.setText("Current Portfolio Value: $" + (cashAccountValue + equityTotalValue));
    }

    /**
     * Method that is called if the BuyEquities button is pressed on the HomePage. Loads the buy equities page.
     *
     * @param event - ActionEvent - the event that caused this method to run.
     */
    @FXML
    protected void handlePortfolioButtonPressed(ActionEvent event) throws IOException {
        Scene scene = new Scene(FXMLLoader.load(this.getClass().getResource("../gui/PortfolioPage.fxml")));
        Stage app_stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        app_stage.setScene(scene);
        app_stage.show();
    }

    /**
     * Method that is called if the BuyEquities button is pressed on the HomePage.
     * Loads the buy equities page.
     *
     * @param event - ActionEvent - the event that caused this method to run.
     */
    @FXML
    protected void handleBuyEquityButtonPressed(ActionEvent event) {
        HoldingAlgorithm eqUpdater = new BuyHoldingAlgorithm();
        eqUpdater.process(FPTS.getSelf());
    }

    /**
     *
     * @param event
     * @throws IOException
     */
    @FXML
    protected void handleSimulateButtonPressed(ActionEvent event) throws IOException {
        Parent parent = (Parent)FXMLLoader.load(this.getClass().getResource("../gui/SimulatePage.fxml"));
        Scene scene = new Scene(parent);
        Stage app_stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        app_stage.setScene(scene);
        app_stage.show();
    }

    /**
     *
     * @param event
     * @throws IOException
     */
    @FXML
    protected void handleLogoutButtonPressed(ActionEvent event) throws IOException {
        Stage stage = new Stage();
        Scene scene = new Scene((Parent)FXMLLoader.load(this.getClass().getResource("../gui/LogoutPage.fxml")));
        stage.setScene(scene);
        stage.show();
    }
}

