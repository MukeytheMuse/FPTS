/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

/**
 *
 * @author ericepstein
 */
public class LoadedEquities {
    private ArrayList<Equity> loadedEquities;

    //does it have index 3 & 4th
    //indexes.
    //4th value may be a sector or index. Sector is capitalized
    //if it is "DOW" is an "NASDA100" is an Index the rest are sectors

    //assume the name of the file is equity.
    public LoadedEquities() {
            String csv = "equity.csv";
            BufferedReader reader = null;
            String line = "";

            try {
                reader = new BufferedReader(new FileReader(csv));
                while ((line = reader.readLine()) != null) {

                    line = line.substring(1,line.length()-1);
                    String[] readline = line.split("\",\"");
                    //splitFile.add(readline);
                    System.out.println(Arrays.toString(readline));
                    String tickerSymbol = readline[0];
                    String name = readline[1];
                    double initialValue = Double.parseDouble(readline[2]);//converting string to a double.


                    //LoadedEquity e = new LoadedEquity(tickerSYmbol, blh, blah);
                    //loadedEquities.add(e);

                }
            } catch (FileNotFoundException e) {
                System.out.println("That file does not exist! Please try again.");
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

        //only reads this file when opening
        //READ FILE
        //convert each row to Equity objects
    }
    
    public ArrayList<Equity> getLoadedEquities() {
        return loadedEquities;
    }
    
    
    
}
