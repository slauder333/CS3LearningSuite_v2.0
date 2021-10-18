/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.workspace;

import djf.modules.AppGUIModule;
import static djf.modules.AppGUIModule.ENABLED;
import djf.ui.AppNodesBuilder;
import java.time.LocalDate;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import properties_manager.PropertiesManager;
import sg.Data.Lab;
import sg.Data.Lecture;
import sg.Data.MeetingTimesData;
import sg.Data.ScheduleData;
import sg.Data.ScheduleItem;
import sg.Data.SiteGeneratorData;
import sg.SiteGeneratorApp;
import static sg.SiteGeneratorPropertyType.*;
import sg.transactions.AddScheduleItem_Transaction;
import sg.transactions.ClearScheduleItem_Transaction;
import sg.transactions.EditScheduleItem_Transaction;
import sg.transactions.RemoveScheduleItem_Transaction;
import static sg.workspace.style.SGStyle.*;

/**
 *
 * @author Brandon
 */
public class ScheduleWorkspaceComponent {
    SiteGeneratorApp app;
    VBox mainPane;
    public ScheduleWorkspaceComponent(SiteGeneratorApp initApp) {
        
        app = initApp;
        // LAYOUT THE APP
        initLayout();

        // INIT THE EVENT HANDLERS
        initControllers();

        // SETUP FOOLPROOF DESIGN FOR THIS APP
        //initFoolproofDesign();
    }

    // THIS HELPER METHOD INITIALIZES ALL THE CONTROLS IN THE WORKSPACE
    private void initLayout() {
        PropertiesManager props = PropertiesManager.getPropertiesManager();
        AppGUIModule gui = app.getGUIModule();
        
        // THIS WILL BUILD ALL OF OUR JavaFX COMPONENTS FOR US
        AppNodesBuilder scBuilder = app.getGUIModule().getNodesBuilder();
        mainPane = scBuilder.buildVBox(SC_MAIN_PANE, null, CLASS_OH_BACKGROUND_PANE, ENABLED);
        
        GridPane calendarPane = scBuilder.buildGridPane(SC_CALENDAR_PANE, mainPane, CLASS_OH_PANE, ENABLED);
        scBuilder.buildLabel(SC_CALENDAR_HEADER, calendarPane, 0, 0, 1, 1, CLASS_MT_HEADER_LABEL, ENABLED);
        scBuilder.buildLabel(SC_START_DATE_LABEL, calendarPane, 0, 1, 1, 1, CLASS_OH_PROMPT, ENABLED);
        scBuilder.buildDatePicker(SC_START_DATE_PICKER, calendarPane, 1, 1, 1, 1, EMPTY_TEXT, ENABLED);
        ((DatePicker)gui.getGUINode(SC_START_DATE_PICKER)).setValue(LocalDate.now());
        scBuilder.buildLabel(SC_END_DATE_LABEL, calendarPane, 2, 1, 1, 1, CLASS_OH_PROMPT, ENABLED);
        scBuilder.buildDatePicker(SC_END_DATE_PICKER, calendarPane, 3, 1, 1, 1, EMPTY_TEXT, ENABLED);
        ((DatePicker)gui.getGUINode(SC_END_DATE_PICKER)).setValue(LocalDate.now());
        
        
        
        VBox schedulePane = scBuilder.buildVBox(SC_SCHEDULE_PANE, mainPane, CLASS_OH_PANE, ENABLED);
        HBox scheduleHeaderBox = scBuilder.buildHBox(SC_SCHEDULE_HEADER_PANE, schedulePane, CLASS_OH_PANE, ENABLED);
        scheduleHeaderBox.setSpacing(20);
        scBuilder.buildTextButton(SC_SCHEDULE_REMOVE_BUTTON, scheduleHeaderBox, EMPTY_TEXT, ENABLED);
        scBuilder.buildLabel(SC_SCHEDULE_HEADER, scheduleHeaderBox, CLASS_MT_HEADER_LABEL, ENABLED);

        // SETUP THE OFFICE HOURS TABLE
        TableView<ScheduleItem> scheduleTable = scBuilder.buildTableView(SC_SCHEDULE_TABLE_VIEW, schedulePane, CLASS_MT_TABLE_VIEW, ENABLED);
        TableColumn typeColumn = scBuilder.buildTableColumn(SC_SCHEDULE_TYPE_COLUMN, scheduleTable, CLASS_MT_COLUMN);
        TableColumn dateColumn = scBuilder.buildTableColumn(SC_SCHEDULE_DATE_COLUMN, scheduleTable, CLASS_MT_COLUMN);
        TableColumn titleColumn = scBuilder.buildTableColumn(SC_SCHEDULE_TITLE_COLUMN, scheduleTable, CLASS_MT_COLUMN);
        TableColumn topicColumn = scBuilder.buildTableColumn(SC_SCHEDULE_TOPIC_COLUMN, scheduleTable, CLASS_MT_COLUMN);
        
        typeColumn.setCellValueFactory(new PropertyValueFactory<String, String>("type"));
        dateColumn.setCellValueFactory(new PropertyValueFactory<String, String>("date"));
        titleColumn.setCellValueFactory(new PropertyValueFactory<String, String>("title"));
        topicColumn.setCellValueFactory(new PropertyValueFactory<String, String>("topic"));
        
        for (int i = 0; i < scheduleTable.getColumns().size(); i++) {
            ((TableColumn)scheduleTable.getColumns().get(i)).prefWidthProperty().bind(scheduleTable.widthProperty().multiply(1.0/4.0));
        }
    
        
        GridPane addPane = scBuilder.buildGridPane(SC_ADD_PANE, mainPane, CLASS_OH_PANE, ENABLED);
        scBuilder.buildLabel(SC_ADD_HEADER, addPane, 0, 0, 1, 1, CLASS_MT_HEADER_LABEL, ENABLED);
        scBuilder.buildLabel(SC_TYPE_LABEL, addPane, 0, 1, 1, 1, CLASS_OH_PROMPT, ENABLED);
        scBuilder.buildComboBox(SC_TYPE_BOX, addPane, 1, 1, 1, 1, EMPTY_TEXT, ENABLED, SCHEDULE_TYPE_OPTIONS, SC_DEFAULT_SCHEDULE_TYPE);
        scBuilder.buildLabel(SC_DATE_LABEL, addPane, 0, 2, 1, 1, CLASS_OH_PROMPT, ENABLED);
        scBuilder.buildDatePicker(SC_DATE_BOX, addPane, 1, 2, 1, 1, EMPTY_TEXT, ENABLED);
        ((DatePicker)gui.getGUINode(SC_DATE_BOX)).setValue(LocalDate.now());
        scBuilder.buildLabel(SC_TITLE_LABEL, addPane, 0, 3, 1, 1, CLASS_OH_PROMPT, ENABLED);
        scBuilder.buildTextField(SC_TITLE_BOX, addPane, 1, 3, 1, 1, EMPTY_TEXT, ENABLED);
        scBuilder.buildLabel(SC_TOPIC_LABEL, addPane, 0, 4, 1, 1, CLASS_OH_PROMPT, ENABLED);
        scBuilder.buildTextField(SC_TOPIC_BOX, addPane, 1, 4, 1, 1, EMPTY_TEXT, ENABLED);
        scBuilder.buildLabel(SC_LINK_LABEL, addPane, 0, 5, 1, 1, CLASS_OH_PROMPT, ENABLED);
        scBuilder.buildTextField(SC_LINK_BOX, addPane, 1, 5, 1, 1, EMPTY_TEXT, ENABLED);
        scBuilder.buildTextButton(SC_ADD_BUTTON, addPane, 0, 6, 1, 1, EMPTY_TEXT, ENABLED);
        scBuilder.buildTextButton(SC_CLEAR_BUTTON, addPane, 1, 6, 1, 1, EMPTY_TEXT, ENABLED);
    }
    public VBox getPane(){
        return mainPane;
    }
    
