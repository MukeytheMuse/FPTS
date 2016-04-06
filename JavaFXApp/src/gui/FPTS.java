package gui;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import controller.*;
import controller.CashAccountCtrl.CashAccountAlgorithm;
import controller.CashAccountCtrl.CashAccountCreator;
import controller.CashAccountCtrl.DepositCashAccountAlgorithm;
import controller.CashAccountCtrl.RemoveCashAccountAlgorithm;
import controller.CashAccountCtrl.TransferCashAccountAlgorithm;
import controller.CashAccountCtrl.ChangeCashAccountAlgorithm;
import controller.CashAccountCtrl.WithdrawCashAccountAlgorithm;

import controller.HoldingCtrl.BuyHoldingAlgorithm;
import controller.HoldingCtrl.HoldingAlgorithm;
import controller.HoldingCtrl.SellHoldingAlgorithm;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.PortfolioElements.Portfolio;
import model.Simulators.Simulator;
import model.User;
import model.*;


import java.io.*;
import java.util.ArrayList;
import java.util.Timer;

public class FPTS extends Application {

    /*
    * context data associated with simulation
    */
    private static double simulationValue;
    private static Simulator currentSimulator;


    private final int WIDTH = 1200;
    //TODO: check Warning:(40, 23) [UnusedDeclaration] Private field 'WIDTH' is never used
    private final int HEIGHT = 600;
    //TODO: check Warning:(41, 23) [UnusedDeclaration] Private field 'HEIGHT' is never used
    private Stage thestage;

    private static User currentUser;
    private static FPTS self;


    public static ArrayList<String> allIndicies;//all index names
    public static ArrayList<String> allSectors;//all sector names

    private WebServiceReader webServiceReader;
    
    public FPTS() {
    }

    public void start(Stage primaryStage) throws IOException {

        self = this;
        this.thestage = primaryStage;
        this.fillIndicies();
        this.fillSectors();
        User.fillUsers();//MUST be called after fillIndicies and fillSectors

        Timer time = new Timer();
        webServiceReader = new WebServiceReader(this);
        time.schedule(new WebService(webServiceReader), 0, 5000);

        Parent root = (Parent) FXMLLoader.load(this.getClass().getResource("LoginPage.fxml"));
        Scene loginScene = new Scene(root, 1200.0D, 600.0D);

        try {
            this.thestage.setScene(this.createLogInScene());
        } catch (Exception var5) {
            //TODO: check
        }

        //this.thestage.setScene(loginScene);

        currentUser = new User("lala");
        currentUser.setMyPortfolio(new Portfolio());

        Parent root2 = (Parent) FXMLLoader.load(this.getClass().getResource("/gui/Watchlist/WatchlistPage.fxml"));
        this.thestage.setScene(new Scene(root2, 1200.0D,600.0D));
        this.thestage.setScene(loginScene);

        this.thestage.show();
    }

    /**
     * Populates the possible index fields.
     * <p>
     * Author(s): Kaitlin Brockway
     */
    public void fillIndicies() {
        allIndicies = new ArrayList<>();
        allIndicies.add("DOW");
        allIndicies.add("NASDAQ100");
    }

    /**
     * Populates all of the possible sector fields.
     * <p>
     * Author(s): Kaitlin Brockway
     */
    public void fillSectors() {
        allSectors = new ArrayList<>();
        allSectors.add("FINANCE");
        allSectors.add("TRANSPORTATION");
        allSectors.add("TECHNOLOGY");
        allSectors.add("HEALTH CARE");
    }

    public Scene createLogInScene() throws IOException {
        Parent root = (Parent) FXMLLoader.load(this.getClass().getResource("LoginPage.fxml"));
        Scene scene = new Scene(root, 900.0D, 600.0D);
        //        Scene scene = new Scene(root, 1200.0D, 600.0D);
        this.thestage.setTitle("Financial Portfolio Tracking System");
        return scene;
    }

    public Scene getHomeScene() {
        Scene scene = null;

        try {
            Parent e = (Parent) FXMLLoader.load(this.getClass().getResource("HomePage.fxml"));
            scene = new Scene(e);
        } catch (IOException var3) {
            var3.printStackTrace();
        }

        return scene;
    }

    public WebServiceReader getWebServiceReader() {
        return webServiceReader;
    }


