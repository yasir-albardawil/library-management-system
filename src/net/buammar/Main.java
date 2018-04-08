package net.buammar;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import net.buammar.database.DBHandler;
import net.buammar.model.Book;

import java.io.IOException;

public class Main extends Application {
    private Stage primaryStage = null;
    private Main main;
    private ObservableList<Book> bookObservableList;
    public Main() {
        bookObservableList = DBHandler.getBooks();
    }

    public ObservableList<Book> getBookObservableList() {
        return bookObservableList;
    }

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws IOException {
        primaryStage = stage;
        showLoginView();
        primaryStage.show();
    }

    private void showLoginView() throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("view/loginView.fxml"));
        primaryStage.setTitle("Library Management System");
        primaryStage.getIcons().add(new Image(getClass().getResourceAsStream("images/book.png")));
        primaryStage.setScene(new Scene(root));
    }

}
