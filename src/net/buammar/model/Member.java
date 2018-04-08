package net.buammar.model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Member {
    private SimpleStringProperty name;
    private SimpleStringProperty memberID;
    private SimpleStringProperty mobile;
    private SimpleStringProperty Email;

    public Member() {
        this(null, null, null, null);
    }

    public Member(String memberID, String name, String mobile, String email) {
        this.name = new SimpleStringProperty(name);
        this.memberID = new SimpleStringProperty(memberID);
        this.mobile = new SimpleStringProperty(mobile);
        Email = new SimpleStringProperty(email);
    }

    public String getName() {
        return name.get();
    }

    public StringProperty nameProperty() {
        return name;
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public String getMemberID() {
        return memberID.get();
    }

    public StringProperty memberIDProperty() {
        return memberID;
    }

    public void setMemberID(String memberID) {
        this.memberID.set(memberID);
    }

    public String getMobile() {
        return mobile.get();
    }

    public StringProperty mobileProperty() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile.set(mobile);
    }

    public String getEmail() {
        return Email.get();
    }

    public StringProperty emailProperty() {
        return Email;
    }

    public void setEmail(String email) {
        this.Email.set(email);
    }
}
