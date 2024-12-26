package it.uniroma2.dicii.ispw.MyLib.engineering.dao;

import it.uniroma2.dicii.ispw.MyLib.engineering.exceptions.DAOException;
import it.uniroma2.dicii.ispw.MyLib.model.Book;
import it.uniroma2.dicii.ispw.MyLib.model.Borrow;
import it.uniroma2.dicii.ispw.MyLib.model.Costumer;
import it.uniroma2.dicii.ispw.MyLib.model.Filter;

import java.util.List;

public interface MakeReservationDAO {
    List<Book> searchBooks(Filter filter) throws DAOException;
    void reserveBook(Borrow borrow, Costumer costumer) throws DAOException;
}
