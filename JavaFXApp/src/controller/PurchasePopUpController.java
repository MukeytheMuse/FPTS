package controller;

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
import model.PortfolioElements.HoldingUpdatable;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

/**
 * Created by Luke Veilleux
 */
public class PurchasePopUpController implements Initializable {

    @FXML
    private Label equityLabel, equityPrice, errors;
    @FXML
    private ChoiceBox<String> fptsChoice;
    @FXML
    private TextField priceTextBox, numSharesBox;
    @FXML
    private TableColumn<CashAccount, String> CAnameCol, amountCol, dateCol;
    @FXML
    private TableView<CashAccount> CAtableView;

    private EquityComponent equity = null;
    private Holding holding = null;
    private int shares;
    private double price;
    private String action;

    public void setEquity(Holding h, int shares, double price, String action){
        holding = h;
        equityLabel.setText(holding.getName());
        this.action = action;
        if (priceTextBox != null){
            priceTextBox.setText(holding.getPricePerShare() + "");
            this.price = holding.getPricePerShare();
        }else {
            this.price = price;
            this.shares = shares;
            equityPrice.setText(price * shares + "");
            updateCashAccounts();
        }
    }

    public void setEquity(EquityComponent e, int shares, double price, String action){
        equity = e;
        equityLabel.setText(equity.getName());
        this.action = action;
        if (priceTextBox != null){
            priceTextBox.setText(equity.getPricePerShare() + "");
            this.price = equity.getPricePerShare();
        }else {
            this.shares = shares;
            this.price = price;
            equityPrice.setText(price * shares + "");
            updateCashAccounts();
        }
    }

    private void updateCashAccounts(){
        ArrayList<CashAccount> list = new ArrayList<>();
        if (action.equals("BUY")) {
            double total = price * shares;
            list.addAll(FPTS.getCurrentUser().getMyPortfolio().getCashAccounts().stream().filter(c ->
                    c.getValue() >= total).collect(Collectors.toList()));
        } else {
            list.addAll(FPTS.getCurrentUser().getMyPortfolio().getCashAccounts());
        }
        CAtableView.setItems(FXCollections.observableArrayList(list));
    }

    @FXML
    public void handleProceedButtonPressed(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../gui/CASelectPage.fxml"));
        Parent root1 = fxmlLoader.load();
        PurchasePopUpController controller=fxmlLoader.getController();
        try {
            int shares = Integer.parseInt(numSharesBox.getText());
            if (holding != null && shares > holding.getNumOfShares()){
                throw new IllegalArgumentException("Shares more than held shares");
            }
            price = Double.parseDouble(priceTextBox.getText());
            if (fptsChoice.getValue().equals("Process Outside FPTS")){
                if (action.equals("BUY")) {
                    BuyHoldingAlgorithm bha = new BuyHoldingAlgorithm();
                    bha.establishContext();
                    bha.processOutsideFPTS(equity, shares, price);
                    Stage stg = FPTS.getSelf().getStage();
                    try {
                        Parent parent = (Parent) FXMLLoader.load(this.getClass().getResource("../gui/PortfolioPage.fxml"));
                        stg.setScene(new Scene(parent));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else {
                    SellHoldingAlgorithm sha = new SellHoldingAlgorithm(holding);
                    sha.establishContext();
                    sha.processOutsideFPTS(null, shares, price);
                    Stage stg = FPTS.getSelf().getStage();
                    try {
                        Parent parent = (Parent) FXMLLoader.load(this.getClass().getResource("../gui/PortfolioPage.fxml"));
                        stg.setScene(new Scene(parent));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                stage.close();
            } else {
                if(equity == null) {
                    controller.setEquity(holding, shares, price, action);
                } else {
                    controller.setEquity(equity, shares, price, action);
                }
                Stage app_stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                app_stage.setScene(new Scene(root1));
                app_stage.show();
            }
        } catch (NumberFormatException e) {
            errors.setText("Numbers ONLY!");
        } catch (IllegalArgumentException e) {
            assert holding != null;
            errors.setText("You can only sell as many shares as you have (" + holding.getNumOfShares() + ")");
        }

    }

    @FXML
    public void handleDoubleClickTableRow(MouseEvent event) {
        if (event.isPrimaryButtonDown() && event.getClickCount() == 2) {
            CashAccount account = CAtableView.getSelectionModel().getSelectedItem();
            if (action.equals("BUY")) {
                BuyHoldingAlgorithm bha = new BuyHoldingAlgorithm();
                bha.establishContext();
                bha.processInsideFPTS(equity, shares, account, price);
                Stage stg = FPTS.getSelf().getStage();
                try {
                    Parent parent = (Parent) FXMLLoader.load(this.getClass().getResource("../gui/PortfolioPage.fxml"));
                    stg.setScene(new Scene(parent));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                SellHoldingAlgorithm sha = new SellHoldingAlgorithm(holding);
                sha.establishContext();
                sha.processInsideFPTS(equity, shares, account, price);
                Stage stg = FPTS.getSelf().getStage();
                try {
                    Parent parent = (Parent) FXMLLoader.load(this.getClass().getResource("../gui/PortfolioPage.fxml"));
                    stg.setScene(new Scene(parent));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.close();
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        if (fptsChoice != null) {
            fptsChoice.setItems(FXCollections.observableArrayList(
                    "Process In FPTS", "Process Outside FPTS"
            ));
            fptsChoice.setValue("Process In FPTS");
        }
        if (CAtableView != null) {
            CAnameCol.setCellValueFactory(new PropertyValueFactory<CashAccount, String>("accountName"));
            amountCol.setCellValueFactory(new PropertyValueFactory<CashAccount, String>("value"));
            dateCol.setCellValueFactory(new PropertyValueFactory<CashAccount, String>("dateAdded"));
            ObservableList<CashAccount> CAdata = FXCollections.observableArrayList(
                    FPTS.getCurrentUser().getMyPortfolio().getCashAccounts());
            CAtableView.setItems(CAdata);
        }
    }
}
