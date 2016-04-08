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
public class UndoRedoManager {
 
    UndoStack undoStack;
    RedoStack redoStack;
    
    public UndoRedoManager() {
        undoStack = new UndoStack();
        redoStack = new RedoStack();
    }
    
    public void execute(Command c) {
        c.execute();
        undoStack.push(c);
    }
    
    public void undo() {
        Command c = undoStack.pop();
        c.undo();
        redoStack.push(c);
    }
    
    public void redo() {
        Command c = redoStack.pop();
        c.execute();
    }
    
}
