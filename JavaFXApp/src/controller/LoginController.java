package controller;
/**
 * Created by nveil_000 on 3/10/2016.
 */
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.User;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class LoginController implements Initializable{
    @FXML private Label error;
    @FXML private PasswordField password;
    @FXML private TextField userid;

    @FXML
    protected void handleLoginButtonPressed(ActionEvent event) throws IOException{
        if (userid.getText().length() != 0 && password.getText().length() != 0) {
                    /*label.setText(password1.getText() + " " + password2.getText() + ", "
                   + "thank you for your comment!");
                    */
            User u = new User(userid.getText(), password.getText());

            if (u.validateUser()) {
                //HOME PAGE
                error.setText("Logging in...");
                Parent parent = FXMLLoader.load(getClass().getResource("../gui/HomePage.fxml"));
                Scene scene = new Scene(parent);
                Stage app_stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

                app_stage.setScene(scene);
                app_stage.show();
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
    protected void handleBackButtonPressed(ActionEvent event) {

    }

    @FXML
    protected void handleRegisterButtonPressed(ActionEvent event) throws IOException{
        Parent register_parent = FXMLLoader.load(getClass().getResource("../gui/RegisterPage.fxml"));
        Scene register_scene = new Scene(register_parent);
        Stage app_stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

        app_stage.setScene(register_scene);
        app_stage.show();
    }

    @FXML
    protected void handleRegistrationButtonPressed(ActionEvent event){

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //TODO
    }
}