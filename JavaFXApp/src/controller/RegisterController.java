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

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class RegisterController extends MenuController {
    @FXML private Label error;
    @FXML private PasswordField password;
    @FXML private PasswordField password1;
    @FXML private TextField userid;


    public void handleRegistrationButtonPressed(ActionEvent event) throws IOException {
        if (userid.getText().length() != 0 && password.getText().length() != 0){
            if (User.ValidLoginID(userid.getText())){
                if (password.getText().equals(password1.getText())){
                    User usr = new User(userid.getText(), password.getText());
                    addUser(usr);
                    Parent parent = FXMLLoader.load(getClass().getResource("../gui/Homepage.fxml"));
                    Scene scene = new Scene(parent);
                    Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                    stage.setScene(scene);
                    stage.show();

                } else {
                    error.setText("Password fields do not match. Try your password again.");
                    password.clear();
                    password1.clear();
                }
            } else {
                error.setText("That User ID is already in use, please pick another one.");
            }
        } else {
            error.setText("Please Enter both a UserID and a Password");
        }
    }

    @FXML
    protected void handleClearButtonPressed(ActionEvent event) {
        userid.clear();
        password.clear();
        password1.clear();
    }

    @FXML
    protected void handleBackButtonPressed(ActionEvent event) throws IOException{
        goToLoginPage(event);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //TODO
    }

    private void addUser(User usr){
        FileWriter fileWriter = null;
        BufferedWriter bufferedWriter = null;
        try {
            fileWriter = new FileWriter("src/DataBase/UserData.txt",true);
            bufferedWriter = new BufferedWriter(fileWriter);
            bufferedWriter.write(usr.getLoginID() + ",");
            bufferedWriter.write(usr.hash(password1.getText()));
            bufferedWriter.newLine();
            bufferedWriter.close();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
    }
}