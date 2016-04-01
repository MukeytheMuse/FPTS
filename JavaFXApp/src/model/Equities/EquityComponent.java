package model.Equities;

/**
 * Defines the interface that manages and accesses the collection
 * of objects of which there are five accessible attributes.
 *
 * @author Eric Epstein & Kaitlin Brockway
 */
public interface EquityComponent {
    
    public String getDisplayName();
    public double getValuePerShare();
    
    /**
     * adds child to the composite
     *
     * @param e - EquityComponent
     */
    public void add(EquityComponent e);

    /**
     * removes child from composite
     *
     * @param e - EquityComponent
     */
    public void remove(EquityComponent e);
}
