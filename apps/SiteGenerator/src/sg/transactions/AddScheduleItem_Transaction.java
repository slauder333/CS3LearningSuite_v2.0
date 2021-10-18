/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.transactions;

import jtps.jTPS_Transaction;
import sg.Data.OfficeHoursData;
import sg.Data.ScheduleData;
import sg.Data.ScheduleItem;
import sg.Data.TeachingAssistantPrototype;

/**
 *
 * @author brand
 */
public class AddScheduleItem_Transaction implements jTPS_Transaction {
    ScheduleData data;
    ScheduleItem item;
    
    public AddScheduleItem_Transaction(ScheduleData initData, ScheduleItem initItem) {
        data = initData;
        item = initItem;
    }

    @Override
    public void doTransaction() {
        data.addItem(item);        
    }

    @Override
    public void undoTransaction() {
        data.removeItem(item);
    }
}