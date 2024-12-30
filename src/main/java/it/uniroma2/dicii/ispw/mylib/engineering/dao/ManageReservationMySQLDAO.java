package it.uniroma2.dicii.ispw.mylib.engineering.dao;

import it.uniroma2.dicii.ispw.mylib.engineering.bean.BorrowBean;
import it.uniroma2.dicii.ispw.mylib.engineering.exceptions.DAOException;
import it.uniroma2.dicii.ispw.mylib.engineering.query.BorrowQuery;
import it.uniroma2.dicii.ispw.mylib.engineering.singleton.Configurations;
import it.uniroma2.dicii.ispw.mylib.engineering.singleton.Connector;
import it.uniroma2.dicii.ispw.mylib.model.Book;
import it.uniroma2.dicii.ispw.mylib.model.Borrow;
import it.uniroma2.dicii.ispw.mylib.other.Printer;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;


public class ManageReservationMySQLDAO implements ManageReservationDAO{

    private static final Logger log = Logger.getLogger(Configurations.LOGGER_NAME);

    public BorrowBean activateReservation(Borrow borrow) throws DAOException {
        Connection conn = null;
        try {
            conn = Connector.getConnection();
            conn.setAutoCommit(false);

            int res = BorrowQuery.activateBorrow(conn, borrow.getBook().getIsbn(), borrow.getCostumer(), borrow.getCopy(), borrow.getInReq());
            if (res == 0) throw new SQLException();

            res = BorrowQuery.setReservationDates(conn, borrow.getBook().getIsbn(), borrow.getCopy());
            if (res == 0) throw new SQLException();

            conn.commit();

            try {
                conn.setAutoCommit(true);

            } catch (SQLException e) {
                handleDAOException(e);
            }

            return new BorrowBean(LocalDate.now(), LocalDate.now().plusMonths(1));

        } catch (SQLException e) {

            if (conn != null) {
                try {
                    conn.rollback();
                    conn.setAutoCommit(true);
                    handleDAOException(e);
                } catch (SQLException r) {
                    handleDAOException(r);
                }
                handleDAOException(e);
            }

            log.severe("Error in ManageReservationMySQLDAO: " + e.getMessage());
            Printer.errorPrint("Error occurred managing reservations.");
            throw new DAOException(e.getMessage());

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
            handleDAOException(e);
        }

        return pendingBorrows;
    }

    private void handleDAOException(Exception e) throws DAOException {
        log.severe("Error in ManageReservationMySQLDAO: " + e.getMessage());
        Printer.errorPrint("Error occurred managing reservations.");
        throw new DAOException(e.getMessage());
    }
}
