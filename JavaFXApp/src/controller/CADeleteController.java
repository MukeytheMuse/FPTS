package controller;

import controller.CashAccountCtrl.RemoveCashAccountAlgorithm;
import controller.HoldingCtrl.BuyHoldingAlgorithm;
import controller.HoldingCtrl.SellHoldingAlgorithm;
import gui.FPTS;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import model.Equities.EquityComponent;
import model.PortfolioElements.CashAccount;
import model.PortfolioElements.CashAccounts;
import model.PortfolioElements.Holding;

import gui.FPTS;
import model.UndoRedo.CashAccountRemoval;
import model.UndoRedo.Command;
import model.UndoRedo.UndoRedoManager;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

/**
 * Created by Ian London
 */
public class CADeleteController extends MenuController {

    @FXML
    private TableColumn<CashAccount, String> CAnameCol, amountCol, dateCol;
    @FXML
    private TableView<CashAccount> CAtableView;

    private FPTS fpts;

    @FXML
    public void handleDoubleClickTableRow(MouseEvent event) {
        if (event.getClickCount() == 2) {
            CashAccount account = CAtableView.getSelectionModel().getSelectedItem();

            UndoRedoManager undoRedoManager = fpts.getUndoRedoManager();
            CashAccounts cashAccounts = fpts.getCurrentUser().getMyPortfolio().getCashAccountsCollection();
            Command comm = (Command) new CashAccountRemoval(cashAccounts, account);

            undoRedoManager.execute(comm);

            Stage stg = (Stage) ((Node) event.getSource()).getScene().getWindow();
            try {
                Scene scene = new Scene(FXMLLoader.load(this.getClass().getResource("/PortfolioPage.fxml")));
                stg.setScene(scene);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        fpts = fpts.getSelf();
        CAnameCol.setCellValueFactory(new PropertyValueFactory<CashAccount, String>("accountName"));
        amountCol.setCellValueFactory(new PropertyValueFactory<CashAccount, String>("value"));
        dateCol.setCellValueFactory(new PropertyValueFactory<CashAccount, String>("dateAdded"));
        CAtableView.setItems(FXCollections.observableArrayList(
                FPTS.getCurrentUser().getMyPortfolio().getCashAccounts()));
    }
}
