/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.transactions;

import java.util.ArrayList;
import jtps.jTPS_Transaction;
import sg.Data.ScheduleData;
import sg.Data.ScheduleItem;

/**
 *
 * @author brand
 */
public class ClearScheduleItem_Transaction implements jTPS_Transaction {
    ScheduleData data;
    ArrayList<ScheduleItem> list;
    
    public ClearScheduleItem_Transaction(ScheduleData initData) {
        data = initData;
        list = new ArrayList();
    }

    @Override
    public void doTransaction() {
        for(ScheduleItem item : data.getScheduleItemData()){
            list.add(item);
        }
        data.reset();
    }

    @Override
    public void undoTransaction() {
        for(ScheduleItem item : list){
            data.addItem(item);
        }
    }
}
