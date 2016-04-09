package gui;

import controller.CashAccountCtrl.*;
import controller.Displayer;
import controller.HoldingCtrl.BuyHoldingAlgorithm;
import controller.HoldingCtrl.HoldingAlgorithm;
import controller.HoldingCtrl.SellHoldingAlgorithm;
import controller.TransactionDisplayer;
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
import model.DataBase.WriteFile;
import model.PortfolioElements.Portfolio;
import model.Simulators.Simulator;
import model.User;
import model.WebService;
import model.WebServiceReader;


import java.io.*;
import java.util.ArrayList;
import java.util.Timer;
import model.UndoRedo.UndoRedoManager;

public class FPTS extends Application {

    /*
    * context data associated with simulation
    */
    private static double simulationValue;
    private static Simulator currentSimulator;

    //TODO: If this is/will be used somewhere add a comment saying where, otherwise delete them
    private final int WIDTH = 1200;
    //TODO: check Warning:(40, 23) [UnusedDeclaration] Private field 'WIDTH' is never used
    private final int HEIGHT = 600;
    //TODO: check Warning:(41, 23) [UnusedDeclaration] Private field 'HEIGHT' is never used
    private Stage thestage;

    private static User currentUser;
    private static FPTS self;

    private static UndoRedoManager undoRedoManager;


    private WebServiceReader webServiceReader;
    
    public FPTS() {
    }

    public void start(Stage primaryStage) throws IOException {
        undoRedoManager = new UndoRedoManager();
        
        self = this;
        this.thestage = primaryStage;
        User.fillUsers();

        Timer time = new Timer();
        webServiceReader = new WebServiceReader(this);
        time.schedule(new WebService(webServiceReader), 0, 5000);

        Parent root = (Parent) FXMLLoader.load(this.getClass().getResource("/LoginPage.fxml"));
        Scene loginScene = new Scene(root, 1200.0D, 600.0D);

//        try {
//            this.thestage.setScene(this.createLogInScene());
//        } catch (Exception var5) {
//            //TODO: check
//        }

        //this.thestage.setScene(loginScene);

        currentUser = new User("lala");
        currentUser.setMyPortfolio(new Portfolio());

//        Parent root2 = (Parent) FXMLLoader.load(this.getClass().getResource("/gui/Watchlist/WatchlistPage.fxml"));
//        this.thestage.setScene(new Scene(root2, 1200.0D,600.0D));
        this.thestage.setScene(loginScene);

        this.thestage.show();
    }
    
    public UndoRedoManager getUndoRedoManager() {
        return undoRedoManager;
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

    
    
    public static void main(String[] args) {
        if (args.length >= 2 && args[0].equals("-delete")) {
            String userID = args[1];
//            File csv = new File("model/DataBase/UserData.csv");
//            File csvTemp = new File("model/DataBase/UserDataTemp.csv");

            //TODO: add comments explaining what this does
            try {
//                BufferedReader e = new BufferedReader(new FileReader(csv));
//                BufferedWriter writer = new BufferedWriter(new FileWriter(csvTemp));
                BufferedReader reader;
                //*******NEW DELETE THIS COMMENT -Kaitlin
                File csv = new File(WriteFile.getPath() + "/lilBase/UserData.csv");

                reader = new BufferedReader(new FileReader(csv));
                File csvTemp = new File(WriteFile.getPath() + "/lilBase/UserDataTemp.csv");
                BufferedWriter writer = new BufferedWriter(new FileWriter(csvTemp, true));

                String line;
                 //******
                while ((line = reader.readLine()) != null) {
                    String[] user = line.split(",");
                    if (user[0].equals(userID)) {
                    } else {
                        writer.write(user[0] + "," + user[1]);
                        writer.newLine();
                    }
                }

                writer.close();
                reader.close();
                csvTemp.renameTo(csv);

                File directory = new File(WriteFile.getPath() + "/lilBase/Portfolios/" + userID);
                if (directory.exists()) {
                    File transFile = new File(directory, "/Trans.csv");
                    File cashFile = new File(directory, "/Cash.csv");
                    File holdingsFile = new File(directory, "/Holdings.csv");
                    File watchFile = new File(directory, "/Watch.csv");
                    transFile.delete();
                    //TODO: check Warning:(181, 31) Result of 'File.delete()' is ignored
                    cashFile.delete();
                    //TODO: check Warning:(181, 31) Result of 'File.delete()' is ignored
                    holdingsFile.delete();
                    watchFile.delete();
                    directory.delete();
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

