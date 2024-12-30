package it.uniroma2.dicii.ispw.mylib.controller;

import it.uniroma2.dicii.ispw.mylib.engineering.bean.BookBean;
import it.uniroma2.dicii.ispw.mylib.engineering.bean.BorrowBean;
import it.uniroma2.dicii.ispw.mylib.engineering.dao.ManageReservationDAO;
import it.uniroma2.dicii.ispw.mylib.engineering.exceptions.DAOException;
import it.uniroma2.dicii.ispw.mylib.engineering.factory.DAOFactory;
import it.uniroma2.dicii.ispw.mylib.engineering.singleton.Configurations;
import it.uniroma2.dicii.ispw.mylib.model.Book;
import it.uniroma2.dicii.ispw.mylib.model.Borrow;
import it.uniroma2.dicii.ispw.mylib.other.Printer;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class ManageReservationsController {

    private static final Logger log = Logger.getLogger(Configurations.LOGGER_NAME);

    public BorrowBean activateReservation(BorrowBean bean) throws DAOException {
        var borrow = new Borrow(new Book(bean.getBook().getIsbn()), bean.getCostumer(), bean.getCopy(), bean.getInReq());

        ManageReservationDAO manageReservationDAO = DAOFactory.getDAOFactory().createManageReservationDAO();
        try {
            return manageReservationDAO.activateReservation(borrow);
        } catch (DAOException e) {
            log.severe("Error in ManageReservationController (activateReservation): " + e.getMessage());
            Printer.errorPrint("Error activating reservation.");
            throw new DAOException();
        }
    }

    public List<BorrowBean> getPendingReservations() throws DAOException {
        List<Borrow> searchResults;
        List<BorrowBean> searchResultsBean = new ArrayList<>();

        ManageReservationDAO menageReservationDAO = DAOFactory.getDAOFactory().createManageReservationDAO();

        try {
            searchResults = menageReservationDAO.getPendingReservations();
        } catch (DAOException e) {
            log.severe("Error in ManageReservationController (getPendingReservation): " + e.getMessage());
            Printer.errorPrint("Error getting pending reservation.");
            throw new DAOException();
        }


        for (Borrow result: searchResults){
            var bookBean = new BookBean(result.getBook().getIsbn());
            var borrowBean = new BorrowBean(bookBean, result.getCostumer(), result.getCopy(), result.getInReq(), result.getPosition());
            searchResultsBean.add(borrowBean);
        }
        return searchResultsBean;
    }
}
