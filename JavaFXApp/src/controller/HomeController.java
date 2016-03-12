package controller;

import gui.FPTS;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by nveil_000 on 3/11/2016.
 */
public class HomeController extends MenuController {

    @FXML
    protected void handlePortfolioButtonPressed(ActionEvent event) throws IOException {
        Parent parent = FXMLLoader.load(getClass().getResource("../gui/PortfolioPage.fxml"));
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
        //TODO
    }
}
