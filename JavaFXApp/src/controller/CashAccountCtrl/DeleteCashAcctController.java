package controller.CashAccountCtrl;

import controller.MenuController;
import gui.FPTS;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import model.PortfolioElements.CashAccount;
import model.PortfolioElements.CashAccounts;

import model.PortfolioElements.Transaction;
import model.UndoRedo.*;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.ResourceBundle;

/**
 * Created by Ian London
 */
public class DeleteCashAcctController extends MenuController {

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

            Command commComposite = new CommandComposite();
            commComposite.addChild(comm);


            Transaction t = (Transaction) new Withdrawal(account, account.getValue());
            comm = new HistoryAddition(t, fpts.getCurrentUser().getMyPortfolio().getHistory());
            commComposite.addChild(comm);

            undoRedoManager.execute(commComposite);

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
