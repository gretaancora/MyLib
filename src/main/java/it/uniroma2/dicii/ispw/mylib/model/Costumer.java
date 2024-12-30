package it.uniroma2.dicii.ispw.mylib.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Costumer extends User {
    private LocalDate membership_date;
    private boolean membership_status;
    private List<Borrow> pending_borrows;
    private List<Borrow> active_borrows;
    private List<Borrow> overdue_borrows;
    private List<Borrow> finished_borrows;

    //login
    public Costumer(String email, String name, String surname, LocalDate subDate, boolean membership_status) {
        super(email, name, surname);
        this.membership_date = subDate;
        this.membership_status = membership_status;
        this.pending_borrows = new ArrayList<>();
        this.active_borrows = new ArrayList<>();
        this.overdue_borrows = new ArrayList<>();
        this.finished_borrows = new ArrayList<>();
    }

    //registrazione
    //con data
    public Costumer(String email, String password, String name, String surname, LocalDate subDate) {
        super(email, name, surname, password);
        this.membership_date = subDate;
        this.membership_status = true;
        this.pending_borrows = new ArrayList<>();
        this.active_borrows = new ArrayList<>();
        this.overdue_borrows = new ArrayList<>();
        this.finished_borrows = new ArrayList<>();
    }

    // se data non inserita attivazione odierna
    public Costumer(String email, String password, String name, String surname) {
        super(email, name, surname, password);
        this.membership_date = LocalDate.now();
        this.membership_status = true;
        this.pending_borrows = new ArrayList<>();
        this.active_borrows = new ArrayList<>();
        this.overdue_borrows = new ArrayList<>();
        this.finished_borrows = new ArrayList<>();
    }

    //creazione user per in memory mode
    public Costumer(String email, String password, String name, String surname, List<Borrow> pending_borrows) {
        super(email, name, surname, password);
        this.membership_date = LocalDate.now();
        this.membership_status = true;
        this.pending_borrows = pending_borrows;
        this.active_borrows = new ArrayList<>();
        this.overdue_borrows = new ArrayList<>();
        this.finished_borrows = new ArrayList<>();
    }


    public LocalDate getMemDate() {return this.membership_date;}
    public void addPedingBorrows(Borrow borrow) {this.pending_borrows.add(borrow);}
    public void addActiveBorrows(Borrow borrow) {this.active_borrows.add(borrow);}
    public void addOverdueBorrows(Borrow borrow) {this.overdue_borrows.add(borrow);}
    public void addFinishedBorrows(Borrow borrow) {this.finished_borrows.add(borrow);}
    public List<Borrow> getPending_borrows() {return this.pending_borrows;}

}
