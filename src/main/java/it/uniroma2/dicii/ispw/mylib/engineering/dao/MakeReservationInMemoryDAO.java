package it.uniroma2.dicii.ispw.mylib.engineering.dao;

import it.uniroma2.dicii.ispw.mylib.model.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class MakeReservationInMemoryDAO implements MakeReservationDAO{
    private static List<Book> books = new ArrayList<>();
    private static List<BookCopy> copies = new ArrayList<>();

    public MakeReservationInMemoryDAO() {
        books.add(new Book("9788806220457", "Delitto e castigo", "Fëdor Dostoevskij", "Einaudi", (short) 2014, "romanzo", (short) 1, (short) 1));
        copies.add(new BookCopy("9788806220457", (short) 1, true, "NAR-A-1-1"));
    }

    @Override
    public List<Book> searchBooks(Filter filter) {
        var booksList = new ArrayList<Book>();

        if (filter.getFilterType().equalsIgnoreCase("author")) {
            for (Book book : books) {
                if(book.getAuthors().contains(filter.getFlt())) {
                    var b = new Book(book.getIsbn(), book.getTitle(), book.getAuthors(), book.getEditor(), book.getPubYear(), book.getGenres(), book.getNumAvailableCopies() != 0);
                    booksList.add(b);
                }
            }
        } else if (filter.getFilterType().equalsIgnoreCase("title")) {
            for (Book book : books) {
                if(book.getTitle().equalsIgnoreCase(filter.getFlt())) {
                    var b = new Book(book.getIsbn(), book.getTitle(), book.getAuthors(), book.getEditor(), book.getPubYear(), book.getGenres(), book.getNumAvailableCopies() != 0);
                    booksList.add(b);
                }
            }
        } else {
            for (Book book : books) {
                if(book.getAuthors().contains(filter.getFlt()) || book.getTitle().equalsIgnoreCase(filter.getFlt()) || book.getGenres().equalsIgnoreCase(filter.getFlt()) || book.getIsbn().equalsIgnoreCase(filter.getFlt())){
                    var b = new Book(book.getIsbn(), book.getTitle(), book.getAuthors(), book.getEditor(), book.getPubYear(), book.getGenres(), book.getNumAvailableCopies() != 0);
                    booksList.add(b);
                }
            }
        }

        return booksList;
    }

    @Override
    public void reserveBook(Borrow borrow, Costumer costumer) {

        int choice = 0;
        for (BookCopy bookCopy : copies) {
            if(bookCopy.getIsbn().equals(borrow.getBook().getIsbn())) {
                borrow = new Borrow(borrow.getBook(), borrow.getCostumer(), bookCopy.getCopyNum(), LocalDateTime.now());
                costumer.addPedingBorrows(borrow);
                break;
            }
            choice++;
        }

        BookCopy bookCopy = copies.get(choice);
        bookCopy.setAvailability(false);

        for (Book book : books) {
            if (book.getIsbn().equalsIgnoreCase(bookCopy.getIsbn())){
                book.reduceNumAvailCopies();
            }
        }

    }
}
