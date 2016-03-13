package model.DataBase;

import model.CashAccount;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by iLondon on 3/13/16.
 */
public class ReadCash {
    public static ArrayList<String[]> readInFile() {
        return ReadFile.readInUser("/Cash.csv");
    }

    public static ArrayList<CashAccount> allCash;
    public static ArrayList<String[]> splitFile;

    /**
     * Created by Ian
     *
     * @return list of all Holding objects
     */
    public static ArrayList<CashAccount> read() {
        splitFile = readInFile();
        allCash = new ArrayList<>();

        // iterate through each line representing an Holding
        for (String[] line : splitFile) {
            Date date = null;
            try {
                date = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy").parse(line[2]);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            // create cash account object form line
            CashAccount curCash = new CashAccount(line[0], Double.parseDouble(line[1]), date);

            // add cash accounts iteratively
            allCash.add(curCash);
        }
        return allCash;
    }
}
