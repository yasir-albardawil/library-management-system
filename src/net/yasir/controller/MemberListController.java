/**
 * Sample Skeleton for 'bookListView.fxml' Controller Class
 */

package net.yasir.controller;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import net.yasir.alert.AlertMaker;
import net.yasir.database.DBHandler;
import net.yasir.model.Member;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class MemberListController implements Initializable {

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="rootPane"
    private AnchorPane rootPane; // Value injected by FXMLLoader

    @FXML // fx:id="tableView"
    private TableView<Member> tableView; // Value injected by FXMLLoader

    @FXML // fx:id="nameColumn"
    private TableColumn<Member, String> nameColumn; // Value injected by FXMLLoader

    @FXML // fx:id="memberIdColumn"
    private TableColumn<Member, String>  memberIdColumn; // Value injected by FXMLLoader

    @FXML // fx:id="phoneColumn"
    private TableColumn<Member, String>  phoneColumn; // Value injected by FXMLLoader

    @FXML // fx:id="emailColumn"
    private TableColumn<Member, String>  emailColumn; // Value injected by FXMLLoader

    private ObservableList<Member> observableList;

    @FXML
    void handleMenuItemEdit(ActionEvent event) {
        Member selectedForEdit = tableView.getSelectionModel().getSelectedItem();
        if (selectedForEdit == null) {
            AlertMaker.showErrorMessage("No book selected", "Please select a book for deletion.");
            return;
        }

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../view/addMemberView.fxml"));
            Parent root = loader.load();

            AddMemberController controller = loader.getController();
            controller.setMember(selectedForEdit);

            Stage stage = new Stage(StageStyle.DECORATED);
            stage.setScene(new Scene(root));
            stage.setTitle("Edit member");
            stage.getIcons().add(new Image(getClass().getResourceAsStream("../images/books-stack-of-three.png")));
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void handleMenuItemDelete(ActionEvent event) {
        Member selectedForDeletion = tableView.getSelectionModel().getSelectedItem();
        if (selectedForDeletion == null) {
            AlertMaker.showErrorMessage("No book selected", "Please select a book for deletion.");
            return;
        }

        if (DBHandler.isMemberHasAnyBooks(selectedForDeletion)) {
            AlertMaker.showErrorMessage("Cant be deleted", "This book is already issued and cant be deleted.");
            return;
        }

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Deleting member");
        alert.setContentText("Are you sure want to delete the book " + selectedForDeletion.getName() + " ?");
        Optional<ButtonType> answer = alert.showAndWait();
        if (answer.get() == ButtonType.OK) {
            Boolean result = DBHandler.deleteColumn(selectedForDeletion);
            if (result) {
                AlertMaker.showSimpleAlert("Book deleted", selectedForDeletion.getName() + " was deleted successfully.");
                tableView.getItems().remove(selectedForDeletion);
            } else {
                AlertMaker.showSimpleAlert("Failed", selectedForDeletion.getName() + " could not be deleted");
            }
        } else {
            AlertMaker.showSimpleAlert("Deletion cancelled", "Deletion process cancelled");
        }
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
        initColumn();
    }

    private void initColumn() {
        new Thread(()-> {
            DBHandler.getInstance(); }).start();

        observableList = DBHandler.getMembers();
        tableView.setItems(observableList);

        nameColumn.setCellValueFactory(cellDate -> cellDate.getValue().nameProperty());
        memberIdColumn.setCellValueFactory(cellDate -> cellDate.getValue().memberIDProperty());
        phoneColumn.setCellValueFactory(cellDate -> cellDate.getValue().mobileProperty());
        emailColumn.setCellValueFactory(cellDate -> cellDate.getValue().emailProperty());
    }


}