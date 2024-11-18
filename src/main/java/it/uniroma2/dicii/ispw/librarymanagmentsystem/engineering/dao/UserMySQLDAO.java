package it.uniroma2.dicii.ispw.librarymanagmentsystem.engineering.dao;

import it.uniroma2.dicii.ispw.librarymanagmentsystem.engineering.bean.LoginBean;
import it.uniroma2.dicii.ispw.librarymanagmentsystem.engineering.exceptions.*;
import it.uniroma2.dicii.ispw.librarymanagmentsystem.engineering.query.QueryLogin;
import it.uniroma2.dicii.ispw.librarymanagmentsystem.model.Librarian;
import it.uniroma2.dicii.ispw.librarymanagmentsystem.model.Costumer;
import it.uniroma2.dicii.ispw.librarymanagmentsystem.other.Connect;
import java.sql.*;
import java.util.logging.Logger;

public class UserMySQLDAO implements UserDAO {
    private static final Logger logger = Logger.getLogger(UserMySQLDAO.class.getName());
    public UserMySQLDAO() {}

    @Override
    public LoginBean getUserInfoByEmail(String email) throws UserNotFoundException, DAOException {
        Statement stmt;

        try{
            Connection connection = Connect.getInstance().getDBConnection();
            stmt = connection.createStatement();

            //verifico le credenziali inserite dall'utente
            try (ResultSet rs = QueryLogin.loginUser(stmt, email)) {

                if(!rs.next()) {
                    throw new UserNotFoundException();
                }
                else{
                    return new LoginBean(email, rs.getString("password"), rs.getString("type"));

                }
            }
        }catch (SQLException e) {
            logger.severe("Error in userDAO: " + e.getMessage());
            throw new DAOException("Error in login DAO MySQL.");
        }
    }

    @Override
    public void insertCostumer(Costumer costumer) throws EmailAlreadyInUseException, UsernameAlreadyInUseException {

    }

    @Override
    public void insertLibrarian(Librarian librarian) throws EmailAlreadyInUseException, UsernameAlreadyInUseException {

    }

    @Override
    public Costumer loadCostumer(String email) throws UserNotFoundException {
        return null;
    }

    @Override
    public Librarian loadLibrarian(String email) throws UserNotFoundException {
        return null;
    }

    @Override
    public void tryCredentialsExisting(String email, String username) throws EmailAlreadyInUseException, UsernameAlreadyInUseException {

    }
}
