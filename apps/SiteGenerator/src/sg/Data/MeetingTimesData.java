/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.Data;

import djf.modules.AppGUIModule;
import java.util.ArrayList;
import javafx.collections.ObservableList;
import javafx.scene.control.TableView;
import static sg.Data.OfficeHoursData.MAX_END_HOUR;
import static sg.Data.OfficeHoursData.MIN_START_HOUR;
import sg.SiteGeneratorApp;
import static sg.SiteGeneratorPropertyType.*;

/**
 *
 * @author Brandon
 */
public class MeetingTimesData {
    SiteGeneratorApp app;

    // NOTE THAT THIS DATA STRUCTURE WILL DIRECTLY STORE THE
    // DATA IN THE ROWS OF THE TABLE VIEW
    ObservableList<Lecture> lectures;
    ArrayList<Lecture> lectureData = new ArrayList<>();
    
    ObservableList<Lab> labs;
    ArrayList<Lab> labData = new ArrayList<>();
    
    ObservableList<Recitation> recitations;
    ArrayList<Recitation> recitationData = new ArrayList<>();
    
    public MeetingTimesData(SiteGeneratorApp initApp) {
        // KEEP THIS FOR LATER
        app = initApp;
        AppGUIModule gui = app.getGUIModule();

        // CONSTRUCT THE LIST OF TAs FOR THE TABLE
        TableView<Lecture> lectureTableView = (TableView)gui.getGUINode(MT_LECTURE_TABLEVIEW);
        lectures = lectureTableView.getItems();
        
        TableView<Recitation> recitationTableView = (TableView)gui.getGUINode(MT_RECITATION_TABLEVIEW);
        recitations = recitationTableView.getItems();
        
        TableView<Lab> labTableView = (TableView)gui.getGUINode(MT_LABS_TABLEVIEW);
        labs = labTableView.getItems();
    }
    
    public void addLecture(Lecture lecture){
        lectures.add(lecture);
        lectureData.add(lecture);
    }
    
    public void removeLecture(Lecture lecture){
        lectures.remove(lecture);
        lectureData.remove(lecture);
    }
    
    public void addRecitation(Recitation recitation){
        recitations.add(recitation);
        recitationData.add(recitation);
    }
    
    public void removeRecitation(Recitation recitation){
        recitations.remove(recitation);
        recitationData.remove(recitation);
    }
    
    public void addLab(Lab lab){
        labs.add(lab);
        labData.add(lab);
    }
    
    public void removeLab(Lab lab){
        labs.remove(lab);
        labData.remove(lab);
    }
    
    public ArrayList<Lecture> getLectureData(){
        return lectureData;
    }
    
    public ArrayList<Lab> getLabData(){
        return labData;
    }
    
    public ArrayList<Recitation> getRecitationData(){
        return recitationData;
    }
    
    public void reset(){
        lectures.clear();
        lectureData.clear();
        labs.clear();
        labData.clear();
        recitations.clear();
        recitationData.clear();
    }
}
