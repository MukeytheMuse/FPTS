package controller;

import gui.FPTS;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.MenuBar;
import javafx.stage.Stage;
import model.DataBase.WriteFile;

import java.io.IOException;

/**
 * This class is the base implementation of the Menu Bar used in this application.
 * Other Controllers will extend this class to gain functionality of the MenuBar in the application.
 * Created by Luke on 3/01/2016.
 */
public abstract class MenuController implements Initializable {
    /**
     * A Local variable to access the MenuBar located in the FXML documents for this FPTS application.
     */
    @FXML
    MenuBar myMenuBar;

    /**
     * Handler for when the Logout button is pressed in the Menu Bar
     *
     * @param event - ActionEvent - Event that caused this function to be called.
     * @throws IOException - Throws IO Exception if the LoginPage.fxml is not found by the program.
     */
    public void handleLogoutMenuItemPressed(ActionEvent event) throws IOException {
        Stage stage = new Stage();
        Parent parent = FXMLLoader.load(getClass().getResource("../gui/LogoutPage.fxml"));
        Scene scene = new Scene(parent);
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Handler for when the Exit button is pressed in the Menu Bar
     *
     * @param event - ActionEvent - Event that caused this function to be called.
     */
    public void handleExitMenuItemPressed(ActionEvent event) {
        Platform.exit();
        System.exit(0);
    }

    public void handleSaveMenuItemPressed(ActionEvent event) {
        //TODO Save Current Portfolio
    }

    /**
     * Handler for when the About button is pressed in the Menu Bar
     *
     * @param event - ActionEvent - Event that caused this function to be called.
     */
    public void handleAboutMenuItemPressed(ActionEvent event) {
        //TODO
    }

    public void handleHomeMenuItemPressed(ActionEvent event) throws IOException {
        Parent parent = FXMLLoader.load(getClass().getResource("../gui/HomePage.fxml"));
        Scene scene = new Scene(parent);
        Stage stage = (Stage) myMenuBar.getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    public void handlePortfolioMenuItemPressed(ActionEvent event) throws IOException {
        Displayer pd = new PortfolioDisplayer();
        pd.display(FPTS.getSelf());
    }


    public void handleBuyEquitiesMenuItemPressed(ActionEvent event) {
        HoldingAlgorithm eqUpdater = new BuyHoldingAlgorithm();
        eqUpdater.process(FPTS.getSelf());
    }

    public void handleSellEquitiesMenuItemPressed(ActionEvent event) {
        HoldingAlgorithm eqUpdater = new SellHoldingAlgorithm();
        eqUpdater.process(FPTS.getSelf());
    }

    public void handleWithdrawMenuItemPressed(ActionEvent event) {
        CashAccountAlgorithm cashAcctAlgor = new WithdrawCashAccountAlgorithm();
        cashAcctAlgor.process(FPTS.getSelf());
    }

    public void handleDepositMenuItemPressed(ActionEvent event) {
        CashAccountAlgorithm cashAcctAlgor = new DepositCashAccountAlgorithm();
        cashAcctAlgor.process(FPTS.getSelf());
    }

    public void handleCreateMenuItemPressed(ActionEvent event) {
        CashAccountCreator cashAcctAlgor = new CashAccountCreator(FPTS.getSelf());
        cashAcctAlgor.getCashAccountCreatorScene();
    }

    public void handleTransferMenuItemPressed(ActionEvent event) {
        CashAccountAlgorithm cashAcctAlgor = new TransferCashAccountAlgorithm();
        cashAcctAlgor.process(FPTS.getSelf());
    }

    public void handleRemoveMenuItemPressed(ActionEvent event) {
        CashAccountAlgorithm cashAcctAlgor = new RemoveCashAccountAlgorithm();
        cashAcctAlgor.process(FPTS.getSelf());
    }

    /**
     * Additional function used in this application to return the application to the Login Page.
     *
     * @param event - ActionEvent - Event that caused the super function to be called, used to get the current Stage.
     * @throws IOException - Throws IO Exception if the LoginPage.fxml cannot be found.
     */
    public void goToLoginPage(ActionEvent event) throws IOException {
        Parent parent = FXMLLoader.load(getClass().getResource("../gui/LoginPage.fxml"));
        Stage stage = new Stage();
        try {
            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        } catch (ClassCastException c) {
            stage = (Stage) myMenuBar.getScene().getWindow();

        }
        Scene scene = new Scene(parent);
        stage.setScene(scene);
        stage.show();
    }

}
