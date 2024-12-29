package it.uniroma2.dicii.ispw.MyLib.controller;

import it.uniroma2.dicii.ispw.MyLib.engineering.bean.BookBean;
import it.uniroma2.dicii.ispw.MyLib.engineering.bean.BorrowBean;
import it.uniroma2.dicii.ispw.MyLib.engineering.dao.ManageReservationDAO;
import it.uniroma2.dicii.ispw.MyLib.engineering.exceptions.DAOException;
import it.uniroma2.dicii.ispw.MyLib.engineering.factory.DAOFactory;
import it.uniroma2.dicii.ispw.MyLib.engineering.singleton.Configurations;
import it.uniroma2.dicii.ispw.MyLib.model.Book;
import it.uniroma2.dicii.ispw.MyLib.model.Borrow;
import it.uniroma2.dicii.ispw.MyLib.other.Printer;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class ManageReservationsController {

    private static final Logger log = Logger.getLogger(Configurations.LOGGER_NAME);

    public BorrowBean activateReservation(BorrowBean bean) throws DAOException {
        var borrow = new Borrow(new Book(bean.getBook().getISBN()), bean.getCostumer(), bean.getCopy(), bean.getInReq());

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
            var bookBean = new BookBean(result.getBook().getISBN());
            var borrowBean = new BorrowBean(bookBean, result.getCostumer(), result.getCopy(), result.getInReq(), result.getPosition());
            searchResultsBean.add(borrowBean);
        }
        return searchResultsBean;
    }
}
