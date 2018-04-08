package net.buammar.controller; /**
 * Sample Skeleton for 'overview.fxml' Controller Class
 */

import java.io.IOException;
import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.jfoenix.controls.*;
import com.jfoenix.effects.JFXDepthManager;
import com.jfoenix.transitions.hamburger.HamburgerSlideCloseTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import net.buammar.alert.AlertMaker;
import net.buammar.database.DBHandler;
import net.buammar.model.Book;
import net.buammar.model.Issue;
import net.buammar.model.Member;
import net.buammar.model.Preferences;

public class OverviewController implements Initializable {
    @FXML
    private StackPane rootPane;

    @FXML
    private BorderPane borderPane;

    @FXML
    private HBox bookInfo;

    @FXML
    private JFXTextField bookIDTextField;

    @FXML
    private StackPane bookInfoContainer;

    @FXML
    private Text bookName;

    @FXML
    private Text bookAuthor;

    @FXML
    private Text bookStatus;

    @FXML
    private PieChart bookChart;

    @FXML
    private PieChart memberChart;

    @FXML
    private HBox memberInfo;

    @FXML
    private JFXTextField memberIDTextField;

    @FXML
    private Text memberName;

    @FXML
    private Text memberContact;

    @FXML
    private JFXButton renewButton;

    @FXML
    private JFXButton submissionButton;

    @FXML
    private VBox memberVBox;

    @FXML
    private Text memberNameHeader;

    @FXML
    private Text memberEmailHeader;

    @FXML
    private Text memberContent;

    @FXML
    private VBox bookVBox;

    @FXML
    private Text bookNameHeader;

    @FXML
    private Text booKAuthorHeader;

    @FXML
    private Text bookPublisherContent;

    @FXML
    private VBox issueVBox;

    @FXML
    private Text issueDateHeader;

    @FXML
    private Text issueNoOfDaysHeader;

    @FXML
    private Text issueFineHeader;

    @FXML
    private JFXTextField bookIDTextField2;

    @FXML
    private JFXHamburger hamburger;

    @FXML
    private JFXDrawer drawer;


    @FXML
    void handleIssue(ActionEvent event) {
        String bookID = bookIDTextField.getText();
        String memberID = memberIDTextField.getText();
        JFXButton buttonOk = new JFXButton("OK");
        JFXButton buttonNo = new JFXButton("No");
        AlertMaker.showMaterialDialog(rootPane, borderPane, Arrays.asList(buttonOk, buttonNo), "issue operation", "Are you sure want to issue this book?");

        buttonOk.addEventHandler(MouseEvent.MOUSE_CLICKED, event1 -> {
            DBHandler.saveIssue(new Issue(bookID, memberID));
            DBHandler.updateColumn("book", "ID", false, bookID);
            AlertMaker.showMaterialDialog(rootPane, borderPane, Arrays.asList(buttonOk), "Success", "book issue complete");
            clearEateries();
        });

        buttonNo.addEventHandler(MouseEvent.MOUSE_CLICKED, event1 -> {
            enableDisableOfGraph(true);
        });
    }

    @FXML
    void handleRenew(ActionEvent event) {
        String bookID = bookIDTextField2.getText();
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirm Renew Operation");
        alert.setContentText("Are you sure you want to renew the book " + bookName.getText() + "\n to " + memberName.getText() + "?");

        Optional<ButtonType> respone = alert.showAndWait();

        if (respone.get() == ButtonType.OK) {
            DBHandler.updateColumn("issue", "bookID", "now()", bookID);
        }
    }

