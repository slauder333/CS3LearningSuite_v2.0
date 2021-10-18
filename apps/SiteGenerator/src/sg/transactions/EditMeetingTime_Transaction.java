/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.transactions;

import jtps.jTPS_Transaction;
import sg.Data.Lab;
import sg.Data.Lecture;
import sg.Data.OfficeHoursData;
import sg.Data.Recitation;
import sg.Data.TeachingAssistantPrototype;

/**
 *
 * @author brand
 */
public class EditMeetingTime_Transaction implements jTPS_Transaction {
    Object obj;
    Object obj1;
    String section;
    String days;
    String time;
    String room;
    String dayTime;
    String ta1;
    String ta2;
    
    public EditMeetingTime_Transaction(Object initobj, Object initobj1) {
        obj1 = initobj1;
        obj = initobj;
        if(obj instanceof Lecture){
            section = ((Lecture) obj).getSection();
            days = ((Lecture) obj).getDay();
            time = ((Lecture) obj).getTime();
            room = ((Lecture) obj).getRoom();
        }
        if(obj instanceof Recitation){
            section = ((Recitation) obj).getSection();
            dayTime = ((Recitation) obj).getDayTime();
            ta1 = ((Recitation) obj).getTa1();
            room = ((Recitation) obj).getRoom();
            ta2 = ((Recitation) obj).getTa2();
        }
        if(obj instanceof Lab){
            section = ((Lab) obj).getSection();
            dayTime = ((Lab) obj).getDayTime();
            ta1 = ((Lab) obj).getTa1();
            room = ((Lab) obj).getRoom();
            ta2 = ((Lab) obj).getTa2();
        }
    }

    @Override
    public void doTransaction() {
        if(obj instanceof Lecture){
            ((Lecture) obj).setSection(((Lecture)obj1).getSection());
            ((Lecture) obj).setDay(((Lecture)obj1).getDay());
            ((Lecture) obj).setTime(((Lecture)obj1).getTime());
            ((Lecture) obj).setRoom(((Lecture)obj1).getRoom());
        }
        if(obj instanceof Recitation){
            ((Recitation) obj).setSection(((Recitation)obj1).getSection());
            ((Recitation) obj).setDayTime(((Recitation)obj1).getDayTime());
            ((Recitation) obj).setTa1(((Recitation)obj1).getTa1());
            ((Recitation) obj).setRoom(((Recitation)obj1).getRoom());
            ((Recitation) obj).setTa2(((Recitation)obj1).getTa2());
        }
        if(obj instanceof Lab){
            ((Lab) obj).setSection(((Lab)obj1).getSection());
            ((Lab) obj).setDayTime(((Lab)obj1).getDayTime());
            ((Lab) obj).setTa1(((Lab)obj1).getTa1());
            ((Lab) obj).setRoom(((Lab)obj1).getRoom());
            ((Lab) obj).setTa2(((Lab)obj1).getTa2());
        }
    }

    @Override
    public void undoTransaction() {
        if(obj instanceof Lecture){
            ((Lecture) obj).setSection(section);
            ((Lecture) obj).setDay(days);
            ((Lecture) obj).setTime(time);
            ((Lecture) obj).setRoom(room);
        }
        if(obj instanceof Recitation){
            ((Recitation) obj).setSection(section);
            ((Recitation) obj).setDayTime(dayTime);
            ((Recitation) obj).setTa1(ta1);
            ((Recitation) obj).setRoom(room);
            ((Recitation) obj).setTa2(ta2);
        }
        if(obj instanceof Lab){
            ((Lab) obj).setSection(section);
            ((Lab) obj).setDayTime(dayTime);
            ((Lab) obj).setTa1(ta1);
            ((Lab) obj).setRoom(room);
            ((Lab) obj).setTa2(ta2);
        }
    }
}