/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.workspace;

import djf.modules.AppFoolproofModule;
import djf.modules.AppGUIModule;
import static djf.modules.AppGUIModule.ENABLED;
import djf.ui.AppNodesBuilder;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import properties_manager.PropertiesManager;
import sg.Data.TeachingAssistantPrototype;
import sg.Data.TimeSlot;
import sg.SiteGeneratorApp;
import static sg.workspace.style.SGStyle.*;
import static sg.SiteGeneratorPropertyType.*;
import sg.workspace.controllers.OfficeHoursController;
import sg.workspace.foolproof.OfficeHoursFoolproofDesign;

/**
 *
 * @author Brandon
 */
public class OfficeHoursWorkspaceComponent {
    ToggleGroup group = new ToggleGroup();
    ToggleGroup groupAdd = new ToggleGroup();
    SiteGeneratorApp app;
    VBox mainPane;
    public OfficeHoursWorkspaceComponent(SiteGeneratorApp initApp) {
        
        app = initApp;
        // LAYOUT THE APP
        initLayout();

        // INIT THE EVENT HANDLERS
        initControllers();

        // SETUP FOOLPROOF DESIGN FOR THIS APP
        initFoolproofDesign();
    }

    // THIS HELPER METHOD INITIALIZES ALL THE CONTROLS IN THE WORKSPACE
    private void initLayout() {
        // FIRST LOAD THE FONT FAMILIES FOR THE COMBO BOX
        PropertiesManager props = PropertiesManager.getPropertiesManager();

        // THIS WILL BUILD ALL OF OUR JavaFX COMPONENTS FOR US
        AppNodesBuilder ohBuilder = app.getGUIModule().getNodesBuilder();

        // INIT THE HEADER ON THE LEFT
        VBox leftPane = ohBuilder.buildVBox(OH_LEFT_PANE, null, CLASS_OH_PANE, ENABLED);
        HBox tasHeaderBox = ohBuilder.buildHBox(OH_TAS_HEADER_PANE, leftPane, CLASS_OH_BOX, ENABLED);
        tasHeaderBox.setSpacing(20);
        ohBuilder.buildLabel(OH_TAS_HEADER_LABEL, tasHeaderBox, CLASS_OH_HEADER_LABEL, ENABLED);

        //ADD SATALEITE BUTTONS
//        final ToggleGroup group = new ToggleGroup();
        RadioButton allTAButton = ohBuilder.buildRadioButton(OH_ALL_TA_BUTTON, tasHeaderBox, CLASS_OH_BUTTON, ENABLED);
        allTAButton.setToggleGroup(group);
        allTAButton.setSelected(true);
        allTAButton.setUserData("All");
        RadioButton undergraduateTAButton = ohBuilder.buildRadioButton(OH_UNDERGRADUATE_TA_BUTTON, tasHeaderBox, CLASS_OH_BUTTON, ENABLED);
        undergraduateTAButton.setToggleGroup(group);
        undergraduateTAButton.setUserData("UNDERGRADUATE");
        RadioButton graduateTAButton = ohBuilder.buildRadioButton(OH_GRADUATE_TA_BUTTON, tasHeaderBox, CLASS_OH_BUTTON, ENABLED);
        graduateTAButton.setToggleGroup(group);
        graduateTAButton.setUserData("GRADUATE");
        
        
        // MAKE THE TABLE AND SETUP THE DATA MODEL
        TableView<TeachingAssistantPrototype> taTable = ohBuilder.buildTableView(OH_TAS_TABLE_VIEW, leftPane, CLASS_OH_TABLE_VIEW, ENABLED);
        taTable.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        TableColumn nameColumn = ohBuilder.buildTableColumn(OH_NAME_TABLE_COLUMN, taTable, CLASS_OH_COLUMN);
        TableColumn emailColumn = ohBuilder.buildTableColumn(OH_EMAIL_TABLE_COLUMN, taTable, CLASS_OH_COLUMN);
        TableColumn slotsColumn = ohBuilder.buildTableColumn(OH_SLOTS_TABLE_COLUMN, taTable, CLASS_OH_COLUMN);
        TableColumn taTypeColumn = ohBuilder.buildTableColumn(OH_TA_TYPE_TABLE_COLUMN, taTable, CLASS_OH_COLUMN);
        nameColumn.setCellValueFactory(new PropertyValueFactory<String, String>("name"));
        nameColumn.prefWidthProperty().bind(taTable.widthProperty().multiply(1.0/4.0));
        emailColumn.setCellValueFactory(new PropertyValueFactory<String, String>("email"));
        emailColumn.prefWidthProperty().bind(taTable.widthProperty().multiply(1.0/4.0));
        slotsColumn.setCellValueFactory(new PropertyValueFactory<String, String>("slots"));
        slotsColumn.prefWidthProperty().bind(taTable.widthProperty().multiply(1.0/4.0));
        taTypeColumn.setCellValueFactory(new PropertyValueFactory<String, String>("taType"));
        taTypeColumn.prefWidthProperty().bind(taTable.widthProperty().multiply(1.0/4.0));
        
        // ADD BOX FOR ADDING A TA
        HBox taBox = ohBuilder.buildHBox(OH_ADD_TA_PANE, leftPane, CLASS_OH_PANE, ENABLED);
        taBox.setSpacing(20);
        ohBuilder.buildTextField(OH_NAME_TEXT_FIELD, taBox, CLASS_OH_TEXT_FIELD, ENABLED, OH_NAME_PROMPT);
        ohBuilder.buildTextField(OH_EMAIL_TEXT_FIELD, taBox, CLASS_OH_TEXT_FIELD, ENABLED, OH_EMAIL_PROMPT);
        
//        final ToggleGroup groupAdd = new ToggleGroup();
        RadioButton addUndergraduateTAButton = ohBuilder.buildRadioButton(OH_ADD_UNDERGRADUATE_TA_BUTTON, taBox, CLASS_OH_BUTTON, ENABLED);
        addUndergraduateTAButton.setToggleGroup(groupAdd);
        addUndergraduateTAButton.setUserData("UNDERGRADUATE");
        RadioButton addGraduateTAButton = ohBuilder.buildRadioButton(OH_ADD_GRADUATE_TA_BUTTON, taBox, CLASS_OH_BUTTON, ENABLED);
        addGraduateTAButton.setToggleGroup(groupAdd);
        addGraduateTAButton.setUserData("GRADUATE");
        ohBuilder.buildTextButton(OH_ADD_TA_BUTTON, taBox, CLASS_OH_BUTTON, ENABLED);
        
        // MAKE SURE IT'S THE TABLE THAT ALWAYS GROWS IN THE LEFT PANE
        VBox.setVgrow(taTable, Priority.ALWAYS);

        // INIT THE HEADER ON THE RIGHT
        VBox rightPane = ohBuilder.buildVBox(OH_RIGHT_PANE, null, CLASS_OH_PANE, ENABLED);
        HBox officeHoursHeaderBox = ohBuilder.buildHBox(OH_OFFICE_HOURS_HEADER_PANE, rightPane, CLASS_OH_PANE, ENABLED);
        ohBuilder.buildLabel(OH_OFFICE_HOURS_HEADER_LABEL, officeHoursHeaderBox, CLASS_OH_HEADER_LABEL, ENABLED);

        // SETUP THE OFFICE HOURS TABLE
        TableView<TimeSlot> officeHoursTable = ohBuilder.buildTableView(OH_OFFICE_HOURS_TABLE_VIEW, rightPane, CLASS_OH_OFFICE_HOURS_TABLE_VIEW, ENABLED);
        TableColumn startTimeColumn = ohBuilder.buildTableColumn(OH_START_TIME_TABLE_COLUMN, officeHoursTable, CLASS_OH_TIME_COLUMN);
        TableColumn endTimeColumn = ohBuilder.buildTableColumn(OH_END_TIME_TABLE_COLUMN, officeHoursTable, CLASS_OH_TIME_COLUMN);
        TableColumn mondayColumn = ohBuilder.buildTableColumn(OH_MONDAY_TABLE_COLUMN, officeHoursTable, CLASS_OH_DAY_OF_WEEK_COLUMN);
        TableColumn tuesdayColumn = ohBuilder.buildTableColumn(OH_TUESDAY_TABLE_COLUMN, officeHoursTable, CLASS_OH_DAY_OF_WEEK_COLUMN);
        TableColumn wednesdayColumn = ohBuilder.buildTableColumn(OH_WEDNESDAY_TABLE_COLUMN, officeHoursTable, CLASS_OH_DAY_OF_WEEK_COLUMN);
        TableColumn thursdayColumn = ohBuilder.buildTableColumn(OH_THURSDAY_TABLE_COLUMN, officeHoursTable, CLASS_OH_DAY_OF_WEEK_COLUMN);
        TableColumn fridayColumn = ohBuilder.buildTableColumn(OH_FRIDAY_TABLE_COLUMN, officeHoursTable, CLASS_OH_DAY_OF_WEEK_COLUMN);
        startTimeColumn.setCellValueFactory(new PropertyValueFactory<String, String>("startTime"));
        endTimeColumn.setCellValueFactory(new PropertyValueFactory<String, String>("endTime"));
        mondayColumn.setCellValueFactory(new PropertyValueFactory<String, String>("monday"));
        tuesdayColumn.setCellValueFactory(new PropertyValueFactory<String, String>("tuesday"));
        wednesdayColumn.setCellValueFactory(new PropertyValueFactory<String, String>("wednesday"));
        thursdayColumn.setCellValueFactory(new PropertyValueFactory<String, String>("thursday"));
        fridayColumn.setCellValueFactory(new PropertyValueFactory<String, String>("friday"));
        for (int i = 0; i < officeHoursTable.getColumns().size(); i++) {
            ((TableColumn)officeHoursTable.getColumns().get(i)).prefWidthProperty().bind(officeHoursTable.widthProperty().multiply(1.0/7.0));
        }

        // MAKE SURE IT'S THE TABLE THAT ALWAYS GROWS IN THE LEFT PANE
        VBox.setVgrow(officeHoursTable, Priority.ALWAYS);

        // BOTH PANES WILL NOW GO IN A SPLIT PANE
        mainPane = ohBuilder.buildVBox(OH_MAIN_PANE, null, CLASS_OH_BACKGROUND_PANE, ENABLED);
        mainPane.getChildren().addAll(leftPane, rightPane);
        
       
    }

