package it.uniroma2.dicii.ispw.MyLib.engineering.bean;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class BorrowBean {
    private BookBean book;
    private short copy;
    private String costumer;
    private LocalDateTime inReq;
    private LocalDate inDate;
    private LocalDate endDate;
    private LocalDate restDate;
    private float fine;
    private String position;


    //pending borrow
    public BorrowBean(BookBean book, String costumer){
        this.book = book;
        this.costumer = costumer;
    }
    public BorrowBean(BookBean book, String costumer, short copy){
        this.book = book;
        this.costumer = costumer;
        this.copy = copy;
    }

    public BorrowBean(LocalDate inDate, LocalDate endDate){
        this.inDate = inDate;
        this.endDate = endDate;
    }

    public BorrowBean(BookBean book, String costumer, short copy, LocalDateTime inReq, String position){
        this.book = book;
        this.costumer = costumer;
        this.copy = copy;
        this.inReq = inReq;
        this.position = position;
    }

    public BookBean getBook() {return this.book;}
    public String getCostumer() {return this.costumer;}
    public short getCopy() {return this.copy;}
    public LocalDateTime getInReq() {return this.inReq;}
    public String getPosition() {return this.position;}
    public LocalDate getInDate() {return this.inDate;}
    public LocalDate getEndDate() {return this.endDate;}

    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append(this.getCostumer()).append(" ").append(this.getBook().getISBN()).append(" ").append(this.getCopy()).append(" ").append(this.getPosition());
        return sb.toString();
    }
}
