package it.uniroma2.dicii.ispw.MyLib.controller;

import it.uniroma2.dicii.ispw.MyLib.engineering.bean.BookBean;
import it.uniroma2.dicii.ispw.MyLib.engineering.bean.BorrowBean;
import it.uniroma2.dicii.ispw.MyLib.engineering.dao.ManageReservationDAO;
import it.uniroma2.dicii.ispw.MyLib.engineering.exceptions.DAOException;
import it.uniroma2.dicii.ispw.MyLib.model.Book;
import it.uniroma2.dicii.ispw.MyLib.model.Borrow;

import java.util.ArrayList;
import java.util.List;

public class ManageReservationsController {
    public BorrowBean activateReservation(BorrowBean bean) throws DAOException {
        var borrow = new Borrow(new Book(bean.getBook().getISBN()), bean.getCostumer(), bean.getCopy(), bean.getInReq());
        var manageReservationDAO = new ManageReservationDAO();
        return manageReservationDAO.activateReservation(borrow);
    }

    public List<BorrowBean> getPendingReservations() throws DAOException {
        List<Borrow> searchResults;
        List<BorrowBean> searchResultsBean = new ArrayList<>();

        var menageReservationDAO = new ManageReservationDAO();

        searchResults = menageReservationDAO.getPendingReservations();


        for (Borrow result: searchResults){
            var bookBean = new BookBean(result.getBook().getISBN());
            var borrowBean = new BorrowBean(bookBean, result.getCostumer(), result.getCopy(), result.getInReq(), result.getPosition());
            searchResultsBean.add(borrowBean);
        }
        return searchResultsBean;
    }
}
