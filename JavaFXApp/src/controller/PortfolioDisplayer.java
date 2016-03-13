/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import gui.FPTS;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import model.Searchable;

import java.util.ArrayList;

/**
 * Displays portfolio elements like Holding and Cash Account in one Scene.
 * 
 * @author ericepstein
 */
public class PortfolioDisplayer implements Displayer {

    /*
     * context data
     */
    FPTS theFPTS;
    ArrayList<Searchable> portfolioElements;

    /**
     * 
     * Overrides the Displayer's display method to display specifically
     * the portfolio elements.
     * 
     * @param theFPTS 
     */
    @Override
    public void display(FPTS theFPTS) {
        this.theFPTS = theFPTS;
        portfolioElements = theFPTS.getPortfolio().getPortfolioElements();
        theFPTS.getStage().setScene(getDisplayScene());
    }

    /**
    * Helper method to construct Scene of display given the elements.
    * 
    * @return Scene
    */
    private Scene getDisplayScene() {

        VBox split = new VBox();
        VBox display = new VBox();

        /*
         * Adds each portfolio element in a list of display.
         */
        for (Searchable s : portfolioElements) {
            display.getChildren().add(new Label(s.toString()));
        }
        split.getChildren().addAll(theFPTS.getNav(), display);
        Scene displayScene = new Scene(split, theFPTS.getWidth(), theFPTS.getHeight());
        return displayScene;
    }
}
