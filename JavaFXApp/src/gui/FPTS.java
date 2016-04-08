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

public class FPTS extends Application {

    /*
    * context data associated with simulation
    */
    private static double simulationValue;
    private static Simulator currentSimulator;

    //TODO: If this is/will be used somewhere add a comment saying where, otherwise delete them
    private final int WIDTH = 1200;
    private final int HEIGHT = 600;

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
        //TODO: what is all of this about? ^ comment appropriately

        Timer time = new Timer();
        webServiceReader = new WebServiceReader(this);
        time.schedule(new WebService(webServiceReader), 0, 5000);


        Parent root = (Parent) FXMLLoader.load(this.getClass().getClassLoader().getResource("res/LoginPage.fxml"));
        Scene loginScene = new Scene(root, 1200.0D, 600.0D);

        //this.thestage.setScene(loginScene);

        //TODO: Why? What is this doing? comment this
        currentUser = new User("lala");
        currentUser.setMyPortfolio(new Portfolio());

        //Parent root2 = (Parent) FXMLLoader.load(this.getClass().getClassLoader().getResource("/LoginPage.fxml"));

        //Parent root2 = (Parent) FXMLLoader.load(getClass().getClassLoader().getResource("res/BuyHoldingPage.fxml"));
        //this.thestage.setScene(new Scene(root2, 1200.0D, 600.0D));
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
    //TODO: Why? ^ and v

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
        Parent root = (Parent) FXMLLoader.load(this.getClass().getResource("/LoginPage.fxml"));
        Scene scene = new Scene(root, 900.0D, 600.0D);
        //        Scene scene = new Scene(root, 1200.0D, 600.0D);
        this.thestage.setTitle("Financial Portfolio Tracking System");
        return scene;
    }

    public Scene getHomeScene() {
        Scene scene = null;

        try {
            Parent e = (Parent) FXMLLoader.load(this.getClass().getClassLoader().getResource("res/HomePage.fxml"));
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
        return new Scene(split, 1200.0D, 600.0D);
    }

    //TODO: comment the reason or delete it
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

            //TODO: add comments explaining what this does
            try {
                BufferedReader reader;

                File csv = new File(WriteFile.getPath() + "/lilBase/UserData.csv");

                reader = new BufferedReader(new FileReader(csv));
                File csvTemp = new File(WriteFile.getPath() + "/lilBase/UserDataTemp.csv");
                BufferedWriter writer = new BufferedWriter(new FileWriter(csvTemp, true));

                String line;
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
                    cashFile.delete();
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

