package controller;

import gui.FPTS;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Holding;
import model.Portfolio;

import java.net.URL;
import java.util.ResourceBundle;

public class PortfolioDisplayController extends MenuController {


    @FXML
    private TableView<Holding> tableView;

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

    }



}

