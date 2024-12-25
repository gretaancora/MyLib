package it.uniroma2.dicii.ispw.MyLib.engineering.dao;

import it.uniroma2.dicii.ispw.MyLib.engineering.bean.BorrowBean;
import it.uniroma2.dicii.ispw.MyLib.engineering.exceptions.DAOException;
import it.uniroma2.dicii.ispw.MyLib.model.Borrow;

import java.util.List;

public class ManageReservationInMemoryDAO implements ManageReservationDAO{
    @Override
    public BorrowBean activateReservation(Borrow borrow) throws DAOException {
        return null;
    }

    @Override
    public List<Borrow> getPendingReservations() throws DAOException {
        return null;
    }
}
