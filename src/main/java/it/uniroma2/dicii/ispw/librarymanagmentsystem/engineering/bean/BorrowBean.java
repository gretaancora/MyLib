package it.uniroma2.dicii.ispw.librarymanagmentsystem.engineering.bean;

import it.uniroma2.dicii.ispw.librarymanagmentsystem.model.Book;
import it.uniroma2.dicii.ispw.librarymanagmentsystem.model.Costumer;

import java.sql.Timestamp;
import java.time.LocalDate;

public class BorrowBean {
    private BookBean book;
    private short copy;
    private String costumer;
    private Timestamp inReq;
    private LocalDate inDate;
    private LocalDate endDate;
    private LocalDate restDate;
    private float fine;
    private String position;


    //pending borrow
    public BorrowBean(BookBean book, String costumer){
        this.book = book;
        this.costumer = costumer;
        this.fine = 0;
    }
    public BorrowBean(BookBean book, String costumer, short copy){
        this.book = book;
        this.costumer = costumer;
        this.copy = copy;
        this.fine = 0;
    }

    public BorrowBean(BookBean book, String costumer, short copy, Timestamp inReq, String position){
        this.book = book;
        this.costumer = costumer;
        this.copy = copy;
        this.inReq = inReq;
        this.position = position;
    }

    public BookBean getBook() {return this.book;}
    public String getCostumer() {return this.costumer;}
    public short getCopy() {return this.copy;}
    public Timestamp getInReq() {return this.inReq;}
    public String getPosition() {return this.position;}
}
