package controller;

import gui.FPTS;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import model.PortfolioElements.CashAccount;

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
    }

    /**
     * Ignore cash account being imported
     * @param actionEvent
     */
    @FXML
    protected void handleIgnoreButtonPressed (ActionEvent actionEvent) {

    }

    /**
     * Replaces existing cash account with cash account being imported
     * @param actionEvent
     */
    @FXML
    protected void handleReplaceButtonPressed (ActionEvent actionEvent) {

    }

    /**
     * Combines existing cash account with cash account being imported
     * @param actionEvent
     */
    @FXML
    protected void handleCombineButtonPressed (ActionEvent actionEvent) {

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        accountLabel.setText("Cash Account: " + account.toString());
    }
}
