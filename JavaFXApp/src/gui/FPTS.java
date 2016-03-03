/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import model.User;
import java.awt.Insets;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

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
import static javafx.application.Application.launch;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

/**
 *
 * @author ericepstein
 */
public class FPTS extends Application {
    
    Stage thestage;
    Page homePage;
    
    @Override
    public void start(Stage primaryStage) {
        thestage=primaryStage;
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
        
        homePage = new Page(scene1, "Home Page");
        Page simPage = new Page(scene2, "Simulation");
        Page searchPage = new Page(scene3, "Symbol Search");
        
        Page loginPage = new Page(loginScene, "Log in");
        Page regPage = new Page(registerScene, "Register");
       
        pages.add(homePage);
        pages.add(simPage);
        pages.add(searchPage);
       
        //g1 = (Group) scene1.getRoot();
        homePage.addNav(pages);
        simPage.addNav(pages);
        searchPage.addNav(pages);
        /*
        Group aGroup = (Group) scene1.getRoot();
        aGroup.getChildren().addAll(createNav(pages), new Label("s1"));
        g2.getChildren().addAll(createNav(pages), new Label("s2"));
        g3.getChildren().addAll(createNav(pages), new Label("s3"));
        */
        
        primaryStage.setTitle("Hello World!");
        primaryStage.setScene(createLogInPage().getScene());
        primaryStage.show();
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
                    //need to establish condition checking for duplicate login ID

                    //should be a global variable
                    ArrayList<User> users = new ArrayList<User>();
                    users.add(new User("lala", "lol"));

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

                    FileWriter fileWriter = null;
                    BufferedWriter bufferedWriter = null;

                    try {
                        fileWriter = new FileWriter("UserData.txt");
                        bufferedWriter = new BufferedWriter(fileWriter);
                        bufferedWriter.write(loginID.getText() + ",");
                        bufferedWriter.write(password1.getText());
                        bufferedWriter.newLine();
                        bufferedWriter.close();
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }

                    //need to establish condition checking for duplicate login ID
                    if (false) {
                        label.setText(loginID.getText() + " is an existing login ID. Please enter another one.");
                    }
                    else if (password1.getText().equals(password2.getText())) {
                        User u = new User(loginID.getText(), password1.getText());
                        //add function to create User
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
        
        public void addNav(ArrayList<Page> pages) {
            Group aGroup = (Group) scene.getRoot();
            aGroup.getChildren().add(createNav(pages));
        }
        
        private HBox createNav(ArrayList<Page> pages) { 
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
            return nav;
        }
        
    }
    
}
