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
public class Lab {
    private final StringProperty section;
    private final StringProperty dayTime;
    private final StringProperty room;
    private final StringProperty ta1;
    private final StringProperty ta2;
    
    
    public Lab(String initSection, String initdayTime, String initRoom, String initTA1, String initTA2) {
        section = new SimpleStringProperty(initSection);
        dayTime = new SimpleStringProperty(initdayTime);
        room = new SimpleStringProperty(initRoom);
        ta1 = new SimpleStringProperty (initTA1);
        ta2 = new SimpleStringProperty (initTA2);
    }
    
    public Lab(Lab another){
        this.section = new SimpleStringProperty(another.getSection());
        this.dayTime = new SimpleStringProperty(another.getDayTime());
        this.room = new SimpleStringProperty(another.getRoom());
        this.ta1 = new SimpleStringProperty (another.getTa1());
        this.ta2 = new SimpleStringProperty (another.getTa2());
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
    
    public String getDayTime() {
        return dayTime.get();
    }

    public void setDayTime(String initName) {
        dayTime.set(initName);
    }
    
    public StringProperty dayTimeProperty() {
        return dayTime;
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
    
    public String getTa1() {
        return ta1.get();
    }

    public void setTa1(String initName) {
        ta1.set(initName);
    }
    
    public StringProperty ta1Property() {
        return ta1;
    }
    
    public String getTa2() {
        return ta2.get();
    }

    public void setTa2(String initName) {
        ta2.set(initName);
    }
    
    public StringProperty ta2Property() {
        return ta2;
    }
}
