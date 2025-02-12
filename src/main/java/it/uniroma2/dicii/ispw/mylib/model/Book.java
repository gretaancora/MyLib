package it.uniroma2.dicii.ispw.mylib.model;

import it.uniroma2.dicii.ispw.mylib.engineering.exceptions.NoAvailableCopy;
import it.uniroma2.dicii.ispw.mylib.other.Printer;

public class Book {
    private String isbn;
    private String title;
    private String authors;
    private String editor;
    private short pubYear;
    private String genres;
    private boolean availability;

    //i seguenti attributi mi servono per l'inserimento di un libro (es_ make reservation in memory dao)
    private short numCopies;
    private short numAvailableCopies;

    //per ricerca
    public Book(String isbn, String title, String authors, String editor, short pubYear, String genres, boolean availability){
        this.isbn = isbn;
        this.title = title;
        this.authors = authors;
        this.editor = editor;
        this.pubYear = pubYear;
        this.genres = genres;
        this.availability = availability;
    }

    //per borrow
    public Book(String isbn, String title, String authors, String editor, short pubYear, String genres){
        this.isbn = isbn;
        this.title = title;
        this.authors = authors;
        this.editor = editor;
        this.pubYear = pubYear;
        this.genres = genres;
    }

    public Book(String isbn) {this.isbn = isbn;}


    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(this.isbn).append(" ").append(this.title).append(" ").append(this.authors).append(" ").append(this.editor).append(" ").append(this.pubYear).append(" ").append(this.genres).append(" ").append(this.availability? "available" : "not available").append('\n');
        return sb.toString();
    }

    public String getIsbn() {return this.isbn;}
    public String getTitle() {return this.title;}
    public String getAuthors() {return this.authors;}
    public String getEditor() {return this.editor;}
    public String getYear() {return String.valueOf(this.pubYear);}
    public short getPubYear() {return this.pubYear;}
    public String getGenres() {return this.genres;}
    public String getAvailability() {return this.availability ? "available" : "not available";}
    public short getNumAvailableCopies() {return this.numAvailableCopies;}

    public void reduceNumAvailCopies() throws NoAvailableCopy {
        Printer.println("Num available copies: " + this.numAvailableCopies);
        if (this.numAvailableCopies == 0){
            throw new NoAvailableCopy(getTitle());
        }else{
            this.numAvailableCopies--;
        }
    }

    public void setNumCopies(short numCopies, short numAvailableCopies) {
        if(numAvailableCopies>numCopies){
            this.numCopies = numCopies;
            this.numAvailableCopies = numCopies;
        }else{
            this.numCopies = numCopies;
            this.numAvailableCopies = numAvailableCopies;
        }
    }
}
