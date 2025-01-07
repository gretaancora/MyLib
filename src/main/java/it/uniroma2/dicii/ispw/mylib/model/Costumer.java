package it.uniroma2.dicii.ispw.mylib.model;

import it.uniroma2.dicii.ispw.mylib.other.SupportedUserTypes;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Costumer extends User {
    private LocalDate membershipDate;
    private boolean membershipStatus;
    private List<Borrow> pendingBorrows;
    private List<Borrow> activeBorrows;
    private List<Borrow> overdueBorrows;
    private List<Borrow> finishedBorrows;

    //login
    public Costumer(String email, String name, String surname, LocalDate subDate, boolean membershipStatus) {
        super(email, name, surname);
        this.membershipDate = subDate;
        this.membershipStatus = membershipStatus;
        this.pendingBorrows = new ArrayList<>();
        this.activeBorrows = new ArrayList<>();
        this.overdueBorrows = new ArrayList<>();
        this.finishedBorrows = new ArrayList<>();
    }

    //registrazione
    //con data
    public Costumer(String email, String password, String name, String surname, LocalDate subDate) {
        super(email, name, surname, password);
        this.membershipDate = subDate;
        this.membershipStatus = true;
        this.pendingBorrows = new ArrayList<>();
        this.activeBorrows = new ArrayList<>();
        this.overdueBorrows = new ArrayList<>();
        this.finishedBorrows = new ArrayList<>();
    }

    // se data non inserita attivazione odierna
    public Costumer(String email, String password, String name, String surname) {
        super(email, name, surname, password);
        this.membershipDate = LocalDate.now();
        this.membershipStatus = true;
        this.pendingBorrows = new ArrayList<>();
        this.activeBorrows = new ArrayList<>();
        this.overdueBorrows = new ArrayList<>();
        this.finishedBorrows = new ArrayList<>();
    }

    //creazione user per in memory mode
    public Costumer(String email, String password, String name, String surname, SupportedUserTypes type, List<Borrow> pendingBorrows) {
        super(email, name, surname, password, type);
        this.membershipDate = LocalDate.now();
        this.membershipStatus = true;
        this.pendingBorrows = pendingBorrows;
        this.activeBorrows = new ArrayList<>();
        this.overdueBorrows = new ArrayList<>();
        this.finishedBorrows = new ArrayList<>();
    }


    public LocalDate getMemDate() {return this.membershipDate;}
    public void addPedingBorrows(Borrow borrow) {this.pendingBorrows.add(borrow);}
    public void addActiveBorrows(Borrow borrow) {this.activeBorrows.add(borrow);}
    public void addOverdueBorrows(Borrow borrow) {this.overdueBorrows.add(borrow);}
    public void addFinishedBorrows(Borrow borrow) {this.finishedBorrows.add(borrow);}
    public List<Borrow> getPendingBorrows() {return this.pendingBorrows;}

}
