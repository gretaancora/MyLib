package it.uniroma2.dicii.ispw.mylib.engineering.bean;

public class BookBean {
    private String isbn;
    private String title;
    private String authors;
    private String editor;
    private String pubYear;
    private String genres;
    private String availability;

    //per ricerca
    public BookBean(String isbn, String title, String authors, String editor, String pubYear, String genres, String availability){
        this.isbn = isbn;
        this.title = title;
        this.authors = authors;
        this.editor = editor;
        this.pubYear = pubYear;
        this.genres = genres;
        this.availability = availability;
    }

    //per borrow
    public BookBean(String isbn, String title, String authors, String editor, String pubYear, String genres){
        this.isbn = isbn;
        this.title = title;
        this.authors = authors;
        this.editor = editor;
        this.pubYear = pubYear;
        this.genres = genres;
    }

    public BookBean(String isbn) {
        this.isbn = isbn;
    }

    public String getIsbn() {return this.isbn;}
    public String getTitle() {return this.title;}
    public String getAuthors() {return this.authors;}
    public String getEditor() {return this.editor;}
    public String getGenres() {return this.genres;}
    public String getPubYear() {return this.pubYear;}
    public String getAvailability() {return this.availability;}

    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append(this.getIsbn()).append(" ").append(this.getTitle()).append(" ").append(this.getAuthors()).append(" ").append(this.getEditor()).append(" ").append(this.getPubYear()).append(" ").append(this.getGenres()).append(" ").append(this.getAvailability());
        return sb.toString();
    }

}
