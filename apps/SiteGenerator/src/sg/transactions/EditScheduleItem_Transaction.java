/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.transactions;

import java.time.LocalDate;
import javafx.beans.property.StringProperty;
import jtps.jTPS_Transaction;
import sg.Data.OfficeHoursData;
import sg.Data.ScheduleItem;
import sg.Data.TeachingAssistantPrototype;

/**
 *
 * @author brand
 */
public class EditScheduleItem_Transaction implements jTPS_Transaction {
    ScheduleItem item;
    ScheduleItem newItem;
    String type;
    LocalDate date;
    String title;
    String topic;
    String link;
    
    public EditScheduleItem_Transaction(ScheduleItem inititem , ScheduleItem initnewItem) {
        item = inititem;
        newItem = initnewItem;
        type = item.getType();
        date = item.getDate();
        title = item.getTitle();
        topic = item.getTopic();
        link = item.getLink();
    }

    @Override
    public void doTransaction() {
        item.setType(newItem.getType());
        item.setDate(newItem.getDate());
        item.setTitle(newItem.getTitle());
        item.setTopic(newItem.getTopic());
        item.setLink(newItem.getLink());
    }

    @Override
    public void undoTransaction() {
        item.setType(type);
        item.setDate(date);
        item.setTitle(title);
        item.setTopic(topic);
        item.setLink(link);
    }
}