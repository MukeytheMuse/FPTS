/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import controller.*;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.io.*;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;
import java.util.Scanner;

import static javafx.application.Application.launch;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.ComboBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.DataBase.WriteFile;
import model.Equity;
import model.Memento;
import model.Portfolio;
import model.Simulator;
import model.User;

import java.io.*;
import java.util.ArrayList;

/**
 * Executes application and refers user to relevant functions.
 *
 * @author Eric Epstein, Kimberly Sookoo, Ian London, Kaitlyn Brockway, Luke Veilleux
 */
public class FPTS extends Application {
    private ArrayList<Memento> savedMementoPortfolioStates;//acts as a queue

    /*
    * context data associated with simulation
    */
    private static double simulationValue;
    private static Simulator currentSimulator;

    /*
    * constants that describe dimension of display
    */
    private final int WIDTH = 1200;
    private final int HEIGHT = 600;

    /*
    * stage
    */
    private Stage thestage;

    /*
    * associated portfolio
    */
    public Portfolio p;

    /*
    * current user
    */
    private static User currentUser;

    /*
    * global reference to loginController
    */
    LoginController loginController;

    /*
    * copy of itself
    */
    private static FPTS self;

    /**
     * Starts the application display and loads users
     *
     * @param primaryStage
     * @throws IOException
     */
    @Override
    public void start(Stage primaryStage) throws IOException {
        self = this;
        thestage = primaryStage;
        //p = new Portfolio();

        /*
        * Fills the User static class
        */
        User.fillUsers();

        // Fills the Equity static class with whats in the equities.csv file
        Equity.getEquityList();

        /*
        * Sets homepage using FXML loader
        */
        Parent root = FXMLLoader.load(getClass().getResource("LoginPage.fxml"));
        Scene loginScene = new Scene(root, WIDTH, HEIGHT);

        try {
            thestage.setScene(createLogInScene());
        } catch (Exception e) {
            //e.printStackTrace();
        }

        self = this;

        thestage.setScene(loginScene);
        thestage.show();

    }



    /**
    * Returns home page
    *
    * @return Scene
    */
    public Scene getHomeScene() {
        Scene scene = null;
        try {
            Parent parent = FXMLLoader.load(getClass().getResource("HomePage.fxml"));
            scene = new Scene(parent);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return scene;
    }
    
    /**
     * Returns scene indicating confirmation of a user's action
     * 
     * @return Scene
     */

    public Scene getConfirmationScene() {
        Label confirmation = new Label("Update completed");
        VBox split = new VBox();
        split.getChildren().addAll(getNav(), confirmation);
        return new Scene(split, WIDTH, HEIGHT);
    }
    
    /**
     * Returns scene indicating error on the part of the user
     *
     * @return Scene
     */
    public Scene getErrorScene() {
        Label confirmation = new Label("Error");
        VBox split = new VBox();
        split.getChildren().addAll(getNav(), confirmation);
        return new Scene(split, WIDTH, HEIGHT);
    }

    /**
     * Returns login page loaded in FXMLLoader
     *
     * @return Scene
     */
    public Scene createLogInScene() throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("LoginPage.fxml"));
        Scene scene = new Scene(root, WIDTH, HEIGHT);

