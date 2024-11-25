package it.uniroma2.dicii.ispw.librarymanagmentsystem.model;

public class Book {
    private String ISBN;
    private String title;
    private String authors;
    private String editor;
    private short pubYear;
    private String genres;

    public Book(String ISBN, String title, String authors, String editor, short pubYear, String genres){
        this.ISBN =ISBN;
        this.title = title;
        this.authors = authors;
        this.editor = editor;
        this.pubYear = pubYear;
        this.genres = genres;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(this.ISBN).append(" ").append(this.title).append(" ").append(this.authors).append(" ").append(this.editor).append(" ").append(this.pubYear).append(" ").append(this.genres).append('\n');
        return sb.toString();
    }

}
