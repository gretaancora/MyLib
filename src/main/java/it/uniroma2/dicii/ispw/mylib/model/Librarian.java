package it.uniroma2.dicii.ispw.mylib.model;

import it.uniroma2.dicii.ispw.mylib.other.SupportedRoleTypes;
import it.uniroma2.dicii.ispw.mylib.other.SupportedUserTypes;

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
    public Librarian(String email, String password, String name, String surname, SupportedUserTypes type) {
        super(email, name, surname, password, type);
        this.empDate = LocalDate.now();
        this.role = SupportedRoleTypes.SUPERVISOR;
    }

}
