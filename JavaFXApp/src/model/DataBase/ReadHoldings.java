package model.DataBase;

import model.Holding;

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
    //TODO: find out if we need this class. Right now its not being used.

    protected static ArrayList<String[]> readInFile() {
        return ReadFile.readInUser("/Holdings.csv");
    }

    private static List<String> indexList = new ArrayList<String>(Arrays.asList("DOW", "NASDAQ100"));
    private static List<String> sectorList = new ArrayList<String>(Arrays.asList("FINANCE", "TECHNOLOGY", "HEALTH CARE", "TRANSPORTATION"));
    public static ArrayList<Holding> allHoldings;
    private static ArrayList<String[]> splitFile;

    /**
     * Created by Ian
     *
     * @return list of all Holding objects
     */
    public static ArrayList<Holding> read() {
        splitFile = readInFile();
        allHoldings = new ArrayList<>();

        // iterate through each line representing an Holding
        for (String[] line : splitFile) {
            ArrayList<String> indices = new ArrayList<String>();
            ArrayList<String> sectors = new ArrayList<String>();
            Date date = null;
            //TODO: Warning:(39, 18) [UnusedDeclaration] Variable 'date' is assigned but never accessed
            try {
                date = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy").parse(line[4]);
                //TODO: Warning:(41, 17) The value new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy").parse(line[4]) assigned to 'date' is never used
            } catch (ParseException e) {
                e.printStackTrace();
            }

            //TODO: change date to Date type :DD
            Holding curHolding = new Holding(line[0], line[1], Double.parseDouble(line[2]), Integer.parseInt(line[3]), "date", indices, sectors);


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
}