    @FXML
    void handleSubmission(ActionEvent event) {


        JFXButton buttonOk = new JFXButton("OK");
        JFXButton buttonNo = new JFXButton("No");
        AlertMaker.showMaterialDialog(rootPane, borderPane, Arrays.asList(buttonOk, buttonNo), "Confirm Submission Operation", "Are you sure you want to submission the book?");

        buttonOk.addEventHandler(MouseEvent.MOUSE_CLICKED, event1 -> {
            disableEnableControls(true);
            String bookID = bookIDTextField2.getText();
            disableEnableControls(false);

            DBHandler.deleteColumn("lms_database.issue", "bookID", bookID);
            DBHandler.updateColumn("lms_database.book", "ID",true, bookID);
            AlertMaker.showMaterialDialog(rootPane, borderPane, Arrays.asList(buttonOk), "Success", "book submission complete");
        });
    }

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
    void handleMenuClose(ActionEvent event) {
        closeStage();
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

    private void clearBookCache() {
        bookName.setText("");
        bookAuthor.setText("");
        bookStatus.setText("");
    }

    private void clearMemberCache() {
        memberName.setText("");
        memberContact.setText("");
    }

    private void clearEateries() {
        memberNameHeader.setText("");
        memberEmailHeader.setText("");
        memberContact.setText("");

        bookNameHeader.setText("");
        booKAuthorHeader.setText("");
        bookPublisherContent.setText("");

        issueDateHeader.setText("");
        issueNoOfDaysHeader.setText("");
        issueFineHeader.setText("");

        disableEnableControls(true);
        disableEnableControls(false);
    }

    private void disableEnableControls(boolean enableFlag) {
         if (enableFlag) {
             renewButton.setDisable(false);
             submissionButton.setDisable(false);

             memberVBox.setVisible(true);
             bookVBox.setVisible(true);
             issueVBox.setVisible(true);
         } else {
             renewButton.setDisable(true);
             submissionButton.setDisable(true);

             memberVBox.setVisible(false);
             bookVBox.setVisible(false);
             issueVBox.setVisible(false);
         }
    }

    private void closeStage() {
        Stage stage = (Stage) rootPane.getScene().getWindow();
        stage.close();
    }

    @FXML
    void handleBookInfo(ActionEvent event) {
        clearBookCache();
        enableDisableOfGraph(false);
        String id = bookIDTextField.getText();

        if (DBHandler.getBookInfo(id).size() == 0) {
            JFXButton button = new JFXButton("Ok");
            AlertMaker.showMaterialDialog(rootPane, borderPane,Arrays.asList(button), "Error", "No such a book id");
            return;
        }

        bookName.setText(DBHandler.getBookInfo(id).get(0).getTitle());
        bookAuthor.setText(DBHandler.getBookInfo(id).get(0).getAuthor());
        String status = DBHandler.getBookInfo(id).get(0).getIsAvailable().equals(1) ? "Available" : "not Available";
        bookStatus.setText(status);
    }



    @FXML
    void handleMemberInfo(ActionEvent event) {
        clearMemberCache();
        enableDisableOfGraph(false);
        String id = memberIDTextField.getText();
        if (DBHandler.getMemberInfo(id).size() != 0 || DBHandler.getMemberInfo(id).size()!= 0) {
            memberName.setText(DBHandler.getMemberInfo(id).get(0).getName());
            memberContact.setText(DBHandler.getMemberInfo(id).get(0).getMobile());
        } else {
            JFXButton button = new JFXButton("Ok");
            AlertMaker.showMaterialDialog(rootPane, borderPane,Arrays.asList(button), "Error", "No such member id");
        }

    }

    @FXML
    void handleInfo(ActionEvent event) {
        clearEateries();
        String bookID = bookIDTextField2.getText();
        List<Object> list = DBHandler.getInfo(bookID);

        if (list.size() == 0) {
            JFXButton button = new JFXButton("OK, I'll check");

            AlertMaker.showMaterialDialog(rootPane, borderPane, Arrays.asList(button), "No exists issues book in database", null);
        } else {
            Member member = (Member) list.get(0);
            memberNameHeader.setText(member.getName());
            memberEmailHeader.setText(member.getEmail());
            memberContent.setText(member.getMobile());

            Book book = (Book) list.get(1);
            bookNameHeader.setText(book.getTitle());
            booKAuthorHeader.setText(book.getAuthor());
            bookPublisherContent.setText(book.getAuthor());
            Issue issue = (Issue) list.get(2);
            issueDateHeader.setText(issue.getIssueTime());
            issueNoOfDaysHeader.setText(String.valueOf(issue.getRenewCount()));
//            System.out.println(issue.getIssueTime());
//            Timestamp timestamp = Timestamp.valueOf(LocalDateTime.now());
//            long timeElapsed = System.currentTimeMillis() - timestamp.getTime();
//            long dayElapsed = TimeUnit.DAYS.convert(timeElapsed, TimeUnit.MICROSECONDS);
//            issue.setNumberDaysHolder(dayElapsed);
//            issueFineHeader.setText(String.valueOf(dayElapsed));

            disableEnableControls(true);
        }





//        ObservableList<String> books = DBHandler.getIssueInfo(bookID);
//
//        issueDataList.getItems().setAll(books);
    }



    @FXML
    void handleMenuItemFullScreen(ActionEvent event) {
        Stage stage = (Stage) rootPane.getScene().getWindow();
        stage.setFullScreen(!stage.isFullScreen());
    }

    private void initDrawer() {
        try {
            VBox toolbar = FXMLLoader.load(getClass().getResource("../view/toolBar.fxml"));
            drawer.setSidePane(toolbar);
            drawer.setDefaultDrawerSize(150);
        } catch (IOException ex) {
            Logger.getLogger(OverviewController.class.getName()).log(Level.SEVERE, null, ex);
        }
        HamburgerSlideCloseTransition task = new HamburgerSlideCloseTransition(hamburger);
        task.setRate(-1);
;
        hamburger.addEventHandler(javafx.scene.input.MouseEvent.MOUSE_CLICKED, event -> {
            task.setRate(task.getRate() * -1);
            task.play();
            if (drawer.isHidden()) {
                drawer.setMinWidth(150);
                drawer.open();
            } else {
                drawer.setMinWidth(0);
                drawer.close();
            }
        });
    }

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {

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
        JFXDepthManager.setDepth(bookInfo, 1);
        JFXDepthManager.setDepth(memberInfo, 1);

        initDrawer();
        initGraphs();
    }

    private void initGraphs() {
        bookChart.setData(DBHandler.getBookGraphicStatistics());
        memberChart.setData(DBHandler.getIssueGraphicStatistics());


    }

    private void refreshGraphs() {
        initGraphs();
    }

    private void enableDisableOfGraph(boolean status) {
        if (status) {
            bookChart.setOpacity(1);
            memberChart.setOpacity(1);
        } else {
            bookChart.setOpacity(0);
            memberChart.setOpacity(0);
        }
    }
}
