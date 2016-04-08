/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.PortfolioElements;


import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import model.PortfolioElements.PortfolioVisitor;

/**
 *
 * @author ericepstein
 */
public class CashAccounts {
    
        List<CashAccount> cashAccounts;
        
        List<CashAccount> results;
    
	public CashAccounts() {
		cashAccounts = new ArrayList<CashAccount>();
	}

	public void addCashAccount(CashAccount c) {
		cashAccounts.add(c);
	}

	public Iterator<CashAccount> iterator(PortfolioVisitor v) {
	    results = new ArrayList<CashAccount>();
            for (CashAccount c : cashAccounts) {
                results.add(c);
            }
            accept(v);
            return new CashAccountIterator();
	}
        
        public void accept(PortfolioVisitor v) {
            v.visit(this);
        }
        
        public List<CashAccount> getResults() {
            return results;
        }

	class CashAccountIterator implements Iterator<CashAccount> {
            
                int currentIndex;
                
		@Override
		public boolean hasNext() {
			return currentIndex < results.size();
		}

		@Override
		public CashAccount next() {
                    return results.get(currentIndex++);     
		}

		@Override
		public void remove() {
			results.remove(--currentIndex);
		}
	}
}

