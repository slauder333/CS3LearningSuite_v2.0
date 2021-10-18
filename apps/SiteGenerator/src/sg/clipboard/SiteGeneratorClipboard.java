/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.clipboard;

import djf.components.AppClipboardComponent;
import djf.modules.AppGUIModule;
import java.util.ArrayList;
import java.util.Iterator;
import javafx.scene.control.TableView;
import sg.SiteGeneratorApp;

/**
 *
 * @author Brandon
 */
public class SiteGeneratorClipboard implements AppClipboardComponent{
    public SiteGeneratorClipboard(SiteGeneratorApp initApp) {
        
    }
    
    @Override
    public void cut() {
        
    }

    @Override
    public void copy() {
        
    }
    
    @Override
    public void paste() {
        
    }    

    @Override
    public boolean hasSomethingToCut() {
        return true;
    }

    @Override
    public boolean hasSomethingToCopy() {
        return true;
    }

    @Override
    public boolean hasSomethingToPaste() {
        return true;
    }
}
