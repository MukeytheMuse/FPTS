package controller.CashAccountCtrl;

import controller.MenuController;
import gui.FPTS;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
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
 * Created by ericepstein on 4/8/16.
 */
public class AddCashAccountController extends MenuController {
    @FXML
    private TextField nameField;

    @FXML
    private TextField amountField;

    @FXML
    private Button submitBtn;

    private FPTS fpts;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        fpts = FPTS.getSelf();

        submitBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {

                CashAccounts cashAccounts = fpts.getCurrentUser().getMyPortfolio().getCashAccountsCollection();
                UndoRedoManager undoRedoManager = fpts.getUndoRedoManager();
                try {
                    String name = nameField.getText();
                    double amount = Double.parseDouble(amountField.getText());
                    if (amount >= 0) {
                        Command commComposite = new CommandComposite();

                        CashAccount c = new CashAccount(name, amount, new Date(), new ArrayList<Transaction>());
                        Command comm = (Command) new CashAccountAddition(cashAccounts, c);
                        commComposite.addChild(comm);
                        Transaction t = (Transaction) new Deposit(c, amount);
                        comm = new HistoryAddition(t, fpts.getCurrentUser().getMyPortfolio().getHistory());
                        commComposite.addChild(comm);

                        undoRedoManager.execute(commComposite);
                        redirect();
                    } else {
                        nameField.setText("INVALID VALUE");
                    }

                } catch (Exception ex) {
                    nameField.setText("INVALID");
                }

            }
        });

    }
    public void redirect() throws IOException {
        Parent parent = FXMLLoader.load(this.getClass().getResource("/HomePage.fxml"));
        Scene scene = new Scene(parent);
        Stage stage = (Stage) this.myMenuBar.getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }
}