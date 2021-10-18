/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.Data;

import java.time.LocalDate;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author Brandon
 */
public class ScheduleItem {
    private final StringProperty type;
    private final LocalDate date;
    private final StringProperty title;
    private final StringProperty topic;
    private final StringProperty link;
    
    
    public ScheduleItem(String initType, LocalDate initDate, String initTitle, String initTopic, String initLink) {
        type = new SimpleStringProperty(initType);
        date = initDate;
        title = new SimpleStringProperty(initTitle);
        topic = new SimpleStringProperty (initTopic);
        link = new SimpleStringProperty (initLink);
    }
    
    public String getType() {
        return type.get();
    }

    public void setType(String initName) {
        type.set(initName);
    }
    
    public StringProperty typeProperty() {
        return type;
    }
    
    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date1) {
        date1.adjustInto(date);
    }
    
    public StringProperty dateProperty() {
        StringProperty dateProperty = new SimpleStringProperty(date.toString());
        return dateProperty;
    }
    
    public String getTitle() {
        return title.get();
    }

    public void setTitle(String initName) {
        title.set(initName);
    }
    
    public StringProperty titleProperty() {
        return title;
    }
    
    public String getTopic() {
        return topic.get();
    }

    public void setTopic(String initName) {
        topic.set(initName);
    }
    
    public StringProperty topicProperty() {
        return topic;
    }
    
    public String getLink() {
        return link.get();
    }

    public void setLink(String initName) {
        link.set(initName);
    }
    
    public StringProperty linkProperty() {
        return link;
    }
}
