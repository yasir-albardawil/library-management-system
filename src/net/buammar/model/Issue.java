package net.buammar.model;

import javafx.beans.property.*;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.concurrent.TimeUnit;

public class Issue {
    private SimpleStringProperty bookID;
    private SimpleStringProperty memberID;
    private SimpleStringProperty issueTime;
    private SimpleIntegerProperty renewCount;
    private SimpleLongProperty numberDaysHolder;

    public Issue() {
        this.bookID = new SimpleStringProperty(null);
        this.memberID = new SimpleStringProperty(null);
        this.issueTime = new SimpleStringProperty(null);
        this.renewCount = new SimpleIntegerProperty(0);
        this.renewCount = new SimpleIntegerProperty(0);
    }

    public Issue(String bookID, String memberID) {
        this.bookID = new SimpleStringProperty(bookID);
        this.memberID = new SimpleStringProperty(memberID);
    }



    public Issue(String bookID, String memberID, Integer renewCount) {
        this.bookID = new SimpleStringProperty(bookID);
        this.memberID = new SimpleStringProperty(memberID);
        this.renewCount = new SimpleIntegerProperty(renewCount);
    }

    public Issue(String bookID, String memberID, Integer renewCount, long numberDaysHolder) {
        this.bookID = new SimpleStringProperty(bookID);
        this.memberID = new SimpleStringProperty(memberID);
        this.renewCount = new SimpleIntegerProperty(renewCount);
        this.numberDaysHolder = new SimpleLongProperty(numberDaysHolder);
    }

    public String getBookID() {
        return bookID.get();
    }

    public StringProperty bookIDProperty() {
        return bookID;
    }

    public void setBookID(String bookID) {
        this.bookID.set(bookID);
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

    public String getIssueTime() {
        return issueTime.get();
    }


    public StringProperty issueTimeProperty() {
        return issueTime;
    }

    public void setIssueTime(String issueTime) {
        this.issueTime.set(issueTime);
    }

    public int getRenewCount() {
        return renewCount.get();
    }

    public IntegerProperty renewCountProperty() {
        return renewCount;
    }

    public void setRenewCount(int renewCount) {
        this.renewCount.set(renewCount);
    }

    public long getNumberDaysHolder() {
        return numberDaysHolder.get();
    }

    public LongProperty numberDaysHolderProperty() {
        return numberDaysHolder;
    }

    public void setNumberDaysHolder(long numberDaysHolder) {
        this.numberDaysHolder.set(numberDaysHolder);
    }
}
