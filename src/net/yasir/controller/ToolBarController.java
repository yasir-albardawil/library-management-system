package net.yasir.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import net.yasir.model.Preferences;

import java.io.IOException;

public class ToolBarController {

    @FXML
    private void handleAddBook(ActionEvent event) {
        loadWindow("../view/addBookView.fxml", "Add new book", "../images/books-stack-of-three.png");
    }

    @FXML
    private void handleAddMember(ActionEvent event) {
        loadWindow("../view/addMemberView.fxml", "Add new member", "../images/add-user-symbol-of-interface.png");
    }

    @FXML
    private void handleBookTable(ActionEvent event) {
        loadWindow("../view/BookListView.fxml", "Book list", "../images/view-list.png");
    }

    @FXML
    private void handleMemberTable(ActionEvent event) {
        Preferences.initConfig();

        loadWindow("../view/memberListView.fxml", "Member list", "../images/view-list-button.png");
    }

    @FXML
    void handleSettings(ActionEvent event) {
        loadWindow("../view/settingsView.fxml", "Settings", "../images/view-list-button.png");
    }

    void loadWindow(String location, String title, String icon) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource(location));
            Stage stage = new Stage(StageStyle.DECORATED);
            stage.setScene(new Scene(root));
            stage.setTitle(title);
            stage.getIcons().add(new Image(getClass().getResourceAsStream(icon)));
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
