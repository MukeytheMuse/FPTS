package model.Simulators;

import model.PortfolioElements.Holding;

import java.util.ArrayList;
import java.util.stream.Collectors;

/**
 * Created by Luke Veilleux
 */
public class Memento {
    private static ArrayList<Memento> mementos;
    private ArrayList<Holding> holdings = new ArrayList<>();
    private double currentValue;

    public Memento(ArrayList<Holding> lst) {
        if (mementos == null){
            mementos = new ArrayList<>();
        }
        currentValue = 0;
        for(Holding h : lst) {
            holdings.add(new Holding(h));
            currentValue += h.getTotalValue();
        }
    }

    public ArrayList<Holding> getHoldings() {
        return holdings;
    }

    public void storeMemento() {
        mementos.add(this);
    }

    public static Memento getMemento(int index) {
        try {
            return mementos.get(index);
        } catch (IndexOutOfBoundsException e) {
            try {
                return mementos.get(mementos.size() - 1);
            } catch (IndexOutOfBoundsException a) {
                return null;
            }
        } catch (Exception e) {
            return null;
        }
    }

    public void resetToMemento(ArrayList<Holding> portfolioHoldings){
        ArrayList<Holding> lst = this.holdings;
        for(Holding holding : portfolioHoldings) {
            for (Holding stored : lst) {
                if (holding.equals(stored)) {
                    holding.setHoldingValue(stored.getPricePerShare());
                    break;
                }
            }
        }
        int index = mementos.indexOf(this);
        if(index == mementos.size() - 1) {
            mementos.remove(this);
        } else if(index < mementos.size() - 1) {
            while(mementos.size() > index) {
                mementos.remove(index);
            }
        }

    }
    public double getCurrentValue() { return currentValue; }
}
