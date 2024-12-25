package it.uniroma2.dicii.ispw.MyLib.controller;

import it.uniroma2.dicii.ispw.MyLib.engineering.bean.BookBean;
import it.uniroma2.dicii.ispw.MyLib.engineering.bean.BorrowBean;
import it.uniroma2.dicii.ispw.MyLib.engineering.bean.FilterBean;
import it.uniroma2.dicii.ispw.MyLib.engineering.dao.MakeReservationMySQLDAO;
import it.uniroma2.dicii.ispw.MyLib.engineering.exceptions.DAOException;
import it.uniroma2.dicii.ispw.MyLib.model.Book;
import it.uniroma2.dicii.ispw.MyLib.model.Borrow;
import it.uniroma2.dicii.ispw.MyLib.model.Filter;
import it.uniroma2.dicii.ispw.MyLib.other.Printer;

import java.util.ArrayList;
import java.util.List;

public class MakeReservationController {

    public List<BookBean> searchMethod(FilterBean filterBean) throws DAOException {

        List<Book> searchResults;
        List<BookBean> searchResultsBean = new ArrayList<>();

        Filter filter = new Filter(filterBean.getFilter(), filterBean.getFilterType());

        var makeReservationDAO = new MakeReservationMySQLDAO();

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
            var reservationDAO = new MakeReservationMySQLDAO();
            reservationDAO.reserveBook(borrow);

            /*RichiesteArrivateCollection.getInstance().aggiungiRichiesta(prenotazioneModel); //pattern Observer*/

        } catch (DAOException e){
            Printer.errorPrint("Error: " + e.getMessage()); //questo dovrei printarlo sul logger e printare semplicemente un messaggio di errore generico sul terminale
        }

    }
}
