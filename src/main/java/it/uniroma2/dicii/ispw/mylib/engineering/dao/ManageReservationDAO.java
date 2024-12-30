package it.uniroma2.dicii.ispw.mylib.engineering.dao;

import it.uniroma2.dicii.ispw.mylib.engineering.bean.BorrowBean;
import it.uniroma2.dicii.ispw.mylib.engineering.exceptions.DAOException;
import it.uniroma2.dicii.ispw.mylib.model.Borrow;

import java.util.List;

public interface ManageReservationDAO {
    BorrowBean activateReservation(Borrow borrow) throws DAOException;
    List<Borrow> getPendingReservations() throws DAOException;
}
