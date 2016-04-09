/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.Equities;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import model.PortfolioElements.CashAccount;
import model.PortfolioElements.Holding;
import model.PortfolioElements.PortfolioCollection;
import model.PortfolioElements.PortfolioVisitor;

/**
 *
 * @author ericepstein
 */
public class EquityComponents implements PortfolioCollection {
    
        List<EquityComponent> equityComponents;
        
        List<EquityComponent> results;
    
	public EquityComponents() {
		equityComponents = LoadedEquity.getEquityList();
	}
        
        public List<EquityComponent> getList() {
            return equityComponents;
        }

	public void addEquityComponent(EquityComponent eq) {
		equityComponents.add(eq);
	}

	public Iterator<EquityComponent> iterator(PortfolioVisitor v) {
	    results = new ArrayList<EquityComponent>();
            for (EquityComponent ec : equityComponents) {
                results.add(ec);
            }
            accept(v);
            return new EquityComponentIterator();
	}
        
        public void accept(PortfolioVisitor v) {
            v.visit(this);
        }
        
        public List<EquityComponent> getResults() {
            return results;
        }

	class EquityComponentIterator implements Iterator<EquityComponent> {
            
                int currentIndex;
                
		@Override
		public boolean hasNext() {
			return currentIndex < results.size();
		}

		@Override
		public EquityComponent next() {
                    return results.get(currentIndex++);     
		}

		@Override
		public void remove() {
			results.remove(--currentIndex);
		}
	}
}

