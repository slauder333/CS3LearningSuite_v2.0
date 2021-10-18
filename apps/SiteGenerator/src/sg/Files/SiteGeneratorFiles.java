/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.Files;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import static djf.AppPropertyType.*;
import djf.components.AppDataComponent;
import djf.components.AppFileComponent;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.Month;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import javafx.beans.property.StringProperty;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.json.JsonReader;
import javax.json.JsonWriter;
import javax.json.JsonWriterFactory;
import javax.json.stream.JsonGenerator;
import javax.json.stream.JsonParser;
import org.apache.commons.io.FileUtils;
import properties_manager.PropertiesManager;
import sg.Data.Lab;
import sg.Data.Lecture;
import sg.Data.Recitation;
import sg.Data.ScheduleItem;
import sg.Data.SiteGeneratorData;
import sg.Data.TeachingAssistantPrototype;
import sg.Data.TimeSlot;
import sg.Data.TimeSlot.DayOfWeek;
import sg.SiteGeneratorApp;


/**
 *
 * @author Brandon
 */
public class SiteGeneratorFiles implements AppFileComponent{
    SiteGeneratorApp app;
    static final String JSON_SYLLABUS = "syllabus";
    static final String JSON_INSTRUCTOR = "instructor";
    static final String JSON_KEY = "key";
    static final String JSON_TEXT = "text";
    static final String JSON_LECTURES = "lectures";
    static final String JSON_LABS = "labs";
    static final String JSON_RECITATIONS = "recitations";
    static final String JSON_SECTION = "section";
    static final String JSON_DAYS = "days";
    static final String JSON_TIME = "time";
    static final String JSON_ROOM = "room";
    static final String JSON_DAY_TIME = "day_time";
    static final String JSON_LOCATION = "location";
    static final String JSON_TA1 = "ta_1";
    static final String JSON_TA2 = "ta_2";
    static final String JSON_STARTING_MONTH = "startingMondayMonth";
    static final String JSON_STARTING_DAY = "startingMondayDay";
    static final String JSON_ENDING_MONTH = "endingFridayMonth";
    static final String JSON_ENDING_DAY = "endingFridayDay";
    static final String JSON_HOLIDAYS = "holidays";
    static final String JSON_REFERENCES = "references";
    static final String JSON_MEETING_TIMES = "meetingTimes";
    static final String JSON_SITE = "site";
    static final String JSON_SCHEDULE = "schedule";
    static final String JSON_DAY = "day";
    static final String JSON_MONTH = "month";
    static final String JSON_TITLE = "title";
    static final String JSON_LINK = "link";
    static final String JSON_TOPIC = "topic";
    static final String JSON_HWS = "hws";
    static final String JSON_PAGES = "pages";
    static final String JSON_NAME = "name";
    static final String JSON_HOME_PAGE = "Home";
    static final String JSON_HOME_LINK = "index.html";
    static final String JSON_SYLLABUS_PAGE = "Syllabus";
    static final String JSON_SYLLABUS_LINK = "syllabus.html";
    static final String JSON_SCHEDULE_PAGE = "Schedule";
    static final String JSON_SCHEDULE_LINK = "schedule.html";
    static final String JSON_HWS_PAGE = "HWs";
    static final String JSON_HWS_LINK = "hws.html";
    static final String JSON_LOGOS = "logos";
    static final String JSON_FAVICON = "favicon";
    static final String JSON_NAVBAR = "navbar";
    static final String JSON_BOTTOM_LEFT = "bottom_left";
    static final String JSON_BOTTOM_RIGHT = "bottom_right";
    static final String JSON_HREF = "href";
    static final String JSON_SRC = "src";
    static final String JSON_UNDERGRAD_TAS = "undergrad_tas";
    static final String JSON_GRAD_TAS = "grad_tas";
    static final String JSON_EMAIL = "email";
    static final String JSON_TA_TYPE = "ta_type";
    static final String JSON_OFFICE_HOURS = "officeHours";
    static final String JSON_START_HOUR = "startHour";
    static final String JSON_END_HOUR = "endHour";
    static final String JSON_START_TIME = "time";
    static final String JSON_DAY_OF_WEEK = "day";
    static final String JSON_MONDAY = "monday";
    static final String JSON_TUESDAY = "tuesday";
    static final String JSON_WEDNESDAY = "wednesday";
    static final String JSON_THURSDAY = "thursday";
    static final String JSON_FRIDAY = "friday";
    
    
    public SiteGeneratorFiles(SiteGeneratorApp initApp) {
        app = initApp;
    }

