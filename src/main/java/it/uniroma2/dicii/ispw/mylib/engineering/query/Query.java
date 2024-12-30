package it.uniroma2.dicii.ispw.mylib.engineering.query;

public class Query {

    private Query(){}

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
                GROUP_CONCAT(DISTINCT CONCAT(ba.author) SEPARATOR ', ') AS authors,
                GROUP_CONCAT(DISTINCT bg.genre SEPARATOR ', ') AS genres,
                bo.copyNum,
                bo.inDate,
                bo.endDate,
                bo.restDate,
                bo.state
            FROM 
                borrow bo
            JOIN 
                book b ON bo.book = b.ISBN
            LEFT JOIN 
                bookauthor ba ON b.ISBN = ba.book
            LEFT JOIN 
                bookgenre bg ON b.ISBN = bg.book
            WHERE 
                bo.costumer = ?
            GROUP BY 
                b.ISBN, bo.copyNum, bo.inDate, bo.endDate, bo.restDate, bo.state
            ORDER BY 
                bo.state, bo.inDate DESC;
        """;

    /*--------------------Register Queries-------------------*/

    //Query per verificare la presenza della mail nel db
    public static final String SEARCH_EMAIL = "SELECT * FROM user WHERE email = ?";

    //query per registrare il costumer sia nella tabella user che costumer
    public static final String REGISTER_USER = "INSERT INTO user (email, password, type) VALUES (?,?,'costumer')";

    public static final String REGISTER_COSTUMER = "INSERT INTO costumer(email, name, surname, memDate, memStatus) VALUES (?,?,?,?,1);";


    /*--------------------Search Queries Costumer-------------------*/
    public static final String SEARCH_BOOK_BY_AUTHOR = """
                SELECT 
                    b.ISBN,
                    b.title,
                    b.editor,
                    b.pubYear,
                    b.numAvailableCopies,
                    GROUP_CONCAT(DISTINCT CONCAT(ba.author) SEPARATOR ', ') AS authors,
                    GROUP_CONCAT(DISTINCT bg.genre SEPARATOR ', ') AS genres
                FROM 
                    book b
                LEFT JOIN 
                    bookauthor ba ON b.ISBN = ba.book
                LEFT JOIN 
                    bookgenre bg ON b.ISBN = bg.book
                WHERE 
                    ba.author = ?
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
                    GROUP_CONCAT(DISTINCT CONCAT(ba.author) SEPARATOR ', ') AS authors,
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
                    GROUP_CONCAT(DISTINCT CONCAT(ba.author) SEPARATOR ', ') AS authors,
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
                    OR MATCH(ba.author) AGAINST(? IN BOOLEAN MODE)
                    OR MATCH(bg.genre) AGAINST(? IN BOOLEAN MODE)
                GROUP BY 
                    b.ISBN, b.title, b.editor, b.pubYear, b.numAvailableCopies
                ORDER BY 
                    relevance DESC;
                """;


    /*--------------------Borrow Queries Costumer-------------------*/
    public static final String SEARCH_BOOK_COPY = """
            SELECT 
                bc.ISBN, 
                bc.copyNum
            FROM 
                bookcopy bc
            JOIN 
                book b ON bc.ISBN = b.ISBN
            WHERE 
                bc.ISBN = ?
                AND bc.availability = 1
            LIMIT 1;
        """;

    public static final String ADD_BORROW = """
            INSERT INTO borrow (costumer, book, copyNum, inReq) 
            VALUES (?, ?, ?, ?);
        """;

    public static final String UPDATE_STATUS_COPY = "UPDATE bookcopy SET availability = ? WHERE ISBN = ? AND copyNum = ?";

    public static final String SET_RESERVATION_DATES = "UPDATE borrow SET inDate = ?, endDate = ? WHERE book = ? AND copyNum = ?";

    public static final String UPDATE_NUM_AVAIL_COPIES = "UPDATE book SET numAvailableCopies = numAvailableCopies - 1 WHERE ISBN = ? AND numAvailableCopies > 0";


    /*--------------------Activate Borrow Queries Librarian-------------------*/
    public static final String UPDATE_STATUS_BORROW = "UPDATE borrow SET state = ? WHERE costumer = ? AND book = ? AND copyNum = ? AND inReq = ?";

    public static final String GET_PENDING_BORROWS = "SELECT costumer, book, bo.copyNum, inReq, position " +
            "FROM borrow AS bo " +
            "JOIN bookcopy AS bc ON bo.book = bc.ISBN AND bo.copyNum = bc.copyNum " +
            "WHERE bo.state = 'pending'";

}
