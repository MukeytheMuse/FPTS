/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.util.TimerTask;
import java.util.Date;

/**
 *
 * @author ericepstein
 */
public class WebService extends TimerTask {
    
    WebServiceReader wsr;
    
    public WebService(WebServiceReader wsr) {
        this.wsr = wsr;
    }
    
    @Override
    public void run() {
       wsr.run();
    }
    
}
