/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import model.User;
import java.awt.Insets;
import java.awt.event.MouseEvent;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import model.*;

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
public class FPTS extends Application implements Observer {
    
    private Stage thestage;
    private Page homePage;
    
    private Portfolio p;
    
    private Searcher s;
    
    private VBox matchDisplay;
    
    private TextField tickerSymbolInput; 
    
    private ArrayList<User> users = new ArrayList<User>();
    
    @Override
    public void start(Stage primaryStage) {
        thestage=primaryStage;
        matchDisplay = new VBox();
        p = new Portfolio();
        s = new LoadedEquitySearcher();
        this.p.addObserver(this);
        
        //LoadedEquities eq = new LoadedEquities();
        
        //can now use the stage in other methods
       
        //make things to put on panes
        
        /*
        btnscene1=new Button("Click to go to Other Scene");
        btnscene2=new Button("Click to go back to First Scene");
        btnscene1.setOnAction(e-> ButtonClicked(e));
        btnscene2.setOnAction(e-> ButtonClicked(e));
        lblscene1=new Label("Scene 1");
        lblscene2=new Label("Scene 2");
        
        //make 2 Panes
        pane1=new FlowPane();
        pane2=new FlowPane();
        pane1.setVgap(10);
        pane2.setVgap(10);
        
        //set background color of each Pane
        pane1.setStyle("-fx-background-color: tan;-fx-padding: 10px;");
        pane2.setStyle("-fx-background-color: red;-fx-padding: 10px;");
        
        //add everything to panes
        pane1.getChildren().addAll(lblscene1, btnscene1);
        pane2.getChildren().addAll(lblscene2, btnscene2);
     
        */
        
        //make 2 scenes from 2 panes
        
        //Group g1 = new Group();
        //Group g2 = new Group();
        //Group g3 = new Group();
        
        Scene scene1 = new Scene(new Group(), 300, 200);
        Scene scene2 = new Scene(new Group(), 300, 200);
        Scene scene3 = new Scene(new Group(), 300, 200);
        
        Scene loginScene = new Scene(new Group(), 300, 200);
        Scene registerScene = new Scene(new Group(), 300, 200);
        
        ArrayList<Page> pages = new ArrayList<Page>();
        
        
        Page homePage = new Page(scene1, "Home Page");
        Page simPage = new Page(scene2, "Simulation");   
        Page searchPage = new Page(scene3, "Symbol Search");
        
        Page loginPage = new Page(loginScene, "Log in");
        Page regPage = new Page(registerScene, "Register");
       
        pages.add(homePage);
        pages.add(simPage);
        pages.add(searchPage);
        Pane nav = createNav(pages);
       
        //g1 = (Group) scene1.getRoot();
        //homePage.addNav(pages);
        //simPage.addNav(pages);
        //searchPage.addNav(pages);
        
        designSearchScene(searchPage.getScene(), nav);
        
        /*
        Group aGroup = (Group) scene1.getRoot();
        aGroup.getChildren().addAll(createNav(pages), new Label("s1"));
        g2.getChildren().addAll(createNav(pages), new Label("s2"));
        g3.getChildren().addAll(createNav(pages), new Label("s3"));
        */
        
        primaryStage.setTitle("Hello World!");
        primaryStage.setScene(createLogInPage().getScene());
        //primaryStage.setScene(searchPage.getScene());
        primaryStage.show();
    }
    
    
    public void designSearchScene(Scene searchScene, Pane nav) {
  
        VBox splitPage = new VBox();
        
        VBox searchPane = new VBox();
        
        VBox queries = new VBox();
      
        tickerSymbolInput = new TextField ();
        
        //createInputField("Ticker symbol: ", tickerSymbolInput);
        
        queries.getChildren().add(createInputField("Ticker symbol: ", tickerSymbolInput));
        queries.getChildren().add(createInputField("Equity name: ", new TextField()));
        queries.getChildren().add(createInputField("Index/sector: ", new TextField()));
        
        
        //tickerSymbolInput.setText();
        ArrayList<Holding> holdings = p.getMatches();

        Button searchBtn = new Button();
        searchBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                
                p.setMatches(queries.getChildren());
            }
        });
             
        searchPane.getChildren().addAll(queries, searchBtn, matchDisplay);
        
        splitPage.getChildren().addAll(nav, searchPane);
        
        //searchGroup.getChildren().add(searchPane);
        Group searchGroup = (Group) searchScene.getRoot();
        searchGroup.getChildren().add(splitPage);
    }
    
    public HBox createInputField(String description, TextField input) {
        HBox aField = new HBox();
        Label descriptionLabel = new Label(description);
        ObservableList<String> attributes = 
            FXCollections.observableArrayList(
                "",
                "contains",
                "starts with",
                "exactly matches"
        );
        ComboBox searchConditions = new ComboBox(attributes);
        searchConditions.getSelectionModel().select(0);
        aField.getChildren().addAll(descriptionLabel, searchConditions, input);
        aField.setSpacing(10);
        return aField;
    }
    
    public void displayMatches(ArrayList<Holding> matches) {
        //given the list
        matchDisplay.getChildren().clear();
        for (Holding h : matches) {
            String symbol = h.getSymbol();
            Button item = new Button(symbol);
            item.setStyle("-fx-background-color: white; -fx-text-fill: black;");
            item.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent e) {
                    tickerSymbolInput.setText(symbol);
                }
            });
            matchDisplay.getChildren().add(item);
        }   
        
    }
    
    @Override
    public void update(Observable o, Object arg) {
        //displayMatches(ArrayList<)
        displayMatches(this.p.getMatches());
        //isplayMatches(this.s.getMatches());
    }
    
    //returns HBox of relevant scenes

    public Page createLogInPage() {
        GridPane grid = new GridPane();
        grid.setVgap(5);
        grid.setHgap(5);
        
        //Defining a Register button
        Button register = new Button("Register");
        GridPane.setConstraints(register, 1, 2);
        grid.getChildren().add(register);
        
        final TextField loginID = new TextField();
        loginID.setPromptText("Enter your login id.");
        loginID.setPrefColumnCount(10);
        loginID.getText();
        GridPane.setConstraints(loginID, 0, 0);
        grid.getChildren().add(loginID);

        //Defining the first Password text field
        final PasswordField password1 = new PasswordField();
        password1.setPromptText("Enter your password.");
        GridPane.setConstraints(password1, 0, 1);
        grid.getChildren().add(password1);
        
        //Defining the Submit button
        Button submit = new Button("Submit");
        GridPane.setConstraints(submit, 1, 0);
        grid.getChildren().add(submit);
        
        //Defining the Clear button
        Button clear = new Button("Clear");
        GridPane.setConstraints(clear, 1, 1);
        grid.getChildren().add(clear);

        //Adding a Label
        final Label label = new Label();
        GridPane.setConstraints(label, 0, 3);
        GridPane.setColumnSpan(label, 2);
        grid.getChildren().add(label);

        //Setting an action for the Submit button
        submit.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                if (fieldHasContent(loginID) && fieldHasContent(password1)) {
                    /*label.setText(password1.getText() + " " + password2.getText() + ", "
                   + "thank you for your comment!");
                    */
                    User u = new User(loginID.getText(), password1.getText());

                    //users.add(new User("lala", "lol"));

                    fillUsers();
                    for (User existingUser : users) {
                        if (u.equals(existingUser)) {
                            thestage.setScene(homePage.getScene());
                            break;
                        }
                    }

                    password1.clear();
                    label.setText("Not a valid combination of login ID and password");

                } else {
                    label.setText("You have missing fields.");
                }
            }
        });
 
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
        
        Group g = new Group();
        g.getChildren().add(grid);
        
        Scene logInScene = new Scene(g, 300, 200);
        
        Page logInPage = new Page(logInScene, "Register");
        return logInPage;
    }
    
    public Page createRegisterPage() {
        
        GridPane grid = new GridPane();
        grid.setVgap(5);
        grid.setHgap(5);
        
        final TextField loginID = new TextField();
        loginID.setPromptText("Enter your login id.");
        loginID.setPrefColumnCount(10);
        loginID.getText();
        GridPane.setConstraints(loginID, 0, 0);
        grid.getChildren().add(loginID);
        //Defining the first Password text field
        final PasswordField password1 = new PasswordField();
        password1.setPromptText("Enter your password.");
        GridPane.setConstraints(password1, 0, 1);
        grid.getChildren().add(password1);
        
        //Defining the second Password text field
        final PasswordField password2 = new PasswordField();
        password2.setPromptText("Re-enter your password.");
        GridPane.setConstraints(password2, 0, 2);
        grid.getChildren().add(password2);
        
        //Defining the Submit button
        Button submit = new Button("Submit");
        GridPane.setConstraints(submit, 1, 0);
        grid.getChildren().add(submit);
        
        //Defining the Clear button
        Button clear = new Button("Clear");
        GridPane.setConstraints(clear, 1, 1);
        grid.getChildren().add(clear);

        //Defining the Back button
        Button back = new Button("Back");
        GridPane.setConstraints(back, 1, 2);
        grid.getChildren().add(back);

        //Adding a Label
        final Label label = new Label();
        GridPane.setConstraints(label, 0, 3);
        GridPane.setColumnSpan(label, 2);
        grid.getChildren().add(label);

        //Setting an action for the Submit button
        submit.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent e) {
                if (fieldHasContent(loginID) && fieldHasContent(password1) && fieldHasContent(password2)) {
                    /*label.setText(password1.getText() + " " + password2.getText() + ", "
                   + "thank you for your comment!");
                    */

                    //need to establish condition checking for duplicate login ID
                    boolean flag = false;

                    fillUsers();
                    for( User usr : users){
                        if(usr.getLoginID().equals(loginID.getText())){
                            flag = true;
                            break;
                        }
                    }

                    FileWriter fileWriter = null;
                    BufferedWriter bufferedWriter = null;
                    User user = new User(loginID.getText(), password1.getText());



                    if (flag) {
                        label.setText(loginID.getText() + " is an existing login ID. Please enter another one.");
                    }
                    else if (password1.getText().equals(password2.getText())) {
                        User u = new User(loginID.getText(), password1.getText());
                        //add function to create User

                        try {
                            fileWriter = new FileWriter("UserData.txt",true);
                            bufferedWriter = new BufferedWriter(fileWriter);
                            bufferedWriter.write(user.getLoginID() + ",");
                            bufferedWriter.write(user.hash(password1.getText()));
                            bufferedWriter.newLine();
                            bufferedWriter.close();
                        } catch (IOException e1) {
                            e1.printStackTrace();
                        }

                        thestage.setScene(homePage.getScene());
                    } else {
                        password1.clear();
                        password2.clear();
                        label.setText("Please re-enter password");
                       }
                } else {
                    label.setText("You have missing fields.");
                }
            }
        });
 
        //Setting an action for the Clear button
        clear.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent e) {
                loginID.clear();
                password1.clear();
                password2.clear();
                label.setText(null);
            }
        });

        //Setting an action for the Back button
        back.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent e) {
                thestage.setScene(createLogInPage().getScene());
            }
        });


        
        Group g = new Group();
        g.getChildren().add(grid);
        Scene regScene = new Scene(g, 300, 200);
        
        Page regPage = new Page(regScene, "LogIn");
        return regPage;
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
        Button logout = new Button("Logout");
        GridPane.setConstraints(logout, 1, 1);
        nav.getChildren().add(logout);

        //Setting an action for the logout button
        logout.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                thestage.setScene(createLogInPage().getScene());
                thestage.show();
            }
        });
        
        return nav;
    }
    
    class Page {
        private Scene scene;
        private String title;
        
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
    /*
     * Private method used to populate the users ArrayList<User> from the UserData.txt file.
     */
    private void fillUsers(){
        if(users.size() == 0){
            try {
                Scanner scanner = new Scanner(new File("UserData.txt"));

                while (scanner.hasNextLine()) {
                    String line = scanner.nextLine();
                    if(line.length() != 0){
                        //System.out.println(line);
                        String[] splitLine = line.split(",");
                        //System.out.println(splitLine[0]);
                        User newUser = new User(splitLine[0], splitLine[1]);
                        users.add(newUser);
                    }
                }

            } catch (FileNotFoundException e1) {
                e1.printStackTrace();
            }
        }
    }

}


