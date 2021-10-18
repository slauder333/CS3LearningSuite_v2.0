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

/**
 *
 * @author brand
 */
public class AddMeetingTime_Transaction implements jTPS_Transaction{
    
    MeetingTimesData data;
    Object lec;
    
    public AddMeetingTime_Transaction(MeetingTimesData initdata, Object initlec){
        data = initdata;
        lec = initlec;
    }
    
    @Override
    public void doTransaction(){
        if(lec instanceof Lecture)
        data.addLecture((Lecture)lec);
        if(lec instanceof Recitation)
        data.addRecitation((Recitation)lec);
        if(lec instanceof Lab)
        data.addLab((Lab)lec);
    }
    @Override
    public void undoTransaction(){
        if(lec instanceof Lecture)
        data.removeLecture((Lecture)lec);
        if(lec instanceof Recitation)
        data.removeRecitation((Recitation)lec);
        if(lec instanceof Lab)
        data.removeLab((Lab)lec);
    }
}
