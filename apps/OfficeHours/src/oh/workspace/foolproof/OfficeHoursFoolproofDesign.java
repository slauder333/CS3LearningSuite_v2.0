package oh.workspace.foolproof;

import djf.modules.AppGUIModule;
import djf.ui.foolproof.FoolproofDesign;
import java.util.Collections;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javafx.collections.ObservableList;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import oh.OfficeHoursApp;
import static oh.OfficeHoursPropertyType.OH_ADD_TA_BUTTON;
import static oh.OfficeHoursPropertyType.OH_EMAIL_TEXT_FIELD;
import static oh.OfficeHoursPropertyType.OH_NAME_TEXT_FIELD;

/**
 *
 * @author McKillaGorilla
 */
public class OfficeHoursFoolproofDesign implements FoolproofDesign {
    OfficeHoursApp app;
    
    public OfficeHoursFoolproofDesign(OfficeHoursApp initApp) {
        app = initApp;
    }

    @Override
    public void updateControls() {
        AppGUIModule gui = app.getGUIModule();
        TextField nameTF =  (TextField) gui.getGUINode(OH_NAME_TEXT_FIELD);
        TextField emailTF = (TextField) gui.getGUINode(OH_EMAIL_TEXT_FIELD);
        Button addButton = (Button) gui.getGUINode(OH_ADD_TA_BUTTON);
        String name = nameTF.getText();
        String email = emailTF.getText();
        ObservableList<String> styleClass = nameTF.getStyleClass();
        ObservableList<String> styleClass1 = emailTF.getStyleClass();
        addButton.setDisable(!validate(email) || name.isEmpty() || email.isEmpty());
        if(!validate(email) || name.isEmpty() || email.isEmpty()){
            if (! styleClass.contains("error")) {
                styleClass.add("error");
                styleClass1.add("error");
            }
        }else {
            styleClass.removeAll(Collections.singleton("error"));
            styleClass1.removeAll(Collections.singleton("error")); 
        }
    }
    
    public static final Pattern VALID_EMAIL_ADDRESS_REGEX = 
    Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);

    public static boolean validate(String emailStr) {
        Matcher matcher = VALID_EMAIL_ADDRESS_REGEX .matcher(emailStr);
        return matcher.find();
    }
}