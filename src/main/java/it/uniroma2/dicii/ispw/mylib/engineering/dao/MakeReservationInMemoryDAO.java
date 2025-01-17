package it.uniroma2.dicii.ispw.mylib.engineering.dao;

import it.uniroma2.dicii.ispw.mylib.engineering.exceptions.DAOException;
import it.uniroma2.dicii.ispw.mylib.engineering.exceptions.NoAvailableCopy;
import it.uniroma2.dicii.ispw.mylib.engineering.singleton.Configurations;
import it.uniroma2.dicii.ispw.mylib.model.*;
import it.uniroma2.dicii.ispw.mylib.other.Printer;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class MakeReservationInMemoryDAO implements MakeReservationDAO{
    private static List<Book> books = new ArrayList<>();
    private static List<BookCopy> copies = new ArrayList<>();
    private List<Book> bookList = new ArrayList<>();

    private static final Logger logger = Logger.getLogger(Configurations.LOGGER_NAME);

    static {
        books.add(new Book("9788806220457", "Delitto e castigo", "Fëdor Dostoevskij", "Einaudi", (short) 2014, "romanzo"));
        books.get(0).setNumCopies((short) 1, (short) 1);
        copies.add(new BookCopy("9788806220457", (short) 1, true, "NAR-A-1-1"));
    }

    public List<Book> getBookList() {return this.bookList;}

    @Override
    public List<Book> searchBooks(Filter filter) throws DAOException {
        if (filter.getFlt() == null) {
            showCatalog();
        } else if (filter.getFilterType().equalsIgnoreCase("author")) {
            searchByAuthor(filter.getFlt());
        } else if (filter.getFilterType().equalsIgnoreCase("title")) {
            searchByTitle(filter.getFlt());
        } else {
            searchByAllFields(filter.getFlt());
        }

        return getBookList();
    }

    private void showCatalog() throws DAOException {
        for (Book book : books){
            if(!this.bookList.add(new Book(book.getIsbn(), book.getTitle(), book.getAuthors(), book.getEditor(), book.getPubYear(), book.getGenres(), book.getNumAvailableCopies() != 0))){
                throw new DAOException("Error in MakeReservationDAO: adding book in showCatalog()");
            }
        }
    }

    private void searchByAllFields(String flt) throws DAOException {
        for (Book book : books) {
            if(book.getAuthors().contains(flt) || book.getTitle().equalsIgnoreCase(flt) || book.getGenres().equalsIgnoreCase(flt) || book.getIsbn().equalsIgnoreCase(flt)){
                var b = new Book(book.getIsbn(), book.getTitle(), book.getAuthors(), book.getEditor(), book.getPubYear(), book.getGenres(), book.getNumAvailableCopies() != 0);
                if(!this.bookList.add(b)){
                    throw new DAOException("Error in MakeReservationDAO: adding book in searchByAllFields()");
                }
            }
        }
    }

    private void searchByTitle(String title) throws DAOException {
        for (Book book : books) {
            if(book.getTitle().equalsIgnoreCase(title)) {
                var b = new Book(book.getIsbn(), book.getTitle(), book.getAuthors(), book.getEditor(), book.getPubYear(), book.getGenres(), book.getNumAvailableCopies() != 0);
                if(!this.bookList.add(b)){
                    throw new DAOException("Error in MakeReservationDAO: adding book in searchByTitle()");
                }
            }
        }
    }

    private void searchByAuthor(String author) throws DAOException {
        for (Book book : books) {
            if(book.getAuthors().contains(author)) {
                var b = new Book(book.getIsbn(), book.getTitle(), book.getAuthors(), book.getEditor(), book.getPubYear(), book.getGenres(), book.getNumAvailableCopies() != 0);
                if (!this.bookList.add(b)){
                    throw new DAOException("Error in MakeReservationDAO: adding book in searchByAuthor()");
                }
            }
        }
    }

    @Override
    public Borrow reserveBook(Borrow borrow) throws NoAvailableCopy {

        int choice = 0;
        for (BookCopy bookCopy : copies) {
            if(bookCopy.getIsbn().equals(borrow.getBook().getIsbn()) && bookCopy.getAvailability()) {
                borrow = new Borrow(borrow.getBook(), borrow.getCostumer(), bookCopy.getCopyNum(), LocalDateTime.now());
                break;
            }
            choice++;
        }

        BookCopy bookCopy = copies.get(choice);
        bookCopy.setAvailability(false);

        for (Book book : books) {
            if (book.getIsbn().equalsIgnoreCase(bookCopy.getIsbn())){
                try {
                    book.reduceNumAvailCopies();
                } catch (NoAvailableCopy e) {
                    logger.severe("Error in MakeReservationInMemoryDAO: " + e.getMessage());
                    Printer.errorPrint("Error searching for an available copy of the book.");
                    throw new NoAvailableCopy();
                }
                break;
            }
        }

        return borrow;

    }
}
