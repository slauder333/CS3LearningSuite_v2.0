/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.transactions;

import jtps.jTPS_Transaction;
import sg.Data.Lab;
import sg.Data.Lecture;
import sg.Data.MeetingTimesData;
import sg.Data.Recitation;
import sg.Data.ScheduleData;
import sg.Data.ScheduleItem;

/**
 *
 * @author brand
 */
public class RemoveScheduleItem_Transaction implements jTPS_Transaction{
    
    ScheduleData data;
    ScheduleItem item;
    
    public RemoveScheduleItem_Transaction(ScheduleData initdata, ScheduleItem inititem){
        data = initdata;
        item = inititem;
    }
    
    @Override
    public void doTransaction(){
        data.removeItem(item);
    }
    @Override
    public void undoTransaction(){
        data.addItem(item);
    }
}
