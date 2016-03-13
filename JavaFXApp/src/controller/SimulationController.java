package controller;

import gui.FPTS;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.*;

import java.awt.*;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by Brockway on 3/12/16.
 */
public class SimulationController extends MenuController {
    @FXML private Label error;
    @FXML private TextField numSteps;
    @FXML private ChoiceBox interval;
    @FXML private RadioButton stepYes;
    @FXML private RadioButton bearSim;
    @FXML private RadioButton bullSim;
    @FXML private TextField priceAnnum;
    private Simulator currentSimulator;
    @FXML private Label pValue;

    /**
     * Checks to make sure the number of steps entered is valid.
     * If the simulation is no growth then the simulation will be called,
     * but if the simulation is a bull or bear market simulation the user
     * will be asked to input a percentage for price increase or decrease
     * per year.
     *
     * @param event
     * @throws java.io.IOException
     */
    public void handleSimulateButtonPressed(ActionEvent event) throws IOException {
        if(numSteps.getText().length() != 0 && priceAnnum.getText().length() != 0){
            try{
                int numberOfSteps = Integer.parseInt(numSteps.getText());
                String curInterval = interval.getValue().toString();//MAKE SURE THE VALUES ARE STRINGS
                Boolean hasSteps;
                if(stepYes.isPressed()){
                    hasSteps = true;
                } else {
                    hasSteps = false;
                }
                if(bullSim.isPressed()){
                    if(priceAnnum.getText().length() != 0){
                        String pricePerAnum = priceAnnum.getText();
                        try {
                            double pricePerYearAsDouble = Double.parseDouble(pricePerAnum);
                            if(pricePerYearAsDouble < 1.00 && pricePerYearAsDouble >0){
                                currentSimulator = new BullSimulator(numberOfSteps, curInterval, hasSteps, pricePerYearAsDouble);
                            } else {
                                error.setText("Please Enter a value between 0 and 1 for the Price per Annum.");
                            }
                        } catch (NumberFormatException x){
                            error.setText("Invalid Format. Please enter a percent value for the number of steps.");
                        }
                    } else {
                        error.setText("Please enter a percent value for the Price Per Annum.");
                    }

                } else if(bearSim.isPressed()){
                    if(priceAnnum.getText().length() != 0){
                        String pricePerAnum = priceAnnum.getText();
                        try {
                            double pricePerYearAsDouble = Double.parseDouble(pricePerAnum);
                            if(pricePerYearAsDouble < 1.00 && pricePerYearAsDouble > 0){
                                currentSimulator = new BearSimulator(numberOfSteps, curInterval, hasSteps, pricePerYearAsDouble);
                            } else {
                                error.setText("Please Enter a value between 0 and 1 for the Price per Annum.");
                            }
                        } catch (NumberFormatException x){
                            error.setText("Invalid Format. Please enter a percent value for the number of steps.");
                        }
                    } else {
                        error.setText("Please enter a percent value for the Price Per Annum.");
                    }

                } else { //This is a No Growth Market Simulation
                    currentSimulator = new NoGrowthSimulator(numberOfSteps, curInterval, hasSteps);
                }

                FPTS.setCurrentSimulator(currentSimulator);
                FPTS.setSimulationValue(currentSimulator.simulate());

                Parent parent = FXMLLoader.load(getClass().getResource("../gui/SimulationPage.fxml"));
                Scene scene = new Scene(parent);
                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                stage.setScene(scene);
                stage.show();
                System.out.println("Value returned = " + FPTS.getSimulationValue());
            } catch(NumberFormatException x){
                error.setText("Invalid Format. Please enter an integer for the number of steps.");
            }
        } else if(numSteps.getText().length() != 0){
            error.setText("Please enter a decimal value for the Price Per Annum.");
        } else{
            error.setText("Please enter an integer for the number of steps.");
        }
    }


    public void displaySimulation(){

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        if (interval != null) {
            interval.setItems(FXCollections.observableArrayList( //TODO: CHECK THIS CALL**
                    "Day", "Month", "Year"
            ));
        }
        if (pValue != null){
            pValue.setText("$" + FPTS.getSimulationValue());
        }

    }
}