    @Override
    public void loadData(AppDataComponent data, String filePath) throws IOException {
	// CLEAR THE OLD DATA OUT
	SiteGeneratorData dataManager = (SiteGeneratorData)data;
        dataManager.reset();

	// LOAD THE JSON FILE WITH ALL THE DATA
	JsonObject json = loadJSONFile(filePath);
        
        JsonObject jsonSyllabusObject = json.getJsonObject(JSON_SYLLABUS);
        HashMap<String, StringProperty> syllabusDataArray = dataManager.getSyllabusData().getSyllabusDataArray();
        
            jsonSyllabusObject.forEach((k ,v) -> {
            syllabusDataArray.get(k).setValue(jsonSyllabusObject.getString(k));
        });
        
        
        JsonObject jsonInstructorObject = json.getJsonObject(JSON_SITE).getJsonObject(JSON_INSTRUCTOR);
        HashMap<String, StringProperty> instructorDataArray = dataManager.getSiteData().getInstructorDataArray();
        jsonInstructorObject.forEach((k ,v) -> {
            instructorDataArray.get(k).setValue(jsonInstructorObject.getString(k));
        });
        JsonObject jsonSiteObject = json.getJsonObject(JSON_SITE);
        HashMap<String, String> siteDataArray = dataManager.getSiteData().getSiteDataArray();
        jsonSiteObject.forEach((k ,v) -> {
            if(siteDataArray.containsKey(k))
            siteDataArray.put(k, jsonSiteObject.getString(k));
        });
        JsonObject jsonLogosObject = jsonSiteObject.getJsonObject(JSON_LOGOS);
        dataManager.getSiteData().setFavicon(jsonLogosObject.getJsonObject(JSON_FAVICON).getString(JSON_HREF));
        dataManager.getSiteData().setNavbar(jsonLogosObject.getJsonObject(JSON_NAVBAR).getString(JSON_SRC));
        dataManager.getSiteData().setBottomLeft(jsonLogosObject.getJsonObject(JSON_BOTTOM_LEFT).getString(JSON_SRC));
        dataManager.getSiteData().setBottomRight(jsonLogosObject.getJsonObject(JSON_BOTTOM_RIGHT).getString(JSON_SRC));
        
        JsonArray jsonLecturesArray = json.getJsonObject(JSON_MEETING_TIMES).getJsonArray(JSON_LECTURES);
        for (int i = 0; i < jsonLecturesArray.size(); i++) {
            JsonObject jsonLecture = jsonLecturesArray.getJsonObject(i);
            String section = jsonLecture.getString(JSON_SECTION);
            String days = jsonLecture.getString(JSON_DAYS);
            String time = jsonLecture.getString(JSON_TIME);
            String room = jsonLecture.getString(JSON_ROOM);
            Lecture lecture = new Lecture(section, days, time, room);
            dataManager.getMeetingTimesData().addLecture(lecture);
        }
        
        JsonArray jsonLabsArray = json.getJsonObject(JSON_MEETING_TIMES).getJsonArray(JSON_LABS);
        for (int i = 0; i < jsonLabsArray.size(); i++) {
            JsonObject jsonLab = jsonLabsArray.getJsonObject(i);
            String section = jsonLab.getString(JSON_SECTION);
            String dayTime = jsonLab.getString(JSON_DAY_TIME);
            String location = jsonLab.getString(JSON_LOCATION);
            String ta1 = jsonLab.getString(JSON_TA1);
            String ta2 = jsonLab.getString(JSON_TA2);
            Lab lab = new Lab(section, dayTime, location, ta1, ta2);
            dataManager.getMeetingTimesData().addLab(lab);
        }
        
        JsonArray jsonRecitationArray = json.getJsonObject(JSON_MEETING_TIMES).getJsonArray(JSON_RECITATIONS);
        for (int i = 0; i < jsonRecitationArray.size(); i++) {
            JsonObject jsonRecitation = jsonRecitationArray.getJsonObject(i);
            String section = jsonRecitation.getString(JSON_SECTION);
            String dayTime = jsonRecitation.getString(JSON_DAY_TIME);
            String location = jsonRecitation.getString(JSON_LOCATION);
            String ta1 = jsonRecitation.getString(JSON_TA1);
            String ta2 = jsonRecitation.getString(JSON_TA2);
            Recitation recitation = new Recitation(section, dayTime, location, ta1, ta2);
            dataManager.getMeetingTimesData().addRecitation(recitation);
        }
        
        JsonObject jsonScheduleObject = json.getJsonObject(JSON_SCHEDULE);
        dataManager.getScheduleData().setStartDate(LocalDate.of(2018, Integer.parseInt(jsonScheduleObject.getString(JSON_STARTING_MONTH)), 
                Integer.parseInt(jsonScheduleObject.getString(JSON_STARTING_DAY))));
        dataManager.getScheduleData().setEndDate(LocalDate.of(2018, Integer.parseInt(jsonScheduleObject.getString(JSON_ENDING_MONTH)), 
                Integer.parseInt(jsonScheduleObject.getString(JSON_ENDING_DAY))));
        
        
        JsonArray jsonHolidayArray = jsonScheduleObject.getJsonArray(JSON_HOLIDAYS);
        for (int i = 0; i < jsonHolidayArray.size(); i++) {
            JsonObject jsonHoliday = jsonHolidayArray.getJsonObject(i);
            LocalDate date = LocalDate.of(2018, Integer.parseInt(jsonHoliday.getString(JSON_MONTH)), Integer.parseInt(jsonHoliday.getString(JSON_DAY)));
            String title = jsonHoliday.getString(JSON_TITLE);
            String link = jsonHoliday.getString(JSON_LINK);
            ScheduleItem item = new ScheduleItem("Holiday", date, title, "", link);
            dataManager.getScheduleData().addItem(item);
        }
        
        JsonArray jsonLectureArray = jsonScheduleObject.getJsonArray(JSON_LECTURES);
        for (int i = 0; i < jsonLectureArray.size(); i++) {
            JsonObject jsonLecture = jsonLectureArray.getJsonObject(i);
            LocalDate date = LocalDate.of(2018, Integer.parseInt(jsonLecture.getString(JSON_MONTH)), Integer.parseInt(jsonLecture.getString(JSON_DAY)));
            String title = jsonLecture.getString(JSON_TITLE);
            String topic = jsonLecture.getString(JSON_TOPIC);
            String link = jsonLecture.getString(JSON_LINK);
            ScheduleItem item = new ScheduleItem("Lecture", date, title, topic, link);
            dataManager.getScheduleData().addItem(item);
        }
        
        JsonArray jsonReferencesArray = jsonScheduleObject.getJsonArray(JSON_REFERENCES);
        for (int i = 0; i < jsonReferencesArray.size(); i++) {
            JsonObject jsonReference = jsonReferencesArray.getJsonObject(i);
            LocalDate date = LocalDate.of(2018, Integer.parseInt(jsonReference.getString(JSON_MONTH)), Integer.parseInt(jsonReference.getString(JSON_DAY)));
            String title = jsonReference.getString(JSON_TITLE);
            String topic = jsonReference.getString(JSON_TOPIC);
            String link = jsonReference.getString(JSON_LINK);
            ScheduleItem item = new ScheduleItem("Reference", date, title, topic, link);
            dataManager.getScheduleData().addItem(item);
        }
        
        JsonArray jsonRecitationsArray = jsonScheduleObject.getJsonArray(JSON_RECITATIONS);
        for (int i = 0; i < jsonRecitationsArray.size(); i++) {
            JsonObject jsonReference = jsonRecitationsArray.getJsonObject(i);
            LocalDate date = LocalDate.of(2018, Integer.parseInt(jsonReference.getString(JSON_MONTH)), Integer.parseInt(jsonReference.getString(JSON_DAY)));
            String title = jsonReference.getString(JSON_TITLE);
            String topic = jsonReference.getString(JSON_TOPIC);
            String link = jsonReference.getString(JSON_LINK);
            ScheduleItem item = new ScheduleItem("Recitation", date, title, topic, link);
            dataManager.getScheduleData().addItem(item);
        }
        
        JsonArray jsonHWsArray = jsonScheduleObject.getJsonArray(JSON_HWS);
        for (int i = 0; i < jsonHWsArray.size(); i++) {
            JsonObject jsonHW = jsonHWsArray.getJsonObject(i);
            LocalDate date = LocalDate.of(2018, Integer.parseInt(jsonHW.getString(JSON_MONTH)), Integer.parseInt(jsonHW.getString(JSON_DAY)));
            String title = jsonHW.getString(JSON_TITLE);
            String topic = jsonHW.getString(JSON_TOPIC);
            String link = jsonHW.getString(JSON_LINK);
            ScheduleItem item = new ScheduleItem("HW", date, title, topic, link);
            dataManager.getScheduleData().addItem(item);
        }
        
        String startHour = json.getJsonObject(JSON_OFFICE_HOURS).getString(JSON_START_HOUR);
        String endHour = json.getJsonObject(JSON_OFFICE_HOURS).getString(JSON_END_HOUR);
        dataManager.getOfficeHoursData().initHours(startHour, endHour);

        // NOW LOAD ALL THE UNDERGRAD TAs
        JsonArray jsonUndergradTAArray = json.getJsonObject(JSON_OFFICE_HOURS).getJsonArray(JSON_UNDERGRAD_TAS);
        for (int i = 0; i < jsonUndergradTAArray.size(); i++) {
            JsonObject jsonTA = jsonUndergradTAArray.getJsonObject(i);
            String name = jsonTA.getString(JSON_NAME);
            String email = jsonTA.getString(JSON_EMAIL);
            TeachingAssistantPrototype ta = new TeachingAssistantPrototype(name, email, "UNDERGRADUATE");
            dataManager.getOfficeHoursData().addTA(ta);
        }
        
        JsonArray jsonGradTAArray = json.getJsonObject(JSON_OFFICE_HOURS).getJsonArray(JSON_GRAD_TAS);
        for (int i = 0; i < jsonGradTAArray.size(); i++) {
            JsonObject jsonTA = jsonGradTAArray.getJsonObject(i);
            String name = jsonTA.getString(JSON_NAME);
            String email = jsonTA.getString(JSON_EMAIL);
            TeachingAssistantPrototype ta = new TeachingAssistantPrototype(name, email, "GRADUATE");
            dataManager.getOfficeHoursData().addTA(ta);
        }
        
        //LOADS TIME SLOTS
        JsonArray jsonSlotArray = json.getJsonObject(JSON_OFFICE_HOURS).getJsonArray(JSON_OFFICE_HOURS);
        for (int i = 0; i<jsonSlotArray.size(); i++){
            JsonObject jsonSlot = jsonSlotArray.getJsonObject(i);
            String name = jsonSlot.getString(JSON_NAME);
            String day = jsonSlot.getString(JSON_DAY_OF_WEEK);
            String time = jsonSlot.getString(JSON_START_TIME);
            time = time.replace("_", ":");
            DayOfWeek dowEnum = DayOfWeek.valueOf(day);
            TimeSlot ts =dataManager.getOfficeHoursData().getTimeSlot(time);
            ts.addTA(dowEnum, dataManager.getOfficeHoursData().getTAWithName(name));
        }
    }
      