    /**
     * Called by "CashAccountCreator" class
     *
     * @return
     */
    public Scene getConfirmationScene() {
        Label confirmation = new Label("Update completed");
        VBox split = new VBox();
        split.getChildren().addAll(new Node[]{this.getNav(), confirmation});
        //TODO: check Warning:(127, 36) Redundant array creation for calling varargs method
        return new Scene(split, 1200.0D, 600.0D);
    }

    public boolean hasCurrentUser() {
       return currentUser != null;
    }
    
    /**
     * @return
     */
    public Scene getErrorScene() {
        Label confirmation = new Label("Error");
        VBox split = new VBox();
        split.getChildren().addAll(new Node[]{this.getNav(), confirmation});
        //TODO: check Warning:(139, 36) Redundant array creation for calling varargs method
        return new Scene(split, 1200.0D, 600.0D);
    }

//    public Scene createRegisterPage() throws IOException {
//        Parent root = (Parent)FXMLLoader.load(this.getClass().getResource("RegisterPage.fxml"));
//        Scene scene = new Scene(root, 1200.0D, 600.0D);
//        this.thestage.setScene(scene);
//        this.thestage.setTitle("Financial Portfolio Tracking System");
//        return scene;
//    }

    public static void main(String[] args) {
        if (args.length >= 2 && args[0].equals("-delete")) {
            String userID = args[1];
            File csv = new File("model/DataBase/UserData.csv");
            File csvTemp = new File("model/DataBase/UserDataTemp.csv");

            try {
                BufferedReader e = new BufferedReader(new FileReader(csv));
                BufferedWriter writer = new BufferedWriter(new FileWriter(csvTemp));

                String line;
                while ((line = e.readLine()) != null) {
                    String[] directory = line.split(",");
                    if (directory[0].equals(userID)) {
                        System.out.println("Deleting " + userID);
                    } else {
                        writer.write(directory[0] + "," + directory[1]);
                        writer.newLine();
                    }
                }

                writer.close();
                e.close();
                csvTemp.renameTo(csv);
                //TODO: check Warning:(174, 25) Result of 'File.renameTo()' is ignored
                File directory1 = new File("model/Database/Portfolios/" + userID);
                if (directory1.exists()) {
                    System.out.println("Has " + userID + " been deleted?");
                    File transFile = new File(directory1, "/Trans.csv");
                    File cashFile = new File(directory1, "/Cash.csv");
                    File holdingsFile = new File(directory1, "/Holdings.csv");
                    transFile.delete();
                    //TODO: check Warning:(181, 31) Result of 'File.delete()' is ignored
                    cashFile.delete();
                    //TODO: check Warning:(181, 31) Result of 'File.delete()' is ignored
                    holdingsFile.delete();
                    //TODO: check Warning:(181, 31) Result of 'File.delete()' is ignored
                    directory1.delete();
                    //TODO: check Warning:(181, 31) Result of 'File.delete()' is ignored
                }
            } catch (Exception var11) {
                ;
            }
        }

        launch(args);
    }

    public Portfolio getPortfolio() {
        return currentUser.getMyPortfolio();
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
                HoldingAlgorithm eqUpdater = new SellHoldingAlgorithm(null);
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
                try {
                    Scene scene = new Scene(FXMLLoader.load(this.getClass().getResource("PortfolioPage.fxml")));
                    thestage.setScene(scene);
                } catch (IOException e) {
                    e.printStackTrace();
                }
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

                //TODO: check Warning:(346, 36) [UnusedDeclaration] Variable 'cashAccountCreator' is never used
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


    public static User getCurrentUser() {
        return currentUser;
    }

    public static String getCurrentUserID() {
        return currentUser.getLoginID();
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
     * Returns simulation value
     *
     * @return double
     */
    public static double getSimulationValue() {
        return simulationValue;
    }

    /**
     * Sets simulation value
     *
     * @param value
     */
    public static void setSimulationValue(double value) {
        simulationValue = value;
    }

    public static FPTS getSelf() {
        return self;
    }

    public boolean hasPortfolio(User user) {
        File directory = new File("model/Database/Portfolios/" + user.getLoginID());
        return directory.exists();
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
     * When a user logs in the current user is set.
     * A portfolio should already exist and will be existing in the user class.
     *
     * @param user
     */
    public static void setCurrentUser(User user) {
        currentUser = user;
    }


    public int getHeight() {
        return 600;
    }

    public int getWidth() {
        return 1200;
    }

    public Stage getStage() {
        return this.thestage;
    }
}

