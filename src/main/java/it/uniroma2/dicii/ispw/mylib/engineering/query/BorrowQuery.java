package it.uniroma2.dicii.ispw.mylib.engineering.query;


import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class BorrowQuery {

    private BorrowQuery() {}

    /*--------------------Borrow Queries Costumer-------------------*/

    public static ResultSet searchBookCopy(Connection conn, String isbn) throws SQLException {

        PreparedStatement stmt = conn.prepareStatement(Query.SEARCH_BOOK_COPY);

        stmt.setString(1, isbn);
        return stmt.executeQuery();
    }

    public static int addBorrow(Connection conn, String isbn, short copy, String costumer, LocalDateTime inReq) throws SQLException {
        PreparedStatement stmt = conn.prepareStatement(Query.ADD_BORROW);

        stmt.setString(1, costumer);
        stmt.setString(2, isbn);
        stmt.setShort(3, copy);
        stmt.setTimestamp(4, Timestamp.valueOf(inReq));

        return stmt.executeUpdate();
    }

    public static int updateStatusCopy(Connection conn, String isbn, short copy) throws SQLException {
        PreparedStatement stmt = conn.prepareStatement(Query.UPDATE_STATUS_COPY);

        stmt.setBoolean(1, false);
        stmt.setString(2, isbn);
        stmt.setShort(3, copy);

        return stmt.executeUpdate();
    }

    public static int setReservationDates(Connection conn, String isbn, short copy) throws SQLException {
        PreparedStatement stmt = conn.prepareStatement(Query.SET_RESERVATION_DATES);

        stmt.setDate(1, Date.valueOf(LocalDate.now()));
        stmt.setDate(2, Date.valueOf(LocalDate.now().plusMonths(1)));
        stmt.setString(3, isbn);
        stmt.setShort(4, copy);

        return stmt.executeUpdate();
    }

    public static int updateNumAvailCopies(Connection conn, String isbn) throws SQLException {
        PreparedStatement stmt = conn.prepareStatement(Query.UPDATE_NUM_AVAIL_COPIES);

            stmt.setString(1,isbn);

            return stmt.executeUpdate();
    }



    /*--------------------Borrow Queries Librarian-------------------*/

    public static int activateBorrow(Connection conn, String book, String costumer, short copy, LocalDateTime inReq) throws SQLException {
        PreparedStatement stmt = conn.prepareStatement(Query.UPDATE_STATUS_BORROW);

        stmt.setString(1, "active");
        stmt.setString(2, costumer);
        stmt.setString(3, book);
        stmt.setShort(4, copy);
        stmt.setTimestamp(5, Timestamp.valueOf(inReq));

        return stmt.executeUpdate();
    }

    public static ResultSet getPendingReservations(Connection conn) throws SQLException {
        PreparedStatement stmt = conn.prepareStatement(Query.GET_PENDING_BORROWS);

        return stmt.executeQuery();
    }
}
