package it.uniroma2.dicii.ispw.MyLib.engineering.dao;

import it.uniroma2.dicii.ispw.MyLib.engineering.bean.BorrowBean;
import it.uniroma2.dicii.ispw.MyLib.engineering.exceptions.DAOException;
import it.uniroma2.dicii.ispw.MyLib.model.Borrow;

import java.util.List;

public interface ManageReservationDAO {
    BorrowBean activateReservation(Borrow borrow) throws DAOException;
    List<Borrow> getPendingReservations() throws DAOException;
}
