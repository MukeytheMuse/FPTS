package controller;

import gui.FPTS;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import model.DataBase.WriteFile;
import model.PortfolioElements.CashAccount;
import model.PortfolioElements.Transaction;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by Kimberly Sookoo
 */
public class ImportCashAccountPopUpController implements Initializable {
    FPTS fpts = FPTS.getSelf();

    @FXML
    private Label accountLabel;
    private CashAccount account, importedAccount;


    public void setAccounts(CashAccount acct, CashAccount imported) {
        this.account = acct;
        this.importedAccount = imported;
        this.accountLabel.setText("Cash Account in the System: " + account.getAccountName() + " with $" +
                                account.getValue() + "\n\nAccount being imported: " + importedAccount.getAccountName() +
                                " with $" + importedAccount.getValue());
    }

    /**
     * Ignore cash account being imported
     * @param actionEvent
     */
    @FXML
    protected void handleIgnoreButtonPressed (ActionEvent actionEvent) {
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.close();
    }

    /**
     * Replaces existing cash account with cash account being imported
     * @param actionEvent
     */
    @FXML
    protected void handleReplaceButtonPressed (ActionEvent actionEvent) {
        this.account.overwrite(this.importedAccount);
        this.account.setTransactions(this.importedAccount.getTransactions());
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.close();
    }

    /**
     * Combines existing cash account with cash account being imported
     * @param actionEvent
     */
    @FXML
    protected void handleCombineButtonPressed (ActionEvent actionEvent) {
        double valueToAdd = this.importedAccount.getValue();
        double currentValue = this.account.getValue();
        this.account.setValue((currentValue + valueToAdd));
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.close();

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {}
}
