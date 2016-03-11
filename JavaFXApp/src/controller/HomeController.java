package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by nveil_000 on 3/11/2016.
 */
public class HomeController extends MenuController {

    @FXML
    protected void handlePortfolioButtonPressed(ActionEvent event){

    }

    @FXML
    protected void handleSearchButtonPressed(ActionEvent event){

    }

    @FXML
    protected void handleOtherButtonPressed(ActionEvent event){

    }

    @FXML
    protected void handleLogoutButtonPressed(ActionEvent event) throws IOException{
        goToLoginPage(event);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
