/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.PortfolioElements;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import model.Equities.EquityComponent;
import model.PortfolioElements.PortfolioVisitor;

/**
 *
 * @author ericepstein
 */
public class Holdings implements PortfolioCollection {
    
        List<Holding> holdings;
        
        List<Holding> results;
    
	public Holdings() {
		holdings = new ArrayList<Holding>();
	}

	public void add(Holding h) {
		holdings.add(h);
	}
        
        public void add(List<Holding> hList) {
            for (Holding h : hList) {
                holdings.add(h);
            }
        }
        
        public List<Holding> getList() {
            return holdings;
        }
        
        
	public void remove(Holding h) {
		holdings.remove(h);
	}
        
        public Holding getHoldingGivenEquityComponent(EquityComponent ec) {
            for (Holding h : holdings) {
                if (h.getTickerSymbol().equals(ec.getTickerSymbol())) {
                    return h;
                }
            }
            return null;
        }
        
        public boolean contains(EquityComponent ec) {
            for (Holding h : holdings) {
                if (h.getTickerSymbol().equals(ec.getTickerSymbol())) {
                    return true;
                }
            }
            return false;
        }
        
        public boolean contains(Holding anH) {
                for (Holding h : holdings) {
                if (h.getDisplayName().equals(anH.getDisplayName())) {
                    return true;
                }
            }
            return false;   
        }

	public Iterator<Holding> iterator(PortfolioVisitor v) {
	    results = new ArrayList<Holding>();
            for (Holding h : holdings) {
                results.add(h);
            }
            accept(v);
            return new HoldingIterator();
	}
        
        public void accept(PortfolioVisitor v) {
            v.visit(this);
        }
        
        public List<Holding> getResults() {
            return results;
        }

	class HoldingIterator implements Iterator<Holding> {
            
                int currentIndex;
                
		@Override
		public boolean hasNext() {
			return currentIndex < results.size();
		}

		@Override
		public Holding next() {
                    return results.get(currentIndex++);     
		}

		@Override
		public void remove() {
			results.remove(--currentIndex);
		}
	}
}

