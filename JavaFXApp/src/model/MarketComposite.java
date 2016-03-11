package model;
import java.util.ArrayList;
import java.util.List;

/**
 * Authors: Eric and Kaitlin
 */
public class MarketComposite implements Holding {
        //TODO: the composite should be an abstract class*********
        private String name;
        private List<Holding> internalEquityList;
        private double currentValue;

        //may only add a surEquity
        public MarketComposite(String marketName, Holding se){
            this.name = marketName;
            internalEquityList = new ArrayList<Holding>();
            internalEquityList.add(se);
        }

        public void add(Holding h){
            internalEquityList.add(h);
        }

        public void delete(Holding h){
            if(internalEquityList.contains(h)){
                internalEquityList.remove(h);
            } else {
                //return error stating this equity is not in the collection.
            }
        }

        public double getValue(){
            double count = 0;
            double curVal;
            for(Holding se : internalEquityList ) {
                curVal = se.getValue();
                count += curVal;
            }

            return count;
        }

        public String getSymbol() {
            return name;
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
