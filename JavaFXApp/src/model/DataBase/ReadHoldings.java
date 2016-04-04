package model.DataBase;

import model.PortfolioElements.Holding;

import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * Created by iLondon on 3/2/16.
 */
public class ReadHoldings {

    private static List<String> indexList = new ArrayList<String>(Arrays.asList("DOW", "NASDAQ100"));
    private static List<String> sectorList = new ArrayList<String>(Arrays.asList("FINANCE", "TECHNOLOGY", "HEALTH CARE", "TRANSPORTATION"));


    /**
     * Created by Ian
     *
     * @return ArrayList of Holding objects in the database for that user
     */
    public static ArrayList<Holding> readDB(String un) {
        ArrayList<String[]> splitFile = ReadFile.readInUser(un + "/Holdings.csv");
        return read(splitFile);
    }

    /**
     * Created by Ian
     *
     * @return list of all Holding objects
     */
    public static ArrayList<Holding> read(ArrayList<String[]> file) {
        ArrayList<String[]> splitFile = file;
        ArrayList<Holding> allHoldings = new ArrayList<>();

        // iterate through each line representing an Holding
        for (String[] line : splitFile) {
            ArrayList<String> indices = new ArrayList<String>();
            ArrayList<String> sectors = new ArrayList<String>();
            Date date = new Date();
            try {
                date = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy").parse(line[4]);
            } catch (ParseException e) {
                e.printStackTrace();
            }

            Holding curHolding = new Holding(line[0], line[1], Double.parseDouble(line[2]), Integer.parseInt(line[3]), date, indices, sectors);


            // iterate through fields of current Holding
            for (int i = 5; i < line.length; i++) {
                // finance, technology, health care, transportation
                if (sectorList.contains(line[i])) {
                    sectors.add(line[i]);
                }
                // dow, nasdaq100
                else if (indexList.contains(line[i])) {
                    indices.add(line[i]);
                }
            }
            allHoldings.add(curHolding);
        }
        return allHoldings;
    }

    /**
     * Reads in external holdings file that the user chooses to import.
     * @param file - file that user chooses to upload.
     * @return - arraylist containing the user's imported holdings.
     * Created by: Kimberly Sookoo.
     */
    public static ArrayList<Holding> readInImports(File file) {
        String path = file.getPath();
        ArrayList<String[]> splitFile = ReadFile.readIn(path);
        return read(splitFile);
    }
}
