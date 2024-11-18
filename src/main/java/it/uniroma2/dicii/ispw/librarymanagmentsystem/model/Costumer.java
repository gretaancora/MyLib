package it.uniroma2.dicii.ispw.librarymanagmentsystem.model;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public class Costumer extends User {
    private LocalDate membership_date;
    private boolean membership_status;
    private Map<String, Book> borrowed_books;
    private Map<String, Book> reserved_books;
    private Map<String, Book> overdue_books;
    private float fines;


    public Costumer(String email, String name, String surname, LocalDate subDate) {
        super(email, name, surname);
        this.membership_date = subDate;
        this.membership_status = true;
        this.borrowed_books = null;
        this.reserved_books = null;
        this.overdue_books = null;
        this.fines = 0;
    }

    public Costumer(String email, String password, String name, String surname, LocalDate subDate) {
        super(email, name, surname, password);
        this.membership_date = subDate;
        this.membership_status = true;
        this.borrowed_books = null;
        this.reserved_books = null;
        this.overdue_books = null;
        this.fines = 0;
    }

    public Costumer(String email, String name, String surname) {
        super(email, name, surname);
        this.membership_date = null;
        this.membership_status = true;
        this.borrowed_books = null;
        this.reserved_books = null;
        this.overdue_books = null;
        this.fines = 0;
    }

    public Costumer(String email, String password, String name, String surname) {
        super(email, name, surname, password);
        this.membership_date = null;
        this.membership_status = true;
        this.borrowed_books = null;
        this.reserved_books = null;
        this.overdue_books = null;
        this.fines = 0;
    }

}
