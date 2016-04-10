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
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import model.DataBase.ReadImports;
import model.DataBase.WriteFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import model.PortfolioElements.CashAccount;
import model.PortfolioElements.Holding;
import model.PortfolioElements.Portfolio;
import model.PortfolioElements.Transaction;
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
    public void handleAboutMenuItemPressed(ActionEvent event) throws IOException {
        Stage stage = new Stage();
        Parent parent = FXMLLoader.load(this.getClass().getResource("/AboutPage.fxml"));
        Scene scene = new Scene(parent);
        stage.setScene(scene);
        stage.show();
    }

    public void handleHomeMenuItemPressed(ActionEvent event) throws IOException {
        Parent parent = (Parent) FXMLLoader.load(this.getClass().getResource("/HomePage.fxml"));
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
        Parent parent = (Parent) FXMLLoader.load(this.getClass().getResource("/PortfolioPage.fxml"));
        Scene scene = new Scene(parent);
        Stage stage = (Stage) this.myMenuBar.getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    public void handleSimulationButtonPressed(ActionEvent event) {
        goToSimulation(event);
    }


    public void handleBuyEquitiesMenuItemPressed(ActionEvent event) throws IOException {
        Parent parent = (Parent) FXMLLoader.load(this.getClass().getResource("/BuyHoldingPage.fxml"));
        Scene scene = new Scene(parent);
        Stage stage = (Stage) this.myMenuBar.getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    public void handleSellEquitiesMenuItemPressed(ActionEvent event) throws IOException {
        Parent parent = (Parent) FXMLLoader.load(this.getClass().getResource("/SellHoldingPage.fxml"));
        Scene scene = new Scene(parent);
        Stage stage = (Stage) this.myMenuBar.getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    public void handleWatchlistMenuItemPressed(ActionEvent event) throws IOException {
        Parent parent = (Parent) FXMLLoader.load(this.getClass().getResource("/Watchlist/WatchlistPage.fxml"));
        Scene scene = new Scene(parent);
        Stage stage = (Stage) this.myMenuBar.getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    public void handleWithdrawMenuItemPressed(ActionEvent event) throws IOException {
        Parent parent = (Parent) FXMLLoader.load(this.getClass().getResource("/WithdrawPage.fxml"));
        Scene scene = new Scene(parent);
        Stage stage = (Stage) this.myMenuBar.getScene().getWindow();
        stage.setScene(scene);
        stage.show();

    }

    public void handleDepositMenuItemPressed(ActionEvent event) throws IOException {

        Parent parent = (Parent) FXMLLoader.load(this.getClass().getResource("/DepositPage.fxml"));
        Scene scene = new Scene(parent);
        Stage stage = (Stage) this.myMenuBar.getScene().getWindow();
        stage.setScene(scene);
        stage.show();

    }

    public void handleCreateMenuItemPressed(ActionEvent event) throws IOException {
        Parent parent = (Parent) FXMLLoader.load(this.getClass().getResource("/CreateCashAccountPage.fxml"));
        Scene scene = new Scene(parent);
        Stage stage = (Stage) this.myMenuBar.getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    public void handleHistoryMenuItemPressed(ActionEvent event) throws IOException {
        Parent parent = (Parent) FXMLLoader.load(this.getClass().getResource("/HistoryPage.fxml"));
        Scene scene = new Scene(parent);
        Stage stage = (Stage) this.myMenuBar.getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    public void handleTransferMenuItemPressed(ActionEvent event) throws IOException {

        Parent parent = (Parent) FXMLLoader.load(this.getClass().getResource("/TransferPage.fxml"));
        Scene scene = new Scene(parent);
        Stage stage = (Stage) this.myMenuBar.getScene().getWindow();
        stage.setScene(scene);
        stage.show();

    }

    public void handleRemoveMenuItemPressed(ActionEvent event) throws IOException {
        Parent parent = (Parent) FXMLLoader.load(this.getClass().getResource("/DeleteCashAcctPage.fxml"));
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

    public void handleImportMenuItemPressed(ActionEvent event) throws IOException{
        HashMap<String, ArrayList> importedEquities;
        ArrayList<Holding> userHoldingsToImport;
        ArrayList<Transaction> userTransactionsToImport;
        ArrayList<CashAccount> userCashAccountsToImport;
        Portfolio newPortfolio;

        Stage stage = new Stage();
        FileChooser fd = new FileChooser();
        fd.setTitle("Select file to upload");
        fd.setInitialDirectory(new File(System.getProperty("user.home")));
        fd.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("CSV", "*.csv"));
        File file = fd.showOpenDialog(stage);

        if (file != null) {
            importedEquities = ReadImports.readInImports(file);
            //They are being checked for accuracy within ReadImports file.
            userHoldingsToImport = importedEquities.get("Holdings");
            userTransactionsToImport = importedEquities.get("Transactions");
            userCashAccountsToImport = importedEquities.get("Cash Accounts");
            boolean flag;

            ArrayList<Holding> HoldingArrayList = FPTS.getCurrentUser().getMyPortfolio().getHoldings();

            for (Holding importedHolding : userHoldingsToImport) {
                flag = false;
                for(Holding existingHolding : HoldingArrayList ){
                    if (importedHolding.getTickerSymbol().equals(existingHolding.getTickerSymbol())) {
                        existingHolding.add(importedHolding.getNumOfShares());
                        flag = true;
                    }
                }
                if (!flag) {
                    HoldingArrayList.add(importedHolding);
                }
            }

            ArrayList<Transaction> TransactionArrayList = FPTS.getCurrentUser().getMyPortfolio().getTransactions();

            for (Transaction importedTransaction : userTransactionsToImport) {
                flag = false;
                for(Transaction existingTransaction : TransactionArrayList ){
                    if (importedTransaction.toString().equals(existingTransaction.toString())) {
                        flag = true;
                    }
                }
                if (!flag){
                    TransactionArrayList.add(importedTransaction);
                }
            }


            ArrayList<CashAccount> cashAccountArrayList = FPTS.getCurrentUser().getMyPortfolio().getCashAccounts();

            if (!cashAccountArrayList.isEmpty()) {
                for (CashAccount importedCashAccount : userCashAccountsToImport) {
                    for (CashAccount cashAccount : cashAccountArrayList) {
                        if (cashAccount.getAccountName().equals(importedCashAccount.getAccountName())) {
                            try {
                                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/ImportPopUp.fxml"));
                                Parent root1 = fxmlLoader.load();
                                ImportCashAccountPopUpController controller = fxmlLoader.getController();
                                controller.setAccounts(cashAccount, importedCashAccount);
                                Stage stg = new Stage();
                                stg.setScene(new Scene(root1));
                                stg.show();
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                        else {
                            cashAccountArrayList.add(importedCashAccount);
                        }
                    }
                }
            } else {
                for (CashAccount importedCashAccount : userCashAccountsToImport) {
                    cashAccountArrayList.add(importedCashAccount);
                }
            }

            Scene scene = new Scene(FXMLLoader.load(this.getClass().getResource("/HomePage.fxml")));
            Stage stageHome = (Stage) this.myMenuBar.getScene().getWindow();
            stageHome.setScene(scene);
            stageHome.show();
        }
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

    public void goToSimulation(ActionEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/SimulatePage.fxml"));
            Parent root1 = fxmlLoader.load();
            SimulationController controller = fxmlLoader.getController();
            controller.setHoldings(fpts.getPortfolio().getHoldings(), null);
            Stage stage;
            try {
                stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            } catch (ClassCastException var5) {
                stage = (Stage) this.myMenuBar.getScene().getWindow();
            }

            Scene scene = new Scene(root1);
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

