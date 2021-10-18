/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.workspace;

import djf.modules.AppGUIModule;
import static djf.modules.AppGUIModule.ENABLED;
import djf.ui.AppNodesBuilder;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import properties_manager.PropertiesManager;
import sg.SiteGeneratorApp;
import static sg.SiteGeneratorPropertyType.*;
import static sg.workspace.style.SGStyle.*;

/**
 *
 * @author Brandon
 */
public class SyllabusWorkspaceComponent {
    SiteGeneratorApp app;
    VBox mainPane;
    public SyllabusWorkspaceComponent(SiteGeneratorApp initApp) {
        
        app = initApp;
        // LAYOUT THE APP
        initLayout();

        // INIT THE EVENT HANDLERS
        initControllers();

        // SETUP FOOLPROOF DESIGN FOR THIS APP
        //initFoolproofDesign();
    }

    // THIS HELPER METHOD INITIALIZES ALL THE CONTROLS IN THE WORKSPACE
    private void initLayout() {
        PropertiesManager props = PropertiesManager.getPropertiesManager();
        
        
        // THIS WILL BUILD ALL OF OUR JavaFX COMPONENTS FOR US
        AppNodesBuilder syBuilder = app.getGUIModule().getNodesBuilder();
        mainPane = syBuilder.buildVBox(SY_MAIN_PANE, null, CLASS_OH_BACKGROUND_PANE, ENABLED);
        
        TextArea descriptionBox = syBuilder.buildTextArea(SY_DESCRIPTION_BOX, null, EMPTY_TEXT, ENABLED);
        TextArea topicsBox = syBuilder.buildTextArea(SY_TOPICS_BOX, null, EMPTY_TEXT, ENABLED);
        TextArea prerequisitesBox = syBuilder.buildTextArea(SY_PREREQUISITES_BOX, null, EMPTY_TEXT, ENABLED);
        TextArea outcomesBox = syBuilder.buildTextArea(SY_OUTCOMES_BOX, null, EMPTY_TEXT, ENABLED);
        TextArea textbooksBox = syBuilder.buildTextArea(SY_TEXTBOOKS_BOX, null, EMPTY_TEXT, ENABLED);
        TextArea gradedComponentsBox = syBuilder.buildTextArea(SY_GRADED_COMPONENTS_BOX, null, EMPTY_TEXT, ENABLED);
        TextArea gradingNoteBox = syBuilder.buildTextArea(SY_GRADING_NOTE_BOX, null, EMPTY_TEXT, ENABLED);
        TextArea academicDishonestyBox = syBuilder.buildTextArea(SY_ACADEMIC_DISHONESTY_BOX, null, EMPTY_TEXT, ENABLED);
        TextArea specialAssistanceBox = syBuilder.buildTextArea(SY_SPECIAL_ASSISTANCE_BOX, null, EMPTY_TEXT, ENABLED);
        
        syBuilder.buildTitledPane(SY_DESCRIPTION_PANE, mainPane, CLASS_SI_PANE, ENABLED, SY_DESCRIPTION_LABEL, descriptionBox);
        syBuilder.buildTitledPane(SY_TOPICS_PANE, mainPane, CLASS_SI_PANE, ENABLED, SY_TOPICS_LABEL, topicsBox);
        syBuilder.buildTitledPane(SY_PREREQUISITES_PANE, mainPane, CLASS_SI_PANE, ENABLED, SY_PREREQUISITES_LABEL, prerequisitesBox);
        syBuilder.buildTitledPane(SY_OUTCOMES_PANE, mainPane, CLASS_SI_PANE, ENABLED, SY_OUTCOMES_LABEL, outcomesBox);
        syBuilder.buildTitledPane(SY_TEXTBOOKS_PANE, mainPane, CLASS_SI_PANE, ENABLED, SY_TEXTBOOKS_LABEL, textbooksBox);
        syBuilder.buildTitledPane(SY_GRADED_COMPONENTS_PANE, mainPane, CLASS_SI_PANE, ENABLED, SY_GRADED_COMPONENTS_LABEL, gradedComponentsBox);
        syBuilder.buildTitledPane(SY_GRADING_NOTE_PANE, mainPane, CLASS_SI_PANE, ENABLED, SY_GRADING_NOTE_LABEL, gradingNoteBox);
        syBuilder.buildTitledPane(SY_ACADEMIC_DISHONESTY_PANE, mainPane, CLASS_SI_PANE, ENABLED, SY_ACADEMIC_DISHONESTY_LABEL, academicDishonestyBox);
        syBuilder.buildTitledPane(SY_SPECIAL_ASSISTANCE_PANE, mainPane, CLASS_SI_PANE, ENABLED, SY_SPECIAL_ASSISTANCE_LABEL, specialAssistanceBox);
    }
    public VBox getPane(){
        return mainPane;
    }
    
    public void initControllers(){
        AppGUIModule gui = app.getGUIModule();
        ((TextArea) gui.getGUINode(SY_DESCRIPTION_BOX)).textProperty().addListener((cl) -> {
        app.getFileModule().markAsEdited(true);
        });
        ((TextArea) gui.getGUINode(SY_TOPICS_BOX)).textProperty().addListener((cl) -> {
        app.getFileModule().markAsEdited(true);
        });
        ((TextArea) gui.getGUINode(SY_PREREQUISITES_BOX)).textProperty().addListener((cl) -> {
        app.getFileModule().markAsEdited(true);
        });
        ((TextArea) gui.getGUINode(SY_OUTCOMES_BOX)).textProperty().addListener((cl) -> {
        app.getFileModule().markAsEdited(true);
        });
        ((TextArea) gui.getGUINode(SY_TEXTBOOKS_BOX)).textProperty().addListener((cl) -> {
        app.getFileModule().markAsEdited(true);
        });
        ((TextArea) gui.getGUINode(SY_GRADED_COMPONENTS_BOX)).textProperty().addListener((cl) -> {
        app.getFileModule().markAsEdited(true);
        });
        ((TextArea) gui.getGUINode(SY_GRADING_NOTE_BOX)).textProperty().addListener((cl) -> {
        app.getFileModule().markAsEdited(true);
        });
        ((TextArea) gui.getGUINode(SY_ACADEMIC_DISHONESTY_BOX)).textProperty().addListener((cl) -> {
        app.getFileModule().markAsEdited(true);
        });
        ((TextArea) gui.getGUINode(SY_SPECIAL_ASSISTANCE_BOX)).textProperty().addListener((cl) -> {
        app.getFileModule().markAsEdited(true);
        });
    }
}