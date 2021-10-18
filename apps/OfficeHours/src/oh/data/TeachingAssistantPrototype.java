package oh.data;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * This class represents a Teaching Assistant for the table of TAs.
 * 
 * @author Richard McKenna
 */
public class TeachingAssistantPrototype {
    // THE TABLE WILL STORE TA NAMES AND EMAILS
    public enum TypeOfTa{
        GRADUATE, UNDERGRADUATE;
    }
    private final StringProperty name;
    private final StringProperty email;
    private final StringProperty slots;
    private final StringProperty taType;
    
    /**
     * Constructor initializes both the TA name and email.
     */
    public TeachingAssistantPrototype(String initName, String initEmail, String initTaType) {
        name = new SimpleStringProperty(initName);
        email = new SimpleStringProperty(initEmail);
        slots = new SimpleStringProperty("0");
        taType = new SimpleStringProperty (initTaType);
    }

    
    
    // ACCESSORS AND MUTATORS FOR THE PROPERTIES

    public String getName() {
        return name.get();
    }

    public void setName(String initName) {
        name.set(initName);
    }
    
    public StringProperty nameProperty() {
        return name;
    }
    
    public String getEmail() {
        return email.get();
    }

    public void setEmail(String initEmail) {
        email.set(initEmail);
    }
    
    public StringProperty emailProperty() {
        return email;
    }
    
    public String getSlots() {
        return slots.get();
    }

    public void setSlots(String initSlots) {
        slots.set(initSlots);
    }
    
    public StringProperty slotsProperty() {
        return slots;
    }
    
    public String getTaType(){
        return taType.get();
    }
    
    public void setTaType(String initTaType){
        taType.set(initTaType);
    }
    
    public StringProperty taTypeProperty(){
        return taType;
    }
    
    
    public void incSlot(){
        int i = Integer.parseInt(slots.get());
        i++;
        slots.setValue(Integer.toString(i));
    }
    
    public void decSlot(){
        int i = Integer.parseInt(slots.get());
        i--;
        slots.setValue(Integer.toString(i));
    }
    
    @Override
    public String toString() {
        return name.getValue();
    }
}