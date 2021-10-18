/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.workspace;

import static djf.AppPropertyType.*;
import static djf.AppTemplate.PATH_WORK;
import djf.modules.AppGUIModule;
import static djf.modules.AppGUIModule.ENABLED;
import djf.ui.AppNodesBuilder;
import djf.ui.dialogs.AppDialogsFacade;

import java.io.File;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import properties_manager.PropertiesManager;
import sg.Data.ScheduleData;
import sg.Data.SiteData;
import sg.Data.SiteGeneratorData;
import sg.SiteGeneratorApp;
import static sg.SiteGeneratorPropertyType.*;
import static sg.workspace.style.SGStyle.*;

/**
 *
 * @author Brandon
 */
public class SiteWorkspaceComponent {
    SiteGeneratorApp app;
    VBox mainPane;
    public SiteWorkspaceComponent(SiteGeneratorApp initApp) {
        
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
        AppNodesBuilder siBuilder = app.getGUIModule().getNodesBuilder();
        mainPane = siBuilder.buildVBox(SI_MAIN_PANE, null, CLASS_OH_BACKGROUND_PANE, ENABLED);
        
        GridPane bannerPane = siBuilder.buildGridPane(SI_BANNER_PANE, mainPane, CLASS_SI_PANE, ENABLED);
        siBuilder.buildLabel(SI_BANNER_LABEL, bannerPane, 0, 0, 1, 1, CLASS_OH_HEADER_LABEL, ENABLED);
        siBuilder.buildLabel(SI_SUBJECT_LABEL, bannerPane, 0, 1, 1, 1, CLASS_OH_PROMPT, ENABLED);
        siBuilder.buildComboBox(SI_SUBJECT_BOX, bannerPane, 1, 1, 1, 1, EMPTY_TEXT, ENABLED, SUBJECT_OPTIONS, SI_DEFAULT_SUBJECT);
        siBuilder.buildLabel(SI_NUMBER_LABEL, bannerPane, 2, 1, 1, 1, CLASS_OH_PROMPT, ENABLED);
        siBuilder.buildComboBox(SI_NUMBER_BOX, bannerPane, 3, 1, 1, 1, EMPTY_TEXT, ENABLED, NUMBER_OPTIONS, SI_DEFAULT_NUMBER);
        siBuilder.buildLabel(SI_SEMESTER_LABEL, bannerPane, 0, 2, 1, 1, CLASS_OH_PROMPT, ENABLED);
        siBuilder.buildComboBox(SI_SEMESTER_BOX, bannerPane, 1, 2, 1, 1, EMPTY_TEXT, ENABLED, SEMESTER_OPTIONS, SI_DEFAULT_SEMESTER);
        siBuilder.buildLabel(SI_YEAR_LABEL, bannerPane, 2, 2, 1, 1, CLASS_OH_PROMPT, ENABLED);
        siBuilder.buildComboBox(SI_YEAR_BOX, bannerPane, 3, 2, 1, 1, EMPTY_TEXT, ENABLED, YEAR_OPTIONS, SI_DEFAULT_YEAR);
        siBuilder.buildLabel(SI_TITLE_LABEL, bannerPane, 0, 3, 1, 1, CLASS_OH_PROMPT, ENABLED);
        siBuilder.buildTextField(SI_TITLE_BOX, bannerPane, 1, 3, 1, 1, EMPTY_TEXT, ENABLED);
        siBuilder.buildLabel(SI_EXPORT_DIR_LABEL, bannerPane, 0, 4, 1, 1, CLASS_OH_PROMPT, ENABLED);
        siBuilder.buildLabel(SI_EXPORT_DIR, bannerPane, 1, 4, 1, 1, EMPTY_TEXT, ENABLED);
        updateExportDir();
        
        GridPane pagesPane = siBuilder.buildGridPane(SI_PAGES_PANE, mainPane, CLASS_SI_PANE, ENABLED);
        siBuilder.buildLabel(SI_PAGES_LABEL, pagesPane, 0, 0, 1, 1, CLASS_OH_HEADER_LABEL, ENABLED);
        
        siBuilder.buildCheckBox(SI_HOME_CHECK, pagesPane, 1, 0, 1, 1, CLASS_SI_CHECK_BOX, ENABLED);
        ((CheckBox)gui.getGUINode(SI_HOME_CHECK)).selectedProperty().set(true);
        siBuilder.buildCheckBox(SI_SYLLABUS_CHECK, pagesPane, 2, 0, 1, 1, CLASS_SI_CHECK_BOX, ENABLED);
        ((CheckBox)gui.getGUINode(SI_SYLLABUS_CHECK)).selectedProperty().set(true);
        siBuilder.buildCheckBox(SI_SCHEDULE_CHECK, pagesPane, 3, 0, 1, 1, CLASS_SI_CHECK_BOX, ENABLED);
        ((CheckBox)gui.getGUINode(SI_SCHEDULE_CHECK)).selectedProperty().set(true);
        siBuilder.buildCheckBox(SI_HWS_CHECK, pagesPane, 4, 0, 1, 1, CLASS_SI_CHECK_BOX, ENABLED);
        ((CheckBox)gui.getGUINode(SI_HWS_CHECK)).selectedProperty().set(true);
        
        GridPane stylePane = siBuilder.buildGridPane(SI_STYLE_PANE, mainPane, CLASS_SI_PANE, ENABLED);
        stylePane.setHgap(10);
        stylePane.setVgap(20);
        siBuilder.buildLabel(SI_STYLE_LABEL, stylePane, 0, 0, 1, 1, CLASS_OH_HEADER_LABEL, ENABLED);
        siBuilder.buildTextButton(SI_FAVICON_BUTTON, stylePane, 0, 1, 1, 1, CLASS_SI_BUTTON, ENABLED);
        siBuilder.buildImageView(SI_FAVICON_IMAGEVIEW, stylePane, 1, 1, 1, 1, CLASS_SI_BUTTON, ENABLED, null);
        siBuilder.buildTextButton(SI_NAVBAR_BUTTON, stylePane, 0, 2, 1, 1, CLASS_SI_BUTTON, ENABLED);
        siBuilder.buildImageView(SI_NAVBAR_IMAGEVIEW, stylePane, 1, 2, 1, 1, CLASS_SI_BUTTON, ENABLED, null);
        siBuilder.buildTextButton(SI_LEFT_FOOTER_BUTTON, stylePane, 0, 3, 1, 1, CLASS_SI_BUTTON, ENABLED);
        siBuilder.buildImageView(SI_LEFT_FOOTER_IMAGEVIEW, stylePane, 1, 3, 1, 1, CLASS_SI_BUTTON, ENABLED, null);
        siBuilder.buildTextButton(SI_RIGHT_FOOTER_BUTTON, stylePane, 0, 4, 1, 1, CLASS_SI_BUTTON, ENABLED);
        siBuilder.buildImageView(SI_RIGHT_FOOTER_IMAGEVIEW, stylePane, 1, 4, 1, 1, CLASS_SI_BUTTON, ENABLED, null);
        siBuilder.buildLabel(SI_STYLE_SHEET_LABEL, stylePane, 0, 5, 1, 1, CLASS_OH_PROMPT, ENABLED);
        siBuilder.buildComboBox(SI_STYLE_SHEETS_BOX, stylePane, 1, 5, 1, 1, EMPTY_TEXT, ENABLED, STYLE_SHEET_OPTIONS, SI_DEFAULT_STYLE_SHEET);
        siBuilder.buildLabel(SI_STYLE_NOTE, stylePane, 0, 6, 1, 1, CLASS_OH_PROMPT, ENABLED);
        
        VBox instructorPane = siBuilder.buildVBox(SI_INSTRUCTOR_PANE, mainPane, CLASS_SI_PANE, ENABLED);
        GridPane topInstructorPane = siBuilder.buildGridPane(SI_TOP_INSTRUCTOR_PANE, instructorPane, EMPTY_TEXT, ENABLED);
        siBuilder.buildLabel(SI_INSTRUCTOR_LABEL, topInstructorPane, 0, 0, 1, 1, CLASS_OH_HEADER_LABEL, ENABLED);
        siBuilder.buildLabel(SI_NAME_LABEL, topInstructorPane, 0, 1, 1, 1, CLASS_OH_PROMPT, ENABLED);
        siBuilder.buildTextField(SI_NAME_BOX, topInstructorPane, 1, 1, 1, 1, EMPTY_TEXT, ENABLED);
        siBuilder.buildLabel(SI_ROOM_LABEL, topInstructorPane, 2, 1, 1, 1, CLASS_OH_PROMPT, ENABLED);
        siBuilder.buildTextField(SI_ROOM_BOX, topInstructorPane, 3, 1, 1, 1, EMPTY_TEXT, ENABLED);
        siBuilder.buildLabel(SI_EMAIL_LABEL, topInstructorPane, 0, 2, 1, 1, CLASS_OH_PROMPT, ENABLED);
        siBuilder.buildTextField(SI_EMAIL_BOX, topInstructorPane, 1, 2, 1, 1, EMPTY_TEXT, ENABLED);
        siBuilder.buildLabel(SI_HOME_PAGE_LABEL, topInstructorPane, 2, 2, 1, 1, CLASS_OH_PROMPT, ENABLED);
        siBuilder.buildTextField(SI_HOME_PAGE_BOX, topInstructorPane, 3, 2, 1, 1, EMPTY_TEXT, ENABLED);
        TextArea bottomInstructorBox = siBuilder.buildTextArea(SI_BOTTOM_INSTRUCTOR_BOX, null, EMPTY_TEXT, ENABLED);
        siBuilder.buildTitledPane(SI_BOTTOM_INSTRUCTOR_PANE, instructorPane, EMPTY_TEXT, ENABLED, SI_BOTTOM_INSTRUCTOR_LABEL, bottomInstructorBox);
    }
    public VBox getPane(){
        return mainPane;
    }
    
