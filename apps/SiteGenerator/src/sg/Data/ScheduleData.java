/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.Data;

import djf.modules.AppGUIModule;
import java.time.LocalDate;
import java.util.ArrayList;
import javafx.collections.ObservableList;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableView;
import sg.SiteGeneratorApp;
import static sg.SiteGeneratorPropertyType.*;

/**
 *
 * @author Brandon
 */
public class ScheduleData {
    SiteGeneratorApp app;
    ObservableList<ScheduleItem> items;
    ArrayList<ScheduleItem> itemData = new ArrayList<>();
    LocalDate startDate;
    LocalDate endDate;
    
    public ScheduleData(SiteGeneratorApp initApp) {
        // KEEP THIS FOR LATER
        app = initApp;
        AppGUIModule gui = app.getGUIModule();

        // CONSTRUCT THE LIST OF TAs FOR THE TABLE
        TableView<ScheduleItem> scheduleTableView = (TableView)gui.getGUINode(SC_SCHEDULE_TABLE_VIEW);
        items = scheduleTableView.getItems();
        
        startDate = LocalDate.now();
        endDate = LocalDate.now();
        
    }
    
    public void addItem(ScheduleItem item){
        items.add(item);
        itemData.add(item);
    }
    
    public void removeItem(ScheduleItem item){
        items.remove(item);
        itemData.remove(item);
    }
    
    public ObservableList<ScheduleItem> getScheduleItems(){
        return items;
    }
    
    public ArrayList<ScheduleItem> getScheduleItemData(){
        return itemData;
    }
    
    public void reset(){
        items.clear();
        itemData.clear();
    }
    
    public void setStartDate(LocalDate initStartDate){
        AppGUIModule gui = app.getGUIModule();
        startDate = initStartDate;
        items.clear();
        for(ScheduleItem item : itemData){
            items.add(item);
        }
        if(startDate!=null){
        for(ScheduleItem item : items){
            if(item.getDate().isBefore(startDate)){
                items.remove(item);
            }
        }
        }
        if(endDate!=null){
        for(ScheduleItem item : items){
            if(item.getDate().isAfter(endDate)){
                items.remove(item);
            }
        }
        }
        ((DatePicker)gui.getGUINode(SC_START_DATE_PICKER)).setValue(initStartDate);
    }
    public void setEndDate(LocalDate initEndDate){
        AppGUIModule gui = app.getGUIModule();
        endDate = initEndDate;
        items.clear();
        for(ScheduleItem item : itemData){
            items.add(item);
        }
        if(startDate!=null){
        for(ScheduleItem item : items){
            if(item.getDate().isBefore(startDate)){
                items.remove(item);
            }
        }
        }
        if(endDate!=null){
        for(ScheduleItem item : items){
            if(item.getDate().isAfter(endDate)){
                items.remove(item);
            }
        }
        }
        ((DatePicker)gui.getGUINode(SC_END_DATE_PICKER)).setValue(initEndDate);
    }
    
    public LocalDate getStartDate(){
        return startDate;
    }
    
    public LocalDate getEndDate(){
        return endDate;
    }
}
