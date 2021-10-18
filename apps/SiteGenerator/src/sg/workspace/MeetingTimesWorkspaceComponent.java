/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.workspace;

import djf.modules.AppGUIModule;
import static djf.modules.AppGUIModule.ENABLED;
import djf.ui.AppNodesBuilder;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import properties_manager.PropertiesManager;
import sg.Data.Lab;
import sg.Data.Lecture;
import sg.Data.MeetingTimesData;
import sg.Data.OfficeHoursData;
import sg.Data.Recitation;
import sg.Data.SiteGeneratorData;
import sg.SiteGeneratorApp;
import static sg.SiteGeneratorPropertyType.*;
import sg.transactions.AddMeetingTime_Transaction;
import sg.transactions.EditMeetingTime_Transaction;
import sg.transactions.RemoveMeetingTime_Transaction;
import sg.workspace.controllers.OfficeHoursController;
import static sg.workspace.style.SGStyle.*;

/**
 *
 * @author Brandon
 */
public class MeetingTimesWorkspaceComponent {
    SiteGeneratorApp app;
    VBox mainPane;
    public MeetingTimesWorkspaceComponent(SiteGeneratorApp initApp) {
        
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
        
        
        // THIS WILL BUILD ALL OF OUR JavaFX COMPONENTS FOR US
        AppNodesBuilder mtBuilder = app.getGUIModule().getNodesBuilder();
        mainPane = mtBuilder.buildVBox(MT_MAIN_PANE, null, CLASS_OH_BACKGROUND_PANE, ENABLED);
        
        VBox lecturePane = mtBuilder.buildVBox(MT_LECTURE_PANE, mainPane, CLASS_OH_PANE, ENABLED);
        HBox lectureHeaderBox = mtBuilder.buildHBox(MT_LECTURE_HEADER_PANE, lecturePane, CLASS_OH_PANE, ENABLED);
        lectureHeaderBox.setSpacing(10);
        mtBuilder.buildTextButton(MT_LECTURE_ADD_BUTTON, lectureHeaderBox, EMPTY_TEXT, ENABLED);
        mtBuilder.buildTextButton(MT_LECTURE_REMOVE_BUTTON, lectureHeaderBox, EMPTY_TEXT, ENABLED);
        mtBuilder.buildLabel(MT_LECTURE_HEADER_LABEL, lectureHeaderBox, CLASS_MT_HEADER_LABEL, ENABLED);

        // SETUP THE OFFICE HOURS TABLE
        TableView<Lecture> lectureTable = mtBuilder.buildTableView(MT_LECTURE_TABLEVIEW, lecturePane, CLASS_MT_TABLE_VIEW, ENABLED);
        TableColumn sectionColumn = mtBuilder.buildTableColumn(MT_LECTURE_SECTION_COLUMN, lectureTable, CLASS_MT_COLUMN);
        TableColumn dayColumn = mtBuilder.buildTableColumn(MT_LECTURE_DAY_COLUMN, lectureTable, CLASS_MT_COLUMN);
        TableColumn timeColumn = mtBuilder.buildTableColumn(MT_LECTURE_TIME_COLUMN, lectureTable, CLASS_MT_COLUMN);
        TableColumn roomColumn = mtBuilder.buildTableColumn(MT_LECTURE_ROOM_COLUMN, lectureTable, CLASS_MT_COLUMN);
        
        sectionColumn.setCellValueFactory(new PropertyValueFactory<String, String>("section"));
        dayColumn.setCellValueFactory(new PropertyValueFactory<String, String>("day"));
        timeColumn.setCellValueFactory(new PropertyValueFactory<String, String>("time"));
        roomColumn.setCellValueFactory(new PropertyValueFactory<String, String>("room"));
        
        for (int i = 0; i < lectureTable.getColumns().size(); i++) {
            ((TableColumn)lectureTable.getColumns().get(i)).prefWidthProperty().bind(lectureTable.widthProperty().multiply(1.0/4.0));
        }
        lectureTable.setEditable(true);
        
        sectionColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        sectionColumn.setOnEditCommit(
        new EventHandler<CellEditEvent<Lecture, String>>() {
        @Override
        public void handle(CellEditEvent<Lecture, String> t) {
            Lecture lec = (Lecture) t.getTableView().getItems().get(t.getTablePosition().getRow());
            Lecture newlec = new Lecture(lec);
            newlec.setSection(t.getNewValue());
            EditMeetingTime_Transaction trans = new EditMeetingTime_Transaction(lec, newlec);
            app.processTransaction(trans);
        }
    }
);
        dayColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        dayColumn.setOnEditCommit(
        new EventHandler<CellEditEvent<Lecture, String>>() {
        @Override
        public void handle(CellEditEvent<Lecture, String> t) {
            Lecture lec = (Lecture) t.getTableView().getItems().get(t.getTablePosition().getRow());
            Lecture newlec = new Lecture(lec);
            newlec.setDay(t.getNewValue());
            EditMeetingTime_Transaction trans = new EditMeetingTime_Transaction(lec, newlec);
            app.processTransaction(trans);
        }
    }
);
        timeColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        timeColumn.setOnEditCommit(
        new EventHandler<CellEditEvent<Lecture, String>>() {
        @Override
        public void handle(CellEditEvent<Lecture, String> t) {
            Lecture lec = (Lecture) t.getTableView().getItems().get(t.getTablePosition().getRow());
            Lecture newlec = new Lecture(lec);
            newlec.setTime(t.getNewValue());
            EditMeetingTime_Transaction trans = new EditMeetingTime_Transaction(lec, newlec);
            app.processTransaction(trans);
        }
    }
);
        roomColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        roomColumn.setOnEditCommit(
        new EventHandler<CellEditEvent<Lecture, String>>() {
        @Override
        public void handle(CellEditEvent<Lecture, String> t) {
            Lecture lec = (Lecture) t.getTableView().getItems().get(t.getTablePosition().getRow());
            Lecture newlec = new Lecture(lec);
            newlec.setRoom(t.getNewValue());
            EditMeetingTime_Transaction trans = new EditMeetingTime_Transaction(lec, newlec);
            app.processTransaction(trans);
        }
    }
);
        
        
        
        VBox recitationPane = mtBuilder.buildVBox(MT_RECITATION_PANE, mainPane, CLASS_OH_PANE, ENABLED);
        HBox recitationHeaderBox = mtBuilder.buildHBox(MT_RECITATION_HEADER_PANE, recitationPane, CLASS_OH_PANE, ENABLED);
        recitationHeaderBox.setSpacing(10);
        mtBuilder.buildTextButton(MT_RECITATION_ADD_BUTTON, recitationHeaderBox, EMPTY_TEXT, ENABLED);
        mtBuilder.buildTextButton(MT_RECITATION_REMOVE_BUTTON, recitationHeaderBox, EMPTY_TEXT, ENABLED);
        mtBuilder.buildLabel(MT_RECITATION_HEADER_LABEL, recitationHeaderBox, CLASS_MT_HEADER_LABEL, ENABLED);

        // SETUP THE OFFICE HOURS TABLE
        TableView<Recitation> recitationTable = mtBuilder.buildTableView(MT_RECITATION_TABLEVIEW, recitationPane, CLASS_MT_TABLE_VIEW, ENABLED);
        TableColumn rsectionColumn = mtBuilder.buildTableColumn(MT_RECITATION_SECTION_COLUMN, recitationTable, CLASS_MT_COLUMN);
        TableColumn rdayTimeColumn = mtBuilder.buildTableColumn(MT_RECITATION_DAYTIME_COLUMN, recitationTable, CLASS_MT_COLUMN);
        TableColumn rRoomColumn = mtBuilder.buildTableColumn(MT_RECITATION_ROOM_COLUMN, recitationTable, CLASS_MT_COLUMN);
        TableColumn rTA1Column = mtBuilder.buildTableColumn(MT_RECITATION_TA1_COLUMN, recitationTable, CLASS_MT_COLUMN);
        TableColumn rTA2Column = mtBuilder.buildTableColumn(MT_RECITATION_TA2_COLUMN, recitationTable, CLASS_MT_COLUMN);
        
        rsectionColumn.setCellValueFactory(new PropertyValueFactory<String, String>("section"));
        rdayTimeColumn.setCellValueFactory(new PropertyValueFactory<String, String>("dayTime"));
        rRoomColumn.setCellValueFactory(new PropertyValueFactory<String, String>("room"));
        rTA1Column.setCellValueFactory(new PropertyValueFactory<String, String>("ta1"));
        rTA2Column.setCellValueFactory(new PropertyValueFactory<String, String>("ta2"));
        
        for (int i = 0; i < recitationTable.getColumns().size(); i++) {
            ((TableColumn)recitationTable.getColumns().get(i)).prefWidthProperty().bind(recitationTable.widthProperty().multiply(1.0/5.0));
        }
        
        recitationTable.setEditable(true);
        
        rsectionColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        rsectionColumn.setOnEditCommit(
        new EventHandler<CellEditEvent<Recitation, String>>() {
        @Override
        public void handle(CellEditEvent<Recitation, String> t) {
            Recitation rec = (Recitation) t.getTableView().getItems().get(t.getTablePosition().getRow());
            Recitation newrec = new Recitation(rec);
            newrec.setSection(t.getNewValue());
            EditMeetingTime_Transaction trans = new EditMeetingTime_Transaction(rec, newrec);
            app.processTransaction(trans);
        }
    }
);
        rdayTimeColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        rdayTimeColumn.setOnEditCommit(
        new EventHandler<CellEditEvent<Recitation, String>>() {
        @Override
        public void handle(CellEditEvent<Recitation, String> t) {
            Recitation rec = (Recitation) t.getTableView().getItems().get(t.getTablePosition().getRow());
            Recitation newrec = new Recitation(rec);
            newrec.setDayTime(t.getNewValue());
            EditMeetingTime_Transaction trans = new EditMeetingTime_Transaction(rec, newrec);
            app.processTransaction(trans);
        }
    }
);
        rRoomColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        rRoomColumn.setOnEditCommit(
        new EventHandler<CellEditEvent<Recitation, String>>() {
        @Override
        public void handle(CellEditEvent<Recitation, String> t) {
            Recitation rec = (Recitation) t.getTableView().getItems().get(t.getTablePosition().getRow());
            Recitation newrec = new Recitation(rec);
            newrec.setRoom(t.getNewValue());
            EditMeetingTime_Transaction trans = new EditMeetingTime_Transaction(rec, newrec);
            app.processTransaction(trans);
        }
    }
);
        rTA1Column.setCellFactory(TextFieldTableCell.forTableColumn());
        rTA1Column.setOnEditCommit(
        new EventHandler<CellEditEvent<Recitation, String>>() {
        @Override
        public void handle(CellEditEvent<Recitation, String> t) {
            Recitation rec = (Recitation) t.getTableView().getItems().get(t.getTablePosition().getRow());
            Recitation newrec = new Recitation(rec);
            newrec.setTa1(t.getNewValue());
            EditMeetingTime_Transaction trans = new EditMeetingTime_Transaction(rec, newrec);
            app.processTransaction(trans);
        }
    }
);
        
        rTA2Column.setCellFactory(TextFieldTableCell.forTableColumn());
        rTA2Column.setOnEditCommit(
        new EventHandler<CellEditEvent<Recitation, String>>() {
        @Override
        public void handle(CellEditEvent<Recitation, String> t) {
            Recitation rec = (Recitation) t.getTableView().getItems().get(t.getTablePosition().getRow());
            Recitation newrec = new Recitation(rec);
            newrec.setTa2(t.getNewValue());
            EditMeetingTime_Transaction trans = new EditMeetingTime_Transaction(rec, newrec);
            app.processTransaction(trans);
        }
    }
);
        
        
        VBox labsPane = mtBuilder.buildVBox(MT_LABS_PANE, mainPane, CLASS_OH_PANE, ENABLED);
        HBox labsHeaderBox = mtBuilder.buildHBox(MT_LABS_HEADER_PANE, labsPane, CLASS_OH_PANE, ENABLED);
        labsHeaderBox.setSpacing(10);
        mtBuilder.buildTextButton(MT_LABS_ADD_BUTTON, labsHeaderBox, EMPTY_TEXT, ENABLED);
        mtBuilder.buildTextButton(MT_LABS_REMOVE_BUTTON, labsHeaderBox, EMPTY_TEXT, ENABLED);
        mtBuilder.buildLabel(MT_LABS_HEADER_LABEL, labsHeaderBox, CLASS_MT_HEADER_LABEL, ENABLED);

        // SETUP THE OFFICE HOURS TABLE
        TableView<Lab> labsTable = mtBuilder.buildTableView(MT_LABS_TABLEVIEW, labsPane, CLASS_MT_TABLE_VIEW, ENABLED);
        TableColumn lsectionColumn = mtBuilder.buildTableColumn(MT_LABS_SECTION_COLUMN, labsTable, CLASS_MT_COLUMN);
        TableColumn ldayTimeColumn = mtBuilder.buildTableColumn(MT_LABS_DAYTIME_COLUMN, labsTable, CLASS_MT_COLUMN);
        TableColumn lRoomColumn = mtBuilder.buildTableColumn(MT_LABS_ROOM_COLUMN, labsTable, CLASS_MT_COLUMN);
        TableColumn lTA1Column = mtBuilder.buildTableColumn(MT_LABS_TA1_COLUMN, labsTable, CLASS_MT_COLUMN);
        TableColumn lTA2Column = mtBuilder.buildTableColumn(MT_LABS_TA2_COLUMN, labsTable, CLASS_MT_COLUMN);
        
        lsectionColumn.setCellValueFactory(new PropertyValueFactory<String, String>("section"));
        ldayTimeColumn.setCellValueFactory(new PropertyValueFactory<String, String>("dayTime"));
        lRoomColumn.setCellValueFactory(new PropertyValueFactory<String, String>("room"));
        lTA1Column.setCellValueFactory(new PropertyValueFactory<String, String>("ta1"));
        lTA2Column.setCellValueFactory(new PropertyValueFactory<String, String>("ta2"));
        
        for (int i = 0; i < labsTable.getColumns().size(); i++) {
            ((TableColumn)labsTable.getColumns().get(i)).prefWidthProperty().bind(labsTable.widthProperty().multiply(1.0/5.0));
        }
        
        labsTable.setEditable(true);
        
        lsectionColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        lsectionColumn.setOnEditCommit(
        new EventHandler<CellEditEvent<Lab, String>>() {
        @Override
        public void handle(CellEditEvent<Lab, String> t) {
            Lab lab = (Lab) t.getTableView().getItems().get(t.getTablePosition().getRow());
            Lab newlab = new Lab(lab);
            newlab.setSection(t.getNewValue());
            EditMeetingTime_Transaction trans = new EditMeetingTime_Transaction(lab, newlab);
            app.processTransaction(trans);
        }
    }
);
        ldayTimeColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        ldayTimeColumn.setOnEditCommit(
        new EventHandler<CellEditEvent<Lab, String>>() {
        @Override
        public void handle(CellEditEvent<Lab, String> t) {
            Lab lab = (Lab) t.getTableView().getItems().get(t.getTablePosition().getRow());
            Lab newlab = new Lab(lab);
            newlab.setDayTime(t.getNewValue());
            EditMeetingTime_Transaction trans = new EditMeetingTime_Transaction(lab, newlab);
            app.processTransaction(trans);
        }
    }
);
        lRoomColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        lRoomColumn.setOnEditCommit(
        new EventHandler<CellEditEvent<Lab, String>>() {
        @Override
        public void handle(CellEditEvent<Lab, String> t) {
            Lab lab = (Lab) t.getTableView().getItems().get(t.getTablePosition().getRow());
            Lab newlab = new Lab(lab);
            newlab.setRoom(t.getNewValue());
            EditMeetingTime_Transaction trans = new EditMeetingTime_Transaction(lab, newlab);
            app.processTransaction(trans);
        }
    }
);
        lTA1Column.setCellFactory(TextFieldTableCell.forTableColumn());
        lTA1Column.setOnEditCommit(
        new EventHandler<CellEditEvent<Lab, String>>() {
        @Override
        public void handle(CellEditEvent<Lab, String> t) {
            Lab lab = (Lab) t.getTableView().getItems().get(t.getTablePosition().getRow());
            Lab newlab = new Lab(lab);
            newlab.setTa1(t.getNewValue());
            EditMeetingTime_Transaction trans = new EditMeetingTime_Transaction(lab, newlab);
            app.processTransaction(trans);
        }
    }
);
        
        lTA2Column.setCellFactory(TextFieldTableCell.forTableColumn());
        lTA2Column.setOnEditCommit(
        new EventHandler<CellEditEvent<Lab, String>>() {
        @Override
        public void handle(CellEditEvent<Lab, String> t) {
            Lab lab = (Lab) t.getTableView().getItems().get(t.getTablePosition().getRow());
            Lab newlab = new Lab(lab);
            newlab.setTa2(t.getNewValue());
            EditMeetingTime_Transaction trans = new EditMeetingTime_Transaction(lab, newlab);
            app.processTransaction(trans);
        }
    }
);
    }
    public VBox getPane(){
        return mainPane;
    }
    
