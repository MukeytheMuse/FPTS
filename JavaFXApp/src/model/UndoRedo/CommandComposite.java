/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.UndoRedo;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author ericepstein
 */
public class CommandComposite implements Command {

    List<Command> commands; 
        
    public CommandComposite() {

        commands = new ArrayList();

    }
    
    @Override
    public void execute() {
        for (Command c : commands) {
            c.execute();
        }

        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void undo() {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        for (Command c : commands) {
            c.undo();
        }
    }

    @Override
    public void addChild(Command c) {
        commands.add(c);
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void removeChild(Command c) {
        commands.remove(c);
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
