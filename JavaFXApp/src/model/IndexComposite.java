/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;
import java.util.List;
import java.util.ArrayList;

/**
 *
 * @author ericepstein & Kaitlin
 */
public class IndexComposite implements Holding {
    private String indexName;
    private List<Holding> surEquityList;
    private double currentValue;
    
    //may only add a surEquity
    public IndexComposite(String indexName, Holding se){
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
