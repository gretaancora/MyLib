package it.uniroma2.dicii.ispw.librarymanagmentsystem.controller;

import it.uniroma2.dicii.ispw.librarymanagmentsystem.engineering.bean.BookBean;
import it.uniroma2.dicii.ispw.librarymanagmentsystem.engineering.bean.BorrowBean;
import it.uniroma2.dicii.ispw.librarymanagmentsystem.engineering.bean.FilterBean;
import it.uniroma2.dicii.ispw.librarymanagmentsystem.engineering.dao.MakeReservationDAO;
import it.uniroma2.dicii.ispw.librarymanagmentsystem.engineering.exceptions.DAOException;
import it.uniroma2.dicii.ispw.librarymanagmentsystem.model.Book;
import it.uniroma2.dicii.ispw.librarymanagmentsystem.model.Borrow;
import it.uniroma2.dicii.ispw.librarymanagmentsystem.model.Filter;
import it.uniroma2.dicii.ispw.librarymanagmentsystem.other.Printer;
import it.uniroma2.dicii.ispw.librarymanagmentsystem.view.gui.controller.MakeReservationGUI;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MakeReservationController {

    public List<BookBean> searchMethod(FilterBean filterBean) {

        List<Book> searchResults;
        List<BookBean> searchResultsBean = new ArrayList<>();

        Filter filter = new Filter(filterBean.getFilter(), filterBean.getFilterType());

        var makeReservationDAO = new MakeReservationDAO();

        searchResults = makeReservationDAO.searchBooks(filter);

        //carico nella lista di ripetizioneInfoBean i risultati della ricerca

        for (Book book: searchResults){
            var result = new BookBean(book.getISBN(), book.getTitle(), book.getAuthors(), book.getEditor(), book.getYear(), book.getGenres(), book.getAvailability());
            searchResultsBean.add(result);
        }
        return searchResultsBean;

    }

    public void reserveBook(BorrowBean borrowBean) {
        //creo model a partire dalla bean
        var book = new Book(borrowBean.getBook().getISBN(), borrowBean.getBook().getTitle(), borrowBean.getBook().getAuthors(), borrowBean.getBook().getEditor(), Short.valueOf(borrowBean.getBook().getPubYear()), borrowBean.getBook().getGenres());
        var borrow = new Borrow(book, borrowBean.getCostumer());

        //passo model alla dao che si connette al db, seleziona la prima copia disponibile del libro desiderato,
        // inserisce un borrow nel db e restituisce un model avente le info della copia selezionata*/

        try{
            var reservationDAO = new MakeReservationDAO();
            reservationDAO.reserveBook(borrow);

            /*RichiesteArrivateCollection.getInstance().aggiungiRichiesta(prenotazioneModel); //pattern Observer*/

        } catch (DAOException e){
            Printer.errorPrint("Error: " + e.getMessage());

        }

    }
}
