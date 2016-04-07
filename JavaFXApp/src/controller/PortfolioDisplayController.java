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
import model.PortfolioElements.Portfolio;

import java.net.URL;
import java.util.ResourceBundle;

public class PortfolioDisplayController extends MenuController {


    @FXML
    private TableView<Holding> tableView;
    @FXML
    private TableView<CashAccount> CAtableView;

    @FXML
    private TableColumn<Holding, String> tickerCol, nameCol, sharesCol, priceCol, valueCol;
    @FXML
    private TableColumn<CashAccount, String> CAnameCol, amountCol, dateCol;


    /**
     * @param location
     * @param resources
     */
    public void initialize(URL location, ResourceBundle resources) {
        Portfolio p = FPTS.getCurrentUser().getMyPortfolio();
        tickerCol.setCellValueFactory(new PropertyValueFactory<Holding, String>("tickerSymbol"));
        nameCol.setCellValueFactory(new PropertyValueFactory<Holding, String>("name"));
        sharesCol.setCellValueFactory(new PropertyValueFactory<Holding, String>("numOfShares"));
        priceCol.setCellValueFactory(new PropertyValueFactory<Holding, String>("pricePerShare"));
        valueCol.setCellValueFactory(new PropertyValueFactory<Holding, String>("totalValue"));

        ObservableList<Holding> data = FXCollections.observableArrayList(p.getHoldings());
        tableView.setItems(data);

        CAnameCol.setCellValueFactory(new PropertyValueFactory<CashAccount, String>("accountName"));
        amountCol.setCellValueFactory(new PropertyValueFactory<CashAccount, String>("value"));
        dateCol.setCellValueFactory(new PropertyValueFactory<CashAccount, String>("dateAdded"));

        ObservableList<CashAccount> CAdata = FXCollections.observableArrayList(p.getCashAccounts());
        CAtableView.setItems(CAdata);

    }


}