    // HELPER METHOD FOR LOADING DATA FROM A JSON FORMAT
    private JsonObject loadJSONFile(String jsonFilePath) throws IOException {
	InputStream is = new FileInputStream(jsonFilePath);
	JsonReader jsonReader = Json.createReader(is);
	JsonObject json = jsonReader.readObject();
	jsonReader.close();
	is.close();
	return json;
    }

    @Override
    public void saveData(AppDataComponent data, String filePath) throws IOException {
	// GET THE DATA
	SiteGeneratorData dataManager = (SiteGeneratorData)data;

	// NOW BUILD THE TA JSON OBJCTS TO SAVE

        JsonObjectBuilder syllabusJsonBuilder = Json.createObjectBuilder();
	dataManager.getSyllabusData().getSyllabusDataArray().forEach((k ,v) -> {
                    syllabusJsonBuilder.add(k, v.getValue());
        });
        JsonObject syllabusJson = syllabusJsonBuilder.build();

        
        JsonObjectBuilder instructorJsonBuilder = Json.createObjectBuilder();
        dataManager.getSiteData().getInstructorDataArray().forEach((k ,v) -> {
            instructorJsonBuilder.add(k, v.getValue());  
        });
        JsonObject instructorJson = instructorJsonBuilder.build();
        JsonObjectBuilder siteBuilderJson = Json.createObjectBuilder();
        dataManager.getSiteData().getSiteDataArray().forEach((k ,v) -> {
            siteBuilderJson.add(k, v);  
        });
        
        JsonObjectBuilder logoJsonBuilder = Json.createObjectBuilder();
        JsonObject faviconJson = Json.createObjectBuilder()
                .add(JSON_HREF, "./images/SBUShieldFavicon.ico")
                .build();
        logoJsonBuilder.add(JSON_FAVICON, faviconJson);
        JsonObject navbarJson = Json.createObjectBuilder()
                .add(JSON_HREF, "http://www.stonybrook.edu")
                .add(JSON_SRC, dataManager.getSiteData().getNavbar())
                .build();
        logoJsonBuilder.add(JSON_NAVBAR, navbarJson);
        JsonObject bottomLeftJson = Json.createObjectBuilder()
                .add(JSON_HREF, "http://www.cs.stonybrook.edu")
                .add(JSON_SRC, dataManager.getSiteData().getLeftFooter())
                .build();
        logoJsonBuilder.add(JSON_BOTTOM_LEFT, bottomLeftJson);
        JsonObject bottomRightJson = Json.createObjectBuilder()
                .add(JSON_HREF, "http://www.cs.stonybrook.edu")
                .add(JSON_SRC, dataManager.getSiteData().getRightFooter())
                .build();
        logoJsonBuilder.add(JSON_BOTTOM_RIGHT, bottomRightJson);
        JsonObject logoJson = logoJsonBuilder.build();
        
        
        
        JsonObject siteJson = siteBuilderJson
                .add(JSON_INSTRUCTOR, instructorJson)
                .add(JSON_LOGOS, logoJson)
                .build();
        
        
        JsonArrayBuilder lectureArrayBuilder = Json.createArrayBuilder();
	for(Lecture lecture : dataManager.getMeetingTimesData().getLectureData()){
            JsonObject lectureJson = Json.createObjectBuilder()
                    .add(JSON_SECTION, lecture.getSection())
                    .add(JSON_DAYS, lecture.getDay())
                    .add(JSON_TIME, lecture.getTime())
                    .add(JSON_ROOM, lecture.getRoom())
                    .build();
            lectureArrayBuilder.add(lectureJson);
        }
	JsonArray lectureArray = lectureArrayBuilder.build();

        JsonArrayBuilder labArrayBuilder = Json.createArrayBuilder();
	for(Lab lab : dataManager.getMeetingTimesData().getLabData()){
            JsonObject labJson = Json.createObjectBuilder()
                    .add(JSON_SECTION, lab.getSection())
                    .add(JSON_DAY_TIME, lab.getDayTime())
                    .add(JSON_LOCATION, lab.getRoom())
                    .add(JSON_TA1, lab.getTa1())
                    .add(JSON_TA2, lab.getTa2())
                    .build();
            labArrayBuilder.add(labJson);
        }
	JsonArray labArray = labArrayBuilder.build();
        
        JsonArrayBuilder recitationArrayBuilder = Json.createArrayBuilder();
	for(Recitation recitation : dataManager.getMeetingTimesData().getRecitationData()){
            JsonObject recitationJson = Json.createObjectBuilder()
                    .add(JSON_SECTION, recitation.getSection())
                    .add(JSON_DAY_TIME, recitation.getDayTime())
                    .add(JSON_LOCATION, recitation.getRoom())
                    .add(JSON_TA1, recitation.getTa1())
                    .add(JSON_TA2, recitation.getTa2())
                    .build();
            recitationArrayBuilder.add(recitationJson);
        }
	JsonArray recitationArray = recitationArrayBuilder.build();
        
        JsonObject meetingTimesJson = Json.createObjectBuilder()
                .add(JSON_LECTURES, lectureArray)
                .add(JSON_LABS, labArray)
                .add(JSON_RECITATIONS, recitationArray)
		.build();
        
        
        
        
        
        JsonArrayBuilder holidayArrayBuilder = Json.createArrayBuilder();
        for(ScheduleItem item : dataManager.getScheduleData().getScheduleItems()){
            if(item.getType().equals("Holiday")){
            JsonObject holidayJson = Json.createObjectBuilder()
                    .add(JSON_MONTH, ""+item.getDate().getMonthValue())
                    .add(JSON_DAY, ""+item.getDate().getDayOfMonth())
                    .add(JSON_TITLE, item.getTitle())
                    .add(JSON_LINK, item.getLink())
                    .build();
            holidayArrayBuilder.add(holidayJson);
            }
        }
        JsonArray holidayArray = holidayArrayBuilder.build();
        
        JsonArrayBuilder lecturesArrayBuilder = Json.createArrayBuilder();
        for(ScheduleItem item : dataManager.getScheduleData().getScheduleItems()){
            if(item.getType().equals("Lecture")){
            JsonObject lectureJson = Json.createObjectBuilder()
                    .add(JSON_MONTH, ""+item.getDate().getMonthValue())
                    .add(JSON_DAY, ""+item.getDate().getDayOfMonth())
                    .add(JSON_TITLE, item.getTitle())
                    .add(JSON_TOPIC, item.getTopic())
                    .add(JSON_LINK, item.getLink())
                    .build();
            lecturesArrayBuilder.add(lectureJson);
            }
        }
        JsonArray lecturesArray = lecturesArrayBuilder.build();
        
        JsonArrayBuilder referencesArrayBuilder = Json.createArrayBuilder();
        for(ScheduleItem item : dataManager.getScheduleData().getScheduleItems()){
            if(item.getType().equals("Reference")){
            JsonObject referenceJson = Json.createObjectBuilder()
                    .add(JSON_MONTH, ""+item.getDate().getMonthValue())
                    .add(JSON_DAY, ""+item.getDate().getDayOfMonth())
                    .add(JSON_TITLE, item.getTitle())
                    .add(JSON_TOPIC, item.getTopic())
                    .add(JSON_LINK, item.getLink())
                    .build();
            referencesArrayBuilder.add(referenceJson);
            }
        }
        JsonArray referenceArray = referencesArrayBuilder.build();
        
        JsonArrayBuilder recitationsArrayBuilder = Json.createArrayBuilder();
        for(ScheduleItem item : dataManager.getScheduleData().getScheduleItems()){
            if(item.getType().equals("Recitation")){
            JsonObject referenceJson = Json.createObjectBuilder()
                    .add(JSON_MONTH, ""+item.getDate().getMonthValue())
                    .add(JSON_DAY, ""+item.getDate().getDayOfMonth())
                    .add(JSON_TITLE, item.getTitle())
                    .add(JSON_TOPIC, item.getTopic())
                    .add(JSON_LINK, item.getLink())
                    .build();
            recitationsArrayBuilder.add(referenceJson);
            }
        }
        JsonArray recitationsArray = recitationsArrayBuilder.build();
        
        JsonArrayBuilder hwsArrayBuilder = Json.createArrayBuilder();
        for(ScheduleItem item : dataManager.getScheduleData().getScheduleItems()){
            if(item.getType().equals("HW")){
            JsonObject hwJson = Json.createObjectBuilder()
                    .add(JSON_MONTH, ""+item.getDate().getMonthValue())
                    .add(JSON_DAY, ""+item.getDate().getDayOfMonth())
                    .add(JSON_TITLE, item.getTitle())
                    .add(JSON_TOPIC, item.getTopic())
                    .add(JSON_LINK, item.getLink())
                    .build();
            hwsArrayBuilder.add(hwJson);
            }
        }
        JsonArray hwArray = hwsArrayBuilder.build();
        
        JsonObject scheduleJson = Json.createObjectBuilder()
                .add(JSON_STARTING_MONTH, ""+dataManager.getScheduleData().getStartDate().getMonthValue())
                .add(JSON_STARTING_DAY, ""+dataManager.getScheduleData().getStartDate().getDayOfMonth())
                .add(JSON_ENDING_MONTH, ""+dataManager.getScheduleData().getEndDate().getMonthValue())
                .add(JSON_ENDING_DAY, ""+dataManager.getScheduleData().getEndDate().getDayOfMonth())
                .add(JSON_HOLIDAYS, holidayArray)
                .add(JSON_LECTURES, lecturesArray)
                .add(JSON_REFERENCES, referenceArray)
                .add(JSON_RECITATIONS, recitationsArray)
                .add(JSON_HWS, hwArray)
                .build();
        
        
        JsonArrayBuilder undergradTaArrayBuilder = Json.createArrayBuilder();
	Iterator<TeachingAssistantPrototype> tasIterator = dataManager.getOfficeHoursData().teachingAssistantsIterator();
        
        while (tasIterator.hasNext()) {
            TeachingAssistantPrototype ta = tasIterator.next();
            if(ta.getTaType().equals("UNDERGRADUATE")){
	    JsonObject taJson = Json.createObjectBuilder()
		    .add(JSON_NAME, ta.getName())
                    .add(JSON_EMAIL, ta.getEmail())
                    .build();
	    undergradTaArrayBuilder.add(taJson);
            }
	}
	JsonArray undergradTAsArray = undergradTaArrayBuilder.build();
        
        JsonArrayBuilder gradTaArrayBuilder = Json.createArrayBuilder();
        
        while (tasIterator.hasNext()) {
            TeachingAssistantPrototype ta = tasIterator.next();
            if(ta.getTaType().equals("GRADUATE")){
	    JsonObject taJson = Json.createObjectBuilder()
		    .add(JSON_NAME, ta.getName())
                    .add(JSON_EMAIL, ta.getEmail())
                    .build();
	    gradTaArrayBuilder.add(taJson);
            }
	}
	JsonArray gradTAsArray = gradTaArrayBuilder.build();
        
        JsonArrayBuilder slotArrayBuilder = Json.createArrayBuilder();
	Iterator<TimeSlot> slotsIterator = dataManager.getOfficeHoursData().officeHoursIterator();
        while (slotsIterator.hasNext()) {
            TimeSlot ts = slotsIterator.next();
            for (DayOfWeek dow : DayOfWeek.values()){
                for(TeachingAssistantPrototype tempTA :ts.getTAList(dow)){
	    JsonObject tsJson = Json.createObjectBuilder()
                    .add(JSON_START_TIME, ts.getStartTime().replace(":", "_"))
                    .add(JSON_DAY_OF_WEEK, dow.toString())
		    .add(JSON_NAME, tempTA.getName())
                    .build();
	    slotArrayBuilder.add(tsJson);
                }
            }
            
           
            
	}
	JsonArray slotsArray = slotArrayBuilder.build();

        
	// THEN PUT IT ALL TOGETHER IN A JsonObject
	JsonObject officeHoursJson = Json.createObjectBuilder()
		.add(JSON_START_HOUR, "" + dataManager.getOfficeHoursData().getStartHour())
		.add(JSON_END_HOUR, "" + dataManager.getOfficeHoursData().getEndHour())
                .add(JSON_UNDERGRAD_TAS, undergradTAsArray)
                .add(JSON_GRAD_TAS, gradTAsArray)
                .add(JSON_OFFICE_HOURS, slotsArray)
		.build();
        
        
        
        
        
	// THEN PUT IT ALL TOGETHER IN A JsonObject
	JsonObject dataManagerJSO = Json.createObjectBuilder()
                .add(JSON_SYLLABUS, syllabusJson)
                .add(JSON_SITE, siteJson)
                .add(JSON_MEETING_TIMES, meetingTimesJson)
                .add(JSON_SCHEDULE, scheduleJson)
                .add(JSON_OFFICE_HOURS, officeHoursJson)
		.build();
	
	// AND NOW OUTPUT IT TO A JSON FILE WITH PRETTY PRINTING
	Map<String, Object> properties = new HashMap<>(1);
	properties.put(JsonGenerator.PRETTY_PRINTING, true);
	JsonWriterFactory writerFactory = Json.createWriterFactory(properties);
	StringWriter sw = new StringWriter();
	JsonWriter jsonWriter = writerFactory.createWriter(sw);
	jsonWriter.writeObject(dataManagerJSO);
	jsonWriter.close();

	// INIT THE WRITER
	OutputStream os = new FileOutputStream(filePath);
	JsonWriter jsonFileWriter = Json.createWriter(os);
	jsonFileWriter.writeObject(dataManagerJSO);
	String prettyPrinted = sw.toString();
	PrintWriter pw = new PrintWriter(filePath);
	pw.write(prettyPrinted);
	pw.close();
    }
    
   
    