        thestage.setTitle("Financial Portfolio Tracking System");
        return scene;
    }

    /**
    * Returns register page loaded in FXMLLoader 
    *
    * @return Scene
    */
    public Scene createRegisterPage() throws IOException {

        Parent root = FXMLLoader.load(getClass().getResource("RegisterPage.fxml"));
        Scene scene = new Scene(root, WIDTH, HEIGHT);

        thestage.setScene(scene);
        thestage.setTitle("Financial Portfolio Tracking System");

        return scene;
    }


    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        if (args.length >= 2) {
            if (args[0].equals("-delete")) {
                String userID = args[1];
                File csv = new File("JavaFXApp/src/model/DataBase/UserData.csv");
                File csvTemp = new File("JavaFXApp/src/model/DataBase/UserDataTemp.csv");
                String line;
                try {
                    BufferedReader reader = new BufferedReader(new FileReader(csv));
                    BufferedWriter writer = new BufferedWriter(new FileWriter(csvTemp));
                    while ((line = reader.readLine()) != null) {
                        String[] split = line.split(",");
                        if (split[0].equals(userID)) {
                            System.out.println("Deleting " + userID);
                            continue;
                        }
                        writer.write(split[0] + "," + split[1]);
                        writer.newLine();
                    }
                    writer.close();
                    reader.close();
                    csvTemp.renameTo(csv);

                    File directory = new File("JavaFXApp/src/model/Database/Portfolios/" + userID);
                    if (directory.exists()) {
                        System.out.println("Has " + userID + " been deleted?");
                        File transFile = new File(directory, "/Trans.csv");
                        File cashFile = new File(directory, "/Cash.csv");
                        File holdingsFile = new File(directory, "/Holdings.csv");
                        transFile.delete();
                        cashFile.delete();
                        holdingsFile.delete();
                        directory.delete();
                    }
                } catch (Exception e) {

                }
            }
        }
        launch(args);
    }


    /**
     * Returns portfolio
     *
     * @return Portfolio
     */
    public Portfolio getPortfolio() {
        return p;
    }

    /**
     * Constructs navigation for relevant subsystems
     *
     * @return HBox
     */
    public HBox getNav() {
        HBox nav = new HBox();
        Button aButton;

        /*
        * Button to visit Home
        */
        aButton = new Button();
        aButton.setText("Home");
        aButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                thestage.setScene(getHomeScene());
            }
        });
        nav.getChildren().add(aButton);

        /*
        * Button to buy Equity
        */
        aButton = new Button();
        aButton.setText("Buy Holding");
        aButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                HoldingAlgorithm eqUpdater = new BuyHoldingAlgorithm();
                eqUpdater.process(self);
            }
        });
        nav.getChildren().add(aButton);

        /*
        * Button to sell equity
        */
        aButton = new Button();
        aButton.setText("Sell Holding");
        aButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                HoldingAlgorithm eqUpdater = new SellHoldingAlgorithm();
                eqUpdater.process(self);
            }
        });
        nav.getChildren().add(aButton);

        /*
        * Button to display portfolio
        */
        aButton = new Button();
        aButton.setText("Display Portfolio");
        //TODO:Action to be set
        aButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Displayer pd = new PortfolioDisplayer();
                pd.display(getSelf());
            }

        });
        nav.getChildren().add(aButton);

        /*
        * Button to view Transaction history
        */
        aButton = new Button();
        aButton.setText("History");
        aButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Displayer td = new TransactionDisplayer();
                td.display(getSelf());
            }

        });
        nav.getChildren().add(aButton);

        /*
        * Button to remove CashAccount
        */
        aButton = new Button();
        aButton.setText("Remove Cash Account");
        aButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                CashAccountAlgorithm cashAccountAlgorithm = new RemoveCashAccountAlgorithm();
                cashAccountAlgorithm.process(self);
                //eqUpdater.process(self);
            }
        });
        nav.getChildren().add(aButton);

        /*
        * Button to Deposit CashAccount
        */
        aButton = new Button();
        aButton.setText("Deposit");
        aButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                CashAccountAlgorithm cashAccountAlgorithm = new DepositCashAccountAlgorithm();
                cashAccountAlgorithm.process(self);
                //eqUpdater.process(self);
            }
        });
        nav.getChildren().add(aButton);

        /*
        * Button to withdraw from CashAccout
        */
        aButton = new Button();
        aButton.setText("Withdraw");
        aButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                CashAccountAlgorithm cashAccountAlgorithm = new WithdrawCashAccountAlgorithm();
                cashAccountAlgorithm.process(self);
                //eqUpdater.process(self);
            }
        });
        nav.getChildren().add(aButton);

        /*
        * Button to transfer from one CashAccount to another
        */
        aButton = new Button();
        aButton.setText("Transfer");
        aButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                CashAccountAlgorithm cashAccountAlgorithm = new TransferCashAccountAlgorithm();
                cashAccountAlgorithm.process(self);
                //eqUpdater.process(self);
            }
        });
        nav.getChildren().add(aButton);

        /*
        * Button to create CashAccount
        */
        aButton = new Button();
        aButton.setText("Add Cash Account");
        aButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                CashAccountCreator cashAccountCreator = new CashAccountCreator(getSelf());
                //eqUpdater.process(self);
            }
        });
        nav.getChildren().add(aButton);

        /* 
        The following code is commented out
        because it complies with our current
        design decision that will be revisited
        in the next release. 
        
        Create/Delete Portfolio Button disabled
        
        
        nav.getChildren().add(aButton);

        //Button to add/remove Portfolio

        Button managePortfolio = new Button();
        WriteFile writeFile = new WriteFile();
        currentUser.setMyPortfolio(this.getPortfolio());

        if (writeFile.hasPortfolio(currentUser)) {
            managePortfolio.setText("Remove Portfolio");
        } else {
            managePortfolio.setText("Add Portfolio");
        }
        managePortfolio.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

                if (writeFile.hasPortfolio(currentUser)) {
                    writeFile.removePortfolioForUser(currentUser);
                    managePortfolio.setText("Add Portfolio");
                } else {
                    writeFile.createPortfolioForUser(currentUser);
                    managePortfolio.setText("Remove Portfolio");
                }
            }
        });
        nav.getChildren().add(managePortfolio);
        */

        /*
        * Button to Logout
        */
        aButton = new Button();
        aButton.setText("Log out");
        //Setting an action for the logout button
        aButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                try {
                    Stage stage = new Stage();
                    Scene scene = new Scene(FXMLLoader.load(getClass().getResource("../gui/LogoutPage.fxml")));
                    stage.setScene(scene);
                    stage.show();
                } catch (Exception ex) {
                    //e1.printStackTrace();
                }
                thestage.show();
            }
        });
        nav.getChildren().add(aButton);

        return nav;
    }

    /**
     * Sets simulation value
     *
     * @param value
     */
    public static void setSimulationValue(double value) {
        simulationValue = value;
    }

    /**
     * Returns simulation value
     *
     * @return double
     */
    public static double getSimulationValue() {
        return simulationValue;
    }

    /**
     * Sets current simulation
     *
     * @param curSim - Simulator
     */
    public static void setCurrentSimulator(Simulator curSim) {
        currentSimulator = curSim;
    }

    /**
     * Returns current simulation
     *
     * @return Simulator
     */
    public static Simulator getCurrentSimulator() {
        return currentSimulator;
    }

    /**
     *
     * @param m
     */
    public void addMemento(Memento m){
        savedMementoPortfolioStates.add(m);
    }

    /**
     *
     * @return
     */
    public Memento getMemento(){
        return savedMementoPortfolioStates.remove(0);
    }
    
     /**
     * Returns current user
     * 
     * @return User
     */
    public User getCurrentUser() {
        return currentUser;
    }

    /**
     * Returns login ID of current user
     * 
     * @return String
     */
    public static String getCurrentUserID() {
        return currentUser.getLoginID();
    }
    
     /**
     * Returns self
     *
     * @return FPTS
     */
    public static FPTS getSelf() {
        return self;
    }

    /**
     * Returns indicator of portfolio existence
     * 
     * @param user
     * @return boolean
     */
    public boolean hasPortfolio(User user) {
        File directory = new File("JavaFXApp/src/model/Database/Portfolios/" + user.getLoginID());
        return directory.exists();
    }
    
    /**
     * Sets user object to the current user
     * 
     * @param user - User
     */
    public void setCurrentUser(User user) {
        currentUser = user;
        WriteFile writeFile = new WriteFile();
        if (!(hasPortfolio(currentUser))) {
            writeFile.createPortfolioForUser(currentUser);
        }
        p = new Portfolio();
    }
    
     /**
     * Returns height of stage
     *
     * @return int
     */
    public int getHeight() {
        return HEIGHT;
    }

    /**
     * Returns width of stage
     *
     * @return int
     */
    public int getWidth() {
        return WIDTH;
    }

    /**
     * Returns primary stage
     *
     * @return Stage
     */
    public Stage getStage() {
        return thestage;
    }
}


