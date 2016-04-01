package controller;

import controller.CashAccountCtrl.*;
import controller.HoldingCtrl.BuyHoldingAlgorithm;
import controller.HoldingCtrl.HoldingAlgorithm;
import controller.HoldingCtrl.SellHoldingAlgorithm;
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

public abstract class MenuController implements Initializable {
    @FXML
    public MenuBar myMenuBar;

    FPTS fpts = FPTS.getSelf();

    /**
     * @param event
     * @throws IOException
     */
    public void handleLogoutMenuItemPressed(ActionEvent event) throws IOException {
        //TODO: Warning:(29, 57) [UnusedDeclaration] Parameter 'event' is never used
        Stage stage = new Stage();
        Parent parent = FXMLLoader.load(this.getClass().getResource("../gui/LogoutPage.fxml"));
        Scene scene = new Scene(parent);
        stage.setScene(scene);
        stage.show();
    }

    public void handleExitMenuItemPressed(ActionEvent event) {
        Platform.exit();
        System.exit(0);
    }

    public void handleSaveMenuItemPressed(ActionEvent event) {
        WriteFile writeFile = new WriteFile();
        writeFile.updatePortfolioForUser(FPTS.getCurrentUser());
        //TODO: check Warning:(44, 42) Static member 'gui.FPTS.getCurrentUser()' accessed via instance reference
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
        Parent parent = FXMLLoader.load(this.getClass().getResource("../gui/HomePage.fxml"));
        Scene scene = new Scene(parent);
        Stage stage = (Stage) this.myMenuBar.getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }


    /**
     * @param event
     * @throws IOException
     */
    public void handlePortfolioMenuItemPressed(ActionEvent event) throws IOException {
        Parent parent = (Parent) FXMLLoader.load(this.getClass().getResource("../gui/PortfolioPage.fxml"));
        Scene scene = new Scene(parent);
        Stage stage = (Stage) this.myMenuBar.getScene().getWindow();
        stage.setScene(scene);
        stage.show();
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
    
    public void handleWatchMenuItemPressed(ActionEvent event) throws IOException {
        Parent parent = FXMLLoader.load(this.getClass().getResource("../gui/Watchlist/WatchlistPage.fxml"));
        Scene scene = new Scene(parent);
        Stage stage = (Stage) this.myMenuBar.getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }


    //TODO: find out where we use this method
    public void goToLoginPage(ActionEvent event) throws IOException {
        Parent parent = FXMLLoader.load(this.getClass().getResource("../gui/LoginPage.fxml"));
        new Stage();

        Stage stage;
        try {
            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        } catch (ClassCastException var5) {
            stage = (Stage) this.myMenuBar.getScene().getWindow();
        }

        Scene scene = new Scene(parent);
        stage.setScene(scene);
        stage.show();
    }
}

