package it.uniroma2.dicii.ispw.librarymanagmentsystem.engineering.dao;

import it.uniroma2.dicii.ispw.librarymanagmentsystem.engineering.exceptions.DAOException;
import it.uniroma2.dicii.ispw.librarymanagmentsystem.engineering.query.BorrowQuery;
import it.uniroma2.dicii.ispw.librarymanagmentsystem.engineering.singleton.Connector;
import it.uniroma2.dicii.ispw.librarymanagmentsystem.model.Book;
import it.uniroma2.dicii.ispw.librarymanagmentsystem.model.Borrow;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ManageReservationDAO {

    public void activateReservation(Borrow borrow) throws DAOException {
        try{
            int res = BorrowQuery.activateBorrow(Connector.getConnection(), borrow.getBook().getISBN(), borrow.getCostumer(), borrow.getCopy(), borrow.getInReq());
            if (res==0) throw new SQLException();

        } catch (SQLException e) {
            throw new DAOException("Error in ManageReservationDAO (activating reservation)");
        }
    }

    public List<Borrow> getPendingReservations() throws DAOException {
        List<Borrow> pendingBorrows = new ArrayList<>();

        try(ResultSet rs = BorrowQuery.getPendingReservations(Connector.getConnection())){
            while(rs.next()){
                var borrow = new Borrow(new Book(rs.getString("book")), rs.getString("costumer"), rs.getShort("copyNum"), rs.getTimestamp("inReq"), rs.getString("position"));
                pendingBorrows.add(borrow);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DAOException("Error in ManageReservationDAO (getting pending request): " + e.getMessage());
        }

        return pendingBorrows;
    }
}
