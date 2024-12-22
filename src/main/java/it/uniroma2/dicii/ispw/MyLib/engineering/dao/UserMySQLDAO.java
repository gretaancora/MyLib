package it.uniroma2.dicii.ispw.MyLib.engineering.dao;

import it.uniroma2.dicii.ispw.MyLib.engineering.bean.LoginBean;
import it.uniroma2.dicii.ispw.MyLib.engineering.exceptions.*;
import it.uniroma2.dicii.ispw.MyLib.engineering.query.LoginQuery;
import it.uniroma2.dicii.ispw.MyLib.engineering.singleton.Connector;

import it.uniroma2.dicii.ispw.MyLib.model.Book;
import it.uniroma2.dicii.ispw.MyLib.model.Borrow;
import it.uniroma2.dicii.ispw.MyLib.model.Librarian;
import it.uniroma2.dicii.ispw.MyLib.model.Costumer;
import it.uniroma2.dicii.ispw.MyLib.other.SupportedRoleTypes;

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
        Connection conn;

        try {

            boolean bool = LoginQuery.checkEmailReg(Connector.getConnection(), costumer.getEmail());

            if (bool) {
                try{

                    conn = Connector.getConnection();

                    conn.setAutoCommit(false);

                    if(LoginQuery.insertUser(conn, costumer) == 0) {
                        throw new DAOException("Error in UserMySQLDAO");
                    }

                    if(LoginQuery.insertCostumer(conn, costumer) == 0) {
                        throw new DAOException("Error in UserMySQLDAO");
                    }

                    conn.commit();

                }catch (SQLException e) {
                    e.printStackTrace();
                    throw new DAOException("Error in UserMySQLDAO: " + e.getMessage());
                }

            } else {
                throw new EmailAlreadyInUseException();
            }

        } catch (SQLException e) {
            e.printStackTrace();
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
            System.out.println("Error in loadCostumerBorrows");
            e.printStackTrace();
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
                return new Librarian(email, rs.getString("name"), rs.getString("surname"), rs.getDate("empDate").toLocalDate(), rs.getBoolean("role") ? SupportedRoleTypes.SUPERVISOR : SupportedRoleTypes.ASSISTANT);
            }
        } catch (SQLException e) {
            System.out.println("Error in loadLibrarian");
            e.printStackTrace();
            throw new DAOException("Error in UserMySQLDAO: " + e.getMessage());
        }
    }
}
