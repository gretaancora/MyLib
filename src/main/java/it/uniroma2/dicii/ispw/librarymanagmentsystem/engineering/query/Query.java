package it.uniroma2.dicii.ispw.librarymanagmentsystem.engineering.query;

public class Query {

    Query(){}

    /*--------------------Login Queries-----------------------------*/

    //Query per ottenere le info dell'utente nel db
    public static final String GET_USER = "SELECT * FROM user WHERE email = ?";

    //query per il load delle info del costumer
    public static final String GET_COSTUMER = "SELECT * FROM costumer WHERE email = ?";

    //query per il load delle info del librarian
    public static final String GET_LIBRARIAN = "SELECT * FROM librarian WHERE email = ?";

    //query per ottenere i prestiti di un costumer
    public static final String GET_BORROWS = """
            SELECT 
                b.ISBN,
                b.title,
                b.editor,
                b.pubYear,
                GROUP_CONCAT(DISTINCT CONCAT(ba.name, ' ', ba.surname) SEPARATOR ', ') AS authors,
                GROUP_CONCAT(DISTINCT bg.genre SEPARATOR ', ') AS genres,
                bo.copyNum,
                bo.inDate,
                bo.endDate,
                bo.restDate,
                bo.fine,
                bo.state
            FROM 
                borrow bo
            JOIN 
                book b ON bo.ISBN = b.ISBN
            LEFT JOIN 
                bookauthor ba ON b.ISBN = ba.book
            LEFT JOIN 
                bookgenre bg ON b.ISBN = bg.book
            WHERE 
                bo.costumer = ?
            GROUP BY 
                b.ISBN, bo.copyNum, bo.inDate, bo.endDate, bo.restDate, bo.fine, bo.state
            ORDER BY 
                bo.state, bo.inDate DESC;
        """;

    /*--------------------Register Queries-------------------*/

    //Query per verificare la presenza della mail nel db
    public static final String SEARCH_EMAIL = "SELECT * FROM user WHERE email = ?";

    //query per registrare il costumer sia nella tabella user che costumer
    public static final String REGISTER_COSTUMER = "INSERT INTO user (email, password, type) VALUES (?,?,'costumer');" +
            "INSERT INTO costumer(email, name, surname, memDate, memStatus) VALUES (?,?,?,?,'active');";


    /*--------------------Search Queries Costumer-------------------*/
    public static final String SEARCH_BOOK_BY_AUTHOR = """
                SELECT 
                    b.ISBN,
                    b.title,
                    b.editor,
                    b.pubYear,
                    b.numAvailableCopies,
                    GROUP_CONCAT(DISTINCT CONCAT(ba.name, ' ', ba.surname) SEPARATOR ', ') AS authors,
                    GROUP_CONCAT(DISTINCT bg.genre SEPARATOR ', ') AS genres
                FROM 
                    book b
                LEFT JOIN 
                    bookauthor ba ON b.ISBN = ba.book
                LEFT JOIN 
                    bookgenre bg ON b.ISBN = bg.book
                WHERE 
                    ba.name = ? AND ba.surname = ?
                GROUP BY 
                    b.ISBN, b.title, b.editor, b.pubYear, b.numAvailableCopies;
                """;

    public static final String SEARCH_BOOK_BY_TITLE = """
                SELECT 
                    b.ISBN,
                    b.title,
                    b.editor,
                    b.pubYear,
                    b.numAvailableCopies,
                    GROUP_CONCAT(DISTINCT CONCAT(ba.name, ' ', ba.surname) SEPARATOR ', ') AS authors,
                    GROUP_CONCAT(DISTINCT bg.genre SEPARATOR ', ') AS genres
                FROM 
                    book b
                LEFT JOIN 
                    bookauthor ba ON b.ISBN = ba.book
                LEFT JOIN 
                    bookgenre bg ON b.ISBN = bg.book
                WHERE 
                    b.title = ?
                GROUP BY 
                    b.ISBN, b.title, b.editor, b.pubYear, b.numAvailableCopies;
                """;

    public static final String SEARCH_BOOK_ALL_FIELDS = """
                SELECT 
                    b.ISBN,
                    b.title,
                    b.editor,
                    b.pubYear,
                    b.numAvailableCopies,
                    GROUP_CONCAT(DISTINCT CONCAT(ba.name, ' ', ba.surname) SEPARATOR ', ') AS authors,
                    GROUP_CONCAT(DISTINCT bg.genre SEPARATOR ', ') AS genres,
                    MATCH(b.title, b.editor) AGAINST(?) AS relevance
                FROM 
                    book b
                LEFT JOIN 
                    bookauthor ba ON b.ISBN = ba.book
                LEFT JOIN 
                    bookgenre bg ON b.ISBN = bg.book
                WHERE 
                    MATCH(b.title, b.editor) AGAINST(?)
                    OR MATCH(ba.name, ba.surname) AGAINST(? IN BOOLEAN MODE)
                    OR MATCH(bg.genre) AGAINST(? IN BOOLEAN MODE)
                GROUP BY 
                    b.ISBN, b.title, b.editor, b.pubYear, b.numAvailableCopies
                ORDER BY 
                    relevance DESC;
                """;

}