    // IMPORTING/EXPORTING DATA IS USED WHEN WE READ/WRITE DATA IN AN
    // ADDITIONAL FORMAT USEFUL FOR ANOTHER PURPOSE, LIKE ANOTHER APPLICATION

    @Override
    public void importData(AppDataComponent data, String filePath) throws IOException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void exportData(AppDataComponent data, String filePath) throws IOException {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        SiteGeneratorData dataManager = (SiteGeneratorData)data;
        PropertiesManager props = PropertiesManager.getPropertiesManager();
        Path exportDir = Paths.get(props.getProperty(APP_PATH_EXPORT));
        if(!Files.exists(exportDir)){
            //exportDir.toFile().mkdir();
            Path webFiles = Paths.get(props.getProperty(APP_EXPORT_WEB_FILES));
            FileUtils.copyDirectory(webFiles.toFile(), exportDir.toFile());
        }
        
        //MAKE PAGEDATA JSON
 
        JsonObjectBuilder instructorJsonBuilder = Json.createObjectBuilder();
        dataManager.getSiteData().getInstructorDataArray().forEach((k ,v) -> {
            instructorJsonBuilder.add(k, v.getValue());  
        });
        JsonObject instructorJson = instructorJsonBuilder.build();
        
        JsonArrayBuilder pagesJsonBuilder = Json.createArrayBuilder();
            if(dataManager.getSiteData().hasHomePage()){
                JsonObject jsonPage = Json.createObjectBuilder()
                    .add(JSON_NAME, JSON_HOME_PAGE)
                    .add(JSON_LINK, JSON_HOME_LINK)
                    .build();
                pagesJsonBuilder.add(jsonPage);
            }
            if(dataManager.getSiteData().hasSyllabusPage()){
                JsonObject jsonPage = Json.createObjectBuilder()
                    .add(JSON_NAME, JSON_SYLLABUS_PAGE)
                    .add(JSON_LINK, JSON_SYLLABUS_LINK)
                    .build();
                pagesJsonBuilder.add(jsonPage);
            }
            if(dataManager.getSiteData().hasSchedulePage()){
                JsonObject jsonPage = Json.createObjectBuilder()
                    .add(JSON_NAME, JSON_SCHEDULE_PAGE)
                    .add(JSON_LINK, JSON_SCHEDULE_LINK)
                    .build();
                pagesJsonBuilder.add(jsonPage);
            }
            if(dataManager.getSiteData().hasHWsPage()){
                JsonObject jsonPage = Json.createObjectBuilder()
                    .add(JSON_NAME, JSON_HWS_PAGE)
                    .add(JSON_LINK, JSON_HWS_LINK)
                    .build();
                pagesJsonBuilder.add(jsonPage);
            }
            JsonArray pagesJson = pagesJsonBuilder.build();
        
        JsonObjectBuilder siteBuilderJson = Json.createObjectBuilder();
        dataManager.getSiteData().getSiteDataArray().forEach((k ,v) -> {
            siteBuilderJson.add(k, v);  
        });
        
        JsonObjectBuilder logoJsonBuilder = Json.createObjectBuilder();
        JsonObject faviconJson = Json.createObjectBuilder()
                .add(JSON_HREF, "./images/SBUShieldFavicon.ico")
                .build();
        logoJsonBuilder.add(JSON_FAVICON, faviconJson);
        JsonObject navbarJson = Json.createObjectBuilder()
                .add(JSON_HREF, "http://www.stonybrook.edu")
                .add(JSON_SRC, makeRelativeImagePath(dataManager.getSiteData().getNavbar()))
                .build();
        logoJsonBuilder.add(JSON_NAVBAR, navbarJson);
        JsonObject bottomLeftJson = Json.createObjectBuilder()
                .add(JSON_HREF, "http://www.cs.stonybrook.edu")
                .add(JSON_SRC, makeRelativeImagePath(dataManager.getSiteData().getLeftFooter()))
                .build();
        logoJsonBuilder.add(JSON_BOTTOM_LEFT, bottomLeftJson);
        JsonObject bottomRightJson = Json.createObjectBuilder()
                .add(JSON_HREF, "http://www.cs.stonybrook.edu")
                .add(JSON_SRC, makeRelativeImagePath(dataManager.getSiteData().getRightFooter()))
                .build();
        logoJsonBuilder.add(JSON_BOTTOM_RIGHT, bottomRightJson);
        JsonObject logoJson = logoJsonBuilder.build();
        
        
        JsonObject siteJson = siteBuilderJson
                .add(JSON_LOGOS, logoJson)
                .add(JSON_INSTRUCTOR, instructorJson)
                .add(JSON_PAGES, pagesJson)
                .build();
        
        

        writeJsonFile(siteJson, exportDir, "PageData");
        
        //MAKE THE SYLLABUSDATA JSON
        
        JsonObjectBuilder syllabusJsonBuilder = Json.createObjectBuilder();
	dataManager.getSyllabusData().getSyllabusDataArray().forEach((k ,v) -> {
                    
                    syllabusJsonBuilder.add(k, v.getValue());
        });
        JsonObject syllabusJson = syllabusJsonBuilder.build();
        
        // AND NOW OUTPUT IT TO A JSON FILE WITH PRETTY PRINTING

	writeJsonFile(syllabusJson, exportDir, "SyllabusData");
        
        //MAKE THE SECTIONSDATA.JSON
        
        JsonArrayBuilder lectureArrayBuilder = Json.createArrayBuilder();
	for(Lecture lecture : dataManager.getMeetingTimesData().getLectureData()){
            JsonObject lectureJson = Json.createObjectBuilder()
                    .add(JSON_SECTION, lecture.getSection())
                    .add(JSON_DAYS, lecture.getDay())
                    .add(JSON_TIME, lecture.getTime())
                    .add(JSON_ROOM, lecture.getRoom())
                    .build();
            lectureArrayBuilder.add(lectureJson);
        }
	JsonArray lectureArray = lectureArrayBuilder.build();

        JsonArrayBuilder labArrayBuilder = Json.createArrayBuilder();
	for(Lab lab : dataManager.getMeetingTimesData().getLabData()){
            JsonObject labJson = Json.createObjectBuilder()
                    .add(JSON_SECTION, lab.getSection())
                    .add(JSON_DAY_TIME, lab.getDayTime())
                    .add(JSON_LOCATION, lab.getRoom())
                    .add(JSON_TA1, lab.getTa1())
                    .add(JSON_TA2, lab.getTa2())
                    .build();
            labArrayBuilder.add(labJson);
        }
	JsonArray labArray = labArrayBuilder.build();
        
        JsonArrayBuilder recitationArrayBuilder = Json.createArrayBuilder();
	for(Recitation recitation : dataManager.getMeetingTimesData().getRecitationData()){
            JsonObject recitationJson = Json.createObjectBuilder()
                    .add(JSON_SECTION, recitation.getSection())
                    .add(JSON_DAY_TIME, recitation.getDayTime())
                    .add(JSON_LOCATION, recitation.getRoom())
                    .add(JSON_TA1, recitation.getTa1())
                    .add(JSON_TA2, recitation.getTa2())
                    .build();
            recitationArrayBuilder.add(recitationJson);
        }
	JsonArray recitationArray = recitationArrayBuilder.build();
        
        JsonObject meetingTimesJson = Json.createObjectBuilder()
                .add(JSON_LECTURES, lectureArray)
                .add(JSON_LABS, labArray)
                .add(JSON_RECITATIONS, recitationArray)
		.build();
        
        writeJsonFile(meetingTimesJson, exportDir, "SectionsData");
        
        //MAKE OFFICEHOURSDATA.JSON
        
        JsonArrayBuilder undergradTaArrayBuilder = Json.createArrayBuilder();
	Iterator<TeachingAssistantPrototype> tasIterator = dataManager.getOfficeHoursData().teachingAssistantsIterator();
        
        while (tasIterator.hasNext()) {
            TeachingAssistantPrototype ta = tasIterator.next();
            if(ta.getTaType().equals("UNDERGRADUATE")){
	    JsonObject taJson = Json.createObjectBuilder()
		    .add(JSON_NAME, ta.getName())
                    .add(JSON_EMAIL, ta.getEmail())
                    .build();
	    undergradTaArrayBuilder.add(taJson);
            }
	}
	JsonArray undergradTAsArray = undergradTaArrayBuilder.build();
        
        JsonArrayBuilder gradTaArrayBuilder = Json.createArrayBuilder();
        
        while (tasIterator.hasNext()) {
            TeachingAssistantPrototype ta = tasIterator.next();
            if(ta.getTaType().equals("GRADUATE")){
	    JsonObject taJson = Json.createObjectBuilder()
		    .add(JSON_NAME, ta.getName())
                    .add(JSON_EMAIL, ta.getEmail())
                    .build();
	    gradTaArrayBuilder.add(taJson);
            }
	}
	JsonArray gradTAsArray = gradTaArrayBuilder.build();
        
        JsonArrayBuilder slotArrayBuilder = Json.createArrayBuilder();
	Iterator<TimeSlot> slotsIterator = dataManager.getOfficeHoursData().officeHoursIterator();
        while (slotsIterator.hasNext()) {
            TimeSlot ts = slotsIterator.next();
            for (DayOfWeek dow : DayOfWeek.values()){
                for(TeachingAssistantPrototype tempTA :ts.getTAList(dow)){
	    JsonObject tsJson = Json.createObjectBuilder()
                    .add(JSON_START_TIME, ts.getStartTime().replace(":", "_"))
                    .add(JSON_DAY_OF_WEEK, dow.toString())
		    .add(JSON_NAME, tempTA.getName())
                    .build();
	    slotArrayBuilder.add(tsJson);
                }
            }
            
           
            
	}
	JsonArray slotsArray = slotArrayBuilder.build();

        
	// THEN PUT IT ALL TOGETHER IN A JsonObject
	JsonObject officeHoursJson = Json.createObjectBuilder()
		.add(JSON_START_HOUR, "" + dataManager.getOfficeHoursData().getStartHour())
		.add(JSON_END_HOUR, "" + dataManager.getOfficeHoursData().getEndHour())
                .add(JSON_INSTRUCTOR, instructorJson)
                .add(JSON_UNDERGRAD_TAS, undergradTAsArray)
                .add(JSON_GRAD_TAS, gradTAsArray)
                .add(JSON_OFFICE_HOURS, slotsArray)
		.build();
        
        writeJsonFile(officeHoursJson, exportDir, "OfficeHoursData");
        
        
        
        //MAKE THE SCHEDULEDATA.JSON
        
        JsonArrayBuilder holidayArrayBuilder = Json.createArrayBuilder();
        for(ScheduleItem item : dataManager.getScheduleData().getScheduleItems()){
            if(item.getType().equals("Holiday")){
            JsonObject holidayJson = Json.createObjectBuilder()
                    .add(JSON_MONTH, ""+item.getDate().getMonthValue())
                    .add(JSON_DAY, ""+item.getDate().getDayOfMonth())
                    .add(JSON_TITLE, item.getTitle())
                    .add(JSON_LINK, item.getLink())
                    .build();
            holidayArrayBuilder.add(holidayJson);
            }
        }
        JsonArray holidayArray = holidayArrayBuilder.build();
        
        JsonArrayBuilder lecturesArrayBuilder = Json.createArrayBuilder();
        for(ScheduleItem item : dataManager.getScheduleData().getScheduleItems()){
            if(item.getType().equals("Lecture")){
            JsonObject lectureJson = Json.createObjectBuilder()
                    .add(JSON_MONTH, ""+item.getDate().getMonthValue())
                    .add(JSON_DAY, ""+item.getDate().getDayOfMonth())
                    .add(JSON_TITLE, item.getTitle())
                    .add(JSON_TOPIC, item.getTopic())
                    .add(JSON_LINK, item.getLink())
                    .build();
            lecturesArrayBuilder.add(lectureJson);
            }
        }
        JsonArray lecturesArray = lecturesArrayBuilder.build();
        
        JsonArrayBuilder referencesArrayBuilder = Json.createArrayBuilder();
        for(ScheduleItem item : dataManager.getScheduleData().getScheduleItems()){
            if(item.getType().equals("Reference")){
            JsonObject referenceJson = Json.createObjectBuilder()
                    .add(JSON_MONTH, ""+item.getDate().getMonthValue())
                    .add(JSON_DAY, ""+item.getDate().getDayOfMonth())
                    .add(JSON_TITLE, item.getTitle())
                    .add(JSON_TOPIC, item.getTopic())
                    .add(JSON_LINK, item.getLink())
                    .build();
            referencesArrayBuilder.add(referenceJson);
            }
        }
        JsonArray referenceArray = referencesArrayBuilder.build();
        
        JsonArrayBuilder recitationsArrayBuilder = Json.createArrayBuilder();
        for(ScheduleItem item : dataManager.getScheduleData().getScheduleItems()){
            if(item.getType().equals("Recitation")){
            JsonObject referenceJson = Json.createObjectBuilder()
                    .add(JSON_MONTH, ""+item.getDate().getMonthValue())
                    .add(JSON_DAY, ""+item.getDate().getDayOfMonth())
                    .add(JSON_TITLE, item.getTitle())
                    .add(JSON_TOPIC, item.getTopic())
                    .add(JSON_LINK, item.getLink())
                    .build();
            recitationsArrayBuilder.add(referenceJson);
            }
        }
        JsonArray recitationsArray = recitationsArrayBuilder.build();
        
        JsonArrayBuilder hwsArrayBuilder = Json.createArrayBuilder();
        for(ScheduleItem item : dataManager.getScheduleData().getScheduleItems()){
            if(item.getType().equals("HW")){
            JsonObject hwJson = Json.createObjectBuilder()
                    .add(JSON_MONTH, ""+item.getDate().getMonthValue())
                    .add(JSON_DAY, ""+item.getDate().getDayOfMonth())
                    .add(JSON_TITLE, item.getTitle())
                    .add(JSON_TOPIC, item.getTopic())
                    .add(JSON_LINK, item.getLink())
                    .build();
            hwsArrayBuilder.add(hwJson);
            }
        }
        JsonArray hwArray = hwsArrayBuilder.build();
        
        JsonObject scheduleJson = Json.createObjectBuilder()
                .add(JSON_STARTING_MONTH, ""+dataManager.getScheduleData().getStartDate().getMonthValue())
                .add(JSON_STARTING_DAY, ""+dataManager.getScheduleData().getStartDate().getDayOfMonth())
                .add(JSON_ENDING_MONTH, ""+dataManager.getScheduleData().getEndDate().getMonthValue())
                .add(JSON_ENDING_DAY, ""+dataManager.getScheduleData().getEndDate().getDayOfMonth())
                .add(JSON_HOLIDAYS, holidayArray)
                .add(JSON_LECTURES, lecturesArray)
                .add(JSON_REFERENCES, referenceArray)
                .add(JSON_RECITATIONS, recitationsArray)
                .add(JSON_HWS, hwArray)
                .build();
        
        
        writeJsonFile(scheduleJson, exportDir, "ScheduleData");
    }
    
