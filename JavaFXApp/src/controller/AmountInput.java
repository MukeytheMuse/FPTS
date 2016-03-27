package controller;

import gui.FPTS;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.util.ArrayList;
import java.util.Observable;

/**
 * Defines view and controller to obtain any number of numerical inputs, validates
 * such inputs, and notifies observers.
 *
 * @author Eric Epstein
 */
public class AmountInput extends Observable {

    /*
    * ArrayList of amounts, often just one element
    */
    ArrayList<Double> amounts;

    /*
    * context data
    */
    private FPTS theFPTS;

    /**
     * Stores context data in construction
     *
     * @param theFPTS - FPTS
     * @param amounts - ArrayList<Double> - often empty
     */
    public AmountInput(FPTS theFPTS, ArrayList<Double> amounts) {

        this.theFPTS = theFPTS;
        this.amounts = amounts;

        theFPTS.getStage().setScene(getAmountInputScene());
    }

    /**
     * Constructs Scene to process input
     *
     * @return Scene
     */
    public Scene getAmountInputScene() {
        VBox split = new VBox();

        HBox aField = new HBox();
        TextField inputAmount = new TextField();
        Label aLabel = new Label("Input amount: ");
        aField.getChildren().addAll(aLabel, inputAmount);

        ArrayList<TextField> inputAmounts = new ArrayList<TextField>();
        inputAmounts.add(inputAmount);

        Button submitBtn = new Button();
        submitBtn.setText("Submit");

        /*
        * Defines action event when the user presses "Submit"
        */
        submitBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                boolean isValid = true;

                /**
                 * For each input amount from the user, all must be true
                 */
                for (TextField inputAmount : inputAmounts) {
                    isValid = isValid && isValid(inputAmount);
                }

                /*
                * If each input is a valid numerical value, add to ArrayList of
                * amounts.
                */
                if (isValid) {
                    for (TextField inputAmount : inputAmounts) {
                        double anAmount = Double.parseDouble(inputAmount.getText());
                        amounts.add(anAmount);
                    }
                    setChanged();
                    notifyObservers();
                /*
                * Else, do not proceed and inform user of invalid input.
                */
                } else {
                    inputAmount.setText("INVALID");
                }

            }
        });

        VBox inputArea = new VBox();
        inputArea.getChildren().addAll(aField, submitBtn);
        split.getChildren().addAll(theFPTS.getNav(), inputArea);
        return new Scene(split, theFPTS.getWidth(), theFPTS.getHeight());

    }

    /**
     * Validates user input
     *
     * @param inputAmount
     * @return boolean
     */
    public boolean isValid(TextField inputAmount) {
        if (inputAmount.getText() == null || inputAmount.getText().equals("")) {
            return false;
        }

        String inputAmountString = inputAmount.getText();

        try {
            Double.parseDouble(inputAmountString);
        } catch (Exception e) {
            return false;
        }

        Double inputAmountDouble = Double.parseDouble(inputAmountString);

        if (inputAmountDouble < 0) {
            return false;
        }

        return true;

    }

}

