package it.uniroma2.dicii.ispw.mylib.engineering.dao;

import it.uniroma2.dicii.ispw.mylib.engineering.exceptions.NoAvailableCopy;
import it.uniroma2.dicii.ispw.mylib.model.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class MakeReservationInMemoryDAO implements MakeReservationDAO{
    private static List<Book> books = new ArrayList<>();
    private static List<BookCopy> copies = new ArrayList<>();
    private List<Book> bookList = new ArrayList<>();

    public MakeReservationInMemoryDAO() {
        books.add(new Book("9788806220457", "Delitto e castigo", "Fëdor Dostoevskij", "Einaudi", (short) 2014, "romanzo", (short) 1, (short) 1));
        copies.add(new BookCopy("9788806220457", (short) 1, true, "NAR-A-1-1"));
    }

    public List<Book> getBookList() {return this.bookList;}

    @Override
    public List<Book> searchBooks(Filter filter) {

        if (filter.getFilterType().equalsIgnoreCase("author")) {
            searchByAuthor(filter.getFlt());
        } else if (filter.getFilterType().equalsIgnoreCase("title")) {
            searchByTitle(filter.getFlt());
        } else {
            searchByAllFields(filter.getFlt());
        }

        return getBookList();
    }

    private void searchByAllFields(String flt) {
        for (Book book : books) {
            if(book.getAuthors().contains(flt) || book.getTitle().equalsIgnoreCase(flt) || book.getGenres().equalsIgnoreCase(flt) || book.getIsbn().equalsIgnoreCase(flt)){
                var b = new Book(book.getIsbn(), book.getTitle(), book.getAuthors(), book.getEditor(), book.getPubYear(), book.getGenres(), book.getNumAvailableCopies() != 0);
                this.bookList.add(b);
            }
        }
    }

    private void searchByTitle(String title) {
        for (Book book : books) {
            if(book.getTitle().equalsIgnoreCase(title)) {
                var b = new Book(book.getIsbn(), book.getTitle(), book.getAuthors(), book.getEditor(), book.getPubYear(), book.getGenres(), book.getNumAvailableCopies() != 0);
                this.bookList.add(b);
            }
        }
    }

    private void searchByAuthor(String author) {
        for (Book book : books) {
            if(book.getAuthors().contains(author)) {
                var b = new Book(book.getIsbn(), book.getTitle(), book.getAuthors(), book.getEditor(), book.getPubYear(), book.getGenres(), book.getNumAvailableCopies() != 0);
                this.bookList.add(b);
            }
        }
    }

    @Override
    public void reserveBook(Borrow borrow, Costumer costumer) throws NoAvailableCopy {

        int choice = 0;
        for (BookCopy bookCopy : copies) {
            if(bookCopy.getIsbn().equals(borrow.getBook().getIsbn()) && bookCopy.getAvailability()) {
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
