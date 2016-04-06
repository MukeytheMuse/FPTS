package controller;

import gui.FPTS;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import model.Equities.EquityComponent;
import model.PortfolioElements.Holding;
import model.PortfolioElements.Portfolio;
import model.SearchThread;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.ResourceBundle;
import java.util.Set;

/**
 * Created by Luke Veilleux
 */
public class SaleController extends MenuController {

    @FXML
    private TableView<Holding> tableView;
    @FXML
    private TableColumn<Holding, String> tickerCol, nameCol, sharesCol, priceCol, valueCol;
    @FXML
    private ChoiceBox<String> fieldDropDown, searchType;
    @FXML
    private TextField searchText;

    private static Set<EquityComponent> matchList = new HashSet<EquityComponent>();
    private String typeSearch = "Any";
    private String fieldSearch = "All";
    private ArrayList<Holding> holdings;

    @FXML
    protected void handleSearchTextEntered() {
        fieldSearch = fieldDropDown.getValue();
        typeSearch = searchType.getValue();
        matchList.clear();
        search();
    }

    private void search() {
        ArrayList<SearchThread> threads = new ArrayList<>();
        switch(fieldSearch){
            case "All":
                threads.add(new SearchThread(searchText.getText(), "Ticker", typeSearch, holdings));
                threads.add(new SearchThread(searchText.getText(), "Name", typeSearch, holdings));
                break;
            case "Ticker":
                threads.add(new SearchThread(searchText.getText(), "Ticker", typeSearch, holdings));
                break;
            case "Name":
                threads.add(new SearchThread(searchText.getText(), "Name", typeSearch, holdings));
                break;
            case "Index":
                threads.add(new SearchThread(searchText.getText(), "Index", typeSearch, holdings));
                break;
            case "Sector":
                threads.add(new SearchThread(searchText.getText(), "Sector", typeSearch, holdings));
                break;
        }
        for(SearchThread thread : threads)
            thread.run();
        for(SearchThread thead : threads) {
            try {
                thead.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    @FXML
    public void handleDoubleClickTableRow(MouseEvent event) throws IOException{
        if (event.isPrimaryButtonDown() && event.getClickCount() == 2) {
            Holding holding = tableView.getSelectionModel().getSelectedItem();
            try {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../gui/PurchasePopUp.fxml"));
                Parent root1 = fxmlLoader.load();
                PurchasePopUpController controller=fxmlLoader.getController();
                controller.setEquity(holding, 0, holding.getPricePerShare(), "SALE");
                Stage stage = new Stage();
                stage.setScene(new Scene(root1));
                stage.show();
            } catch(Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        fieldDropDown.setItems(FXCollections.observableArrayList(
                "All", "Ticker", "Name", "Index", "Sector"
        ));
        fieldDropDown.setValue("All");
        searchType.setItems(FXCollections.observableArrayList(
                "Any", "Contains", "Starts with", "Exactly Matches"
        ));
        searchType.setValue("Any");

        Portfolio p = FPTS.getCurrentUser().getMyPortfolio();
        tickerCol.setCellValueFactory(new PropertyValueFactory<Holding, String>("tickerSymbol"));
        nameCol.setCellValueFactory(new PropertyValueFactory<Holding, String>("name"));
        sharesCol.setCellValueFactory(new PropertyValueFactory<Holding, String>("numOfShares"));
        priceCol.setCellValueFactory(new PropertyValueFactory<Holding, String>("pricePerShare"));
        valueCol.setCellValueFactory(new PropertyValueFactory<Holding, String>("totalValue"));

        ObservableList<Holding> data = FXCollections.observableArrayList(p.getHoldings());
        tableView.setItems(data);

        holdings = FPTS.getSelf().getPortfolio().getHoldings();
    }

}
