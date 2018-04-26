package net.yasir.database;

import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Alert;
import net.yasir.alert.AlertMaker;
import net.yasir.model.Book;
import net.yasir.model.Issue;
import net.yasir.model.Member;
import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DBHandler extends Configs {
    private static Connection connection = null;
    private static DBHandler dbHandler = null;
    private static PreparedStatement preparedStatement = null;
    private static ResultSet resultSet = null;


    public static Connection getConnection() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            final String url = "jdbc:mysql://" + dbhost + ":" + dbport + "/" + dbname + "?useSSL=true";
            connection = DriverManager.getConnection(url, dbusername, dbpassword);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            AlertMaker.showErrorMessage("Can't load database", "Database error");
            System.exit(0);
        }
        return connection;
    }

    public static DBHandler getInstance() {
        if (dbHandler == null) {
            dbHandler = new DBHandler();
        }
        return dbHandler;
    }

    public static void saveBook(Book book) {
        String sql = "INSERT INTO lms_database.book (id, title, author, publisher) " +
                "VALUES (?, ?, ?, ?)";
        connection = getConnection();
        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, book.getId());
            preparedStatement.setString(2, book.getTitle());
            preparedStatement.setString(3, book.getAuthor());
            preparedStatement.setString(4, book.getPublisher());
            preparedStatement.executeUpdate();

            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {

        }
    }

    public static boolean editBook(Book book) {
        String query = "UPDATE lms_database.book SET title = ?, author = ?, publisher = ? WHERE ID = ?";
        connection = getConnection();
        try {
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, book.getTitle());
            preparedStatement.setString(2, book.getAuthor());
            preparedStatement.setString(3, book.getPublisher());
            preparedStatement.setString(4, book.getId());
            int res = preparedStatement.executeUpdate();
            connection.close();
            return (res > 0);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
        }

        return false;
    }


    public static void saveMember(Member member) {
        String sql = "INSERT INTO member (id, name, mobile, email) " +
                "VALUES (?, ?, ?, ?)";
        connection = getConnection();
        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, member.getMemberID());
            preparedStatement.setString(2, member.getName());
            preparedStatement.setString(3, member.getMobile());
            preparedStatement.setString(4, member.getEmail());
            preparedStatement.execute();

            connection.close();
        } catch (MySQLIntegrityConstraintViolationException e) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Duplicate ID");
            alert.setContentText("The ID: " + member.getMemberID() + " is duplicate entry");
            alert.showAndWait();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public static boolean editMember(Member member) {
        String query = "UPDATE lms_database.member SET name = ?, mobile = ?, email = ? WHERE ID = ?";
        connection = getConnection();
        try {
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, member.getName());
            preparedStatement.setString(2, member.getMobile());
            preparedStatement.setString(3, member.getEmail());
            preparedStatement.setString(4, member.getMemberID());
            int res = preparedStatement.executeUpdate();
            connection.close();
            return (res > 0);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
        }

        return false;
    }

    public static void saveIssue(Issue issue) {
        String sql = "INSERT INTO issue (bookID, memberID) " +
                "VALUES (?, ?);";
        connection = getConnection();
        boolean flag = false;
        Alert alert;
        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, issue.getBookID());
            preparedStatement.setString(2, issue.getMemberID());
            preparedStatement.execute();
            flag = true;

            connection.close();
        } catch (MySQLIntegrityConstraintViolationException e) {
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Failed");
            alert.setContentText("Issue operation failed");
            alert.showAndWait();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {

        }

        if (flag) {
            alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Success");
            alert.setContentText("Book issue complete");
            alert.showAndWait();
        }
    }

    private static ResultSet executeQuery(String query) {
        ResultSet resultSet;
        try {
            connection = getConnection();
            preparedStatement = connection.prepareStatement(query);
            resultSet = preparedStatement.executeQuery();

            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        } finally {

        }
        return resultSet;
    }

    public static ObservableList<Book> getBooks() {
        ObservableList<Book> list = FXCollections.observableArrayList();
        String query = "SELECT * FROM lms_database.book;";
        try {
            connection = getConnection();
            preparedStatement = connection.prepareStatement(query);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Book book = new Book();

                book.setId(resultSet.getString(1));
                book.setTitle(resultSet.getString(2));
                book.setAuthor(resultSet.getString(3));
                book.setPublisher(resultSet.getString(4));
                book.setIsAvailable(resultSet.getBoolean(5));
                list.add(book);
            }

            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public static List<Book> getBooksList() {
        List<Book> list = new ArrayList();
        String query = "SELECT * FROM lms_database.book";
        connection = getConnection();
        try {
            preparedStatement = connection.prepareStatement(query);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Book book = new Book();

                book.setTitle(resultSet.getString(1));
                book.setId(resultSet.getString(2));
                book.setAuthor(resultSet.getString(3));
                book.setPublisher(resultSet.getString(4));
                book.setIsAvailable(resultSet.getBoolean(5));
                list.add(book);
            }

            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public static List<Book> getBookInfo(String id) {
        List<Book> list = new ArrayList();
        String query = "SELECT * FROM lms_database.book WHERE ID = ?;";
        connection = getConnection();
        try {
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();

            resultSet.next();
            Book book = new Book();
            book.setId(resultSet.getString(1));
            book.setTitle(resultSet.getString(2));
            book.setAuthor(resultSet.getString(3));
            book.setPublisher(resultSet.getString(4));
            book.setIsAvailable(resultSet.getBoolean(5));
            list.add(book);

            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public static List<Object> getInfo(String id) {
        List<Object> list = new ArrayList();
        String query = "\n" +
                "select bookID, issue.memberID, issue.issueTime, issue.renewCount,\n" +
                "member.name,member.mobile, member.email,\n" +
                "book.title, book.author, book.publisher, book.isAvailable\n" +
                "from issue\n" +
                "left join member\n" +
                "on issue.memberID = member.ID\n" +
                "left join book\n" +
                "on issue.bookID = book.ID\n" +
                "WHERE issue.bookID = ?";
        connection = getConnection();
        try {
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, id);
            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                Member member = new Member();
                member.setName(resultSet.getString(5));
                member.setMobile(resultSet.getString(6));
                member.setEmail(resultSet.getString(7));

                Book book = new Book();
                book.setId(resultSet.getString(1));
                book.setTitle(resultSet.getString(8));
                book.setAuthor(resultSet.getString(9));
                book.setPublisher(resultSet.getString(10));
                book.setIsAvailable(resultSet.getBoolean(11));

                Issue issue = new Issue();
                Timestamp timestamp = resultSet.getTimestamp(3);
                Date date = new Date(timestamp.getTime());
                issue.setIssueTime(date.toString());
                issue.setRenewCount(resultSet.getInt(4));

                list.add(member);
                list.add(book);
                list.add(issue);
            }

            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }


    public static ObservableList<String> getIssueInfo(String id) {
        ObservableList<String> issueDate = FXCollections.observableArrayList();
        String query = "SELECT * FROM lms_database.issue WHERE bookID = ?;";
        connection = getConnection();
        try {
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, id);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                String bookID = resultSet.getString(1);
                String memberID =resultSet.getString(2);
                Timestamp issueTime = resultSet.getTimestamp(3);
                int renewCount = resultSet.getInt(4);

                issueDate.add("Issue Date and Time" + issueTime);
                issueDate.add("Renew count: " + renewCount);
                issueDate.add("Book information:- ");
                query = "SELECT * FROM lms_database.book WHERE ID = ?;";
                preparedStatement = connection.prepareStatement(query);
                preparedStatement.setString(1, bookID);
                resultSet = preparedStatement.executeQuery();

                while (resultSet.next()) {
                    issueDate.add(" Book ID: " + resultSet.getString(1));
                    issueDate.add(" Book title: " + resultSet.getString(2));
                    issueDate.add(" Book author: " + resultSet.getString(3));
                    issueDate.add(" Book publisher: " + resultSet.getString(4));
                }

                issueDate.add("Member information: ");
                query = "SELECT * FROM lms_database.member WHERE ID = ?;";
                preparedStatement = connection.prepareStatement(query);
                preparedStatement.setString(1, memberID);
                resultSet = preparedStatement.executeQuery();
                while (resultSet.next()) {
                    issueDate.add(" Member Name: " + resultSet.getString(1));
                    issueDate.add(" Member mobile: " + resultSet.getString(2));
                    issueDate.add(" Member email: " + resultSet.getString(3));
                }
            }



            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return issueDate;
    }


    public static ObservableList<Member> getMembers() {
        ObservableList<Member> list = FXCollections.observableArrayList();
        String query = "SELECT * FROM lms_database.member;";

        try {
            connection = getConnection();
            preparedStatement = connection.prepareStatement(query);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Member member = new Member();
                member.setMemberID(resultSet.getString(1));
                member.setName(resultSet.getString(2));
                member.setMobile(resultSet.getString(3));
                member.setEmail(resultSet.getString(4));
                list.add(member);
            }
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public static List<Member> getMemberInfo(String id) {
        List<Member> list = new ArrayList();
        String query = "SELECT * FROM lms_database.member WHERE ID = ?;";
        connection = getConnection();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            boolean flag = false;
            Member member = new Member();
            while (resultSet.next()) {
                member.setMemberID(resultSet.getString(1));
                member.setName(resultSet.getString(2));
                member.setMobile(resultSet.getString(3));
                member.setEmail(resultSet.getString(4));
                list.add(member);
                flag = true;
            }


            if (!flag) {
                System.out.print("ERROR");
            }

            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (IndexOutOfBoundsException e) {
            System.out.print("Error");
        }
        return list;
    }

    public static  void deleteColumn(String tableName, String columnName, String id) {
        String query = "DELETE FROM "+tableName+" WHERE "+columnName+" = ?;";
        connection = getConnection();
        try {
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, id);
            preparedStatement.executeUpdate();

            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static boolean deleteColumn(Book book) {
        String query = "DELETE FROM lms_database.book WHERE ID = ?;";
        connection = getConnection();
        try {
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, book.getId());
            preparedStatement.executeUpdate();

            connection.close();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    public static boolean deleteColumn(Member member) {
        String query = "DELETE FROM lms_database.member WHERE ID = ?;";
        connection = getConnection();
        try {
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, member.getMemberID());
            preparedStatement.executeUpdate();

            connection.close();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    public static void updateColumn(String tableName, String columnName, boolean val, String id) {
        String query = "UPDATE "+tableName+" SET isAvailable = "+val+" WHERE "+columnName+" = ?;";
        connection = getConnection();
        try {
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, id);
            preparedStatement.execute();

            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void updateColumn(String tableName, String columnName, String issueTime, String id) {
        String query = "UPDATE "+tableName+" SET issueTime = "+issueTime+", renewCount = renewCount+1 WHERE "+columnName+" = ?;";
        connection = getConnection();
        try {
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, id);
            preparedStatement.execute();

            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static boolean isBookAlreadyIssued(Book book) {
        String query = "SELECT COUNT(*) FROM ISSUE WHERE bookID=?";
        connection = getConnection();
        try {
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, book.getId());
            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {

                int count  = resultSet.getInt(1);
                if (count > 0) {
                    return true;
                } else {
                    return false;
                }
            }
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    public static boolean isMemberHasAnyBooks(Member member) {
        String query = "SELECT COUNT(*) FROM ISSUE WHERE memberID=?";
        connection = getConnection();
        try {
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, member.getMemberID());
            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {

                int count  = resultSet.getInt(1);
                if (count > 0) {
                    return true;
                } else {
                    return false;
                }
            }
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    public static ObservableList<PieChart.Data> getBookGraphicStatistics() {
        ObservableList<PieChart.Data> data = FXCollections.observableArrayList();
        connection = getConnection();
        try {
            String query1 = "SELECT COUNT(*) FROM lms_database.book;";
            String query2 = "SELECT COUNT(*) FROM lms_database.issue;";
            preparedStatement = connection.prepareStatement(query1);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                int count = resultSet.getInt(1);
                data.add(new PieChart.Data("Total books ("+count+")", count));
            }


            preparedStatement = connection.prepareStatement(query2);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                int count = resultSet.getInt(1);
                data.add(new PieChart.Data("Total issues ("+count+")", count));
            }
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return data;
    }

    public static ObservableList<PieChart.Data> getIssueGraphicStatistics() {
        ObservableList<PieChart.Data> data = FXCollections.observableArrayList();
        connection = getConnection();
        try {
            String query1 = "SELECT COUNT(*) FROM lms_database.member;";
            String query2 = "SELECT COUNT(distinct memberID) FROM lms_database.issue;";
            preparedStatement = connection.prepareStatement(query1);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                int count = resultSet.getInt(1);
                data.add(new PieChart.Data("Total members ("+count+")", count));
            }


            preparedStatement = connection.prepareStatement(query2);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                int count = resultSet.getInt(1);
                data.add(new PieChart.Data("members with books ("+count+")", count));
            }
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return data;
    }

}
