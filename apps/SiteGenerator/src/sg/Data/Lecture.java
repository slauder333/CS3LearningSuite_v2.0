/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.Data;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author Brandon
 */
public class Lecture {
    private final StringProperty section;
    private final StringProperty day;
    private final StringProperty room;
    private final StringProperty time;
   
    
    public Lecture(String initSection, String initday, String initTime, String initRoom) {
        section = new SimpleStringProperty(initSection);
        day = new SimpleStringProperty(initday);
        room = new SimpleStringProperty(initRoom);
        time = new SimpleStringProperty (initTime);
    }
    
    public Lecture(Lecture another){
        this.section = new SimpleStringProperty(another.getSection());
        this.day = new SimpleStringProperty(another.getDay());
        this.room = new SimpleStringProperty(another.getRoom());
        this.time = new SimpleStringProperty (another.getTime());
    }
    
    public String getSection() {
        return section.get();
    }

    public void setSection(String initName) {
        section.set(initName);
    }
    
    public StringProperty sectionProperty() {
        return section;
    }
    
    public String getDay() {
        return day.get();
    }

    public void setDay(String initName) {
        day.set(initName);
    }
    
    public StringProperty dayProperty() {
        return day;
    }
    
    public String getRoom() {
        return room.get();
    }

    public void setRoom(String initName) {
        room.set(initName);
    }
    
    public StringProperty roomProperty() {
        return room;
    }
    
    public String getTime() {
        return time.get();
    }

    public void setTime(String initName) {
        time.set(initName);
    }
    
    public StringProperty timeProperty() {
        return time;
    }
}
