package model.PortfolioElements;

/**
 * Created by ericepstein on 4/9/16.
 */
public interface PortfolioCollection {

    public void accept(PortfolioVisitor v);

}
