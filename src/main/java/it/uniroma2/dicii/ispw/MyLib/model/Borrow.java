package it.uniroma2.dicii.ispw.MyLib.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

public class Borrow {
    private Book book;
    private short copy;
    private String costumer;
    private LocalDate inDate;
    private LocalDate endDate;
    private LocalDate restDate;
    private float fine;
    private LocalDateTime inReq;
    private String position;

    public Borrow(Book book, short copy, String costumer, LocalDate inDate, LocalDate endDate, LocalDate restDate, float fine){
        this.book = book;
        this.copy = copy;
        this. costumer = costumer;
        this.inDate = inDate;
        this.endDate = endDate;
        this.restDate = restDate;
        this.fine = fine;
    }

    public Borrow(Book book, short copy, LocalDate inDate, LocalDate endDate, LocalDate restDate){
        this.book = book;
        this.copy = copy;
        this.inDate = inDate;
        this.endDate = endDate;
        this.restDate = restDate;
    }

    public Borrow(Book book, short copy, LocalDate inDate, LocalDate endDate, float fine){
        this.book = book;
        this.copy = copy;
        this.inDate = inDate;
        this.endDate = endDate;
        this.fine = fine;
    }

    public Borrow(Book book, short copy, LocalDate inDate, LocalDate endDate){
        this.book = book;
        this.copy = copy;
        this.inDate = inDate;
        this.endDate = endDate;
    }

    public Borrow(Book book, String costumer){
        this.book = book;
        this.costumer = costumer;
    }

    public Borrow(Book book, short copy){
        this.book = book;
        this.copy = copy;
    }

    public Borrow(Book book, String costumer, short copy){
        this.book = book;
        this.costumer = costumer;
        this.copy = copy;
    }

    public Borrow(Book book, String costumer, short copy, LocalDateTime inReq){
        this.book = book;
        this.costumer = costumer;
        this.copy = copy;
        this.inReq = inReq;
    }

    public Borrow(Book book, String costumer, short copy, LocalDateTime inReq, String position){
        this.book = book;
        this.costumer = costumer;
        this.copy = copy;
        this.inReq = inReq;
        this.position = position;
    }

    //per manage borrow in memory mode
    public Borrow(Book book, String costumer, short copy, LocalDateTime inReq, String position, LocalDate inDate, LocalDate endDate){
        this.book = book;
        this.costumer = costumer;
        this.copy = copy;
        this.inReq = inReq;
        this.position = position;
        this.inDate = inDate;
        this.endDate = endDate;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(this.book).append(" ").append(this.copy).append(" ").append(this.costumer).append(" ").append(this.inDate == null ? "" : this.inDate).append(" ").append(this.endDate == null ? "" : this.endDate).append(" ").append(this.restDate == null ? "" : this.restDate).append(" ").append(this.fine).append('\n');
        return sb.toString();
    }

    public static float calculateFine(LocalDate endDate, LocalDate restDate) {
        // Calcola i giorni di ritardo
        long daysLate = ChronoUnit.DAYS.between(endDate, restDate);

        // Se non ci sono giorni di ritardo, la penale è 0
        if (daysLate <= 0) {
            return 0;
        }

        // Calcola la penale: 0.50 euro per giorno di ritardo
        double fine = daysLate * 0.50;

        // Applica il tetto massimo di 30 euro
        return (float) Math.min(fine, 30.0);
    }

    public Book getBook() {return this.book;}
    public String getCostumer() {return this.costumer;}
    public short getCopy() {return this.copy;}
    public LocalDateTime getInReq() {return this.inReq;}
    public String getPosition() {return this.position;}

}
