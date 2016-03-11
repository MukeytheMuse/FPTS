package controller;
/**
 * Created by nveil_000 on 3/10/2016.
 */

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import model.User;

public class FPTSController {
    @FXML private Label error;
    @FXML private PasswordField password;
    @FXML private TextField userid;


    @FXML
    protected void handleLoginButtonPressed(ActionEvent event) {
        if (userid.getText().length() != 0 && password.getText().length() != 0) {
                    /*label.setText(password1.getText() + " " + password2.getText() + ", "
                   + "thank you for your comment!");
                    */
            User u = new User(userid.getText(), password.getText());

            if (u.validateUser()) {
                //HOME PAGE
                error.setText("Logging in...");
            }
            else {
                password.clear();
                error.setText("Not a valid combination of login ID and password");
            }

        } else {
            error.setText("You have missing fields.");
        }
    }

    @FXML
    protected void handleClearButtonPressed(ActionEvent event) {
        userid.clear();
        password.clear();
    }

    @FXML
    protected void handleRegisterButtonPressed(ActionEvent event) {

    }
}


/*
//Setting an action for the Submit button
        submit.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {


        //Setting an action for the Clear button
        clear.setOnAction(new EventHandler<ActionEvent>() {

@Override
public void handle(ActionEvent e) {
        loginID.clear();
        password1.clear();
        label.setText(null);
        }
        });

        //Setting an action for the Register button
        register.setOnAction(new EventHandler<ActionEvent>() {

@Override
public void handle(ActionEvent e) {
        thestage.setScene(createRegisterPage().getScene());
        }
        });
 */