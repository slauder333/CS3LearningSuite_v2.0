/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.Data;

import djf.modules.AppGUIModule;
import java.util.ArrayList;
import java.util.HashMap;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.control.TextArea;
import sg.SiteGeneratorApp;
import static sg.SiteGeneratorPropertyType.*;

/**
 *
 * @author Brandon
 */
public class SyllabusData {
    SiteGeneratorApp app;
    private final StringProperty description = new SimpleStringProperty();
    private final StringProperty topics = new SimpleStringProperty();
    private final StringProperty prerequisites = new SimpleStringProperty();
    private final StringProperty outcomes = new SimpleStringProperty();
    private final StringProperty textbooks = new SimpleStringProperty();
    private final StringProperty gradedComponents = new SimpleStringProperty();
    private final StringProperty gradingNote = new SimpleStringProperty();
    private final StringProperty academicDishonesty = new SimpleStringProperty();
    private final StringProperty specialAssistance = new SimpleStringProperty();
    HashMap<String, StringProperty> syllabusDataArray;
    
    
    SyllabusData(SiteGeneratorApp initApp){
        app = initApp;
        AppGUIModule gui = app.getGUIModule();
        description.bindBidirectional(((TextArea)gui.getGUINode(SY_DESCRIPTION_BOX)).textProperty());
        topics.bindBidirectional(((TextArea)gui.getGUINode(SY_TOPICS_BOX)).textProperty());
        prerequisites.bindBidirectional(((TextArea)gui.getGUINode(SY_PREREQUISITES_BOX)).textProperty());
        outcomes.bindBidirectional(((TextArea)gui.getGUINode(SY_OUTCOMES_BOX)).textProperty());
        textbooks.bindBidirectional(((TextArea)gui.getGUINode(SY_TEXTBOOKS_BOX)).textProperty());
        gradedComponents.bindBidirectional(((TextArea)gui.getGUINode(SY_GRADED_COMPONENTS_BOX)).textProperty());
        gradingNote.bindBidirectional(((TextArea)gui.getGUINode(SY_GRADING_NOTE_BOX)).textProperty());
        academicDishonesty.bindBidirectional(((TextArea)gui.getGUINode(SY_ACADEMIC_DISHONESTY_BOX)).textProperty());
        specialAssistance.bindBidirectional(((TextArea)gui.getGUINode(SY_SPECIAL_ASSISTANCE_BOX)).textProperty());
        syllabusDataArray = new HashMap<>();
        syllabusDataArray.put("description", description);
        syllabusDataArray.put("topics", topics);
        syllabusDataArray.put("prerequisites", prerequisites);
        syllabusDataArray.put("outcomes", outcomes);
        syllabusDataArray.put("textbooks", textbooks);
        syllabusDataArray.put("gradedComponents", gradedComponents);
        syllabusDataArray.put("gradingNote", gradingNote);
        syllabusDataArray.put("academicDishonesty", academicDishonesty);
        syllabusDataArray.put("specialAssistance", specialAssistance);
        
    }
    
    public void reset(){
        syllabusDataArray.forEach((k ,v) -> {
            v.setValue("");
        });
    }
    
    public HashMap<String, StringProperty> getSyllabusDataArray(){
        return syllabusDataArray;
    }
    
    
}
