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
import model.Portfolio;
import model.Simulator;
import model.User;

import java.io.IOException;

/**
 * @author ericepstein
 */
public class FPTS extends Application {
    private static double simulationValue;
    private static Simulator currentSimulator;

    HoldingAlgorithm eqUpdater; // for updates & nav, MUST KEEP
    CashAccountAlgorithm cashAccountAlgorithm;

    private final int WIDTH = 1000;
    private final int HEIGHT = 600;

    private Stage thestage;

    private Portfolio p;

    private User currentUser;

    LoginController loginController;

    private static FPTS self;

    public static FPTS getSelf() {
        return self;
    }

    public void setCurrentUser(User user) {
        this.currentUser = user;
    }

    /**
     * @param primaryStage
     * @throws IOException
     */
    @Override
    public void start(Stage primaryStage) throws IOException {
        self = this;
        thestage = primaryStage;
        p = new Portfolio();


        //Fills the User static class with whats in the UserData.txt file

        User.fillUsers();


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

    public int getHeight() {
        return HEIGHT;
    }

    public int getWidth() {
        return WIDTH;
    }


    public Stage getStage() {
        return thestage;
    }

    //returns HBox of relevant scenes

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

    public Scene getConfirmationScene() {
        Label confirmation = new Label("Update completed");
        VBox split = new VBox();
        split.getChildren().addAll(getNav(), confirmation);
        return new Scene(split, WIDTH, HEIGHT);
    }

    public Scene getErrorScene() {
        Label confirmation = new Label("Error");
        VBox split = new VBox();
        split.getChildren().addAll(getNav(), confirmation);
        return new Scene(split, WIDTH, HEIGHT);
    }

    public Scene createLogInScene() throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("LoginPage.fxml"));
        Scene scene = new Scene(root, WIDTH, HEIGHT);

        thestage.setTitle("Financial Portfolio Tracking System");
        return scene;
    }

    public Scene createRegisterPage() throws IOException {

        Parent root = FXMLLoader.load(getClass().getResource("RegisterPage.fxml"));
        Scene scene = new Scene(root, WIDTH, HEIGHT);

        thestage.setScene(scene);
        thestage.setTitle("Financial Portfolio Tracking System");

        return scene;
    }

    //Overloading fieldHasContent for PasswordField
    public boolean fieldHasContent(PasswordField aField) {
        return (aField.getText() != null && !aField.getText().isEmpty());
    }

    //Overloading fieldHasContent for TextField
    public boolean fieldHasContent(TextField aField) {
        return (aField.getText() != null && !aField.getText().isEmpty());
    }

    public HBox createField(String name) {
        Label aLabel = new Label(name + ":");
        TextField textField = new TextField();
        HBox aField = new HBox();
        aField.getChildren().addAll(aLabel, textField);
        aField.setSpacing(10);
        return aField;
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        if (args.length == 2) {
            if (args[0].equals("-delete")) {
                String userID = args[1];
                File csv = new File ("JavaFXApp/src/model/DataBase/UserData.csv");
                File csvTemp = new File ("JavaFXApp/src/model/DataBase/UserDataTemp.csv");
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


    public Portfolio getPortfolio() {
        return p;
    }

    public HBox getNav() {
        HBox nav = new HBox();
        Button aButton;

        //Home button
        aButton = new Button();
        aButton.setText("Home");
        aButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                thestage.setScene(getHomeScene());
            }
        });
        nav.getChildren().add(aButton);


        //Buy Button
        aButton = new Button();
        aButton.setText("Buy Holding");
        aButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                eqUpdater = new BuyHoldingAlgorithm();
                eqUpdater.process(self);
            }
        });
        nav.getChildren().add(aButton);

        //Sell Button
        aButton = new Button();
        aButton.setText("Sell Holding");
        aButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                eqUpdater = new SellHoldingAlgorithm();
                eqUpdater.process(self);
            }
        });
        nav.getChildren().add(aButton);

        //Portfolio Button
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

        //History Button
        aButton = new Button();
        aButton.setText("History");
        //TODO:Action to be set
        aButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Displayer td = new TransactionDisplayer();
                td.display(getSelf());
            }

        });
        nav.getChildren().add(aButton);

        //Remove Cash Account Button
        aButton = new Button();
        aButton.setText("Remove Cash Account");
        aButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                cashAccountAlgorithm = new RemoveCashAccountAlgorithm();
                cashAccountAlgorithm.process(self);
                //eqUpdater.process(self);
            }
        });
        nav.getChildren().add(aButton);

        //Deposit CashAccount
        aButton = new Button();
        aButton.setText("Deposit");
        aButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                cashAccountAlgorithm = new DepositCashAccountAlgorithm();
                cashAccountAlgorithm.process(self);
                //eqUpdater.process(self);
            }
        });
        nav.getChildren().add(aButton);

        //Withdraw CashAccount
        aButton = new Button();
        aButton.setText("Withdraw");
        aButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                cashAccountAlgorithm = new WithdrawCashAccountAlgorithm();
                cashAccountAlgorithm.process(self);
                //eqUpdater.process(self);
            }
        });
        nav.getChildren().add(aButton);

        //Transfer CashAccount
        aButton = new Button();
        aButton.setText("Transfer");
        aButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                cashAccountAlgorithm = new TransferCashAccountAlgorithm();
                cashAccountAlgorithm.process(self);
                //eqUpdater.process(self);
            }
        });
        nav.getChildren().add(aButton);

        //Create CashAccount
        aButton = new Button();
        aButton.setText("Create Cash Account");
        aButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                CashAccountCreator cashAccountCreator = new CashAccountCreator(getSelf());
                //eqUpdater.process(self);
            }
        });
        nav.getChildren().add(aButton);

        Button managePortfolio = new Button();
        WriteFile writeFile = new WriteFile();

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


        //Logout Button
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

    public static void setSimulationValue(double value) {
        simulationValue = value;
    }

    public static double getSimulationValue() {
        return simulationValue;
    }

    public static void setCurrentSimulator(Simulator curSim) {
        currentSimulator = curSim;
    }

    public static Simulator getCurrentSimulator() {
        return currentSimulator;
    }
}


