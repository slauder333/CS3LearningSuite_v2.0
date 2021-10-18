/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sg;

import djf.AppTemplate;
import djf.components.AppClipboardComponent;
import djf.components.AppDataComponent;
import djf.components.AppFileComponent;
import djf.components.AppWorkspaceComponent;
import java.util.Locale;
import static javafx.application.Application.launch;
import java.util.Locale;
import static javafx.application.Application.launch;
import sg.Data.SiteGeneratorData;
import sg.Files.SiteGeneratorFiles;
import sg.clipboard.SiteGeneratorClipboard;
import sg.workspace.SiteGeneratorWorkspace;

/**
 *
 * @author Brandon
 */
public class SiteGeneratorApp extends AppTemplate{

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
	Locale.setDefault(Locale.US);
	launch(args);
    }

    @Override
    public AppClipboardComponent buildClipboardComponent(AppTemplate app) {
        return new SiteGeneratorClipboard(this);
    }

    @Override
    public AppDataComponent buildDataComponent(AppTemplate app) {
        return new SiteGeneratorData(this);
    }

    @Override
    public AppFileComponent buildFileComponent() {
        return new SiteGeneratorFiles(this);
    }

    @Override
    public AppWorkspaceComponent buildWorkspaceComponent(AppTemplate app) {
        return new SiteGeneratorWorkspace(this);        
    }
    
}
