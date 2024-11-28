package it.uniroma2.dicii.ispw.librarymanagmentsystem.engineering.dao;

import it.uniroma2.dicii.ispw.librarymanagmentsystem.engineering.bean.BookBean;
import it.uniroma2.dicii.ispw.librarymanagmentsystem.engineering.bean.LoginBean;
import it.uniroma2.dicii.ispw.librarymanagmentsystem.engineering.exceptions.*;
import it.uniroma2.dicii.ispw.librarymanagmentsystem.engineering.query.LoginQuery;
import it.uniroma2.dicii.ispw.librarymanagmentsystem.engineering.singleton.Connector;

import it.uniroma2.dicii.ispw.librarymanagmentsystem.model.Book;
import it.uniroma2.dicii.ispw.librarymanagmentsystem.model.Borrow;
import it.uniroma2.dicii.ispw.librarymanagmentsystem.model.Librarian;
import it.uniroma2.dicii.ispw.librarymanagmentsystem.model.Costumer;
import it.uniroma2.dicii.ispw.librarymanagmentsystem.other.SupportedRoleTypes;

import java.sql.*;
import java.time.LocalDate;
import java.util.logging.Logger;

public class UserMySQLDAO implements UserDAO {
    private static final Logger logger = Logger.getLogger(UserMySQLDAO.class.getName());
    public UserMySQLDAO() {}

    @Override
    public LoginBean getUserInfoByEmail(String email) throws UserNotFoundException, DAOException {

        try (ResultSet rs = LoginQuery.loginUser(Connector.getConnection(), email)) {

            if (!rs.next()) {
                throw new UserNotFoundException();
            } else {
                return new LoginBean(email, rs.getString("password"), rs.getString("type"));

            }
        } catch (SQLException e) {
            throw new DAOException("Error in UserMySQLDAO: " + e.getMessage());
        }

    }

    @Override
    public void insertCostumer(Costumer costumer) throws EmailAlreadyInUseException, DAOException {

        try {

            boolean bool = LoginQuery.checkEmailReg(Connector.getConnection(), costumer.getEmail());

            if (bool) {
                try{

                    if(LoginQuery.insertCostumer(Connector.getConnection(), costumer) == 0) {
                        throw new DAOException("Error in UserMySQLDAO");
                    }

                }catch (SQLException e) {
                    throw new DAOException("Error in UserMySQLDAO: " + e.getMessage());
                }

            } else {
                throw new EmailAlreadyInUseException();
            }

        } catch (SQLException e) {
            throw new DAOException("Error in UserMySQLDAO: " + e.getMessage());
        }
    }


    @Override
    public Costumer loadCostumer(String email) throws UserNotFoundException, DAOException {

        Costumer costumer;

        try (ResultSet rs = LoginQuery.loadCostumerInfo(Connector.getConnection(), email)) {

            if (!rs.next()) {
                throw new UserNotFoundException();
            } else {
                costumer = new Costumer(email, rs.getString("name"), rs.getString("surname"), rs.getDate("memDate").toLocalDate(), rs.getBoolean("memStatus"));
            }
        } catch (SQLException e) {
            throw new DAOException("Error in UserMySQLDAO: " + e.getMessage());
        }

        try (ResultSet rs = LoginQuery.loadCostumerBorrows(Connector.getConnection(), email)) {

            while(rs.next()){
                var book = new Book(rs.getString("ISBN"), rs.getString("title"), rs.getString("authors"), rs.getString("editor"), rs.getShort("pubYear"), rs.getString("genres"));
                if(rs.getString("state").equalsIgnoreCase("pending")) {
                    var borrow = new Borrow(book, rs.getShort("copyNum"));
                    costumer.addPedingBorrows(borrow);
                }else if(rs.getString("state").equalsIgnoreCase("finished")){
                    var borrow = new Borrow(book, rs.getShort("copyNum"), rs.getDate("inDate").toLocalDate(), rs.getDate("endDate").toLocalDate(), rs.getDate("restDate").toLocalDate());
                    costumer.addFinishedBorrows(borrow);
                }else if(rs.getString("state").equalsIgnoreCase("active")) {
                    if(LocalDate.now().isAfter(rs.getDate("endDate").toLocalDate())){
                        var borrow = new Borrow(book, rs.getShort("copyNum"), rs.getDate("inDate").toLocalDate(), rs.getDate("endDate").toLocalDate(), Borrow.calculateFine(rs.getDate("inDate").toLocalDate(), rs.getDate("endDate").toLocalDate()));
                        costumer.addOverdueBorrows(borrow);
                    }else{
                        var borrow = new Borrow(book, rs.getShort("copyNum"), rs.getDate("inDate").toLocalDate(), rs.getDate("endDate").toLocalDate());
                        costumer.addActiveBorrows(borrow);
                    }
                }
            }

        } catch (SQLException e) {
            throw new DAOException("Error in UserMySQLDAO: " + e.getMessage());
        }

        return costumer;
    }

    @Override
    public Librarian loadLibrarian(String email) throws UserNotFoundException, DAOException {
        try (ResultSet rs = LoginQuery.loadLibrarian(Connector.getConnection(), email)) {

            if (!rs.next()) {
                throw new UserNotFoundException();
            } else {
                return new Librarian(email, rs.getString("name"), rs.getString("surname"), rs.getDate("memDate").toLocalDate(), rs.getBoolean("memStatus") ? SupportedRoleTypes.SUPERVISOR : SupportedRoleTypes.ASSISTANT);
            }
        } catch (SQLException e) {
            throw new DAOException("Error in UserMySQLDAO: " + e.getMessage());
        }
    }
}
