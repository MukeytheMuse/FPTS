/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.*;
import model.User;
import java.awt.Insets;
import java.awt.event.MouseEvent;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import model.*;

import controller.*;

import javafx.event.ActionEvent;
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
import javafx.scene.control.SelectionModel;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

/**
 *
 * @author ericepstein
 */
public class FPTS extends Application {
    
    HoldingAlgorithm eqUpdater; // for updates & nav, MUST KEEP
    CashAccountAlgorithm cashAccountAlgorithm;
    
    private final int WIDTH = 800;
    private final int HEIGHT = 600;

    private Stage thestage;

    private Portfolio p;

    private static FPTS self;

    public static FPTS getSelf(){
        return self;
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        self = this;
        thestage=primaryStage;
        p = new Portfolio();

        /*
        //Fills the User static class with whats in the UserData.txt file
        
        //User.fillUsers();
        */

        Parent root = FXMLLoader.load(getClass().getResource("LoginPage.fxml"));
        Scene loginScene = new Scene(root, WIDTH, HEIGHT);

        try {
            thestage.setScene(createLogInScene());
        } catch (Exception e) {
            //e.printStackTrace();
        }
        
        self = this;
        
        //thestage.setScene(loginScene);
        thestage.setScene(getHomeScene());
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
            Parent parent = FXMLLoader.load(getClass().getResource("../gui/HomePage.fxml"));
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
    
    public Scene createLogInScene() throws IOException{
        Parent root = FXMLLoader.load(getClass().getResource("LoginPage.fxml"));
        Scene scene = new Scene(root, WIDTH, HEIGHT);

        thestage.setTitle("Financial Portfolio Tracking System");
        return scene;
    }

    public Scene createRegisterPage() throws IOException{

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
            public void handle( ActionEvent event ) {
                thestage.setScene(getHomeScene());
            }
        });
        nav.getChildren().add(aButton);
        


        //Buy Button
        aButton = new Button();
        aButton.setText("Buy Holding");
        aButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle( ActionEvent event ) {
                eqUpdater = new BuyHoldingAlgorithm();
                eqUpdater.process(getSelf());
            }
        });
        nav.getChildren().add(aButton);
        
        //Sell Button
        aButton = new Button();
        aButton.setText("Sell Holding");
        aButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle( ActionEvent event ) {
                eqUpdater = new SellHoldingAlgorithm();
                eqUpdater.process(getSelf());
            }
        });
        nav.getChildren().add(aButton);

        //Portfolio Button
        Button portfolio = new Button();
        portfolio.setText("Portfolio");
        //TODO:Action to be set
        aButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

            }

        });
        nav.getChildren().add(portfolio);
        
        //Remove Cash Account Button
        aButton = new Button();
        aButton.setText("Remove Cash Account");
        aButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle( ActionEvent event ) {
                cashAccountAlgorithm = new RemoveCashAccountAlgorithm();
                cashAccountAlgorithm.process(getSelf());
                //eqUpdater.process(self);
            }
        });
        nav.getChildren().add(aButton);
        
        //Deposit CashAccount
        aButton = new Button();
        aButton.setText("Deposit");
        aButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle( ActionEvent event ) {
                cashAccountAlgorithm = new DepositCashAccountAlgorithm();
                cashAccountAlgorithm.process(getSelf());
                //eqUpdater.process(self);
            }
        });
        nav.getChildren().add(aButton);
        
        //Withdraw CashAccount
        aButton = new Button();
        aButton.setText("Withdraw");
        aButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle( ActionEvent event ) {
                cashAccountAlgorithm = new WithdrawCashAccountAlgorithm();
                cashAccountAlgorithm.process(getSelf());
                //eqUpdater.process(self);
            }
        });
        nav.getChildren().add(aButton);
        
        //Transfer CashAccount
        aButton = new Button();
        aButton.setText("Transfer");
        aButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle( ActionEvent event ) {
                cashAccountAlgorithm = new TransferCashAccountAlgorithm();
                cashAccountAlgorithm.process(getSelf());
                //eqUpdater.process(self);
            }
        });
        nav.getChildren().add(aButton);
       
        //Logout Button
         aButton = new Button();
        aButton.setText("Log out");
         //Setting an action for the logout button
        aButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                try {
                    thestage.setScene(createLogInScene());
                } catch (Exception ex) {
                    //e1.printStackTrace();
                }
                thestage.show();
            }
        });
        nav.getChildren().add(aButton);


        return nav;
    }
}


