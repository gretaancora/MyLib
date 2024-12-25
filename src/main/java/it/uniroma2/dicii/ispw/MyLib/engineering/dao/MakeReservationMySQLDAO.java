package it.uniroma2.dicii.ispw.MyLib.engineering.dao;

import it.uniroma2.dicii.ispw.MyLib.engineering.exceptions.DAOException;
import it.uniroma2.dicii.ispw.MyLib.engineering.query.BorrowQuery;
import it.uniroma2.dicii.ispw.MyLib.engineering.query.SearchQuery;
import it.uniroma2.dicii.ispw.MyLib.model.Book;
import it.uniroma2.dicii.ispw.MyLib.model.Borrow;
import it.uniroma2.dicii.ispw.MyLib.model.Filter;
import it.uniroma2.dicii.ispw.MyLib.engineering.singleton.Connector;
import it.uniroma2.dicii.ispw.MyLib.other.Printer;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class MakeReservationMySQLDAO implements MakeReservationDAO{

    private static final Logger logger = Logger.getLogger(MakeReservationMySQLDAO.class.getName());

    public List<Book> searchBooks(Filter filter) throws DAOException {
        ResultSet rs = null;
        List<Book> books = new ArrayList<>();

        //controllo del filterType e ricerca associata
        if (filter.getFilterType().equalsIgnoreCase("author")) {
            try {
                rs = SearchQuery.searchBookByAuthor(Connector.getConnection(), filter.getFilter());
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
            try {
                rs = SearchQuery.searchBookByAllFields(Connector.getConnection(), filter.getFilter());
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

    private void handleDAOException(Exception e) throws DAOException {
        logger.severe(e.getMessage());
        Printer.errorPrint("Error occurred making the reservation.");
        throw new DAOException(e.getMessage());
    }

    public void reserveBook(Borrow borrow) throws DAOException {
        Borrow borrowInfo;
        Connection conn = null;

        try {
            conn = Connector.getConnection();

            conn.setAutoCommit(false);

            ResultSet rs = BorrowQuery.searchBookCopy(Connector.getConnection(), borrow.getBook().getISBN());

            /*prendi la copia ottenuta, aggiungi numero di copia a book, aggiungi borrow in persistenza
             (ricordati di modificare il numero di copie available e lo stato della copia prestata),
              restituisci le info del borrow per farle vedere all'utente*/

            if (rs.next()) {
                borrowInfo = new Borrow(borrow.getBook(), borrow.getCostumer(), rs.getShort("copyNum"));

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
            }

        } catch (SQLException e) {
            e.printStackTrace();
            if (conn != null) {
                try {
                    conn.rollback();
                    System.out.println("Error occurred during borrow transaction: " + e.getMessage());
                } catch (SQLException r) {
                    r.printStackTrace();
                    throw new DAOException("Error in MakeReservationDAO (during rollback): " + e.getMessage());
                }
            }

            throw new DAOException("Error in MakeReservationDAO: " + e.getMessage());

        } finally {

            if (conn != null) {
                try {
                    conn.setAutoCommit(true);

                } catch (SQLException closeEx) {
                    closeEx.printStackTrace();
                }
            }

        }
    }
}