    public void initControllers(){
        AppGUIModule gui = app.getGUIModule();
        ((ComboBox)gui.getGUINode(SI_SUBJECT_BOX)).getSelectionModel().selectedItemProperty().addListener(( ov,  t,  t1) -> {
            updateExportDir();
            SiteGeneratorData dataComponent = (SiteGeneratorData)app.getDataComponent();
            SiteData data = dataComponent.getSiteData();
            data.setSubject(((ComboBox)gui.getGUINode(SI_SUBJECT_BOX)).getValue().toString());
        });
        ((ComboBox)gui.getGUINode(SI_NUMBER_BOX)).getSelectionModel().selectedItemProperty().addListener((ObservableValue ov, Object t, Object t1) -> {
            updateExportDir();
            SiteGeneratorData dataComponent = (SiteGeneratorData)app.getDataComponent();
            SiteData data = dataComponent.getSiteData();
            data.setNumber(((ComboBox)gui.getGUINode(SI_NUMBER_BOX)).getValue().toString());
        });
        ((ComboBox)gui.getGUINode(SI_SEMESTER_BOX)).getSelectionModel().selectedItemProperty().addListener((ObservableValue ov, Object t, Object t1) -> {
            updateExportDir();
            SiteGeneratorData dataComponent = (SiteGeneratorData)app.getDataComponent();
            SiteData data = dataComponent.getSiteData();
            data.setSemester(((ComboBox)gui.getGUINode(SI_SEMESTER_BOX)).getValue().toString());
        });
        ((ComboBox)gui.getGUINode(SI_YEAR_BOX)).getSelectionModel().selectedItemProperty().addListener((ObservableValue ov, Object t, Object t1) -> {
            updateExportDir();
            SiteGeneratorData dataComponent = (SiteGeneratorData)app.getDataComponent();
            SiteData data = dataComponent.getSiteData();
            data.setYear(((ComboBox)gui.getGUINode(SI_YEAR_BOX)).getValue().toString());
        });
       ((Button) gui.getGUINode(SI_FAVICON_BUTTON)).setOnAction(e -> {
            File favicon = AppDialogsFacade.selectImageDialog(app.getGUIModule().getWindow());
            //GridPane pane = (GridPane) gui.getGUINode(SI_STYLE_PANE);
            //Image img = new Image("file:"+favicon.getPath());
            //pane.add(new ImageView(img), 1, 1, 1, 1);
            //((ImageView)gui.getGUINode(SI_FAVICON_IMAGEVIEW)).setImage(img);
            //PropertiesManager props = PropertiesManager.getPropertiesManager();
            //String path = favicon.getPath();
            //String base = props.getProperty(APP_EXPORT_WEB_FILES);
            //String relative = new File(base).toURI().relativize(new File(path).toURI()).getPath();
            //relative = "./"+relative;
            SiteGeneratorData dataComponent = (SiteGeneratorData)app.getDataComponent();
            SiteData data = dataComponent.getSiteData();
            data.setFavicon(favicon.getPath());
            
        });
       ((Button) gui.getGUINode(SI_NAVBAR_BUTTON)).setOnAction(e -> {
            File navbar = AppDialogsFacade.selectImageDialog(app.getGUIModule().getWindow());
            //GridPane pane = (GridPane) gui.getGUINode(SI_STYLE_PANE);
            //Image img = new Image("file:"+navbar.getPath());
            //pane.add(new ImageView(img), 1, 2, 1, 1);
            //((ImageView)gui.getGUINode(SI_NAVBAR_IMAGEVIEW)).setImage(img);
            //PropertiesManager props = PropertiesManager.getPropertiesManager();
            //String path = navbar.getPath();
            //String base = props.getProperty(APP_EXPORT_WEB_FILES);
            //String relative = new File(base).toURI().relativize(new File(path).toURI()).getPath();
            //relative = "./"+relative;
            SiteGeneratorData dataComponent = (SiteGeneratorData)app.getDataComponent();
            SiteData data = dataComponent.getSiteData();
            data.setNavbar(navbar.getPath());
        });
       ((Button) gui.getGUINode(SI_LEFT_FOOTER_BUTTON)).setOnAction(e -> {
            File leftFooter = AppDialogsFacade.selectImageDialog(app.getGUIModule().getWindow());
            //GridPane pane = (GridPane) gui.getGUINode(SI_STYLE_PANE);
            //Image img = new Image("file:"+leftFooter.getPath());
            //pane.add(new ImageView(img), 1, 3, 1, 1);
            //((ImageView)gui.getGUINode(SI_LEFT_FOOTER_IMAGEVIEW)).setImage(img);
            //PropertiesManager props = PropertiesManager.getPropertiesManager();
            //String path = leftFooter.getPath();
            //String base = props.getProperty(APP_EXPORT_WEB_FILES);
            //String relative = new File(base).toURI().relativize(new File(path).toURI()).getPath();
            //relative = "./"+relative;
            SiteGeneratorData dataComponent = (SiteGeneratorData)app.getDataComponent();
            SiteData data = dataComponent.getSiteData();
            data.setBottomLeft(leftFooter.getPath());
        });
       ((Button) gui.getGUINode(SI_RIGHT_FOOTER_BUTTON)).setOnAction(e -> {
            File rightFooter = AppDialogsFacade.selectImageDialog(app.getGUIModule().getWindow());
            //GridPane pane = (GridPane) gui.getGUINode(SI_STYLE_PANE);
            //Image img = new Image("file:"+rightFooter.getPath());
            //pane.add(new ImageView(img), 1, 4, 1, 1);
            //((ImageView)gui.getGUINode(SI_RIGHT_FOOTER_IMAGEVIEW)).setImage(img);
            //PropertiesManager props = PropertiesManager.getPropertiesManager();
//            String path = rightFooter.getPath();
//            String base = props.getProperty(APP_EXPORT_WEB_FILES);
//            String relative = new File(base).toURI().relativize(new File(path).toURI()).getPath();
//            relative = "./"+relative;
            SiteGeneratorData dataComponent = (SiteGeneratorData)app.getDataComponent();
            SiteData data = dataComponent.getSiteData();
            data.setBottomRight(rightFooter.getPath());
        });
        
    }
    
    public void updateExportDir(){
        String str = new String();
        AppGUIModule gui = app.getGUIModule();
        String subject = ((ComboBox)(gui.getGUINode(SI_SUBJECT_BOX))).getValue().toString();
        String number = ((ComboBox)(gui.getGUINode(SI_NUMBER_BOX))).getValue().toString();
        String semester = ((ComboBox)(gui.getGUINode(SI_SEMESTER_BOX))).getValue().toString();
        String year = ((ComboBox)(gui.getGUINode(SI_YEAR_BOX))).getValue().toString();
        str = ".\\export\\" + subject + "_" + number + "_" + semester + "_" + year + "\\";
        Label lbl = (Label)gui.getGUINode(SI_EXPORT_DIR);
        lbl.setText(str);
        PropertiesManager props = PropertiesManager.getPropertiesManager();
        props.addProperty(APP_PATH_EXPORT, str);
        props.addProperty(APP_EXPORT_PAGE, str+"index.html");
    }
    
    
}
