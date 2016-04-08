/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.UndoRedo;

/**
 *
 * @author ericepstein
 */
public interface Command {
    
    public void execute();
    public void undo();
    public void addChild(Command c);
    public void removeChild(Command c);
    
}
