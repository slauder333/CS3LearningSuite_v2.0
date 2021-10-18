package sg.workspace.controllers;

import static djf.AppPropertyType.APP_ERROR_CONTENT;
import static djf.AppPropertyType.APP_ERROR_TITLE;
import djf.modules.AppGUIModule;
import djf.ui.dialogs.AppDialogsFacade;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javafx.collections.ObservableList;
import javafx.scene.control.TablePosition;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import sg.Data.OfficeHoursData;
import sg.Data.SiteGeneratorData;
import sg.Data.TeachingAssistantPrototype;
import sg.Data.TimeSlot;
import sg.Data.TimeSlot.DayOfWeek;
import sg.SiteGeneratorApp;
import static sg.SiteGeneratorPropertyType.*;
import sg.transactions.AddSlot_Transaction;
import sg.transactions.AddTA_Transaction;
import sg.workspace.dialogs.OfficeHoursDialogs;

/**
 *
 * @author McKillaGorilla
 */
public class OfficeHoursController{

    SiteGeneratorApp app;
    static String tota;

    
    public OfficeHoursController(SiteGeneratorApp initApp) {
        app = initApp;
    }

    public void processAddTA() {
        AppGUIModule gui = app.getGUIModule();
        TextField nameTF = (TextField) gui.getGUINode(OH_NAME_TEXT_FIELD);
        String name = nameTF.getText();
        TextField emailTF = (TextField) gui.getGUINode(OH_EMAIL_TEXT_FIELD);
        String email = emailTF.getText();
        try{
            if(!validate(email)){throw new InvalidEmailException();}
        SiteGeneratorData dataComponent = (SiteGeneratorData)app.getDataComponent();
        OfficeHoursData data = dataComponent.getOfficeHoursData();
        TeachingAssistantPrototype ta = new TeachingAssistantPrototype(name, email, tota);
        
        Iterator<TeachingAssistantPrototype> tasIterator = data.teachingAssistantsIterator();
        
        while (tasIterator.hasNext()) {
            TeachingAssistantPrototype tempta = tasIterator.next();
            if(tempta.getName().equals(ta.getName()) || tempta.getEmail().equals(ta.getEmail())){
                throw new InvalidEmailException();
            }
        }
        AddTA_Transaction addTATransaction = new AddTA_Transaction(data, ta);
        app.processTransaction(addTATransaction);
        }catch(InvalidEmailException e){
            AppDialogsFacade.showStackTraceDialog(gui.getWindow(), e, INVALID_EMAIL_ERROR, INVALID_EMAIL_ERROR);
        }
        // NOW CLEAR THE TEXT FIELDS
        nameTF.setText("");
        emailTF.setText("");
        nameTF.requestFocus();
    }
    
    public void setTypeOfTa(String initTota){
            tota = initTota;
        }
    
    public void processAddHours() {
        AppGUIModule gui = app.getGUIModule();
        TableView officeHoursTableView = (TableView) gui.getGUINode(OH_OFFICE_HOURS_TABLE_VIEW);
        SiteGeneratorData dataComponent = (SiteGeneratorData)app.getDataComponent();
        OfficeHoursData data = dataComponent.getOfficeHoursData();
        TablePosition tp;
        tp =officeHoursTableView.getFocusModel().getFocusedCell();
        DayOfWeek dow = data.getColumnDayOfWeek(tp.getColumn());
        TimeSlot ts = (TimeSlot) officeHoursTableView.getFocusModel().getFocusedItem();
        TableView tasTable = (TableView)gui.getGUINode(OH_TAS_TABLE_VIEW);
        TeachingAssistantPrototype ta = (TeachingAssistantPrototype) tasTable.getSelectionModel().getSelectedItem();
        AddSlot_Transaction addSlotTransaction = new AddSlot_Transaction(ts, dow, ta);
        app.processTransaction(addSlotTransaction);
        //ts.addTA(dow, ta);
    }
    
    public void updateTADisplay(String o){
        SiteGeneratorData dataComponent = (SiteGeneratorData)app.getDataComponent();
        OfficeHoursData data = dataComponent.getOfficeHoursData();
        ArrayList<TeachingAssistantPrototype> taData = data.getTaData();
        ObservableList<TeachingAssistantPrototype> taList = data.getTAList();
        ObservableList<TimeSlot> tsList = data.getTSList();
        ArrayList<TimeSlot> tsData = data.getTSData();
        if(o=="All"){
           taList.clear();
           for(TeachingAssistantPrototype ta : taData){
                taList.add(ta);
            }
           Iterator<TimeSlot> slotsIterator = data.officeHoursIterator();
            while (slotsIterator.hasNext()) {
                TimeSlot ts = slotsIterator.next();
                ts.updateTimeSlot("All");
            }
        }
        if(o=="UNDERGRADUATE"){
            taList.clear();
            for(TeachingAssistantPrototype ta : taData){
                if (ta.getTaType() == "UNDERGRADUATE"){
                    taList.add(ta);
                }  
            }
            Iterator<TimeSlot> slotsIterator = data.officeHoursIterator();
            while (slotsIterator.hasNext()) {
                TimeSlot ts = slotsIterator.next();
                ts.updateTimeSlot("UNDERGRADUATE");
            }
            
        }
        if(o=="GRADUATE"){
            taList.clear();
            for(TeachingAssistantPrototype ta : taData){
                if (ta.getTaType() == "GRADUATE"){
                    taList.add(ta);
                }  
            }
            Iterator<TimeSlot> slotsIterator = data.officeHoursIterator();
            while (slotsIterator.hasNext()) {
                TimeSlot ts = slotsIterator.next();
                ts.updateTimeSlot("GRADUATE");
            }
        }
        
    }
    
    public void showEditTADialog(){
        OfficeHoursDialogs officeHoursDialog = new OfficeHoursDialogs();
        officeHoursDialog.editTADialog(app);
    }
    
    public static final Pattern VALID_EMAIL_ADDRESS_REGEX = 
    Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);

    public static boolean validate(String emailStr) {
        Matcher matcher = VALID_EMAIL_ADDRESS_REGEX .matcher(emailStr);
        return matcher.find();
}
}
