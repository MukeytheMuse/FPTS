/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.UndoRedo;

import java.util.Stack;

/**
 *
 * @author ericepstein
 */
public class UndoStack {
    
    Stack<Command> undoStack;
    
    public UndoStack() {
        undoStack = new Stack<Command>();
    }
    
    public Command pop() {
        return undoStack.pop();
    }
    
    public void push(Command c) {
        undoStack.push(c);
    }
    
}
