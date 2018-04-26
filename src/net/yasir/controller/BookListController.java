/**
 * Sample Skeleton for 'bookListView.fxml' Controller Class
 */

package net.yasir.controller;

import java.io.IOException;
import java.net.URL;
import java.util.*;

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
import net.yasir.model.Book;

public class BookListController implements Initializable {

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;
    @FXML // fx:id="tableView"

    private TableView<Book> tableView; // Value injected by FXMLLoader
    @FXML // fx:id="rootPane"
    private AnchorPane rootPane; // Value injected by FXMLLoader

    @FXML // fx:id="titleColumn"
    private TableColumn<Book, String> titleColumn; // Value injected by FXMLLoader

    @FXML // fx:id="bookIdColumn"
    private TableColumn<Book, String> bookIdColumn; // Value injected by FXMLLoader

    @FXML // fx:id="authorColumn"
    private TableColumn<Book, String> authorColumn; // Value injected by FXMLLoader

    @FXML // fx:id="publisherColumn"
    private TableColumn<Book, String> publisherColumn; // Value injected by FXMLLoader

    @FXML // fx:id="availabilityColumn"
    private TableColumn<Book, Boolean> availabilityColumn; // Value injected by FXMLLoader

    @FXML
    void handleMenuItemEdit(ActionEvent event) {
        Book selectedForEdit = tableView.getSelectionModel().getSelectedItem();
        if (selectedForEdit == null) {
            AlertMaker.showErrorMessage("No book selected", "Please select a book for deletion.");
            return;
        }

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../view/addBookView.fxml"));
            Parent root = loader.load();

            AddBookController controller = loader.getController();
            controller.setBook(selectedForEdit);

            Stage stage = new Stage(StageStyle.DECORATED);
            stage.setScene(new Scene(root));
            stage.setTitle("Edit book");
            stage.getIcons().add(new Image(getClass().getResourceAsStream("../images/books-stack-of-three.png")));
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void handleMenuItemDelete(ActionEvent event) {
        Book selectedForDeletion = tableView.getSelectionModel().getSelectedItem();
        if (selectedForDeletion == null) {
            AlertMaker.showErrorMessage("No book selected", "Please select a book for deletion.");
            return;
        }

        if (DBHandler.isBookAlreadyIssued(selectedForDeletion)) {
            AlertMaker.showErrorMessage("Cant be deleted", "This book is already issued and cant be deleted.");
            return;
        }

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Deleting book");
        alert.setContentText("Are you sure want to delete the book " + selectedForDeletion.getTitle() + " ?");
        Optional<ButtonType> answer = alert.showAndWait();
        if (answer.get() == ButtonType.OK) {
            Boolean result = DBHandler.deleteColumn(selectedForDeletion);
            if (result) {
                AlertMaker.showSimpleAlert("Book deleted", selectedForDeletion.getTitle() + " was deleted successfully.");
                tableView.getItems().remove(selectedForDeletion);
            } else {
                AlertMaker.showSimpleAlert("Failed", selectedForDeletion.getTitle() + " could not be deleted");
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
        ObservableList<Book> books = DBHandler.getBooks();
        tableView.setItems(books);

        titleColumn.setCellValueFactory(cellDate -> cellDate.getValue().titleProperty());
        bookIdColumn.setCellValueFactory(cellDate -> cellDate.getValue().idProperty());
        authorColumn.setCellValueFactory(cellDate -> cellDate.getValue().authorProperty());
        publisherColumn.setCellValueFactory(cellDate -> cellDate.getValue().publisherProperty());
    }


}