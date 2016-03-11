package model;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Brockway on 3/11/16.
 */
public class MarketComposite implements Holding {
        private String indexName;
        private List<Holding> surEquityList;
        private double currentValue;

        //may only add a surEquity
        public MarketComposite(String indexName, Holding se){
            this.indexName = indexName;
            surEquityList = new ArrayList<Holding>();
            surEquityList.add(se);
        }

        public void add(Holding h){
            surEquityList.add(h);
        }

        public void delete(Holding h){
            if(surEquityList.contains(h)){
                surEquityList.remove(h);
            } else {
                //return error stating this equity is not in the collection.
            }
        }

        public double getValue(){
            double count = 0;
            double curVal;
            for(Holding se : surEquityList ) {
                curVal = se.getValue();
                count += curVal;
            }

            return count;
        }

        public String getSymbol() {
            return indexName;
        }

}
