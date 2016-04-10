/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.UndoRedo;

/**
 *
 * Persists undo/redo stack throughout the lifetime of the invocation, transfers Command objects between stacks when called.
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
        try { 
            Command c = undoStack.pop();
            c.undo();
            redoStack.push(c);
        } catch (Exception ex) {}
    }
    
    public void redo() {
        try {
            Command c = redoStack.pop();
            c.execute();
        } catch (Exception ex) {}
    }
    
}
