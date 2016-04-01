package controller.CashAccountCtrl;

import gui.FPTS;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import model.PortfolioElements.CashAccount;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

/**
 * Implements view and control to create a new CashAccount for a current portfolio.
 *
 * @author Eric Epstein
 */
public class CashAccountCreator {

    /*
    * context data
    */
    private FPTS theFPTS;

    /**
     * Establishes context data and calls for scene construction
     *
     * @param theFPTS - FPTS
     */
    public CashAccountCreator(FPTS theFPTS) {
        this.theFPTS = theFPTS;
        theFPTS.getStage().setScene(getCashAccountCreatorScene());
    }

    /**
     * Constructs scene with specified fields and input controls.
     *
     * @return Scene
     */
    public Scene getCashAccountCreatorScene() {
        VBox split = new VBox();

        /*
        * Defines the search fields
        */
        HBox aField = new HBox();
        TextField nameInputField = new TextField();
        Label mainInput = new Label("Account name: ");
        aField.getChildren().addAll(mainInput, nameInputField);

        TextField amountInputField = new TextField();
        Label aLabel = new Label("Amount: ");
        aField.getChildren().addAll(aLabel, amountInputField);

        DatePicker dateField = new DatePicker(LocalDate.now());
        aLabel = new Label("Date: ");
        aField.getChildren().addAll(aLabel, dateField);

        Button submitBtn = new Button();
        submitBtn.setText("Submit");

        /*
        * Processes input
        */
        submitBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {

                boolean isValid = isValidAccountName(nameInputField) && isValidDouble(amountInputField);

                /*
                * Converts dateField to Date object
                */
                Date theDate = Date.from(dateField.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant());
                //TODO: check Warning:(80, 22) [UnusedDeclaration] Variable 'theDate' is never used


                //TODO: change theDate to type string then pass in instead of "" below for cashAccount constructor.
                if (isValid) {
                    CashAccount c = new CashAccount(nameInputField.getText(), Double.parseDouble(amountInputField.getText()), theDate.toString(), null);
                    theFPTS.getPortfolio().add(c);

                    /*
                    * Refers to confirmation scene
                    */
                    theFPTS.getStage().setScene(theFPTS.getConfirmationScene());
                } else {
                    nameInputField.setText("INVALID");
                }
            }
        });

        VBox inputArea = new VBox();
        inputArea.getChildren().addAll(aField, submitBtn);
        split.getChildren().addAll(theFPTS.getNav(), inputArea);
        return new Scene(split, theFPTS.getWidth(), theFPTS.getHeight());

    }

    /**
     * Helper logic function to validate account name input
     *
     * @param inputAccountName - TextField
     * @return boolean
     */
    private boolean isValidAccountName(TextField inputAccountName) {
        if (inputAccountName.getText() == null || inputAccountName.getText().equals("")) {
            return false;
        }

        String inputAccountString = inputAccountName.getText();

        for (CashAccount c : theFPTS.getPortfolio().getCashAccounts()) {
            if (c.getAccountName().equals(inputAccountString)) {
                return false;
            }
        }

        return true;
    }

    /**
     * Helper logic function to validate numerical input
     *
     * @param inputAmount - TextField
     * @return boolean
     */
    private boolean isValidDouble(TextField inputAmount) {

        /*
        * Determine whether the input amount is not empty
        */
        if (inputAmount.getText() == null || inputAmount.getText().equals("")) {
            return false;
        }

        String inputAmountString = inputAmount.getText();

        /*
        * Determine whether the input amount can be parsed to a double
        */
        try {
            Double.parseDouble(inputAmountString);
            //TODO: check Warning:(147, 20) Result of 'Double.parseDouble()' is ignored
        } catch (Exception e) {
            return false;
        }

        Double inputAmountDouble = Double.parseDouble(inputAmountString);

        /*
        * Return whether the input amount is greater than 0
        */
        return (inputAmountDouble >= 0);

    }

}

