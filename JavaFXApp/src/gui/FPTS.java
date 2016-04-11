package gui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.DataBase.WriteFile;
import model.PortfolioElements.Portfolio;
import model.Simulators.Simulator;
import model.User;
import model.WebService;
import model.WebServiceReader;


import java.io.*;
import java.util.Timer;
import model.UndoRedo.UndoRedoManager;

public class FPTS extends Application {

    /*
    * context data associated with simulation
    */
    private static double simulationValue;
    private static Simulator currentSimulator;

    private final int WIDTH = 1200;
    private final int HEIGHT = 600;
    private Stage thestage;

    private static User currentUser;
    private static FPTS self;

    private static UndoRedoManager undoRedoManager;


    private static WebServiceReader webServiceReader;
    
    public FPTS() {
    }

    public void start(Stage primaryStage) throws IOException {
        undoRedoManager = new UndoRedoManager();
        
        self = this;
        this.thestage = primaryStage;
        User.fillUsers();

        Parent root = (Parent) FXMLLoader.load(this.getClass().getResource("/LoginPage.fxml"));
        Scene loginScene = new Scene(root, 800.0D, 600.0D);


        currentUser = new User("lala");
        currentUser.setMyPortfolio(new Portfolio());

        this.thestage.setScene(loginScene);
        this.thestage.setTitle("Financial Portfolio Tracking System");
        this.thestage.show();
    }
    
    public UndoRedoManager getUndoRedoManager() {
        return undoRedoManager;
    }

    public Scene createLogInScene() throws IOException {
        Parent root = (Parent) FXMLLoader.load(this.getClass().getResource("/LoginPage.fxml"));
        Scene scene = new Scene(root, 900.0D, 600.0D);
        this.thestage.setTitle("Financial Portfolio Tracking System");
        return scene;
    }

    public Scene getHomeScene() {
        Scene scene = null;

        try {
            Parent e = (Parent) FXMLLoader.load(this.getClass().getResource("/HomePage.fxml"));
            scene = new Scene(e);
        } catch (IOException var3) {
            var3.printStackTrace();
        }

        return scene;
    }

    public WebServiceReader getWebServiceReader() {
        return webServiceReader;
    }


    public static void main(String[] args) {
        if (args.length >= 2 && args[0].equals("-delete")) {
            String userID = args[1];

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
                csv.delete();
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
        beginWatching();
    }

    public static void beginWatching() {
        Timer time = new Timer();
        webServiceReader = new WebServiceReader(FPTS.getSelf());
        time.schedule(new WebService(webServiceReader), 0, 30000);
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

