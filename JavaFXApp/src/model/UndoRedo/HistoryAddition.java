package model.UndoRedo;

import model.PortfolioElements.History;
import model.PortfolioElements.Transaction;

/**
 * Created by ericepstein on 4/9/16.
 */
public class HistoryAddition implements Command {

    Transaction t;
    History history;

    public HistoryAddition(Transaction t, History history) {
        this.t = t;
        this.history = history;
    }

    @Override
    public void execute() {
        history.add(t);
    }

    @Override
    public void undo() {
        history.remove(t);
    }

    @Override
    public void addChild(Command c) {

    }

    @Override
    public void removeChild(Command c) {

    }
}
