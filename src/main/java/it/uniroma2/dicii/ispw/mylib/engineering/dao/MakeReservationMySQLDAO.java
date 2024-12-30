package it.uniroma2.dicii.ispw.mylib.engineering.dao;

import it.uniroma2.dicii.ispw.mylib.engineering.exceptions.DAOException;
import it.uniroma2.dicii.ispw.mylib.engineering.query.BorrowQuery;
import it.uniroma2.dicii.ispw.mylib.engineering.query.SearchQuery;
import it.uniroma2.dicii.ispw.mylib.engineering.singleton.Configurations;
import it.uniroma2.dicii.ispw.mylib.model.Book;
import it.uniroma2.dicii.ispw.mylib.model.Borrow;
import it.uniroma2.dicii.ispw.mylib.model.Costumer;
import it.uniroma2.dicii.ispw.mylib.model.Filter;
import it.uniroma2.dicii.ispw.mylib.engineering.singleton.Connector;
import it.uniroma2.dicii.ispw.mylib.other.Printer;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class MakeReservationMySQLDAO implements MakeReservationDAO{

    private static final Logger logger = Logger.getLogger(Configurations.LOGGER_NAME);

    public List<Book> searchBooks(Filter filter) throws DAOException {
        ResultSet rs = null;
        List<Book> books = new ArrayList<>();

        //controllo del filterType e ricerca associata
        if (filter.getFilterType().equalsIgnoreCase("author")) {
            try {
                rs = SearchQuery.searchBookByAuthor(Connector.getConnection(), filter.getFlt());
            } catch (SQLException e) {
                handleDAOException(e);
            }
        } else if (filter.getFilterType().equalsIgnoreCase("title")) {
            try {
                rs = SearchQuery.searchBookByTitle(Connector.getConnection(), filter.getFlt());
            } catch (SQLException e) {
                handleDAOException(e);
            }
        } else {
            try {
                rs = SearchQuery.searchBookByAllFields(Connector.getConnection(), filter.getFlt());
            } catch (SQLException e) {
                handleDAOException(e);
            }
        }

        try {
            while (rs.next()) {
                var book = new Book(rs.getString("ISBN"), rs.getString("title"), rs.getString("authors"), rs.getString("editor"), rs.getShort("pubYear"), rs.getString("genres"), rs.getShort("numAvailableCopies") != 0);
                books.add(book);
            }
        } catch (SQLException e) {
            handleDAOException(e);
        }

        return books;
    }


    public void reserveBook(Borrow borrow, Costumer costumer) throws DAOException {
        Borrow borrowInfo;
        Connection conn = null;

        try {
            conn = Connector.getConnection();

            conn.setAutoCommit(false);

            ResultSet rs = BorrowQuery.searchBookCopy(Connector.getConnection(), borrow.getBook().getIsbn());

            /*prendi la copia ottenuta, aggiungi numero di copia a book, aggiungi borrow in persistenza
             (ricordati di modificare il numero di copie available e lo stato della copia prestata),
              restituisci le info del borrow per farle vedere all'utente*/

            if (rs.next()) {
                borrowInfo = new Borrow(borrow.getBook(), borrow.getCostumer(), rs.getShort("copyNum"), LocalDateTime.now());

                int rs1 = BorrowQuery.addBorrow(Connector.getConnection(), borrowInfo.getBook().getIsbn(), borrowInfo.getCopy(), borrowInfo.getCostumer(), borrowInfo.getInReq());
                if(rs1==0){
                    throw new SQLException();
                }

                int rs2 = BorrowQuery.updateStatusCopy(Connector.getConnection(), borrowInfo.getBook().getIsbn(), borrowInfo.getCopy());
                if(rs2==0){
                    throw new SQLException();
                }

                int rs3 = BorrowQuery.updateNumAvailCopies(Connector.getConnection(), borrowInfo.getBook().getIsbn());
                if(rs3==0){
                    throw new SQLException();
                }

                conn.commit();

                costumer.addPedingBorrows(borrowInfo);
            }

        } catch (SQLException e) {

            if (conn != null) {
                try {
                    conn.rollback();
                    handleDAOException(e);
                } catch (SQLException r) {
                    handleDAOException(r);
                }
            }

            handleDAOException(e);

        } finally {

            if (conn != null) {
                try {
                    conn.setAutoCommit(true);

                } catch (SQLException e) {
                    handleDAOException(e);
                }
            }

        }
    }


    private void handleDAOException(Exception e) throws DAOException {
        logger.severe(e.getMessage());
        Printer.errorPrint("Error occurred making the reservation.");
        throw new DAOException(e.getMessage());
    }
}