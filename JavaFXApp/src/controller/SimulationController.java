package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.PortfolioElements.Holding;
import model.Simulators.*;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

/**
 * authors: Kaitlin Brockway & Luke
 */
public class SimulationController extends MenuController {
    @FXML
    private Label error;
    @FXML
    private XYChart<Integer, Double> graph;
    @FXML
    private TextField numSteps;
    @FXML
    private ChoiceBox<String> interval;
    @FXML
    private Button stepButton;
    @FXML
    private TextField priceAnnum;
    private Simulator currentSimulator;
    @FXML
    private Label pValue;
    @FXML
    private Label totalValue;
    @FXML
    private Label stepNumber;
    private String simulation = "NOGROWTH";
    private boolean steps = false;
    private ArrayList<Holding> holdings;

    @FXML
    protected void handleBearSimulateRadioButtonPressed(ActionEvent event) {
        simulation = "BEAR";
    }

    @FXML
    protected void handleStepYesRadioButtonPressed(ActionEvent event) {
        steps = true;
    }

    @FXML
    protected void handleStepNoRadioButtonPressed(ActionEvent event) {
        steps = false;
    }

    @FXML
    protected void handleBullSimulateRadioButtonPressed(ActionEvent event) {
        simulation = "BULL";
    }


    @FXML
    protected void handleNoSimulateRadioButtonPressed(ActionEvent event) {
        simulation = "NOGROWTH";
    }


    protected void setHoldings(ArrayList<Holding> holdings) {
        this.holdings = holdings;
    }

    protected void setSimulator(Simulator simulator) {
        this.currentSimulator = simulator;
        if (pValue != null) {
            pValue.setText("$" + currentSimulator.getChangeInValue());
            stepNumber.setText("" + currentSimulator.getCurrentStep());
            if (currentSimulator.getCurrentStep() >= currentSimulator.getTotalSteps()) {
                stepButton.setDisable(true);
            }
            totalValue.setText("$" + currentSimulator.getCurrentValue());

        }
    }

    private void storeMemento() {
        Memento memento = new Memento(holdings);
        memento.storeMemento();
    }

    private void resetToMemento() {

    }

    /**
     * Checks to make sure the number of steps entered is valid.
     * If the simulation is no growth then the simulation will be called,
     * but if the simulation is a bull or bear market simulation the user
     * will be asked to input a percentage for price increase or decrease
     * per year.
     *
     * @param event - ActionEvent - The event that is created when Simulate button is pressed.
     * @throws java.io.IOException - Exception thrown if the SimulationPage.fxml is not found.
     */
    public void handleSimulateButtonPressed(ActionEvent event) throws IOException {
        storeMemento();
        if (numSteps.getText().length() != 0 && priceAnnum.getText().length() != 0) {
            try {
                int numberOfSteps = Integer.parseInt(numSteps.getText());
                String curInterval = interval.getValue();
                Boolean hasSteps;
                hasSteps = steps;
                if (simulation.equals("NOGROWTH")) {
                    currentSimulator = new NoGrowthSimulator(numberOfSteps, holdings);
                } else {
                    if (priceAnnum.getText().length() != 0) {
                        String pricePerAnum = priceAnnum.getText();
                        try {
                            double pricePerYearAsDouble = Double.parseDouble(pricePerAnum);
                            if (pricePerYearAsDouble < 1.00 && pricePerYearAsDouble > 0) {
                                if (simulation.equals("BEAR")) {
                                    currentSimulator = new BearSimulator(numberOfSteps, curInterval, pricePerYearAsDouble, holdings);
                                } else {
                                    currentSimulator = new BullSimulator(numberOfSteps, curInterval, pricePerYearAsDouble, holdings);
                                }
                            }
                        } catch (NumberFormatException x) {
                            error.setText("Invalid Format. Please enter a percent value for the number of steps.");
                        }
                    } else {
                        error.setText("Please enter a percent value for the Price Per Annum.");
                    }
                }
                try {
                    if (hasSteps) {
                        currentSimulator.simulate(1);
                    } else {
                        currentSimulator.simulate(numberOfSteps);
                    }
                    try {
                        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/SimulationPage.fxml"));
                        Parent root1 = fxmlLoader.load();
                        SimulationController controller = fxmlLoader.getController();
                        controller.setSimulator(currentSimulator);
                        controller.setHoldings(holdings);
                        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                        stage.setScene(new Scene(root1));
                        stage.show();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } catch (NullPointerException n) {
                    error.setText("Please Enter a value between 0 and 1 for the Price per Annum.");
                }
            } catch (NumberFormatException x) {
                error.setText("Invalid Format. Please enter an integer for the number of steps.");
            }
        } else if (numSteps.getText().length() != 0) {
            error.setText("Please enter a decimal value for the Price Per Annum.");
        } else {
            error.setText("Please enter an integer for the number of steps.");
        }
    }

    /**
     * @param event - Event that caused this class to be called.
     * @throws IOException - Throws IOException if the SimulatorPage is not found.
     */
    @FXML
    protected void handleStepButtonPressed(ActionEvent event) throws IOException {
        if (currentSimulator.getCurrentStep() < currentSimulator.getTotalSteps()) {
            currentSimulator.simulate(1);
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/SimulationPage.fxml"));
            Parent root1 = fxmlLoader.load();
            SimulationController controller = fxmlLoader.getController();
            controller.setSimulator(currentSimulator);
            controller.setHoldings(holdings);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root1));
            stage.show();
        }
    }

    /**
     * Handles starting a new simulation without resetting the Holdings.
     * @param event - Event that caused this class to be called.
     * @throws IOException - Throws IOException if the SimulatePage is not found.
     */
    @FXML
    protected void handleResetToStartButtonPressed(ActionEvent event) throws IOException {
        ArrayList<Holding> lst = Memento.getMemento().getHoldings();
        for(Holding holding : holdings) {
            for (Holding stored : lst) {
                if (holding.equals(stored)) {
                    holding.setHoldingValue(stored.getPricePerShare());
                    break;
                }
            }
        }
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/SimulatePage.fxml"))));
        stage.show();
    }

    /**
     * Restores Holdings back to the start of the original simulation.
     * @param event authors: Kaitlin
     */
    @FXML
    protected void handleResetToCurrentPricesButtonPressed(ActionEvent event) throws IOException {
        ArrayList<Holding> lst = Memento.getMemento().getHoldings();
        for(Holding holding : holdings) {
            for (Holding stored : lst) {
                if (holding.equals(stored)) {
                    holding.setHoldingValue(stored.getPricePerShare());
                    break;
                }
            }
        }
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/SimulatePage.fxml"));
        Parent root1 = fxmlLoader.load();
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root1));
        stage.show();
    }

    /**
     * Method used to initialize the choiceBox on the SimulatePage.
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        if (interval != null) {
            interval.setItems(FXCollections.observableArrayList(
                    "Day", "Month", "Year"
            ));
            priceAnnum.setText("0.0");
        }
        /*
        if (graph != null){
            ArrayList<Double> data = Simulator.series;
            XYChart.Series series = new XYChart.Series();
            for (int i = 0; i < data.size(); i++) {
                series.getData().add(new XYChart.Data(i, data.get(i)));
            }
            graph.getData().add(series);
        }
        */
    }
}

