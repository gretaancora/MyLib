package it.uniroma2.dicii.ispw.MyLib.engineering.dao;

import it.uniroma2.dicii.ispw.MyLib.engineering.exceptions.DAOException;
import it.uniroma2.dicii.ispw.MyLib.model.Book;
import it.uniroma2.dicii.ispw.MyLib.model.Borrow;
import it.uniroma2.dicii.ispw.MyLib.model.Filter;

import java.util.ArrayList;
import java.util.List;

public class MakeReservationInMemoryDAO implements MakeReservationDAO{
    @Override
    public List<Book> searchBooks(Filter filter) throws DAOException {
        List<Book> books = new ArrayList<>();
        books.add(new Book(""))
    }

    @Override
    public void reserveBook(Borrow borrow) throws DAOException {

    }
}
