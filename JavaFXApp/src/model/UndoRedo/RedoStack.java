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
public class RedoStack {
    
    Stack<Command> redoStack;
    
    public RedoStack() {
        redoStack = new Stack<Command>();
    }
    
    public Command pop() {
        return redoStack.pop();
    }
    
    public void push(Command c) {
        redoStack.push(c);
    }
    
}
