package it.uniroma2.dicii.ispw.MyLib.model;

import it.uniroma2.dicii.ispw.MyLib.other.SupportedRoleTypes;

import java.time.LocalDate;

public class Librarian extends User {
    private LocalDate empDate;
    private SupportedRoleTypes role;

    public Librarian(String email, String name, String surname, LocalDate empDate, SupportedRoleTypes role) {
        super(email, name, surname);
        this.empDate = empDate;
        this.role = role;
    }

}
