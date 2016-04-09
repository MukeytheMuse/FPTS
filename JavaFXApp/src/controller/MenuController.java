package controller;

import controller.CashAccountCtrl.*;
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
import model.UndoRedo.UndoRedoManager;

public abstract class MenuController implements Initializable {
    @FXML
    public MenuBar myMenuBar;

    FPTS fpts = FPTS.getSelf();

    /**
     * @param event
     * @throws IOException
     */
    public void handleLogoutMenuItemPressed(ActionEvent event) throws IOException {
        Stage stage = new Stage();
        Parent parent = FXMLLoader.load(this.getClass().getResource("/LogoutPage.fxml"));
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
    }

    /**
     * Handler for when the About button is pressed in the Menu Bar
     *
     * @param event - ActionEvent - Event that caused this function to be called.
     */
    public void handleAboutMenuItemPressed(ActionEvent event) {
        //TODO Add an about page
    }

    public void handleHomeMenuItemPressed(ActionEvent event) throws IOException {
        Parent parent = (Parent) FXMLLoader.load(this.getClass().getClassLoader().getResource("res/HomePage.fxml"));
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
        Parent parent = (Parent) FXMLLoader.load(this.getClass().getClassLoader().getResource("res/PortfolioPage.fxml"));
        Scene scene = new Scene(parent);
        Stage stage = (Stage) this.myMenuBar.getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }


    public void handleBuyEquitiesMenuItemPressed(ActionEvent event) throws IOException {
        Parent parent = (Parent) FXMLLoader.load(this.getClass().getClassLoader().getResource("res/BuyHoldingPage.fxml"));
        Scene scene = new Scene(parent);
        Stage stage = (Stage) this.myMenuBar.getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    public void handleSellEquitiesMenuItemPressed(ActionEvent event) throws IOException {
        Parent parent = (Parent) FXMLLoader.load(this.getClass().getClassLoader().getResource("res/SellHoldingPage.fxml"));
        Scene scene = new Scene(parent);
        Stage stage = (Stage) this.myMenuBar.getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    public void handleWatchlistMenuItemPressed(ActionEvent event) throws IOException {
        Parent parent = (Parent) FXMLLoader.load(this.getClass().getClassLoader().getResource("res/Watchlist/WatchlistPage.fxml"));
        Scene scene = new Scene(parent);
        Stage stage = (Stage) this.myMenuBar.getScene().getWindow();
        stage.setScene(scene);
        stage.show();
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

    public void handleRemoveMenuItemPressed(ActionEvent event) throws IOException {
        Parent parent = (Parent) FXMLLoader.load(this.getClass().getResource("/CADeletePage.fxml"));
        Scene scene = new Scene(parent);
        Stage stage = (Stage) this.myMenuBar.getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    public void handleWatchMenuItemPressed(ActionEvent event) throws IOException {
        Scene scene = new Scene(FXMLLoader.load(this.getClass().getResource("/Watchlist/WatchlistPage.fxml")));
        Stage stage = (Stage) this.myMenuBar.getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }
    
    public void handleUndoMenuItemPressed(ActionEvent event) throws IOException {
        FPTS fpts = FPTS.getSelf();
        UndoRedoManager undoRedoManager = fpts.getUndoRedoManager();
        undoRedoManager.undo();
        
        Scene scene = new Scene(FXMLLoader.load(this.getClass().getResource("/HomePage.fxml")));
        Stage stage = (Stage) this.myMenuBar.getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }
    
    public void handleRedoMenuItemPressed(ActionEvent event) throws IOException {
        FPTS fpts = FPTS.getSelf();
        UndoRedoManager undoRedoManager = fpts.getUndoRedoManager();
        undoRedoManager.redo();
        
        Scene scene = new Scene(FXMLLoader.load(this.getClass().getResource("/HomePage.fxml")));
        Stage stage = (Stage) this.myMenuBar.getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    public void goToLoginPage(ActionEvent event) throws IOException {
        Parent parent = FXMLLoader.load(this.getClass().getResource("/LoginPage.fxml"));
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