    public void initControllers(){
        
        AppGUIModule gui = app.getGUIModule();
        ((Button) gui.getGUINode(MT_LECTURE_ADD_BUTTON)).setOnAction(e -> {
            Lecture lecture = new Lecture("?", "?", "?", "?");
            SiteGeneratorData dataComponent = (SiteGeneratorData)app.getDataComponent();
            MeetingTimesData data = dataComponent.getMeetingTimesData();
            AddMeetingTime_Transaction trans = new AddMeetingTime_Transaction(data, lecture);
            app.processTransaction(trans);
        });
        ((Button) gui.getGUINode(MT_LECTURE_REMOVE_BUTTON)).setOnAction(e -> {
            TableView lectureTable = (TableView)gui.getGUINode(MT_LECTURE_TABLEVIEW);
            Lecture lecture = (Lecture) lectureTable.getItems().get(lectureTable.getFocusModel().getFocusedCell().getRow());
            SiteGeneratorData dataComponent = (SiteGeneratorData)app.getDataComponent();
            MeetingTimesData data = dataComponent.getMeetingTimesData();
            RemoveMeetingTime_Transaction trans = new RemoveMeetingTime_Transaction(data, lecture);
            app.processTransaction(trans);
        });
        ((Button) gui.getGUINode(MT_RECITATION_ADD_BUTTON)).setOnAction(e -> {
            Recitation recitation = new Recitation("?", "?", "?", "?", "?");
            SiteGeneratorData dataComponent = (SiteGeneratorData)app.getDataComponent();
            MeetingTimesData data = dataComponent.getMeetingTimesData();
            AddMeetingTime_Transaction trans = new AddMeetingTime_Transaction(data, recitation);
            app.processTransaction(trans);
        });
        ((Button) gui.getGUINode(MT_RECITATION_REMOVE_BUTTON)).setOnAction(e -> {
            TableView recitationTable = (TableView)gui.getGUINode(MT_RECITATION_TABLEVIEW);
            Recitation recitation = (Recitation) recitationTable.getItems().get(recitationTable.getFocusModel().getFocusedCell().getRow());
            SiteGeneratorData dataComponent = (SiteGeneratorData)app.getDataComponent();
            MeetingTimesData data = dataComponent.getMeetingTimesData();
            RemoveMeetingTime_Transaction trans = new RemoveMeetingTime_Transaction(data, recitation);
            app.processTransaction(trans);
        });
        ((Button) gui.getGUINode(MT_LABS_ADD_BUTTON)).setOnAction(e -> {
            Lab lab = new Lab("?", "?", "?", "?", "?");
            SiteGeneratorData dataComponent = (SiteGeneratorData)app.getDataComponent();
            MeetingTimesData data = dataComponent.getMeetingTimesData();
            AddMeetingTime_Transaction trans = new AddMeetingTime_Transaction(data, lab);
            app.processTransaction(trans);
        });
        ((Button) gui.getGUINode(MT_LABS_REMOVE_BUTTON)).setOnAction(e -> {
            TableView labTable = (TableView)gui.getGUINode(MT_LABS_TABLEVIEW);
            Lab lab = (Lab) labTable.getItems().get(labTable.getFocusModel().getFocusedCell().getRow());
            SiteGeneratorData dataComponent = (SiteGeneratorData)app.getDataComponent();
            MeetingTimesData data = dataComponent.getMeetingTimesData();
            RemoveMeetingTime_Transaction trans = new RemoveMeetingTime_Transaction(data, lab);
            app.processTransaction(trans);
        });
    }
}
