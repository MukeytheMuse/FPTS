/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.PortfolioElements;


import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 *
 * @author ericepstein
 */
public class Watchlist implements PortfolioCollection {

        List<WatchedEquity> watchedEquities;
        List<WatchedEquity> results;

	public Watchlist() {
		watchedEquities = new ArrayList<WatchedEquity>();
	}
        
        public List<WatchedEquity> getList() {
            return watchedEquities;
        }

	public void add(WatchedEquity c) {
		watchedEquities.add(c);
	}
        
        public void add(List<WatchedEquity> cList) {
            for (WatchedEquity c : cList) {
                watchedEquities.add(c);
            }
        }
        
        public void remove(WatchedEquity c) {
            watchedEquities.remove(c);
        }
        
        public WatchedEquity get(WatchedEquity c) {
            for (WatchedEquity aC : watchedEquities) {
                if (aC.getSymbol().equals(c.getSymbol())) {
                    return aC;
                } 
            }
            return null;
        }
        
        
        public boolean contains(WatchedEquity c) {
            for (WatchedEquity aC : watchedEquities) {
                if (aC.getSymbol().equals(c.getSymbol())) {
                    return true;
                } 
            }
            return false;
        }

    public List<WatchedEquity> getWatchedEquities() {
        return watchedEquities;
    }


    public void addWatchedEquity(WatchedEquity w) {
        watchedEquities.add(w);
    }

    public void updateWatchlist() {

        for (WatchedEquity w : watchedEquities) {

            //EquityComponent ec = w.getAssocEquity();

            w.handleNewPrice();

        }
    }

	public WatchedEquityIterator iterator() {
	    results = new ArrayList<WatchedEquity>();
            for (WatchedEquity c : watchedEquities) {
                results.add(c);
            }
            return new WatchedEquityIterator();
	}
        
        public void accept(PortfolioVisitor v) {
            v.visit(this);
        }
        
        public List<WatchedEquity> getResults() {
            return results;
        }

	class WatchedEquityIterator implements Iterator<WatchedEquity> {
            
                int currentIndex;
                
		@Override
		public boolean hasNext() {
			return currentIndex < results.size();
		}

		@Override
		public WatchedEquity next() {
                    return results.get(currentIndex++);     
		}

		@Override
		public void remove() {
			results.remove(--currentIndex);
		}
	}
}

