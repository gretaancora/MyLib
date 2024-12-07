package it.uniroma2.dicii.ispw.librarymanagmentsystem.engineering.query;

import java.sql.*;

public class SearchQuery {
    public static ResultSet searchBookByAuthor(Connection conn, String name, String surname) throws SQLException {

        PreparedStatement stmt = conn.prepareStatement(Query.SEARCH_BOOK_BY_AUTHOR);

        stmt.setString(1, name);
        stmt.setString(2, surname);

        return stmt.executeQuery();

    }

    public static ResultSet searchBookByTitle(Connection conn, String title) throws SQLException {

        PreparedStatement stmt = conn.prepareStatement(Query.SEARCH_BOOK_BY_TITLE);

        stmt.setString(1, title);

        return stmt.executeQuery();
    }

    public static ResultSet searchBookByAllFields(Connection conn, String filter) throws SQLException {

        PreparedStatement stmt = conn.prepareStatement(Query.SEARCH_BOOK_ALL_FIELDS);

        stmt.setString(1, filter);
        stmt.setString(2, filter);
        stmt.setString(3, filter);
        stmt.setString(4, filter);

        return stmt.executeQuery();

    }

}
