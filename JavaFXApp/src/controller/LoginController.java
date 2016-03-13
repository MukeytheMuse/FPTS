package controller;
/**
 * Created by nveil_000 on 3/10/2016.
 */
import gui.FPTS;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.User;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * This class controls the actions from button presses on the Login and Register page of this application.
 * Extends LoginMenuController because that class holds the code to handle events based on clicking items in the Menu.
 */
public class LoginController extends LoginMenuController {
    /**
     * Private variables representing the input of the user ID and password on both login and registration as well
     * as a label that is used to output error messages to the user.
     */
    @FXML private Label error;
    @FXML private PasswordField password;
    @FXML private TextField userid;
    @FXML private PasswordField password1;
    FPTS fpts = FPTS.getSelf();

    /**
     * Controls the program when the Login button is pressed on the Login page of the application. Validates the user
     * entered correct credentials, and if so logs them in, otherwise one of a few different errors will appear.
     * @param event - ActionEvent - event that caused this method to be called.
     * @throws IOException - Exception thrown if the HomePage.fxml is not found where it should be.
     */
    @FXML
    protected void handleLoginButtonPressed(ActionEvent event) throws IOException{
        if (userid.getText().length() != 0 && password.getText().length() != 0) {
            User u = new User(userid.getText(), password.getText());
            System.out.println("Before if");
            if (u.validateUser()) {
                System.out.println("Lalal");
                fpts.setCurrentUser(u);
                error.setText("Logging in...");

                Parent parent = FXMLLoader.load(getClass().getResource("../gui/HomePage.fxml"));
                Scene scene = new Scene(parent);
                Stage app_stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

                app_stage.setScene(scene);
                app_stage.show();
            } else {
                password.clear();
                error.setText("Not a valid combination of login ID and password");
            }
        } else {
            error.setText("You have missing fields.");
        }
    }

    /**
     * Controls the programs actions if the register button is pressed on the Registration page.
     * The user id is checked to ensure it is not already in use in the system, and the registers a new user.
     * A few different error messages are displayed based on different criteria not being met.
     * @param event - ActionEvent - event that caused this method to be called.
     * @throws IOException - Exception thrown if the HomePage.fxml is not found where it should be.
     */
    @FXML
    public void handleRegistrationButtonPressed(ActionEvent event) throws IOException {
        if (userid.getText().length() != 0 && password.getText().length() != 0){
            if (User.ValidLoginID(userid.getText())){
                if (password.getText().equals(password1.getText())){
                    User usr = new User(userid.getText(), password.getText());
                    fpts.setCurrentUser(usr);
                    addUser(usr);
                    Parent parent = FXMLLoader.load(getClass().getResource("../gui/HomePage.fxml"));
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

    /**
     * Controls the program if the clear button is pressed on either of the Login or Registration pages.
     * Clears the text entered in the userid and password fields.
     * @param event - ActionEvent - event that caused this method to be called.
     */
    @FXML
    protected void handleClearButtonPressed(ActionEvent event) {
        userid.clear();
        password.clear();
        if (password1 != null) password1.clear();
    }

    /**
     * Controls the program when the back button is clicked on the Registration page.
     * @param event - ActionEvent - event that caused this method to be called.
     * @throws IOException - Exception thrown if the LoginPage.fxml is not found where the program expects.
     */
    @FXML
    protected void handleBackButtonPressed(ActionEvent event) throws IOException {
        goToLoginPage(event);
    }

    /**
     * Controls the program when the register button is clicked on the Login page.
     * @param event - ActionEvent - event that caused this method to be called.
     * @throws IOException - Exception thrown if the RegisterPage.fxml is not found where the program expects.
     */
    @FXML
    protected void handleRegisterButtonPressed(ActionEvent event) throws IOException {
        Parent register_parent = FXMLLoader.load(getClass().getResource("../gui/RegisterPage.fxml"));
        Scene register_scene = new Scene(register_parent);
        Stage app_stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

        app_stage.setScene(register_scene);
        app_stage.show();
    }

    @FXML
    protected void handleSaveExitButtonPressed(ActionEvent event) throws IOException {
        Stage stg = FPTS.getSelf().getStage();
        // TODO Save portfolio.
        stg.setScene(FPTS.getSelf().createLogInScene());
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }

    @FXML
    protected void handleExitExitButtonPressed(ActionEvent event) throws IOException {
        Stage stg = FPTS.getSelf().getStage();
        stg.setScene(FPTS.getSelf().createLogInScene());
        stg.show();
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //TODO
    }

    /**
     * Adds a new user into the UserData.csv file for use in later uses of this application.
     * @param usr - User - New user object to be added into the text file.
     */
    private void addUser(User usr){
        FileWriter fileWriter = null;
        BufferedWriter bufferedWriter = null;
        try {
            fileWriter = new FileWriter(new File("JavaFXApp/src/model/Database/UserData.csv").getAbsolutePath(),true);
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