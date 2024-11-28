package it.uniroma2.dicii.ispw.librarymanagmentsystem.engineering.query;

import it.uniroma2.dicii.ispw.librarymanagmentsystem.model.Borrow;

import java.sql.*;

public class BorrowQuery {

    public static ResultSet searchBookCopy(Connection conn, String ISBN) throws SQLException {

        try (PreparedStatement stmt = conn.prepareStatement(Query.SEARCH_BOOK_COPY)) {

            stmt.setString(1, ISBN);
            return stmt.executeQuery();

        }
    }

    public static int addBorrow(Connection conn, String ISBN, short copy, String costumer) throws SQLException {
        try (PreparedStatement stmt = conn.prepareStatement(Query.ADD_BORROW)) {

            stmt.setString(1, costumer);
            stmt.setString(2, ISBN);
            stmt.setShort(3, copy);

            return stmt.executeUpdate();

        }
    }

    public static int updateStatusCopy(Connection conn, String ISBN, short copy) throws SQLException {
        try (PreparedStatement stmt = conn.prepareStatement(Query.UPDATE_STATUS_COPY)) {

            stmt.setString(1, ISBN);
            stmt.setShort(3, copy);

            return stmt.executeUpdate();

        }
    }

    public static int updateNumAvailCopies(Connection conn, String ISBN) throws SQLException {
        try (PreparedStatement stmt = conn.prepareStatement(Query.UPDATE_NUM_AVAIL_COPIES)) {

            stmt.setString(1,ISBN);

            return stmt.executeUpdate();

        }
    }



    /*--------------------Borrow Queries Librarian-------------------*/
    public static int activateBorrow(Connection conn, String book, String costumer, short copy, Timestamp inReq) throws SQLException {
        try (PreparedStatement stmt = conn.prepareStatement(Query.UPDATE_STATUS_BORROW)) {

            stmt.setString(1,"active");
            stmt.setString(2, costumer);
            stmt.setString(3, book);
            stmt.setShort(4, copy);
            stmt.setTimestamp(5, inReq);

            return stmt.executeUpdate();

        }
    }

    public static ResultSet getPendingReservations(Connection conn) throws SQLException {
        try (PreparedStatement stmt = conn.prepareStatement(Query.GET_PENDING_BORROWS)) {

            return stmt.executeQuery();

        }
    }
}
