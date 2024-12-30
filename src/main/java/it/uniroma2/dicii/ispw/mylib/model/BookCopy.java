package it.uniroma2.dicii.ispw.mylib.model;

public class BookCopy {
    private String isbn;
    private short copyNum;
    private boolean availability;
    private String position;

    public BookCopy(String isbn, short copyNum, boolean availability, String position) {
        this.isbn = isbn;
        this.copyNum = copyNum;
        this.availability = availability;
        this.position = position;
    }

    public String getIsbn() {return this.isbn;}
    public short getCopyNum() {return this.copyNum;}

    public void setAvailability(boolean b) {
        this.availability = b;
    }
}
