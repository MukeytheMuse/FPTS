package model.DataBase;

import model.EquityComponent;
import model.Equity;
import model.EquityComposite;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by kimberlysookoo on 2/17/16.
 * Re-purposed by Ian
 *
 */
public class ReadFile {
    public static List<String> indexList = new ArrayList<String>(Arrays.asList("DOW", "NASDAQ100"));
    public static List<String> sectorList = new ArrayList<String>(Arrays.asList("FINANCE", "TECHNOLOGY","HEALTH CARE","TRANSPORTATION"));
    public static ArrayList<String[]> splitFile = new ArrayList<String[]>();

    /**
     * Created by Ian
     *
     * @return list of all Equity objects
     */
    public static ArrayList<EquityComponent> readEquity(){
        readInFile();
        ArrayList<EquityComponent> allEquities = new ArrayList<>();
        ArrayList<EquityComposite> CompositeEquities = loadCompositeList();
        allEquities.addAll(CompositeEquities);

        // iterate through each line representing an equity
        for( String[] line : splitFile){
            ArrayList<String> indices = new ArrayList<String>();
            ArrayList<String> sectors = new ArrayList<String>();
            Equity curEquity = new Equity(line[0], line[1], Double.parseDouble(line[2]), indices, sectors);
            // iterate through fields of current equity
            for( int i = 3; i < line.length; i++ ){
                // finance, technology, health care, transportation
                if (sectorList.contains(line[i])){
                    sectors.add(line[i]);
                    // add to sector composite
                    try {
                        for(EquityComposite ec : CompositeEquities ){
                            if(ec.getEquityType().equals("Sector") & ec.getEquityName().equals(line[i])){
                                ec.add((EquityComponent)curEquity);
                            }
                        }
                    } catch (Exception e) {
                        System.out.println("sector composite object not found! Please try again.");
                }
                }
                // dow, nasdaq100
                else if (indexList.contains(line[i])){
                    indices.add(line[i]);
                    //add to index composite
                    try {
                        for(EquityComposite ec : CompositeEquities ){
                            if(ec.getEquityType().equals("Index") & ec.getEquityName().equals(line[i])){
                                ec.add((EquityComponent)curEquity);
                            }
                        }
                    } catch (Exception e) {
                        System.out.println("index composite object not found! Please try again.");
                    }
                }
            }
            allEquities.add(curEquity);
        }
        return allEquities;
    }

    /**
     * Created by Ian
     * @return list of composite Equities
     */
    public static ArrayList<EquityComposite> loadCompositeList() {
        ArrayList<EquityComposite> compositeList = new ArrayList<EquityComposite>();
        // create the bare index composites
        for( String index : indexList){
            ArrayList<String> indices = new ArrayList<String>();
            ArrayList<String> sectors = new ArrayList<String>();
            compositeList.add( new EquityComposite(index, "Index"));
        }
        // create the bare index composites
        for( String sector : sectorList){
            ArrayList<String> indices = new ArrayList<String>();
            ArrayList<String> sectors = new ArrayList<String>();
            compositeList.add( new EquityComposite(sector, "Sector"));
        }
        return compositeList;
    }

    // reads in CSV file
    public static void readInFile() {
        String csv = "JavaFXApp/src/model/DataBase/equities.csv";
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
            //readInFile();
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