    public void initControllers(){
        AppGUIModule gui = app.getGUIModule();
        ((Button) gui.getGUINode(SC_ADD_BUTTON)).setOnAction(e -> {
            TableView scheduleTable = (TableView)gui.getGUINode(SC_SCHEDULE_TABLE_VIEW);
            if(scheduleTable.getSelectionModel().getSelectedItem()==null){
                ComboBox typeBox = (ComboBox)gui.getGUINode(SC_TYPE_BOX);
                String type = (String)typeBox.getSelectionModel().getSelectedItem();
                LocalDate date = ((DatePicker)gui.getGUINode(SC_DATE_BOX)).getValue();
                String title = ((TextField)gui.getGUINode(SC_TITLE_BOX)).getText();
                String topic = ((TextField)gui.getGUINode(SC_TOPIC_BOX)).getText();
                String link = ((TextField)gui.getGUINode(SC_LINK_BOX)).getText();
                ScheduleItem item = new ScheduleItem(type, date, title, topic, link);
                SiteGeneratorData dataComponent = (SiteGeneratorData)app.getDataComponent();
                ScheduleData data = dataComponent.getScheduleData();
                //data.addItem(item);
                AddScheduleItem_Transaction trans = new AddScheduleItem_Transaction(data, item);
                app.processTransaction(trans);
                ObservableList<ScheduleItem> list = data.getScheduleItems();
                list.sort((a, b) -> a.getDate().compareTo(b.getDate()));
                typeBox.getSelectionModel().select("Options");
                ((DatePicker)gui.getGUINode(SC_DATE_BOX)).setValue(LocalDate.now());
                ((TextField)gui.getGUINode(SC_TITLE_BOX)).setText("");
                ((TextField)gui.getGUINode(SC_TOPIC_BOX)).setText("");
                ((TextField)gui.getGUINode(SC_LINK_BOX)).setText("");
            }else{
                ScheduleItem item = (ScheduleItem)scheduleTable.getSelectionModel().getSelectedItem();
                ComboBox typeBox = (ComboBox)gui.getGUINode(SC_TYPE_BOX);
                String type = ((String)typeBox.getSelectionModel().getSelectedItem());
                LocalDate date = (((DatePicker)gui.getGUINode(SC_DATE_BOX)).getValue());
                String title = (((TextField)gui.getGUINode(SC_TITLE_BOX)).getText());
                String topic = (((TextField)gui.getGUINode(SC_TOPIC_BOX)).getText());
                String link = (((TextField)gui.getGUINode(SC_LINK_BOX)).getText());
                SiteGeneratorData dataComponent = (SiteGeneratorData)app.getDataComponent();
                ScheduleData data = dataComponent.getScheduleData();
                ScheduleItem newItem = new ScheduleItem(type, date, title, topic, link);
                EditScheduleItem_Transaction trans = new EditScheduleItem_Transaction(item, newItem);
                app.processTransaction(trans);
                
                ObservableList<ScheduleItem> list = data.getScheduleItems();
                list.sort((a, b) -> a.getDate().compareTo(b.getDate()));
                typeBox.getSelectionModel().select("Options");
                ((DatePicker)gui.getGUINode(SC_DATE_BOX)).setValue(LocalDate.now());
                ((TextField)gui.getGUINode(SC_TITLE_BOX)).setText("");
                ((TextField)gui.getGUINode(SC_TOPIC_BOX)).setText("");
                ((TextField)gui.getGUINode(SC_LINK_BOX)).setText("");
            }
        });
        TableView scheduleTable = (TableView)gui.getGUINode(SC_SCHEDULE_TABLE_VIEW);
        scheduleTable.setOnMouseClicked(e -> {
            ComboBox typeBox = (ComboBox)gui.getGUINode(SC_TYPE_BOX);
            if(scheduleTable.getSelectionModel().getSelectedItem()==null){
            typeBox.getSelectionModel().select("Options");
                ((DatePicker)gui.getGUINode(SC_DATE_BOX)).setValue(LocalDate.now());
                ((TextField)gui.getGUINode(SC_TITLE_BOX)).setText("");
                ((TextField)gui.getGUINode(SC_TOPIC_BOX)).setText("");
                ((TextField)gui.getGUINode(SC_LINK_BOX)).setText("");
            }else{
            ScheduleItem item = (ScheduleItem)scheduleTable.getSelectionModel().getSelectedItem();
            typeBox.getSelectionModel().select(item.getType());
            ((DatePicker)gui.getGUINode(SC_DATE_BOX)).setValue(item.getDate());
            ((TextField)gui.getGUINode(SC_TITLE_BOX)).setText(item.getTitle());
            ((TextField)gui.getGUINode(SC_TOPIC_BOX)).setText(item.getTopic());
            ((TextField)gui.getGUINode(SC_LINK_BOX)).setText(item.getLink());
            }
        });
        ((Button) gui.getGUINode(SC_SCHEDULE_REMOVE_BUTTON)).setOnAction(e -> {
            ScheduleItem item = (ScheduleItem)scheduleTable.getSelectionModel().getSelectedItem();
            SiteGeneratorData dataComponent = (SiteGeneratorData)app.getDataComponent();
            ScheduleData data = dataComponent.getScheduleData();
            RemoveScheduleItem_Transaction trans = new RemoveScheduleItem_Transaction(data, item);
            app.processTransaction(trans);
        });
        ((DatePicker)gui.getGUINode(SC_START_DATE_PICKER)).valueProperty().addListener((ov, t, t1) -> {
            SiteGeneratorData dataComponent = (SiteGeneratorData)app.getDataComponent();
            ScheduleData data = dataComponent.getScheduleData();
            data.setStartDate(t1);
        });
        ((DatePicker)gui.getGUINode(SC_END_DATE_PICKER)).valueProperty().addListener((ov, t, t1) -> {
            SiteGeneratorData dataComponent = (SiteGeneratorData)app.getDataComponent();
            ScheduleData data = dataComponent.getScheduleData();
            data.setEndDate(t1);
        });
        ((Button) gui.getGUINode(SC_CLEAR_BUTTON)).setOnAction(e -> {
            SiteGeneratorData dataComponent = (SiteGeneratorData)app.getDataComponent();
            ScheduleData data = dataComponent.getScheduleData();
            ClearScheduleItem_Transaction trans = new ClearScheduleItem_Transaction(data);
            app.processTransaction(trans);
        });
    }
}
