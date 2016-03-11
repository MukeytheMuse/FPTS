package model.DataBase;

import model.LoadedEquity;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by kimberlysookoo on 2/17/16.
 * Re-purposed by Ian
 *
 */
public class ReadFile {
    private static ArrayList<String[]> splitFile;

    public ReadFile() {
        this.splitFile = new ArrayList<String[]>();
    }

    public static ArrayList<LoadedEquity> loadEquityList(){
        readFile();
        ArrayList<LoadedEquity> loadedList = new ArrayList<LoadedEquity>();
        // iterate through each line representing an equity
        for( String[] line : splitFile){
            ArrayList<String> indices = new ArrayList<String>();
            ArrayList<String> sectors = new ArrayList<String>();
            // iterate through fields of current equity
            for( int i = 3; i < line.length; i++ ){
                // finance, technology, health care, transportation
                if (line[i].startsWith("F") | line[i].startsWith("T") | line[i].startsWith("H")){
                    indices.add(line[i]);
                }
                // dow, nasdaq100
                else if (line[i].startsWith("D") | line[i].startsWith("N")){
                    sectors.add(line[i]);
                }
            }
            loadedList.add( new LoadedEquity(line[0], line[1], Double.parseDouble(line[2]), indices, sectors));
        }
        return loadedList;
    }

    //Reads in CSV file
    public static void readFile() {
        String csv = "src/model/DataBase/equities.csv";
        BufferedReader reader = null;
        String line;

        try {
            reader = new BufferedReader(new FileReader(csv));
            while ((line = reader.readLine()) != null) {
                line = line.substring(1,line.length()-1);
                String[] split = line.split("\",\"");
                splitFile.add(split);
            }
        } catch (FileNotFoundException e) {
            System.out.println("src/model/DataBase/equities.csv not found! Please try again.");
            readFile();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
