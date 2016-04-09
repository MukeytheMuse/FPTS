package model.Simulators;

import model.PortfolioElements.Holding;

import java.util.ArrayList;
import java.util.stream.Collectors;

/**
 * Created by Luke Veilleux
 */
public class Memento {
    public static ArrayList<Memento> mementos;
    private ArrayList<Holding> holdings = new ArrayList<>();

    public Memento(ArrayList<Holding> lst) {
        if (mementos == null){
            mementos = new ArrayList<>();
        }
        holdings.addAll(lst.stream().map(Holding::new).collect(Collectors.toList()));
    }

    public ArrayList<Holding> getHoldings() {
        return holdings;
    }

    public void storeMemento() {
        mementos.add(this);
    }

    public void removedMemento() {
        mementos.remove(this);
    }

}
