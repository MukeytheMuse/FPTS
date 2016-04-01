package controller;

import gui.FPTS;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import model.PortfolioElements.CashAccount;
import model.PortfolioElements.Holding;
import model.Portfolio;

import java.net.URL;
import java.util.ResourceBundle;

public class PortfolioDisplayController extends MenuController {


    @FXML
    private TableView<Holding> tableView;
    @FXML TableView<CashAccount> CAtableView;

    @FXML
    private TableColumn<Holding, String> tickerCol;
    @FXML
    private TableColumn<Holding, String> nameCol;
    @FXML
    private TableColumn<Holding, String> sharesCol;
    @FXML
    private TableColumn<Holding, String> priceCol;
    @FXML
    private TableColumn<Holding, String> valueCol;
    @FXML
    private TableColumn<CashAccount, String> CAnameCol;
    @FXML
    private TableColumn<CashAccount, String> amountCol;
    @FXML
    private TableColumn<CashAccount, String> dateCol;

    /**
     *
     * @param location
     * @param resources
     */
    public void initialize(URL location, ResourceBundle resources) {
        Portfolio p = FPTS.getCurrentUser().getMyPortfolio();
        tickerCol.setCellValueFactory(new PropertyValueFactory<Holding, String>("tickerSymbol"));
        nameCol.setCellValueFactory(new PropertyValueFactory<Holding, String>("holdingName"));
        sharesCol.setCellValueFactory(new PropertyValueFactory<Holding, String>("numOfShares"));
        priceCol.setCellValueFactory(new PropertyValueFactory<Holding, String>("valuePerShare"));
        valueCol.setCellValueFactory(new PropertyValueFactory<Holding, String>("currentValue"));

        ObservableList<Holding> data = FXCollections.observableArrayList(p.getHoldings());
        tableView.setItems(data);

        CAnameCol.setCellValueFactory(new PropertyValueFactory<CashAccount, String>("accountName"));
        amountCol.setCellValueFactory(new PropertyValueFactory<CashAccount, String>("currentValue"));
        dateCol.setCellValueFactory(new PropertyValueFactory<CashAccount, String>("dateAdded"));

        ObservableList<CashAccount> CAdata = FXCollections.observableArrayList(p.getCashAccounts());
        CAtableView.setItems(CAdata);

    }



}

