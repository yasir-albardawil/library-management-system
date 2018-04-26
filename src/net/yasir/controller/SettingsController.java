/**
 * Sample Skeleton for 'settingsView.fxml' Controller Class
 */

package net.yasir.controller;

import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import net.yasir.model.Preferences;

public class SettingsController implements Initializable {

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="rootPane"
    private AnchorPane rootPane; // Value injected by FXMLLoader

    @FXML // fx:id="noDaysTextField"
    private JFXTextField noDaysTextField; // Value injected by FXMLLoader

    @FXML // fx:id="finePerDatTextField"
    private JFXTextField finePerDatTextField; // Value injected by FXMLLoader

    @FXML // fx:id="usernameTextField"
    private JFXTextField usernameTextField; // Value injected by FXMLLoader

    @FXML // fx:id="passwordPasswordField"
    private JFXPasswordField passwordPasswordField; // Value injected by FXMLLoader

    @FXML
    void handleCancel(ActionEvent event) {
        Stage stage = (Stage) rootPane.getScene().getWindow();
        stage.close();
    }

    @FXML
    void handleSave(ActionEvent event) {
        int noDays = Integer.parseInt(noDaysTextField.getText());
        float fineDays = Float.parseFloat(finePerDatTextField.getText());
        String username = usernameTextField.getText();
        String password = passwordPasswordField.getText();

        Preferences perseverance = Preferences.getPreference();
        perseverance.setNoDaysWithoutFine(noDays);
        perseverance.setFinePerDay(fineDays);
        perseverance.setUsername(username);
        perseverance.setPassword(password);

        Preferences.setPerseverance(perseverance);
    }

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert noDaysTextField != null : "fx:id=\"noDaysTextField\" was not injected: check your FXML file 'settingsView.fxml'.";
        assert finePerDatTextField != null : "fx:id=\"finePerDatTextField\" was not injected: check your FXML file 'settingsView.fxml'.";
        assert usernameTextField != null : "fx:id=\"usernameTextField\" was not injected: check your FXML file 'settingsView.fxml'.";
        assert passwordPasswordField != null : "fx:id=\"passwordPasswordField\" was not injected: check your FXML file 'settingsView.fxml'.";

    }

    /**
     * Called to initialize a controller after its root element has been
     * completely processed.
     *
     * @param location  The location used to resolve relative paths for the root object, or
     *                  {@code null} if the location is not known.
     * @param resources The resources used to localize the root object, or {@code null} if
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initDefaultValues();
    }

    private void initDefaultValues() {
        Preferences perseverance = Preferences.getPreference();
        noDaysTextField.setText(String.valueOf(perseverance.getNoDaysWithoutFine()));
        finePerDatTextField.setText(String.valueOf(perseverance.getFinePerDay()));
        usernameTextField.setText(String.valueOf(perseverance.getUsername()));
        passwordPasswordField.setText(String.valueOf(perseverance.getPassword()));
    }
}
