package model;
import java.util.ArrayList;
import java.util.List;

/**
 * Authors: Eric and Kaitlin
 */
public class EquityComposite implements Searchable, EquityComponent {
    private String name;
    private String type;
    private List<HoldingUpdatable> childEquities;
    private double currentValue;

    public EquityComposite(String name, String type){
        this.name = name;
        this.type = type;
        childEquities = new ArrayList<HoldingUpdatable>();
    }
        
    public String getDisplayName() {
            return name;
        }

    public String getTickerSymbol() {
            return name;
        }
        
    public String getEquityName() {
        return name;
    }

    public String getEquityType() {
        return type;
    }

    public void add(HoldingUpdatable e) {
            childEquities.add(e);
        }

    public double getValuePerShare(){
        double count = 0;
        double curVal;
        for(HoldingUpdatable se : childEquities ) {
            curVal = se.getValuePerShare();
            count += curVal;
        }
        return (float) count / childEquities.size();
    }
        
    public ArrayList<String> getSectors() {
            return new ArrayList<String>();
        }

    public ArrayList<String> getIndices() {
            return new ArrayList<String>();
        }

    public void remove(EquityComponent ec) {
            childEquities.remove((HoldingUpdatable) ec);
        }
        
    public void add(EquityComponent ec) {
            childEquities.add( (HoldingUpdatable) ec);
        }
        


            //From Eric's diagram.
//        public List<?????> getPricePerShare(){
//
//        }
        //getTickerSymbol() : name
        //getEquityName(): same as ticker symbol
        //getIndices(): returns empty ArrayList<String>
        //getSectors(): returns empty ArrayList<String>

}
