package it.uniroma2.dicii.ispw.mylib.engineering.dao;

import it.uniroma2.dicii.ispw.mylib.engineering.exceptions.DAOException;
import it.uniroma2.dicii.ispw.mylib.engineering.exceptions.NoAvailableCopy;
import it.uniroma2.dicii.ispw.mylib.model.Book;
import it.uniroma2.dicii.ispw.mylib.model.Borrow;
import it.uniroma2.dicii.ispw.mylib.model.Costumer;
import it.uniroma2.dicii.ispw.mylib.model.Filter;

import java.util.List;

public interface MakeReservationDAO {
    List<Book> searchBooks(Filter filter) throws DAOException;
    void reserveBook(Borrow borrow, Costumer costumer) throws DAOException, NoAvailableCopy;
}
