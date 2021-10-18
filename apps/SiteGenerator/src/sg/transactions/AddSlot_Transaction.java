/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.transactions;

import jtps.jTPS_Transaction;
import sg.Data.TeachingAssistantPrototype;
import sg.Data.TimeSlot;
import sg.Data.TimeSlot.DayOfWeek;


/**
 *
 * @author Brandon
 */
public class AddSlot_Transaction implements jTPS_Transaction{
    DayOfWeek dow;
    TimeSlot slot;
    TeachingAssistantPrototype ta;
    
    public AddSlot_Transaction(TimeSlot initSlot, DayOfWeek initDow, TeachingAssistantPrototype initTa) {
        slot = initSlot;
        dow = initDow;
        ta = initTa;
        
    }

    @Override
    public void doTransaction() {
        slot.addTA(dow, ta);     
    }

    @Override
    public void undoTransaction() {
        slot.addTA(dow, ta);
    }
}
