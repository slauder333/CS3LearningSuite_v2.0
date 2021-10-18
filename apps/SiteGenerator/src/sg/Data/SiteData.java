/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.Data;

import djf.modules.AppGUIModule;
import java.util.HashMap;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import sg.SiteGeneratorApp;
import static sg.SiteGeneratorPropertyType.*;

/**
 *
 * @author Brandon
 */
public class SiteData {
    SiteGeneratorApp app;
    private final StringProperty instructorName = new SimpleStringProperty();
    private final StringProperty instructorRoom = new SimpleStringProperty();
    private final StringProperty instructorEmail = new SimpleStringProperty();
    private final StringProperty instructorHomePage = new SimpleStringProperty();
    private final StringProperty instructorOfficeHours = new SimpleStringProperty();
    private String subject;
    private String number;
    private String semester;
    private String year;
    private String title;
    private String favicon="";
    private String navbar="";
    private String leftFooter="";
    private String rightFooter="";
    HashMap<String, String> siteImageArray;
    HashMap<String, StringProperty> instructorDataArray;
    HashMap<String, String> siteDataArray;
    AppGUIModule gui;
    
    SiteData(SiteGeneratorApp initApp){
        app = initApp;
        gui = app.getGUIModule();
        instructorName.bindBidirectional(((TextField)gui.getGUINode(SI_NAME_BOX)).textProperty());
        instructorRoom.bindBidirectional(((TextField)gui.getGUINode(SI_ROOM_BOX)).textProperty());
        instructorEmail.bindBidirectional(((TextField)gui.getGUINode(SI_EMAIL_BOX)).textProperty());
        instructorHomePage.bindBidirectional(((TextField)gui.getGUINode(SI_HOME_PAGE_BOX)).textProperty());
        instructorOfficeHours.bindBidirectional(((TextArea)gui.getGUINode(SI_BOTTOM_INSTRUCTOR_BOX)).textProperty());
        subject = (((ComboBox)gui.getGUINode(SI_SUBJECT_BOX)).getValue().toString());
        number=(((ComboBox)gui.getGUINode(SI_NUMBER_BOX)).getValue().toString());
        semester=(((ComboBox)gui.getGUINode(SI_SEMESTER_BOX)).getValue().toString());
        year=(((ComboBox)gui.getGUINode(SI_YEAR_BOX)).getValue().toString());
        title=(((TextField)gui.getGUINode(SI_TITLE_BOX)).getText());
        
        instructorDataArray = new HashMap<>();
        instructorDataArray.put("name", instructorName);
        instructorDataArray.put("room", instructorRoom);
        instructorDataArray.put("email", instructorEmail);
        instructorDataArray.put("link", instructorHomePage);
        instructorDataArray.put("hours", instructorOfficeHours);
        
        siteDataArray = new HashMap<>();
        siteDataArray.put("subject", subject);
        siteDataArray.put("number", number);
        siteDataArray.put("semester", semester);
        siteDataArray.put("year", year);
        siteDataArray.put("title", title);
        
        siteImageArray = new HashMap<>();
        siteImageArray.put("favicon", favicon);
        siteImageArray.put("navbar", navbar);
        siteImageArray.put("bottom_left", leftFooter);
        siteImageArray.put("bottom_right", rightFooter);
    }
    
    public void reset(){
        siteDataArray.forEach((k ,v) -> {
            v=("");
        });
        
        this.setFavicon("");
        this.setNavbar("");
        this.setBottomLeft("");
        this.setBottomRight("");
    }
    
    public HashMap<String, String> getSiteDataArray(){
        return siteDataArray;
    }
    
    public HashMap<String, StringProperty> getInstructorDataArray(){
        return instructorDataArray;
    }
    
    public void setSubject(String str){
        subject=(str);
    }
    
    public void setNumber(String str){
        number=(str);
    }
    
    public void setSemester(String str){
        semester=(str);
    }
    
    public void setYear(String str){
        year=(str);
    }
    
    public void setTitle(String str){
        title=(str);
    }
    
    public String getSubject(){
        return subject;
    }
    
    public String getNumber(){
        return number;
    }
    
    public String getSemester(){
        return semester;
    }
    
    public String getYear(){
        return year;
    }
    
    public String getTitle(){
        return title;
    }
    
    public boolean hasHomePage(){
        return ((CheckBox)gui.getGUINode(SI_HOME_CHECK)).isSelected();
    }
    
    public boolean hasSyllabusPage(){
        return ((CheckBox)gui.getGUINode(SI_SYLLABUS_CHECK)).isSelected();
    }
    
    public boolean hasSchedulePage(){
        return ((CheckBox)gui.getGUINode(SI_SCHEDULE_CHECK)).isSelected();
    }
    
    public boolean hasHWsPage(){
        return ((CheckBox)gui.getGUINode(SI_HWS_CHECK)).isSelected();
    }
    
    public void setFavicon(String str){
        favicon=(str);
        Image img = new Image("file:"+favicon); 
        ((ImageView)gui.getGUINode(SI_FAVICON_IMAGEVIEW)).setImage(img);
    }
    
    public void setNavbar(String str){
        navbar=(str);
        Image img = new Image("file:"+navbar); 
        ((ImageView)gui.getGUINode(SI_NAVBAR_IMAGEVIEW)).setImage(img);
    }
    
    public void setBottomLeft(String str){
        leftFooter=(str);
        Image img = new Image("file:"+leftFooter); 
        ((ImageView)gui.getGUINode(SI_LEFT_FOOTER_IMAGEVIEW)).setImage(img);
    }
    
    public void setBottomRight(String str){
        rightFooter=(str);
        Image img = new Image("file:"+rightFooter); 
        ((ImageView)gui.getGUINode(SI_RIGHT_FOOTER_IMAGEVIEW)).setImage(img);
    }
    
    public String getFavicon(){
        return favicon;
    }
    
    public String getNavbar(){
        return navbar;
    }
    
    public String getLeftFooter(){
        return leftFooter;
    }
    
    public String getRightFooter(){
        return rightFooter;
    }
}
