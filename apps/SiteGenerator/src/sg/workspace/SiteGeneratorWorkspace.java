/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.workspace;

import djf.components.AppWorkspaceComponent;
import djf.modules.AppFoolproofModule;
import djf.modules.AppGUIModule;
import static djf.modules.AppGUIModule.ENABLED;
import djf.ui.AppNodesBuilder;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.SplitPane;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import static javafx.scene.control.TabPane.STYLE_CLASS_FLOATING;
import static javafx.scene.control.TabPane.TabClosingPolicy.UNAVAILABLE;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Toggle;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import properties_manager.PropertiesManager;
import sg.SiteGeneratorApp;
import static sg.SiteGeneratorPropertyType.*;

/**
 *
 * @author Brandon
 */
public class SiteGeneratorWorkspace extends AppWorkspaceComponent{
    
    MeetingTimesWorkspaceComponent meetingTimesWorkspaceComponent;
    OfficeHoursWorkspaceComponent officeHoursWorkspaceComponent;
    ScheduleWorkspaceComponent scheduleWorkspaceComponent;
    SiteWorkspaceComponent siteWorkspaceComponent;
    SyllabusWorkspaceComponent syllabusWorkspaceComponent;
    
    public SiteGeneratorWorkspace(SiteGeneratorApp app) {
        super(app);

        // LAYOUT THE APP
        initWorkspaceComponents();

    }

    public void initWorkspaceComponents(){
        meetingTimesWorkspaceComponent = new MeetingTimesWorkspaceComponent((SiteGeneratorApp)app);
        officeHoursWorkspaceComponent = new OfficeHoursWorkspaceComponent((SiteGeneratorApp)app);
        scheduleWorkspaceComponent = new ScheduleWorkspaceComponent((SiteGeneratorApp)app);
        siteWorkspaceComponent = new SiteWorkspaceComponent((SiteGeneratorApp)app);
        syllabusWorkspaceComponent = new SyllabusWorkspaceComponent((SiteGeneratorApp)app);
        TabPane tabPane = new TabPane();
        tabPane.setTabClosingPolicy(UNAVAILABLE);
        Tab siteTab = new Tab();
        Tab syllabusTab = new Tab();
        Tab meetingTimesTab = new Tab();
        Tab officeHoursTab = new Tab();
        Tab scheduleTab = new Tab();
        siteTab.setText("Site");
        syllabusTab.setText("Syllabus");
        meetingTimesTab.setText("Meeting Times");
        officeHoursTab.setText("OfficeHours");
        scheduleTab.setText("Schedule");
       
        
        
        
        
        
        officeHoursTab.setContent(officeHoursWorkspaceComponent.getPane());
        siteTab.setContent(siteWorkspaceComponent.getPane());
        syllabusTab.setContent(syllabusWorkspaceComponent.getPane());
        meetingTimesTab.setContent(meetingTimesWorkspaceComponent.getPane());
        scheduleTab.setContent(scheduleWorkspaceComponent.getPane());
        
        tabPane.getTabs().add(siteTab);
        tabPane.getTabs().add(syllabusTab);
        tabPane.getTabs().add(meetingTimesTab);
        tabPane.getTabs().add(officeHoursTab);
        tabPane.getTabs().add(scheduleTab);
        
        workspace = new BorderPane();
        ((BorderPane)workspace).setCenter(tabPane);
        tabPane.setTabMinWidth(300);
    }
    @Override
    public void showNewDialog() {
        // WE AREN'T USING THIS FOR THIS APPLICATION
    }
    public MeetingTimesWorkspaceComponent getMeetingTimesWorkspaceComponent(){
        return meetingTimesWorkspaceComponent;
    }
    public OfficeHoursWorkspaceComponent getOfficeHoursWorkspaceComponent(){
        return officeHoursWorkspaceComponent;
    }
    public ScheduleWorkspaceComponent getScheduleWorkspaceComponent(){
        return scheduleWorkspaceComponent;
    }
    public SiteWorkspaceComponent getSiteWorkspaceComponent(){
        return siteWorkspaceComponent;
    }
    public SyllabusWorkspaceComponent getSyllabusWorkspaceComponent(){
        return syllabusWorkspaceComponent;
    }
}

