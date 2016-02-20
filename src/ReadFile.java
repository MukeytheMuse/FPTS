import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

/**Kaitlin
 *
 *
 */


/**
 * Created by kimberlysookoo on 2/17/16.
 * what up player?
 */
public class ReadFile {
    private ArrayList<String[]> splitFile;

    public ReadFile() {
        this.splitFile = new ArrayList<String[]>();
    }

    //Reads in CSV file
    public void readFile() {
        //String filePath = "/Users/kimberlysookoo/Desktop/rit-challenge/transactions-person-A.csv";
        Scanner userInput = new Scanner(System.in);
        System.out.println("Enter file name: ");
        String csv = userInput.nextLine();
        BufferedReader reader = null;
        String line = "";

        try {
            reader = new BufferedReader(new FileReader(csv));
            while ((line = reader.readLine()) != null) {

                String[] split = line.split(",");
                splitFile.add(split);
            }
        } catch (FileNotFoundException e) {
            System.out.println("That file does not exist! Please try again.");
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
