package it.uniroma2.dicii.ispw.librarymanagmentsystem.engineering.bean;

import it.uniroma2.dicii.ispw.librarymanagmentsystem.other.SupportedRoleTypes;

import java.time.LocalDate;
import java.util.List;

public class RegisterBean extends LoginBean{
    private String name;
    private String surname;

    public RegisterBean(String name, String surname, String email, String password) {
        super(email, password);
        this.name = name;
        this.surname = surname;
    }


    public String getName() {return this.getName();}
    public String getSurname() {return this.getSurname();}

}
