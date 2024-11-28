package it.uniroma2.dicii.ispw.librarymanagmentsystem.model;

public class Book {
    private String ISBN;
    private String title;
    private String authors;
    private String editor;
    private short pubYear;
    private String genres;
    private boolean availability;

    //per ricerca
    public Book(String ISBN, String title, String authors, String editor, short pubYear, String genres, boolean availability){
        this.ISBN =ISBN;
        this.title = title;
        this.authors = authors;
        this.editor = editor;
        this.pubYear = pubYear;
        this.genres = genres;
        this.availability = availability;
    }

    //per borrow
    public Book(String ISBN, String title, String authors, String editor, short pubYear, String genres){
        this.ISBN =ISBN;
        this.title = title;
        this.authors = authors;
        this.editor = editor;
        this.pubYear = pubYear;
        this.genres = genres;
    }

    public Book(String ISBN) {this.ISBN = ISBN;}

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(this.ISBN).append(" ").append(this.title).append(" ").append(this.authors).append(" ").append(this.editor).append(" ").append(this.pubYear).append(" ").append(this.genres).append(" ").append(this.availability==true? "available" : "not available").append('\n');
        return sb.toString();
    }

    public String getISBN() {return this.ISBN;}
    public String getTitle() {return this.title;}
    public String getAuthors() {return this.authors;}
    public String getEditor() {return this.editor;}
    public String getYear() {return String.valueOf(this.pubYear);}
    public String getGenres() {return this.genres;}
    public String getAvailability() {return this.availability==true ? "available" : "not available";}
}
