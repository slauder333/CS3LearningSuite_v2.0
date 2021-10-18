/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.workspace.dialogs;


import djf.modules.AppGUIModule;
import java.util.Optional;
import javafx.geometry.Insets;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.GridPane;
import javafx.util.Pair;
import sg.Data.TeachingAssistantPrototype;
import sg.SiteGeneratorApp;
import static sg.SiteGeneratorPropertyType.OH_TAS_TABLE_VIEW;
import sg.transactions.AddEdit_TA_Transaction;


/**
 *
 * @author Brandon
 */
public class OfficeHoursDialogs {
    public void editTADialog(SiteGeneratorApp app){
        AppGUIModule gui = app.getGUIModule();
         TableView tasTable = (TableView)gui.getGUINode(OH_TAS_TABLE_VIEW);
        TeachingAssistantPrototype ta = (TeachingAssistantPrototype) tasTable.getSelectionModel().getSelectedItem();
        
        Dialog<Pair<String, String>> dialog = new Dialog<>();
        dialog.setTitle("Edit Teaching Assistant");
        dialog.setHeaderText("Edit Teaching Assistant");
        ButtonType okButtonType = new ButtonType("Ok", ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(okButtonType, ButtonType.CANCEL);
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));
        TextField name = new TextField();
        name.setText(ta.getName());
        TextField email = new TextField();
        email.setText(ta.getEmail());
        grid.add(new Label("Name:"), 0, 0);
        grid.add(name, 1, 0);
        grid.add(new Label("Email:"), 0, 1);
        grid.add(email, 1, 1);
        final ToggleGroup group = new ToggleGroup();

        RadioButton undergraduateRB = new RadioButton("UNDERGRADUATE");
        undergraduateRB.setUserData("UNDERGRADUATE");
        undergraduateRB.setToggleGroup(group);
        undergraduateRB.setSelected(ta.getTaType().equals("UNDERGRADUATE"));

        RadioButton graduateRB = new RadioButton("GRADUATE");
        graduateRB.setUserData("GRADUATE");
        graduateRB.setToggleGroup(group);
        graduateRB.setSelected(ta.getTaType().equals("GRADUATE"));
 
        grid.add(new Label("TA Type: "), 0, 2);
        grid.add(undergraduateRB, 1, 2);
        grid.add(graduateRB, 2, 2);
        
        dialog.getDialogPane().setContent(grid);
        dialog.setResultConverter(dialogButton -> {
        if (dialogButton == okButtonType) {
            TeachingAssistantPrototype oldTa = new TeachingAssistantPrototype(ta.getName(), ta.getEmail(), ta.getTaType());
//            ta.setName(name.getText());
//            ta.setEmail(email.getText());
//            ta.setTaType((String) group.getSelectedToggle().getUserData());
              AddEdit_TA_Transaction editTATransaction = new AddEdit_TA_Transaction(oldTa, ta, name.getText(), email.getText(), (String) group.getSelectedToggle().getUserData());
              app.processTransaction(editTATransaction);
              
        }
        return null;
        });
        dialog.showAndWait();
    }
    
}