    public String makeRelativeImagePath(String dir){
        PropertiesManager props = PropertiesManager.getPropertiesManager();
        String path = dir;
        String base = props.getProperty(APP_EXPORT_WEB_FILES);
        String relative = new File(base).toURI().relativize(new File(path).toURI()).getPath();
        relative = "./"+relative;
        return relative;
    }
    
    public void writeJsonFile(JsonObject siteJson, Path exportDir, String fileName) throws IOException{
        Map<String, Object> properties = new HashMap<>(1);
	properties.put(JsonGenerator.PRETTY_PRINTING, true);
	JsonWriterFactory writerFactory = Json.createWriterFactory(properties);
	StringWriter sw = new StringWriter();
	JsonWriter jsonWriter = writerFactory.createWriter(sw);
	jsonWriter.writeObject(siteJson);
	jsonWriter.close();

	// INIT THE WRITER
        Path pageDataPath = Paths.get(exportDir.toString()+"/js/"+fileName+".json");
        try{
        Files.deleteIfExists(pageDataPath);
        }catch(Exception e){
            System.out.println("File did not delete");
        }
	OutputStream os = new FileOutputStream(exportDir.toString()+"/js/"+fileName+".json");
	JsonWriter jsonFileWriter = Json.createWriter(os);
	jsonFileWriter.writeObject(siteJson);
	String prettyPrinted = sw.toString();
	PrintWriter pw = new PrintWriter(exportDir.toString()+"/js/"+fileName+".json");
	pw.write(prettyPrinted);
	pw.close();
        os.close();
        sw.close();
        
    }
}
