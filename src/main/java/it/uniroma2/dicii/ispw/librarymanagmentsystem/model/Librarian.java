package it.uniroma2.dicii.ispw.librarymanagmentsystem.model;

import it.uniroma2.dicii.ispw.librarymanagmentsystem.other.SupportedRoleTypes;

import java.time.LocalDate;

public class Librarian extends User {
    private LocalDate employmentDate;
    private SupportedRoleTypes role;

    public Librarian(String email, String name, String surname, LocalDate empDate, SupportedRoleTypes role) {
        super(email, name, surname);
        this.employmentDate = empDate;
        this.role = role;
    }

}
