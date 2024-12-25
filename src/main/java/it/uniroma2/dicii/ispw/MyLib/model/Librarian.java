package it.uniroma2.dicii.ispw.MyLib.model;

import it.uniroma2.dicii.ispw.MyLib.other.SupportedRoleTypes;

import java.time.LocalDate;

public class Librarian extends User {
    private LocalDate empDate;
    private SupportedRoleTypes role;

    //login
    public Librarian(String email, String name, String surname, LocalDate empDate, SupportedRoleTypes role) {
        super(email, name, surname);
        this.empDate = empDate;
        this.role = role;
    }

    //creazione librarian per in memory mode
    public Librarian(String email, String password, String name, String surname) {
        super(email, name, surname, password);
        this.empDate = LocalDate.now();
        this.role = SupportedRoleTypes.SUPERVISOR;
    }

}
