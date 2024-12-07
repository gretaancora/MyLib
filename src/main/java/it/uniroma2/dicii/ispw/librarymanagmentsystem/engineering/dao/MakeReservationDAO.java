package it.uniroma2.dicii.ispw.librarymanagmentsystem.engineering.dao;

import it.uniroma2.dicii.ispw.librarymanagmentsystem.engineering.exceptions.DAOException;
import it.uniroma2.dicii.ispw.librarymanagmentsystem.engineering.query.BorrowQuery;
import it.uniroma2.dicii.ispw.librarymanagmentsystem.engineering.query.SearchQuery;
import it.uniroma2.dicii.ispw.librarymanagmentsystem.model.Book;
import it.uniroma2.dicii.ispw.librarymanagmentsystem.model.Borrow;
import it.uniroma2.dicii.ispw.librarymanagmentsystem.model.Filter;
import it.uniroma2.dicii.ispw.librarymanagmentsystem.engineering.singleton.Connector;
import it.uniroma2.dicii.ispw.librarymanagmentsystem.other.Printer;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class MakeReservationDAO {
    private static final Logger logger = Logger.getLogger(MakeReservationDAO.class.getName());

    public List<Book> searchBooks(Filter filter) {
        ResultSet rs = null;
        List<Book> books = new ArrayList<>();
        String name;
        String surname;
        String[] tokens;

        //controllo del filterType e ricerca associata
        if (filter.getFilterType()==null) {
            try {
                rs = SearchQuery.searchBookByAllFields(Connector.getConnection(), filter.getFilter());
            } catch (SQLException e) {
                handleDAOException(e);
            }
        } else if (filter.getFilterType().equalsIgnoreCase("title")) {
            try {
                rs = SearchQuery.searchBookByTitle(Connector.getConnection(), filter.getFilter());
            } catch (SQLException e) {
                handleDAOException(e);
            }
        } else {
            tokens = filter.getFilter().split("\\s+");
            name = tokens[0];
            surname = tokens[1];

            try {
                rs = SearchQuery.searchBookByAuthor(Connector.getConnection(), name, surname);
            } catch (SQLException e) {
                handleDAOException(e);
            }
        }

        try {
            while (rs.next()) {
                var book = new Book(rs.getString("ISBN"), rs.getString("title"), rs.getString("authors"), rs.getString("editor"), rs.getShort("pubYear"), rs.getString("genres"), rs.getShort("numAvailableCopies") == 0 ? false : true);
                books.add(book);
            }
        } catch (SQLException e) {
            handleDAOException(e);
        }

        return books;
    }

    private void handleDAOException(Exception e) {
        e.printStackTrace();
        logger.severe(e.getMessage());
        Printer.errorPrint(String.format("MakeReservationDAO: %s", e.getMessage()));
    }

    public void reserveBook(Borrow borrow) throws DAOException {
        Borrow borrowInfo = null;
        Connection conn = null;

        try {
            conn = Connector.getConnection();

            conn.setAutoCommit(false);

            ResultSet rs = BorrowQuery.searchBookCopy(Connector.getConnection(), borrow.getBook().getISBN());

            /*prendi la copia ottenuta, aggiungi numero di copia a book, aggiungi borrow in persistenza
             (ricordati di modificare il numero di copie available e lo stato della copia prestata),
              restituisci le info del borrow per farle vedere all'utente*/

            while (rs.next()) {
                borrowInfo = new Borrow(borrow.getBook(), borrow.getCostumer(), rs.getShort("copyNum"));
            }

            int rs1 = BorrowQuery.addBorrow(Connector.getConnection(), borrowInfo.getBook().getISBN(), borrowInfo.getCopy(), borrowInfo.getCostumer());
            if(rs1==0){
                throw new SQLException();
            }

            int rs2 = BorrowQuery.updateStatusCopy(Connector.getConnection(), borrowInfo.getBook().getISBN(), borrowInfo.getCopy());
            if(rs2==0){
                throw new SQLException();
            }

            int rs3 = BorrowQuery.updateNumAvailCopies(Connector.getConnection(), borrowInfo.getBook().getISBN());
            if(rs3==0){
                throw new SQLException();
            }

            conn.commit();

        } catch (SQLException e) {
            e.printStackTrace();
            if (conn != null) {
                try {
                    conn.rollback();
                    System.out.println("Error occurred during borrow transaction: " + e.getMessage());
                } catch (SQLException rollbackEx) {
                    rollbackEx.printStackTrace();
                    throw new DAOException("Error in MakeReservationDAO (during rollback): " + e.getMessage());
                }
            }

            throw new DAOException("Error in MakeReservationDAO: " + e.getMessage());

        } finally {

            if (conn != null) {
                try {
                    conn.setAutoCommit(true); // Ripristina l'auto-commit
                    conn.close();
                } catch (SQLException closeEx) {
                    closeEx.printStackTrace();
                }
            }

        }
    }
}