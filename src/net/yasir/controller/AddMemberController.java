/**
 * Sample Skeleton for 'addMemberView.fxml' Controller Class
 */

package net.yasir.controller;

import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import net.yasir.alert.AlertMaker;
import net.yasir.database.DBHandler;
import net.yasir.model.Member;

public class AddMemberController {

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="rootPane"
    private AnchorPane rootPane; // Value injected by FXMLLoader

    @FXML // fx:id="nameTextField"
    private JFXTextField nameTextField; // Value injected by FXMLLoader

    @FXML // fx:id="memberTextFiled"
    private JFXTextField memberIDTextFiled; // Value injected by FXMLLoader

    @FXML // fx:id="mobileTextField"
    private JFXTextField mobileTextField; // Value injected by FXMLLoader

    @FXML // fx:id="emailTextField"
    private JFXTextField emailTextField; // Value injected by FXMLLoader

    private Stage stage = null;
    private String name, id, mobile, email;
    private Boolean isEditMode = Boolean.FALSE;

    @FXML
    void handleCancel(ActionEvent event) {
        stage = (Stage) rootPane.getScene().getWindow();
        stage.close();
    }

    @FXML
    void handleSave(ActionEvent event) {
        name = nameTextField.getText();
        id = memberIDTextFiled.getText();
        mobile = mobileTextField.getText();
        email = emailTextField.getText();

        if (!isValid()) {
            if (!isEditMode) {
                DBHandler.saveMember(new Member(id, name, mobile, email));
                AlertMaker.showSimpleAlert("Add success", "Success");
            } else {
                DBHandler.editMember(new Member(id, name, mobile, email));
                AlertMaker.showSimpleAlert("Update success", "Success");
            }

        }
    }

    public void setMember(Member member) {
        nameTextField.setText(member.getName());
        memberIDTextFiled.setText(member.getMemberID());
        mobileTextField.setText(member.getMobile());
        emailTextField.setText(member.getEmail());

        memberIDTextFiled.setEditable(false);
        isEditMode = Boolean.TRUE;
    }

    private boolean isValid() {
        String errorMessage = "";

        if (name.isEmpty() || name.length() == 0) {
            errorMessage += "Invalid name\n";
        }

        if (id.isEmpty() || id.length() == 0) {
            errorMessage += "Invalid ID\n";
        }

        if (mobile.isEmpty() || mobile.length() == 0) {
            errorMessage += "Invalid mobile\n";
        }

        if (email.isEmpty() || email.length() == 0) {
            errorMessage += "Invalid email address\n";
        }

        if (errorMessage.length() > 0) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Invalid...");
            alert.setContentText(errorMessage);
            alert.showAndWait();
            return true;
        }
        return false;
    }

}
