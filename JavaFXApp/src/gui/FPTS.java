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
    
    EquityUpdater eqUpdater;
    
    private final int WIDTH = 800;
    private final int HEIGHT = 600;

    private Stage thestage;
    private Page homePage;
    
    private Scene buyEquity;
    
    private Portfolio p;
    
    private Pane nav;

    private Searcher pEqSearcher;

    private Searcher s;

    private VBox matchDisplay;
    
    private TextField mainInput;

    private FPTS self;

    private Scene homeScene;
    private Scene searchCashAccount;
    
    private CashAccount CashAccountOfInterest;
    private CashAccount CashAccountOfInterest2;

    
    @Override
    public void start(Stage primaryStage) throws IOException {
        self = this;
        thestage=primaryStage;
        matchDisplay = new VBox();
        p = new Portfolio();
        //s = new LoadedEquitySearcher();

        //LoadedEquities eq = new LoadedEquities();
        
        //can now use the stage in other methods
       
        //make things to put on panes

        //Fills the User static class with whats in the UserData.txt file
        User.fillUsers();
        //make 2 scenes from 2 panes
        
        //Group g1 = new Group();
        //Group g2 = new Group();
        //Group g3 = new Group();
        
        
        
        Scene scene1 = new Scene(new Group(), WIDTH, HEIGHT);
        Scene scene2 = new Scene(new Group(), WIDTH, HEIGHT);
        Scene scene3 = new Scene(new Group(), WIDTH, HEIGHT);

        Parent root = FXMLLoader.load(getClass().getResource("LoginPage.fxml"));
        Scene loginScene = new Scene(root, WIDTH, HEIGHT);

        thestage.setScene(loginScene);
        thestage.setTitle("Financial Portfolio Tracking System");
        thestage.show();
        Scene registerScene = new Scene(new Group(), 300, 200);

        ArrayList<Page> pages = new ArrayList<Page>();
        
        homeScene = scene1;

        Page homePage = new Page(scene1, "Home Page");
        Page simPage = new Page(scene2, "Simulation");   
        Page searchPage = new Page(scene3, "Symbol Search");
        
        Page loginPage = new Page(loginScene, "Log in");
        Page regPage = new Page(registerScene, "Register");
       
        pages.add(homePage);
        pages.add(simPage);
        pages.add(searchPage);
        nav = createNav(pages);

        mainInput = new TextField ();
       
        //g1 = (Group) scene1.getRoot();
        //homePage.addNav(pages);
        //simPage.addNav(pages);
        //searchPage.addNav(pages);
        
        
        
        /*
        Group aGroup = (Group) scene1.getRoot();
        aGroup.getChildren().addAll(createNav(pages), new Label("s1"));
        g2.getChildren().addAll(createNav(pages), new Label("s2"));
        g3.getChildren().addAll(createNav(pages), new Label("s3"));
        */

        primaryStage.setTitle("Hello World!");
        try {
            primaryStage.setScene(createLogInPage());
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        primaryStage.setScene(searchPage.getScene());
        primaryStage.show();
        
        self = this;
        
        thestage.setScene(getHomeScene());
        
        /*
        goToBuyEquity = new BuyEquityUpdater();
        goToBuyEquity.process(self);
        */
        
        
    }
    
    public Scene getHomeScene() {
        VBox split = new VBox();
        Label l = new Label("Welcome to FPTS");
        split.getChildren().addAll(getNav(), l);
        Scene homeSc = new Scene(split, WIDTH, HEIGHT);
        return homeSc;
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

/*
    public void displayMatches(ArrayList<Searchable> matches) {
        //given the list
        matchDisplay.getChildren().clear();
        for (Searchable s : matches) {
            String symbol = s.getDisplayName();
            Button item = new Button(symbol);
            item.setStyle("-fx-background-color: white; -fx-text-fill: black;");
            item.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent e) {
                    mainInput.setText(symbol);
                }
            });
            matchDisplay.getChildren().add(item);
        }   
        
    }
    
    @Override
    public void update(Observable o, Object arg) {
        //displayMatches(ArrayList<)
        //displayMatches(this.p.getMatches());
        System.out.println("SIZE IS : " + s.getMatches().size());
        displayMatches(s.getMatches());
        System.out.println("UPDATED");
        //isplayMatches(this.s.getMatches());
    }

   */
    //returns HBox of relevant scenes

    public Scene createLogInPage() throws IOException{
        Parent root = FXMLLoader.load(getClass().getResource("LoginPage.fxml"));
        Scene scene = new Scene(root, WIDTH, HEIGHT);

        thestage.setScene(scene);
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
        TextField textField = new TextField ();
        HBox aField = new HBox();
        aField.getChildren().addAll(aLabel, textField);
        aField.setSpacing(10);
        return aField;
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
        
        aButton = new Button();
        aButton.setText("Home");
        aButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle( ActionEvent event ) {
                thestage.setScene(getHomeScene());
            }
        });
        nav.getChildren().add(aButton);
        
        aButton = new Button();
        aButton.setText("Buy");
        aButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle( ActionEvent event ) {
                eqUpdater = new BuyEquityUpdater();
                eqUpdater.process(self);
            }
        });
        nav.getChildren().add(aButton);
        
        aButton = new Button();
        aButton.setText("Sell");
        aButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle( ActionEvent event ) {
                eqUpdater = new SellEquityUpdater();
                eqUpdater.process(self);
            }
        });
        nav.getChildren().add(aButton);
        

        
        return nav;
    }
    
    
    
    /*
    *
    * TERRIBLE - DO NOT USE
    */
    public HBox createNav(ArrayList<Page> pages) {
        HBox nav = new HBox();
        for (Page aPage : pages) {
            //Creates the visit buttons
            EventHandler< ActionEvent > visitPage =
                new EventHandler< ActionEvent >() {
                    @Override
                    public void handle( ActionEvent event ) {
                        thestage.setScene(aPage.getScene());
                    }
                };
            Button visitBtn = new Button(aPage.toString());
            visitBtn.setOnAction( visitPage );
            nav.getChildren().add(visitBtn);
        }

        //Define the logout button
        Button logout = getLogoutButton();
        GridPane.setConstraints(logout, 1, 1);
        nav.getChildren().add(logout);
        
        return nav;
    }
    
    public Button getLogoutButton() {
        Button logout = new Button();
        
         //Setting an action for the logout button
        logout.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e){
                try {
                    thestage.setScene(createLogInPage());
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
                thestage.show();
            }
        });
        return logout;
    }
    
    
    
    class Page {
        private Scene scene;
        private String title;

        public Page(){
            this.scene = new Scene(new AnchorPane());
            this.title = "DO NOT USE";
        }

        public Page(Scene scene, String title) {
            this.scene = scene;
            this.title = title;
        }
        
        public Scene getScene() {
            return scene;
        }
        
        public String toString() {
            return title;
        }


    }

}


