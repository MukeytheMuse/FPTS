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
import model.PortfolioElements.HoldingUpdatable;
import model.PortfolioElements.Portfolio;
import model.SearchThread;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.ResourceBundle;
import java.util.Set;

/**
 * Class that displays all of the equities in the system with their ticker symbol, the name of the company, and the
 * current price per share of that company. Allows for users to double click a holding to purchase one.
 * The table is dynamically filtered with each character entered into the searchbox.
 * Created by Luke Veilleux
 */
public class SearchController extends MenuController {

    /**
     * ID tags to elements found in the FXML page itself. This includes the table and the columns of the table,
     * the search text, and the search criteria.
     */
    @FXML
    private TableView<EquityComponent> tableView;
    @FXML
    private TableColumn<EquityComponent, String> tickerCol, nameCol, priceCol;
    @FXML
    private ChoiceBox<String> fieldDropDown, searchType;
    @FXML
    private TextField searchText;

    private static Set<EquityComponent> matchList = new HashSet<EquityComponent>();
    private String typeSearch = "Any";
    private String fieldSearch = "All";

    /**
     * Method called when ever a keypress is entered into the search Textbox on the page.
     * Gets the current values that are to be searched for, and calls to search and update the shown list.
     */
    @FXML
    protected void handleSearchTextEntered() {
        fieldSearch = fieldDropDown.getValue();
        typeSearch = searchType.getValue();
        matchList.clear();
        search();
        update();
    }

    /**
     * Method used to start separate threads to search through the equities in the system. Runs all of the threads
     * and waits for them to finish.
     */
    private void search() {
        ArrayList<SearchThread> threads = new ArrayList<>();
        switch (fieldSearch) {
            case "All":
                threads.add(new SearchThread(searchText.getText(), "Ticker", typeSearch, null));
                threads.add(new SearchThread(searchText.getText(), "Name", typeSearch, null));
                break;
            case "Ticker":
                threads.add(new SearchThread(searchText.getText(), "Ticker", typeSearch, null));
                break;
            case "Name":
                threads.add(new SearchThread(searchText.getText(), "Name", typeSearch, null));
                break;
            case "Index":
                threads.add(new SearchThread(searchText.getText(), "Index", typeSearch, null));
                break;
            case "Sector":
                threads.add(new SearchThread(searchText.getText(), "Sector", typeSearch, null));
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
    }

    /**
     * Method used to update the table with the data stored in matchList. Shows the new list of equities in the table.
     */
    private void update() {
        ObservableList<EquityComponent> data = FXCollections.observableArrayList(matchList);
        tableView.setItems(data);
    }

    /**
     * Method used by threads to add equities that were found to match serch criteria back into matchList.
     * Checks for duplicates already inside of matchList.
     * @param lst - ArrayList<HoldingUpdateable> - List of Equities to add to the table.
     */
    public static void addEquitiesToList(ArrayList<HoldingUpdatable> lst){
        for (HoldingUpdatable eqt : lst){
            boolean flag = false;
            for (EquityComponent e : matchList) {
                if (e.equals((EquityComponent) eqt))
                    flag = true;
            }
            if (!flag)
                matchList.add((EquityComponent) eqt);
        }
    }

    /**
     * Method used to handle when a row in the table is double clicked, signifying that the user wants to purchase an
     * share in that equity. Opens the PurchasePopUp page to continue this transaction.
     * @param event - ActionEvent - event that caused this function to be called.
     * @throws IOException - Throws IOException if the PurchasePopUp page is not found.
     */
    @FXML
    public void handleDoubleClickTableRow(MouseEvent event) throws IOException {
        if (event.isPrimaryButtonDown() && event.getClickCount() == 2) {
            EquityComponent equity = tableView.getSelectionModel().getSelectedItem();
            try {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/PurchasePopUp.fxml"));
                Parent root1 = fxmlLoader.load();
                PurchasePopUpController controller = fxmlLoader.getController();
                controller.setEquity(equity, 0, equity.getPricePerShare(), "BUY");
                Stage stage = new Stage();
                stage.setScene(new Scene(root1));
                stage.show();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Initializes the table and menu drop downs to be filled with appropriate information. Table starts filled with
     * all equities in the system.
     * @param location - Unused parameter
     * @param resources - Unused parameter
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
        tickerCol.setCellValueFactory(new PropertyValueFactory<EquityComponent, String>("tickerSymbol"));
        nameCol.setCellValueFactory(new PropertyValueFactory<EquityComponent, String>("name"));
        priceCol.setCellValueFactory(new PropertyValueFactory<EquityComponent, String>("pricePerShare"));

        ObservableList<EquityComponent> data = FXCollections.observableArrayList(p.getEquityComponents());
        tableView.setItems(data);
    }

}
