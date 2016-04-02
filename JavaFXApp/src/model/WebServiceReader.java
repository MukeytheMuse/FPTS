/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import gui.FPTS;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Observable;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import model.Equities.EquityComponent;
import model.PortfolioElements.Portfolio;
import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

/**
 *
 * @author ericepstein
 */
public class WebServiceReader extends Observable {
    
    private FPTS fpts;
    
    public WebServiceReader(FPTS fpts) {
        this.fpts = fpts;
    }
    
    public void run() {
       
        
        try { 
            String strArrayInQuery = "";
            ArrayList<EquityComponent> eqComponents = fpts.getCurrentUser().getMyPortfolio().getEquityComponents();    
           
            for (EquityComponent ec : eqComponents) {
                String display = ec.getDisplayName();
                display = display.replaceAll(" ","%20");
                strArrayInQuery = strArrayInQuery + "%22" + display + "%22%2C";
            }
            strArrayInQuery = strArrayInQuery.substring(0, strArrayInQuery.length() - 3);

            String url = "http://query.yahooapis.com/v1/public/yql?q=select%20*%20from%20yahoo.finance.quotes%20where%20symbol%20in%20(" + strArrayInQuery + ")&env=store://datatables.org/alltableswithkeys";

            // Create a URL and open a connection
            URL YahooURL = new URL(url);
            HttpURLConnection con = (HttpURLConnection) YahooURL.openConnection();
            
            // Set the HTTP Request type method to GET (Default: GET)
            con.setRequestMethod("GET");
            con.setConnectTimeout(10000);
            con.setReadTimeout(10000);
	
            // Created a BufferedReader to read the contents of the request.
            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuilder response = new StringBuilder();
            
            
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
        
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            InputSource is = new InputSource(new StringReader(response.toString()));
            Document document = builder.parse(is);
            
            Portfolio p = fpts.getCurrentUser().getMyPortfolio();
            p.updateEquityComponentsPrice(document);

            
            // MAKE SURE TO CLOSE YOUR CONNECTION!
            in.close();

            // response is the contents of the XML
       
            setChanged();
            notifyObservers();
           
        } catch (NullPointerException | IOException | ParserConfigurationException | SAXException | DOMException ex) {}
        
        
        
    }
    
}
