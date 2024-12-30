package it.uniroma2.dicii.ispw.MyLib.engineering.query;

import it.uniroma2.dicii.ispw.MyLib.model.Costumer;

import java.sql.*;

public class LoginQuery {

    private LoginQuery() {}

    public static boolean checkEmailReg(Connection conn, String email) throws SQLException {

        PreparedStatement stmt = conn.prepareStatement(Query.SEARCH_EMAIL);

        stmt.setString(1, email);
        ResultSet rs = stmt.executeQuery();

        return !rs.next();

    }


    public static ResultSet loginUser(Connection conn, String email) throws SQLException {

        PreparedStatement stmt = conn.prepareStatement(Query.GET_USER);

        stmt.setString(1, email);
        return stmt.executeQuery();

    }

    public static int insertUser(Connection conn, Costumer costumer) throws SQLException{

        PreparedStatement stmt = conn.prepareStatement(Query.REGISTER_USER);

        stmt.setString(1, costumer.getEmail());
        stmt.setString(2, costumer.getPassword());

        return stmt.executeUpdate();
    }


    public static int insertCostumer(Connection conn, Costumer costumer) throws SQLException{

        PreparedStatement stmt = conn.prepareStatement(Query.REGISTER_COSTUMER);

        stmt.setString(1, costumer.getEmail());
        stmt.setString(2, costumer.getName());
        stmt.setString(3, costumer.getSurname());
        stmt.setDate(4, Date.valueOf(costumer.getMemDate()));

        return stmt.executeUpdate();
    }


    public static ResultSet loadCostumerInfo(Connection conn, String email) throws SQLException {

        PreparedStatement stmt = conn.prepareStatement(Query.GET_COSTUMER);

        stmt.setString(1, email);
        return stmt.executeQuery();

    }

    public static ResultSet loadCostumerBorrows(Connection conn, String email) throws SQLException {

        PreparedStatement stmt = conn.prepareStatement(Query.GET_BORROWS);

        stmt.setString(1, email);
        return stmt.executeQuery();
    }

    public static ResultSet loadLibrarian(Connection conn, String email) throws SQLException {

        PreparedStatement stmt = conn.prepareStatement(Query.GET_LIBRARIAN);

        stmt.setString(1, email);
        return stmt.executeQuery();
    }
}
