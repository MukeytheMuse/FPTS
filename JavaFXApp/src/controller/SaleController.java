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
import model.PortfolioElements.Holding;
import model.PortfolioElements.HoldingUpdatable;
import model.PortfolioElements.Portfolio;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.ResourceBundle;
import java.util.Set;

/**
 * Search controller used to display all the Holding in the user's account for selling of those holdings. Handles the
 * SellHoldingPage.fxml page
 * Created by Luke Veilleux
 */
public class SaleController extends MenuController {

    /**
     * ID tages for the table and the table columes as well as search criteria shown on the page.
     */
    @FXML
    private TableView<Holding> tableView;
    @FXML
    private TableColumn<Holding, String> tickerCol, nameCol, sharesCol, priceCol, valueCol;
    @FXML
    private ChoiceBox<String> fieldDropDown, searchType;
    @FXML
    private TextField searchText;

    private static Set<Holding> matchList = new HashSet<Holding>();
    private String typeSearch = "Any";
    private String fieldSearch = "All";
    private ArrayList<Holding> holdings;

    /**
     * Method used to get the values of the current search criteria, and calls to update the displayed holdings
     * to only show those that conform to this criteria.
     */
    @FXML
    protected void handleSearchTextEntered() {
        fieldSearch = fieldDropDown.getValue();
        typeSearch = searchType.getValue();
        matchList.clear();
        search();
    }

    /**
     * private function used to initialize threads to search through the Holding lists for those that fit the criteria.
     */
    private void search() {
        ArrayList<SearchThread> threads = new ArrayList<>();
        switch (fieldSearch) {
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
        threads.forEach(SearchThread::run);
        for(SearchThread thead : threads) {
            try {
                thead.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        update();
    }

    /**
     * private method that updates the table on the FXML page to update and display all the matches found in matchList.
     */
    private void update(){
        tableView.setItems(FXCollections.observableArrayList(matchList));
    }

    /**
     * Adds a list of HoldingUpdatable objects, that should all be holdings to the matchList for display in the table.
     * @param lst - ArrayList<HoldingUpdatable> - list of HoldingUpdatable Objects to add to matchList.
     */
    public static void addHoldingsToList(ArrayList<HoldingUpdatable> lst){
        for (HoldingUpdatable holding : lst){
            boolean flag = false;
            for (Holding h : matchList){
                if (h.equals((Holding) holding))
                    flag = true;
            }
            if (!flag)
                matchList.add((Holding) holding);
        }
    }

    /**
     * Handles double clicking on a table row to signify the user wants to sell shares in that Holding.
     * Loads the PurchasePopUp page to complete this transaction.
     * @param event - ActionEvent - Event that caused this method call
     * @throws IOException - Throws IOException if the PurchasePopUp page is not found.
     */
    @FXML
    public void handleDoubleClickTableRow(MouseEvent event) throws IOException {
        if (event.isPrimaryButtonDown() && event.getClickCount() == 2) {
            Holding holding = tableView.getSelectionModel().getSelectedItem();
            try {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/PurchasePopUp.fxml"));
                Parent root1 = fxmlLoader.load();
                PurchasePopUpController controller = fxmlLoader.getController();
                controller.setEquity(holding, 0, holding.getPricePerShare(), "SALE");
                Stage stage = new Stage();
                stage.setScene(new Scene(root1));
                stage.show();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Initializes the fields of the drop down menus and the table shown on the page.
     * @param location
     * @param resources
     */
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
        holdings = p.getHoldings();
        ObservableList<Holding> data = FXCollections.observableArrayList(holdings);
        tableView.setItems(data);
    }

}
