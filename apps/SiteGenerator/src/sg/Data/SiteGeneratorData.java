/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.Data;

import djf.components.AppDataComponent;
import sg.SiteGeneratorApp;

/**
 *
 * @author Brandon
 */
public class SiteGeneratorData implements AppDataComponent{
    SiteGeneratorApp app;
    MeetingTimesData meetingTimesData;
    OfficeHoursData officeHoursData;
    ScheduleData scheduleData;
    SiteData siteData;
    SyllabusData syllabusData;
    
    public SiteGeneratorData(SiteGeneratorApp initApp) {
        app = initApp;
        initDataComponents();
    }
    
    public void initDataComponents(){
        officeHoursData = new OfficeHoursData(app);
        syllabusData = new SyllabusData(app);
        siteData = new SiteData(app);
        meetingTimesData = new MeetingTimesData(app);
        scheduleData = new ScheduleData(app);
    }
    
    @Override
    public void reset() {
        syllabusData.reset();
        officeHoursData.reset();
        siteData.reset();
        meetingTimesData.reset();
        scheduleData.reset();
    }
    public MeetingTimesData getMeetingTimesData(){
        return meetingTimesData;
    }
    public OfficeHoursData getOfficeHoursData(){
        return officeHoursData;
    }
    public ScheduleData getScheduleData(){
        return scheduleData;
    }
    public SiteData getSiteData(){
        return siteData;
    }
    public SyllabusData getSyllabusData(){
        return syllabusData;
    }
}
