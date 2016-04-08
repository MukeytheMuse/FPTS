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
import model.PortfolioElements.Holding;

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

    private void updateCashAccounts() {
        ArrayList<CashAccount> list = new ArrayList<>();
        list.addAll(FPTS.getCurrentUser().getMyPortfolio().getCashAccounts());
        CAtableView.setItems(FXCollections.observableArrayList(list));
    }

    @FXML
    public void handleDoubleClickTableRow(MouseEvent event) {
        if (event.getClickCount() == 2) {
            CashAccount account = CAtableView.getSelectionModel().getSelectedItem();
            RemoveCashAccountAlgorithm rcaa = new RemoveCashAccountAlgorithm();
            rcaa.fxAction(account);
            Stage stg = FPTS.getSelf().getStage();
            try {
                Parent parent = (Parent) FXMLLoader.load(this.getClass().getResource("/PortfolioPage.fxml"));
                stg.setScene(new Scene(parent));
            } catch (IOException e) {
                e.printStackTrace();
            }
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.close();
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        CAnameCol.setCellValueFactory(new PropertyValueFactory<CashAccount, String>("accountName"));
        amountCol.setCellValueFactory(new PropertyValueFactory<CashAccount, String>("value"));
        dateCol.setCellValueFactory(new PropertyValueFactory<CashAccount, String>("dateAdded"));
        ObservableList<CashAccount> CAdata = FXCollections.observableArrayList(
                FPTS.getCurrentUser().getMyPortfolio().getCashAccounts());
        CAtableView.setItems(CAdata);
    }
}
