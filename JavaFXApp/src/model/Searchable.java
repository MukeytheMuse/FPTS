/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

/**
 * Defines an interface that accesses a display name. Allows objects of
 * different types to be processed in the same algorithm.
 *
 * @author Eric Epstein
 */
public interface Searchable {

    /**
     * Returns a displayable name
     */
    public String getDisplayName();

    ;
}
