package controller;

import gui.FPTS;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.MenuBar;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.Holding;
import model.Portfolio;
import model.Transaction;
import model.User;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class LoginController {
    @FXML
    private Label error;
    @FXML
    private PasswordField password;
    @FXML
    private TextField userid;
    @FXML
    private PasswordField password1;

    private User currentUser;

    private boolean importHoldingsRequested = false;
    private boolean importTransactionsRequested = false;

    @FXML
    MenuBar myMenuBar;

    public void handleLogoutMenuItemPressed(ActionEvent event) throws IOException {
        this.goToLoginPage(event);
    }

    public void handleExitMenuItemPressed(ActionEvent event) {
        Platform.exit();
        System.exit(0);
    }

    public void handleAboutMenuItemPressed(ActionEvent event) {
        //TODO Add an about page
    }

    protected void goToLoginPage(ActionEvent event) throws IOException {
        Stage stage;
        try {
            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        } catch (ClassCastException var4) {
            stage = (Stage) this.myMenuBar.getScene().getWindow();
        }

        Scene scene = new Scene((Parent) FXMLLoader.load(this.getClass().getResource("../gui/LoginPage.fxml")));
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Validates the users loginID and password combination and then gets the User object from
     * the allUsers map in the User class that was populated when the system started up.
     * <p>
     * Sets the currentUser to the User object obtained from the user map.
     *
     * @param event
     * @throws IOException Author(s): Kaitlin Brockway
     */
    @FXML
    protected void handleLoginButtonPressed(ActionEvent event) throws IOException {
        if (userid.getText().length() != 0 && password.getText().length() != 0) {
            if (User.validateUser(this.userid.getText(), this.password.getText())) {
                this.error.setText("Logging in...");
                User sub_user = new User(userid.getText());
                currentUser = sub_user.getAllUsersMap().get(userid.getText());
                FPTS.setCurrentUser(currentUser);
                Scene scene = new Scene((Parent) FXMLLoader.load(this.getClass().getResource("../gui/HomePage.fxml")));
                Stage app_stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                app_stage.setScene(scene);
                app_stage.show();
            } else {
                this.password.clear();
                this.error.setText("Not a valid combination of login ID and password");
            }
        } else {
            this.error.setText("You have missing fields.");
        }
    }

    /**
     * Called when a user clicks "Register" on the LoginPage.
     * The user is redirected to the RegisterPage.
     *
     * @param event
     * @throws IOException
     */
    @FXML
    protected void handleRegisterButtonPressed(ActionEvent event) throws IOException {
        Parent register_parent = (Parent) FXMLLoader.load(this.getClass().getResource("../gui/RegisterPage.fxml"));
        Scene register_scene = new Scene(register_parent);
        Stage app_stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        app_stage.setScene(register_scene);
        app_stage.show();
    }

    /**
     * Called when a user clicks "Register" on the RegisterPage.
     *
     * @param event
     * @throws IOException
     */
    @FXML
    public void handleRegistrationButtonPressed(ActionEvent event) throws IOException {
        if (this.userid.getText().length() != 0 && this.password.getText().length() != 0) {
            if (User.ValidLoginID(this.userid.getText())) {
                if (this.password.getText().equals(this.password1.getText())) {
                    //At this point, now that we know the username is valid and
                    // the passwords match ask the user if they would like to import
                    // holdings &OR transactions to initialize the new users portfolio.

                    ArrayList<Holding> userHoldingsToImport;
                    ArrayList<Transaction> userTransactionsToImport;
                    if (importTransactionsRequested && importHoldingsRequested) {
                        //TODO:
                        System.out.println("NOT IMPLEMENTED YET 1");
                    } else if (importTransactionsRequested && !importHoldingsRequested) {
                        //TODO: Warning:(146, 63) Condition '!importHoldingsRequested' is always 'true' when reached
                        //TODO: Warning:(146, 64) Value 'importHoldingsRequested' is always 'false'
                        System.out.println("NOT IMPLEMENTED YET 2");
                    } else if (!importTransactionsRequested && importHoldingsRequested) {
                        //TODO:Warning:(149, 32) Condition '!importTransactionsRequested' is always 'true'
                        //TODO:Warning:(149, 33) Value 'importTransactionsRequested' is always 'false'
                        System.out.println("NOT IMPLEMENTED YET 3");
                    } else {
                        Portfolio newEmptyPortfolio = new Portfolio();
                        User usr = new User(this.userid.getText(), this.password.getText(), newEmptyPortfolio);
                        this.addUser(usr, this.password1.getText(), new ArrayList<>(), new ArrayList<>());
                        currentUser = usr;
                    }
                    Parent parent = (Parent) FXMLLoader.load(this.getClass().getResource("../gui/LoginPage.fxml"));
                    Scene scene = new Scene(parent);
                    Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                    stage.setScene(scene);
                    stage.show();
                } else {
                    this.error.setText("Password fields do not match. Try your password again.");
                    this.password.clear();
                    this.password1.clear();
                }
            } else {
                this.error.setText("That User ID is already in use, please pick another one.");
            }
        } else {
            this.error.setText("Please Enter both a UserID and a Password");
        }

    }

    /**
     * Based off of user specifications during new user registration.
     * <p>
     * Author(s): Kaitlin Brockway
     */
    public void handleYesImportTransactionsButtonPressed() {
        importTransactionsRequested = true;
    }

    /**
     * Based off of user specifications during new user registration.
     * <p>
     * Author(s): Kaitlin Brockway
     */
    public void handleNoImportTransactionsButtonPressed() {
        importTransactionsRequested = false;
    }

    /**
     * Based off of user specifications during new user registration.
     * <p>
     * Author(s): Kaitlin Brockway
     */
    public void handleYesImportHoldingsButtonPressed() {
        importHoldingsRequested = true;
    }

    /**
     * Based off of user specifications during new user registration.
     * <p>
     * Author(s): Kaitlin Brockway
     */
    public void handleNoImportHoldingsButtonPressed() {
        importHoldingsRequested = false;
    }


    /**
     * @param event
     */
    @FXML
    protected void handleClearButtonPressed(ActionEvent event) {
        this.userid.clear();
        this.password.clear();
        if (this.password1 != null) {
            this.password1.clear();
        }

    }

    /**
     * Controls the program when the back button is clicked on the Registration page.
     *
     * @param event - ActionEvent - event that caused this method to be called.
     * @throws IOException - Exception thrown if the LoginPage.fxml is not found where the program expects.
     */
    @FXML
    protected void handleBackButtonPressed(ActionEvent event) throws IOException {
        goToLoginPage(event);
    }


    /**
     * @param event
     * @throws IOException
     */
    @FXML
    protected void handleSaveExitButtonPressed(ActionEvent event) throws IOException {
        Stage stg = FPTS.getSelf().getStage();
        stg.setScene(FPTS.getSelf().createLogInScene());
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }

    /**
     * @param event
     * @throws IOException
     */
    @FXML
    protected void handleExitExitButtonPressed(ActionEvent event) throws IOException {
        Stage stg = FPTS.getSelf().getStage();
        stg.setScene(FPTS.getSelf().createLogInScene());
        stg.show();
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }

    /**
     * CURRENTLY NOT IN USE.
     *
     * @param location
     * @param resources
     */
    public void initialize(URL location, ResourceBundle resources) {
    }

    /**
     * Upon registering a new user the handleRegistrationButtonPressed
     * method will call this method after it is confirmed that the user
     * id is not already associated with a portfolio in the system.
     *
     * @param usr
     * @param pw1
     */
    private void addUser(User usr, String pw1, ArrayList<Holding> holdings, ArrayList<Transaction> transactions) {
        User.addToUserMap(usr);//add to the Static collection allUsersMap
        usr.addUser(usr, pw1, holdings, transactions);//add to the non Static collection of UserData.csv
    }
}

