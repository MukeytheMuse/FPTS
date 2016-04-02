package model.Searchers;

/**
 * Defines an interface that accesses a display name. Allows objects of
 * different types to be processed in the same algorithm.
 *
 * @author Eric Epstein
 */
public interface Searchable {

    /**
     * Returns a displayable name
     *
     * @return String
     */
    public String getDisplayName();

}
