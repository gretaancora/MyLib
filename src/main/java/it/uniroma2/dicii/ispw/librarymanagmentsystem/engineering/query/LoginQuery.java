package it.uniroma2.dicii.ispw.librarymanagmentsystem.engineering.query;

import it.uniroma2.dicii.ispw.librarymanagmentsystem.engineering.exceptions.EmailAlreadyInUseException;
import it.uniroma2.dicii.ispw.librarymanagmentsystem.engineering.singleton.Connector;
import it.uniroma2.dicii.ispw.librarymanagmentsystem.model.Costumer;
import it.uniroma2.dicii.ispw.librarymanagmentsystem.other.Printer;

import java.sql.*;

public class LoginQuery {

    private LoginQuery() {}


    public static boolean checkEmailReg(Connection conn, String email) throws EmailAlreadyInUseException, SQLException {

        try (PreparedStatement stmt = conn.prepareStatement(Query.SEARCH_EMAIL)) {

            stmt.setString(1, email);
            ResultSet rs = stmt.executeQuery();

            if(!rs.next()){
                return true;
            }else{
                return false;
            }

        }
    }


    public static ResultSet loginUser(Connection conn, String email) throws SQLException {

        try (PreparedStatement stmt = conn.prepareStatement(Query.GET_USER)) {

            stmt.setString(1, email);
            return stmt.executeQuery();

        }
    }


    public static int insertCostumer(Connection conn, Costumer costumer) throws SQLException{

        try (PreparedStatement stmt = conn.prepareStatement(Query.REGISTER_COSTUMER)) {

            stmt.setString(1, costumer.getEmail());
            stmt.setString(2, costumer.getPassword());
            stmt.setString(3, costumer.getEmail());
            stmt.setString(4, costumer.getName());
            stmt.setString(5, costumer.getSurname());
            stmt.setDate(6, Date.valueOf(costumer.getMemDate()));

            return stmt.executeUpdate();

        }
    }


    public static ResultSet loadCostumerInfo(Connection conn, String email) throws SQLException {

        try (PreparedStatement stmt = conn.prepareStatement(Query.GET_COSTUMER)) {

            stmt.setString(1, email);
            return stmt.executeQuery();

        }
    }

    public static ResultSet loadCostumerBorrows(Connection conn, String email) throws SQLException {

        try (PreparedStatement stmt = conn.prepareStatement(Query.GET_BORROWS)) {

            stmt.setString(1, email);
            return stmt.executeQuery();

        }
    }

    public static ResultSet loadLibrarian(Connection conn, String email) throws SQLException {

        try (PreparedStatement stmt = conn.prepareStatement(Query.GET_LIBRARIAN)) {

            stmt.setString(1, email);
            return stmt.executeQuery();

        }
    }
}
