package model;
import java.util.ArrayList;
import java.util.List;

/**
 * Authors: Eric and Kaitlin
 */
public class MarketComposite implements EquityUpdatable, Searchable {
        private String name;
        private List<EquityUpdatable> childEquities;
        private double currentValue;

        //may only add a surEquity
        public MarketComposite(String name){
            this.name = name;
            childEquities = new ArrayList<EquityUpdatable>();
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

        public void add(EquityUpdatable e) {
            childEquities.add(e);
        }

        public float getValuePerShare(){
            double count = 0;
            double curVal;
            for(EquityUpdatable se : childEquities ) {
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

}
