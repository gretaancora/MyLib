package it.uniroma2.dicii.ispw.librarymanagmentsystem.engineering.query;

import it.uniroma2.dicii.ispw.librarymanagmentsystem.engineering.exceptions.EmailAlreadyInUseException;
import it.uniroma2.dicii.ispw.librarymanagmentsystem.other.Printer;
import it.uniroma2.dicii.ispw.librarymanagmentsystem.other.SupportedUserTypes;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class QueryLogin {

    private QueryLogin() {}


    public static boolean checkEmailReg(Statement stmt, String email) throws EmailAlreadyInUseException {

        try{
            String sql = String.format(Query.SEARCH_EMAIL, email);
            ResultSet rs = stmt.executeQuery(sql);

            if (rs.next()){
                throw new EmailAlreadyInUseException();
            }else{
                return false;
            }

        }catch (SQLException e){
            throw new EmailAlreadyInUseException();
        }
    }




    public static ResultSet loginUser(Statement stmt, String email) throws SQLException {

        String sql = String.format(Query.GET_USER, email);
        return stmt.executeQuery(sql);

    }



/*
    public static void registerCostumer(Statement stmt, RegisterInfo reg){
        try{

            String email = reg.getEmail();
            String password = reg.getPassword();
            String username = reg.getUsername();
            String name = reg.getName();
            String surname = reg.getSurname();
            SupportedUserTypes supportedUserTypes = reg.getRole();

            String sql = String.format(Query.REGISTER_COSTUMER, email, password, username, name, surname, supportedUserTypes);
            stmt.executeUpdate(sql);

        }catch (SQLException e){
            handleException(e);
        }

    }



    public static void registerLibrarian(Statement stmt, RegisterInfo reg){
        try{
            String sql = String.format(Query.REGISTER_LIBRARIAN, email, nome, cognome);
            stmt.executeUpdate(inserisciTutorStmt);

        } catch (SQLException e) {
            handleException(e);
        }


    }*/

    private static void handleException(Exception e) {
        Printer.errorPrint(String.format("QueryLogin: %s", e.getMessage()));
    }

}
