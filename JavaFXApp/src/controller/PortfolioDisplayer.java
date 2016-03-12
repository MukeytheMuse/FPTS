/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import gui.FPTS;
import java.util.ArrayList;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import model.Searchable;

/**
 *
 * @author ericepstein
 */
public class PortfolioDisplayer {
    
    FPTS theFPTS;
    ArrayList<Searchable> portfolioElements;
    
    public PortfolioDisplayer(FPTS theFPTS) {
        this.theFPTS = theFPTS;
        portfolioElements = theFPTS.getPortfolio().getPortfolioElements();
        theFPTS.getStage().setScene(getDisplayScene());
    }
    
    public Scene getDisplayScene() {
        
        VBox split = new VBox();
        VBox display = new VBox();
        
        for (Searchable s : portfolioElements) {
            display.getChildren().add(new Label(s.toString()));
        }
        
        split.getChildren().addAll(theFPTS.getNav(), display);
        
        Scene displayScene = new Scene(split, theFPTS.getWidth(), theFPTS.getHeight());
        return displayScene;
    }
}
