package it.uniroma2.dicii.ispw.mylib.controller;

import it.uniroma2.dicii.ispw.mylib.engineering.bean.BookBean;
import it.uniroma2.dicii.ispw.mylib.engineering.bean.BorrowBean;
import it.uniroma2.dicii.ispw.mylib.engineering.bean.FilterBean;
import it.uniroma2.dicii.ispw.mylib.engineering.dao.MakeReservationDAO;
import it.uniroma2.dicii.ispw.mylib.engineering.exceptions.DAOException;
import it.uniroma2.dicii.ispw.mylib.engineering.factory.DAOFactory;
import it.uniroma2.dicii.ispw.mylib.engineering.singleton.Configurations;
import it.uniroma2.dicii.ispw.mylib.model.Book;
import it.uniroma2.dicii.ispw.mylib.model.Borrow;
import it.uniroma2.dicii.ispw.mylib.model.Costumer;
import it.uniroma2.dicii.ispw.mylib.model.Filter;
import it.uniroma2.dicii.ispw.mylib.other.Printer;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class MakeReservationController {

    private static final Logger log = Logger.getLogger(Configurations.LOGGER_NAME);

    public List<BookBean> searchMethod(FilterBean filterBean) throws DAOException {

        List<Book> searchResults;
        List<BookBean> searchResultsBean = new ArrayList<>();

        Filter filter = new Filter(filterBean.getFilter(), filterBean.getFilterType());

        MakeReservationDAO makeReservationDAO = DAOFactory.getDAOFactory().createMakeReservationDAO();

        try {
            searchResults = makeReservationDAO.searchBooks(filter);
        } catch (DAOException e) {
            log.severe("Error in MakeReservationController (searchMethod): " + e.getMessage());
            Printer.errorPrint("Error borrowing book.");
            throw new DAOException();
        }

        //carico nella lista di ripetizioneInfoBean i risultati della ricerca

        for (Book book: searchResults){
            var result = new BookBean(book.getIsbn(), book.getTitle(), book.getAuthors(), book.getEditor(), book.getYear(), book.getGenres(), book.getAvailability());
            searchResultsBean.add(result);
        }

        return searchResultsBean;

    }

    public void reserveBook(BorrowBean borrowBean, Costumer costumer) {
        //creo model a partire dalla bean
        var book = new Book(borrowBean.getBook().getIsbn(), borrowBean.getBook().getTitle(), borrowBean.getBook().getAuthors(), borrowBean.getBook().getEditor(), Short.valueOf(borrowBean.getBook().getPubYear()), borrowBean.getBook().getGenres());
        var borrow = new Borrow(book, borrowBean.getCostumer());

        //passo model alla dao che si connette al db, seleziona la prima copia disponibile del libro desiderato,
        // inserisce un borrow nel db e restituisce un model avente le info della copia selezionata*/

        try{
            MakeReservationDAO reservationDAO = DAOFactory.getDAOFactory().createMakeReservationDAO();
            reservationDAO.reserveBook(borrow, costumer);
            costumer.addPedingBorrows(borrow);
        } catch (DAOException e){
            log.severe("Error in MakeReservationController (reserveBook): " + e.getMessage());
            Printer.errorPrint("Error borrowing book.");
        }

    }
}
