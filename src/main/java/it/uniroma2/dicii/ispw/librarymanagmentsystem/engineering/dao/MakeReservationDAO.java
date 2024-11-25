package it.uniroma2.dicii.ispw.librarymanagmentsystem.engineering.dao;

import it.uniroma2.dicii.ispw.librarymanagmentsystem.engineering.query.SearchQuery;
import it.uniroma2.dicii.ispw.librarymanagmentsystem.model.Book;
import it.uniroma2.dicii.ispw.librarymanagmentsystem.model.Filter;
import it.uniroma2.dicii.ispw.librarymanagmentsystem.engineering.singleton.Connector;
import it.uniroma2.dicii.ispw.librarymanagmentsystem.other.Printer;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Logger;

public class MakeReservationDAO {
    private static final Logger logger = Logger.getLogger(MakeReservationDAO.class.getName());
    public List<Book> searchBooks(Filter filter) {
        ResultSet rs = null;
        List<Book> books = null;
        String name;
        String surname;
        String[] tokens;

        //controllo del filterType e ricerca associata
        if (filter.getFilterType().equalsIgnoreCase("author")) {
            tokens = filter.getFilter().split("\\s+");
            name = tokens[0];
            surname = tokens[1];

            try{
                rs = SearchQuery.searchBookByAuthor(Connector.getConnection(), name, surname);
            }catch (SQLException e){
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
            while(rs.next()){
                var book = new Book(rs.getString("ISBN"), rs.getString("title"), rs.getString("authors"), rs.getString("editor"), rs.getShort("pubYear"), rs.getString("genres"));
                books.add(book);
            }
        } catch (SQLException e) {
            handleDAOException(e);
        }

        return books;
    }

    private void handleDAOException(Exception e) {
        logger.severe(e.getMessage());
        Printer.errorPrint(String.format("MakeReservationDAO: %s", e.getMessage()));
    }
}
