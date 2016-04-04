package model.DataBase;

import model.PortfolioElements.WatchedEquity;

import java.util.ArrayList;

/**
 * Created by Kimberly Sookoo on 4/3/16.
 */
public class ReadWatchedEquity {

    public static ArrayList<WatchedEquity> readDB(String user) {
        ArrayList<String[]> splitFile = ReadFile.readInUser(user + "/Watch.csv");
        return readWatchedEquity(splitFile);
    }

    public static ArrayList<WatchedEquity> readWatchedEquity(ArrayList<String[]> split) {
        ArrayList<WatchedEquity> watchedEquities = new ArrayList<>();

        for (String[] watchedEquity : split) {
            System.out.println(watchedEquity[0]);
            WatchedEquity newWatchedEquity = new WatchedEquity(watchedEquity[0], Double.parseDouble(watchedEquity[1]), Double.parseDouble(watchedEquity[2]),
                    Boolean.parseBoolean(watchedEquity[3]), Boolean.parseBoolean(watchedEquity[4]), Boolean.parseBoolean(watchedEquity[5]),
                    Boolean.parseBoolean(watchedEquity[6]));

            watchedEquities.add(newWatchedEquity);
        }

        return watchedEquities;
    }
}
