package model;

/**
 * authors: Kaitlin Brockway & Luke
 */
public class Memento {
    private final Portfolio portfolio;

    /**
     * saveState
     */
    public Memento(Portfolio p){
        portfolio = p;
    }

    public Portfolio getSavedState(){
        return portfolio;
    }
}
