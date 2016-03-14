package controller;

import gui.FPTS;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.*;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by Brockway on 3/12/16.
 */
public class SimulationController extends MenuController {
    @FXML
    private Label error;
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
        if (numSteps.getText().length() != 0 && priceAnnum.getText().length() != 0) {
            try {
                int numberOfSteps = Integer.parseInt(numSteps.getText());
                String curInterval = interval.getValue();
                Boolean hasSteps;
                hasSteps = steps;
                if (simulation.equals("NOGROWTH")) {
                    currentSimulator = new NoGrowthSimulator(numberOfSteps, curInterval, hasSteps);
                    System.out.println("NOGROWTH");
                } else {
                    if (priceAnnum.getText().length() != 0) {
                        String pricePerAnum = priceAnnum.getText();
                        try {
                            double pricePerYearAsDouble = Double.parseDouble(pricePerAnum);
                            if (pricePerYearAsDouble < 1.00 && pricePerYearAsDouble > 0) {
                                if (simulation.equals("BEAR")) {
                                    System.out.println("BEARSIM");
                                    currentSimulator = new BearSimulator(numberOfSteps, curInterval, hasSteps, pricePerYearAsDouble);
                                } else {
                                    System.out.println("BULLSIM");
                                    currentSimulator = new BullSimulator(numberOfSteps, curInterval, hasSteps, pricePerYearAsDouble);
                                }
                            } else {
                                error.setText("Please Enter a value between 0 and 1 for the Price per Annum.");
                            }
                        } catch (NumberFormatException x) {
                            error.setText("Invalid Format. Please enter a percent value for the number of steps.");
                        }
                    } else {
                        error.setText("Please enter a percent value for the Price Per Annum.");
                    }
                }
                FPTS.setCurrentSimulator(currentSimulator);
                if (hasSteps) {
                    FPTS.setSimulationValue(currentSimulator.simulate(1));
                } else {
                    FPTS.setSimulationValue(currentSimulator.simulate(numberOfSteps));
                }
                Parent parent = FXMLLoader.load(getClass().getResource("../gui/SimulationPage.fxml"));
                Scene scene = new Scene(parent);
                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                stage.setScene(scene);
                stage.show();
                System.out.println("Value returned = " + FPTS.getSimulationValue());
            } catch (NumberFormatException x) {
                error.setText("Invalid Format. Please enter an integer for the number of steps.");
            }
        } else if (numSteps.getText().length() != 0) {
            error.setText("Please enter a decimal value for the Price Per Annum.");
        } else {
            error.setText("Please enter an integer for the number of steps.");
        }
    }

    @FXML
    protected void handleStepButtonPressed(ActionEvent event) throws IOException {
        currentSimulator = FPTS.getCurrentSimulator();
        if (currentSimulator.getCurrentStep() < currentSimulator.getTotalSteps()) {
            FPTS.setSimulationValue(FPTS.getSimulationValue() + currentSimulator.simulate(1));

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("../gui/SimulationPage.fxml"))));
            stage.show();
        }
    }

    @FXML
    protected void handleResetToStartButtonPressed(ActionEvent event) throws IOException {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("../gui/SimulatePage.fxml"))));
        stage.show();
    }

    @FXML
    protected void handleResetToCurrentPricesButtonPressed(ActionEvent event) {
        //TODO Memento portfolio restoration
    }

    /**
     * Method used to initialize the choiceBox on the SimulatePage and the simulation value on the SimulationPage.
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        if (interval != null) {
            interval.setItems(FXCollections.observableArrayList(
                    "Day", "Month", "Year"
            ));
        }
        if (pValue != null) {
            pValue.setText("$" + FPTS.getSimulationValue());
            stepNumber.setText("" + FPTS.getCurrentSimulator().getCurrentStep());
            currentSimulator = FPTS.getCurrentSimulator();
            if (currentSimulator.getCurrentStep() >= currentSimulator.getTotalSteps()) {
                stepButton.setDisable(true);
            }
            double value = 0;
            for (Holding h : FPTS.getSelf().getPortfolio().getHoldings()) {
                value += h.getValue();
            }
            totalValue.setText("$" + (value + FPTS.getSimulationValue()));

        }

    }
}
