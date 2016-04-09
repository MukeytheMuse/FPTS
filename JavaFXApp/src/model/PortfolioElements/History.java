/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.PortfolioElements;

import model.Equities.EquityComponent;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 *
 * @author ericepstein
 */
public class History implements PortfolioCollection {

    List<Transaction> transactions;

    List<Transaction> results;

    public History() {
        transactions = new ArrayList<Transaction>();
    }

    public void add(Transaction h) {
        transactions.add(h);
    }

    public void add(List<Transaction> hList) {
        for (Transaction h : hList) {
            transactions.add(h);
        }
    }

    public List<Transaction> getList() {
        return transactions;
    }


    public void remove(Transaction h) {
        transactions.remove(h);
    }



    public Iterator<Transaction> iterator(PortfolioVisitor v) {
        results = new ArrayList<Transaction>();
        for (Transaction h : transactions) {
            results.add(h);
        }
        accept(v);
        return new TransactionIterator();
    }

    public void accept(PortfolioVisitor v) {
        v.visit(this);
    }

    public List<Transaction> getResults() {
        return results;
    }

    class TransactionIterator implements Iterator<Transaction> {

        int currentIndex;

        @Override
        public boolean hasNext() {
            return currentIndex < results.size();
        }

        @Override
        public Transaction next() {
            return results.get(currentIndex++);
        }

        @Override
        public void remove() {
            results.remove(--currentIndex);
        }
    }
}

