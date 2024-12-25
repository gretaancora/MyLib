package it.uniroma2.dicii.ispw.MyLib.engineering.dao;

import it.uniroma2.dicii.ispw.MyLib.engineering.bean.BorrowBean;
import it.uniroma2.dicii.ispw.MyLib.engineering.exceptions.DAOException;
import it.uniroma2.dicii.ispw.MyLib.engineering.query.BorrowQuery;
import it.uniroma2.dicii.ispw.MyLib.engineering.singleton.Connector;
import it.uniroma2.dicii.ispw.MyLib.model.Book;
import it.uniroma2.dicii.ispw.MyLib.model.Borrow;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


public class ManageReservationMySQLDAO implements ManageReservationDAO{

    public BorrowBean activateReservation(Borrow borrow) throws DAOException {
        Connection conn = null;
        try {
            conn = Connector.getConnection();
            conn.setAutoCommit(false);

            int res = BorrowQuery.activateBorrow(conn, borrow.getBook().getISBN(), borrow.getCostumer(), borrow.getCopy(), borrow.getInReq());
            if (res == 0) throw new SQLException();

            res = BorrowQuery.setReservationDates(conn, borrow.getBook().getISBN(), borrow.getCopy());
            if (res == 0) throw new SQLException();

            conn.commit();

            return new BorrowBean(LocalDate.now(), LocalDate.now().plusMonths(1));

        } catch (SQLException e) {

            e.printStackTrace();

            if (conn != null) {
                try {
                    conn.rollback();
                    System.out.println("Error occurred activating reservation transaction: " + e.getMessage());
                } catch (SQLException rollbackEx) {
                    rollbackEx.printStackTrace();
                    throw new DAOException("Error in ManageReservationDAO (during rollback): " + e.getMessage());
                }
            }

            throw new DAOException("Error in ManageReservationDAO: " + e.getMessage());

        }
    }

    public List<Borrow> getPendingReservations() throws DAOException {
        List<Borrow> pendingBorrows = new ArrayList<>();

        try(ResultSet rs = BorrowQuery.getPendingReservations(Connector.getConnection())){
            while(rs.next()){
                var borrow = new Borrow(new Book(rs.getString("book")), rs.getString("costumer"), rs.getShort("copyNum"), rs.getTimestamp("inReq").toLocalDateTime(), rs.getString("position"));
                pendingBorrows.add(borrow);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DAOException("Error in ManageReservationDAO (getting pending request): " + e.getMessage());
        }

        return pendingBorrows;
    }
}
