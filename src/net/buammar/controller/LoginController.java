/**
 * Sample Skeleton for 'loginView.fxml' Controller Class
 */

package net.buammar.controller;

import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import net.buammar.model.Preferences;
import org.apache.commons.codec.digest.DigestUtils;

public class LoginController implements Initializable {

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;


    @FXML // fx:id="rootPane"
    private AnchorPane rootPane; // Value injected by FXMLLoader

    @FXML // fx:id="usernameTextField"
    private JFXTextField usernameTextField; // Value injected by FXMLLoader

    @FXML // fx:id="passwordPasswodField"
    private JFXPasswordField passwordPasswodField; // Value injected by FXMLLoader

    private Preferences perseverance = null;
    @FXML
    void handleCancel(ActionEvent event) {
        System.exit(0);
    }

    @FXML
    void handleLogin(ActionEvent event) {
        String username = usernameTextField.getText();
        String password = DigestUtils.shaHex(passwordPasswodField.getText());

        if (username.equals(perseverance.getUsername()) && password.equals(perseverance.getPassword())) {
            closeStage();
            loadOverview();
        } else {
            usernameTextField.getStyleClass().add("wrong-carnelians");
            passwordPasswodField.getStyleClass().add("wrong-carnelians");
        }

    }

    private void closeStage() {
        Stage stage = (Stage) rootPane.getScene().getWindow();
        stage.close();
    }

    void loadOverview() {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("../view/overview.fxml"));
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Library Management System");
            stage.getIcons().add(new Image(getClass().getResourceAsStream("../images/book.png")));
            //stage.getIcons().add(new Image(getClass().getResourceAsStream(icon)));
            stage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert usernameTextField != null : "fx:id=\"usernameTextField\" was not injected: check your FXML file 'loginView.fxml'.";
        assert passwordPasswodField != null : "fx:id=\"passwordPasswodField\" was not injected: check your FXML file 'loginView.fxml'.";

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
        perseverance = Preferences.getPreference();

        rootPane.requestFocus();
    }
}
