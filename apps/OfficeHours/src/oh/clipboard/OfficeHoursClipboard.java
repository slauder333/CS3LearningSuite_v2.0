package oh.clipboard;

import djf.components.AppClipboardComponent;
import djf.modules.AppGUIModule;
import java.util.ArrayList;
import java.util.Iterator;
import javafx.collections.ObservableList;
import javafx.scene.control.TableView;
import oh.OfficeHoursApp;
import static oh.OfficeHoursPropertyType.OH_TAS_TABLE_VIEW;
import oh.data.OfficeHoursData;
import oh.data.TeachingAssistantPrototype;
import oh.data.TimeSlot;

/**
 *
 * @author McKillaGorilla
 */
public class OfficeHoursClipboard implements AppClipboardComponent {
    OfficeHoursApp app;
    ArrayList<TeachingAssistantPrototype> clipboardCutItems;
    ArrayList<TeachingAssistantPrototype> clipboardCopiedItems;
    
    public OfficeHoursClipboard(OfficeHoursApp initApp) {
        app = initApp;
        clipboardCutItems = new ArrayList<>();
        clipboardCopiedItems = null;
    }
    
    @Override
    public void cut() {
        clipboardCutItems.clear();
        AppGUIModule gui = app.getGUIModule();
        TableView tasTable = (TableView)gui.getGUINode(OH_TAS_TABLE_VIEW);
        TeachingAssistantPrototype ta = (TeachingAssistantPrototype) tasTable.getSelectionModel().getSelectedItem();
        OfficeHoursData data = (OfficeHoursData) app.getDataComponent();
        clipboardCutItems.add(ta);
        data.removeTA(ta);
           Iterator<TimeSlot> slotsIterator = data.officeHoursIterator();
            while (slotsIterator.hasNext()) {
                TimeSlot ts = slotsIterator.next();
                ts.removeTAFromTS(ta);
            }
        app.getFoolproofModule().updateAll();
        
    }

    @Override
    public void copy() {
        clipboardCutItems.clear();
        AppGUIModule gui = app.getGUIModule();
        TableView tasTable = (TableView)gui.getGUINode(OH_TAS_TABLE_VIEW);
        TeachingAssistantPrototype ta = (TeachingAssistantPrototype) tasTable.getSelectionModel().getSelectedItem();
        clipboardCutItems.add(ta);
        app.getFoolproofModule().updateAll();
    }
    
    @Override
    public void paste() {
        OfficeHoursData data = (OfficeHoursData) app.getDataComponent();
        TeachingAssistantPrototype ta = clipboardCutItems.get(0);
        TeachingAssistantPrototype newTA = new TeachingAssistantPrototype(ta.getName(), ta.getEmail(), ta.getTaType());
        data.addTA(newTA);
    }    

    @Override
    public boolean hasSomethingToCut() {
        return ((OfficeHoursData)app.getDataComponent()).isTASelected();
    }

    @Override
    public boolean hasSomethingToCopy() {
        return ((OfficeHoursData)app.getDataComponent()).isTASelected();
    }

    @Override
    public boolean hasSomethingToPaste() {
        if ((clipboardCutItems != null) && (!clipboardCutItems.isEmpty()))
            return true;
        else if ((clipboardCopiedItems != null) && (!clipboardCopiedItems.isEmpty()))
            return true;
        else
            return false;
    }
}