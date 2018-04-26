package net.yasir.controller; /**
 * Sample Skeleton for 'addBookView.fxml' Controller Class
 */

import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import net.yasir.alert.AlertMaker;
import net.yasir.database.DBHandler;
import net.yasir.model.Book;

public class AddBookController implements Initializable {

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="rootPane"
    private AnchorPane rootPane; // Value injected by FXMLLoader

    @FXML // fx:id="titleTextField"
    private JFXTextField titleTextField; // Value injected by FXMLLoader

    @FXML // fx:id="idTextFiled"
    private JFXTextField idTextFiled; // Value injected by FXMLLoader

    @FXML // fx:id="authorTextField"
    private JFXTextField authorTextField; // Value injected by FXMLLoader

    @FXML // fx:id="publisherTextField"
    private JFXTextField publisherTextField; // Value injected by FXMLLoader

    private Stage stage = null;
    private String idBook, titleBook, authorBook, publisher;

    public void setStage(Stage stage) {
        this.stage = stage;
    }
    private Boolean isEditMode = Boolean.FALSE;
    TableView<Book> tableView;




    @FXML
    void handleCancel(ActionEvent event) {
        Stage stage = (Stage) rootPane.getScene().getWindow();
        stage.close();
    }

    @FXML
    void handleSave(ActionEvent event) {
        idBook = idTextFiled.getText();
        titleBook = titleTextField.getText();
        authorBook = authorTextField.getText();
        publisher = publisherTextField.getText();
        if (!isValid()) {
            if (!isEditMode) {
                DBHandler.saveBook(new Book(idBook, titleBook, authorBook, publisher, "", true));
                AlertMaker.showSimpleAlert("Add success", "Success");
            } else {
                DBHandler.editBook(new Book(idBook, titleBook, authorBook, publisher, "", true));
                AlertMaker.showSimpleAlert("Update success", "Success");
            }

        }
    }

    private boolean isValid() {
        String errorMessage = "";

        if (idBook.isEmpty() || idBook.length() == 0) {
            errorMessage += "Invalid ID\n";
        }

        if (titleBook.isEmpty() || titleBook.length() == 0) {
            errorMessage += "Invalid title\n";
        }

        if (authorBook.isEmpty() || authorBook.length() == 0) {
            errorMessage += "Invalid author\n";
        }

        if (publisher.isEmpty() || publisher.length() == 0) {
            errorMessage += "Invalid publisher\n";
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

    public void setBook(Book book) {
        titleTextField.setText(book.getTitle());
        idTextFiled.setText(book.getId());
        authorTextField.setText(book.getAuthor());
        publisherTextField.setText(book.getPublisher());

        idTextFiled.setEditable(false);
        isEditMode = Boolean.TRUE;
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

    }
}