    private void initControllers() {
        OfficeHoursController controller = new OfficeHoursController((SiteGeneratorApp) app);
        AppGUIModule gui = app.getGUIModule();
        ((TextField) gui.getGUINode(OH_NAME_TEXT_FIELD)).setOnAction(e -> {
            controller.processAddTA();
        });
        ((TextField) gui.getGUINode(OH_EMAIL_TEXT_FIELD)).setOnAction(e -> {
            controller.processAddTA();
        });
        ((Button) gui.getGUINode(OH_ADD_TA_BUTTON)).setOnAction(e -> {
            controller.processAddTA();
        });
        TableView officeHoursTableView = (TableView) gui.getGUINode(OH_OFFICE_HOURS_TABLE_VIEW);
        // DON'T LET ANYONE SORT THE TABLES
        for (int i = 0; i < officeHoursTableView.getColumns().size(); i++) {
            ((TableColumn)officeHoursTableView.getColumns().get(i)).setSortable(false);
        }
        officeHoursTableView.setOnMouseClicked(e -> {
            controller.processAddHours();
        });
        AppFoolproofModule foolProof = app.getFoolproofModule();
        foolProof.updateAll();
        ((TextField) gui.getGUINode(OH_NAME_TEXT_FIELD)).textProperty().addListener((cl) -> {
        foolProof.updateAll();
        });
        ((TextField) gui.getGUINode(OH_EMAIL_TEXT_FIELD)).textProperty().addListener((cl) -> {
        foolProof.updateAll();
        });
        groupAdd.selectedToggleProperty().addListener((ObservableValue<? extends Toggle> ov, Toggle old_toggle, Toggle new_toggle) -> {
            String tota = (String)groupAdd.getSelectedToggle().getUserData();
            controller.setTypeOfTa(tota);
        });
        group.selectedToggleProperty().addListener((ObservableValue<? extends Toggle> ov, Toggle old_toggle, Toggle new_toggle) -> {
            String tota = (String)group.getSelectedToggle().getUserData();
            controller.updateTADisplay(tota);
        });
        
        TableView tasTableView = (TableView) gui.getGUINode(OH_TAS_TABLE_VIEW);
        tasTableView.setOnMouseClicked(e -> {
            if (e.getClickCount() == 2){
                controller.showEditTADialog();
            }
            app.getFoolproofModule().updateAll();
        }
    );  
        
//        tasTableView.setOnMouseClicked(e -> {
//            app.getFoolproofModule().updateAll();
//        }
//    );
    }

    private void initFoolproofDesign() {
        AppGUIModule gui = app.getGUIModule();
        AppFoolproofModule foolproofSettings = app.getFoolproofModule();
        foolproofSettings.registerModeSettings(OH_FOOLPROOF_SETTINGS,
                new OfficeHoursFoolproofDesign((SiteGeneratorApp) app));
    }
    
    public VBox getPane(){
        return mainPane;
    }
}
