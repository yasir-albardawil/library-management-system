package net.buammar.model;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Book {
    private SimpleStringProperty id;
    private SimpleStringProperty title;
    private SimpleStringProperty author;
    private SimpleStringProperty publisher;
    private SimpleStringProperty intcode;
    private SimpleBooleanProperty isAvailable;

    public Book() {
        this(null, null, null, null, null, true);
    }

    public Book( String id, String title, String author, String publisher, String intcode, Boolean isAvailable) {
        this.id = new SimpleStringProperty(id);
        this.title = new SimpleStringProperty(title);
        this.author = new SimpleStringProperty(author);
        this.publisher = new SimpleStringProperty(publisher);
        this.intcode = new SimpleStringProperty(intcode);
        this.isAvailable = new SimpleBooleanProperty(isAvailable);
    }

    public String getId() {
        return id.get();
    }

    public StringProperty idProperty() {
        return id;
    }

    public void setId(String id) {
        this.id.set(id);
    }

    public String getTitle() {
        return title.get();
    }

    public StringProperty titleProperty() {
        return title;
    }

    public void setTitle(String title) {
        this.title.set(title);
    }

    public String getAuthor() {
        return author.get();
    }

    public StringProperty authorProperty() {
        return author;
    }

    public void setAuthor(String author) {
        this.author.set(author);
    }

    public String getPublisher() {
        return publisher.get();
    }

    public StringProperty publisherProperty() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher.set(publisher);
    }

    public String getIntcode() {
        return intcode.get();
    }

    public StringProperty intcodeProperty() {
        return intcode;
    }

    public void setIntcode(String intcode) {
        this.intcode.set(intcode);
    }

    public Boolean getIsAvailable() {
        return isAvailable.get();
    }

    public BooleanProperty isAvailableProperty() {
        return isAvailable;
    }

    public void setIsAvailable(Boolean isAvailable) {
        this.isAvailable.set(isAvailable);
    }
}
