package it.uniroma2.dicii.ispw.mylib.engineering.dao;

import it.uniroma2.dicii.ispw.mylib.engineering.bean.LoginBean;
import it.uniroma2.dicii.ispw.mylib.engineering.exceptions.*;
import it.uniroma2.dicii.ispw.mylib.engineering.query.LoginQuery;
import it.uniroma2.dicii.ispw.mylib.engineering.singleton.Configurations;
import it.uniroma2.dicii.ispw.mylib.engineering.singleton.Connector;
import it.uniroma2.dicii.ispw.mylib.model.Book;
import it.uniroma2.dicii.ispw.mylib.model.Borrow;
import it.uniroma2.dicii.ispw.mylib.model.Librarian;
import it.uniroma2.dicii.ispw.mylib.model.Costumer;
import it.uniroma2.dicii.ispw.mylib.other.Printer;
import it.uniroma2.dicii.ispw.mylib.other.SupportedRoleTypes;
import it.uniroma2.dicii.ispw.mylib.other.SupportedUserTypes;

import java.sql.*;
import java.time.LocalDate;
import java.util.logging.Logger;

public class UserMySQLDAO implements UserDAO {
    private static final Logger logger = Logger.getLogger(Configurations.LOGGER_NAME);
    private static final String STATE = "state";
    private static final String COPY_NUM = "copyNum";
    private static final String IN_DATE = "inDate";
    private static final String END_DATE = "endDate";


    @Override
    public LoginBean getUserInfoByEmail(String email) throws UserNotFoundException, DAOException {

        try (ResultSet rs = LoginQuery.loginUser(Connector.getConnection(), email)) {

            if (!rs.next()) {
                throw new UserNotFoundException();
            } else {
                return new LoginBean(email, rs.getString("password"), SupportedUserTypes.valueOf((rs.getString("type")).toUpperCase()));

            }
        } catch (SQLException e) {
            logger.severe("Error in UserMySQLDAO (getUserInfoByEmail): " + e.getMessage());
            Printer.errorPrint("Error getting user information.");
            throw new DAOException();
        }

    }

    @Override
    public void insertCostumer(Costumer costumer) throws EmailAlreadyInUseException, DAOException {

        try {

            boolean bool = LoginQuery.checkEmailReg(Connector.getConnection(), costumer.getEmail());

            if (bool) {

                insert(costumer);

            } else {
                throw new EmailAlreadyInUseException();
            }

        } catch (SQLException e) {
            logger.severe("Error in UserMySQLDAO (insertCostumer): " + e.getMessage());
            Printer.errorPrint("Error adding costumer.");
            throw new DAOException();
        }

    }

    private void insert(Costumer costumer) throws DAOException {
        try{

            Connection conn = Connector.getConnection();

            conn.setAutoCommit(false);

            if(LoginQuery.insertUser(conn, costumer) == 0) {
                throw new DAOException("Error in UserMySQLDAO");
            }

            if(LoginQuery.insertCostumer(conn, costumer) == 0) {
                throw new DAOException("Error in UserMySQLDAO");
            }

            conn.commit();

        }catch (SQLException e) {
            logger.severe("Error in UserMySQLDAO (insertCostumer): " + e.getMessage());
            Printer.errorPrint("Error adding costumer.");
            throw new DAOException();
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
            logger.severe("Error in UserMySQLDAO (loadCostumer): " + e.getMessage());
            Printer.errorPrint("Error loading costumer.");
            throw new DAOException();
        }

        try (ResultSet rs = LoginQuery.loadCostumerBorrows(Connector.getConnection(), email)) {

            while(rs.next()){
                var book = new Book(rs.getString("ISBN"), rs.getString("title"), rs.getString("authors"), rs.getString("editor"), rs.getShort("pubYear"), rs.getString("genres"));
                if(rs.getString(STATE).equalsIgnoreCase("pending")) {
                    var borrow = new Borrow(book, rs.getShort(COPY_NUM));
                    costumer.addPedingBorrows(borrow);
                }else if(rs.getString(STATE).equalsIgnoreCase("finished")){
                    var borrow = new Borrow(book, rs.getShort(COPY_NUM), rs.getDate(IN_DATE).toLocalDate(), rs.getDate(END_DATE).toLocalDate(), rs.getDate("restDate").toLocalDate());
                    costumer.addFinishedBorrows(borrow);
                }else if(rs.getString(STATE).equalsIgnoreCase("active")) {
                    if(LocalDate.now().isAfter(rs.getDate(END_DATE).toLocalDate())){
                        var borrow = new Borrow(book, rs.getShort(COPY_NUM), rs.getDate(IN_DATE).toLocalDate(), rs.getDate(END_DATE).toLocalDate(), Borrow.calculateFine(rs.getDate(IN_DATE).toLocalDate(), rs.getDate(END_DATE).toLocalDate()));
                        costumer.addOverdueBorrows(borrow);
                    }else{
                        var borrow = new Borrow(book, rs.getShort(COPY_NUM), rs.getDate(IN_DATE).toLocalDate(), rs.getDate(END_DATE).toLocalDate());
                        costumer.addActiveBorrows(borrow);
                    }
                }
            }

        } catch (SQLException e) {
            logger.severe("Error in UserMySQLDAO (loadCostumer): " + e.getMessage());
            Printer.errorPrint("Error loading costumer.");
            throw new DAOException();
        }

        return costumer;
    }

    @Override
    public Librarian loadLibrarian(String email) throws UserNotFoundException, DAOException {
        try (ResultSet rs = LoginQuery.loadLibrarian(Connector.getConnection(), email)) {

            if (!rs.next()) {
                throw new UserNotFoundException();
            } else {
                return new Librarian(email, rs.getString("name"), rs.getString("surname"), rs.getDate("empDate").toLocalDate(), rs.getBoolean("role") ? SupportedRoleTypes.SUPERVISOR : SupportedRoleTypes.ASSISTANT);
            }
        } catch (SQLException e) {
            logger.severe("Error in UserMySQLDAO (loadLibrarian): " + e.getMessage());
            Printer.errorPrint("Error loading librarian.");
            throw new DAOException();
        }
    }
}
