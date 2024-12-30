package it.uniroma2.dicii.ispw.mylib.model;

public class BookCopy {
    private String ISBN;
    private short copyNum;
    private boolean availability;
    private String position;

    public BookCopy(String ISBN, short copyNum, boolean availability, String position) {
        this.ISBN = ISBN;
        this.copyNum = copyNum;
        this.availability = availability;
        this.position = position;
    }

    public String getISBN() {return this.ISBN;}
    public short getCopyNum() {return this.copyNum;}

    public void setAvailability(boolean b) {
        this.availability = b;
    }
}
