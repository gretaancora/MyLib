package it.uniroma2.dicii.ispw.librarymanagmentsystem.engineering.bean;

public class BookBean {
    private String ISBN;
    private String title;
    private String authors;
    private String editor;
    private String pubYear;
    private String genres;
    private String availability;

    //per ricerca
    public BookBean(String ISBN, String title, String authors, String editor, String pubYear, String genres, String availability){
        this.ISBN =ISBN;
        this.title = title;
        this.authors = authors;
        this.editor = editor;
        this.pubYear = pubYear;
        this.genres = genres;
        this.availability = availability;
    }

    //per borrow
    public BookBean(String ISBN, String title, String authors, String editor, String pubYear, String genres){
        this.ISBN =ISBN;
        this.title = title;
        this.authors = authors;
        this.editor = editor;
        this.pubYear = pubYear;
        this.genres = genres;
    }

    public BookBean(String isbn) {
        this.ISBN = isbn;
    }

    public String getISBN() {return this.ISBN;}
    public String getTitle() {return this.title;}
    public String getAuthors() {return this.authors;}
    public String getEditor() {return this.editor;}
    public String getGenres() {return this.genres;}
    public String getPubYear() {return this.pubYear;}
    public String getAvailability() {return this.availability;}

}
