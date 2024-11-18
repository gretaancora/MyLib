package it.uniroma2.dicii.ispw.librarymanagmentsystem.engineering.exceptions;

import it.uniroma2.dicii.ispw.librarymanagmentsystem.engineering.exceptions.WrongCredentialsException;

public class WrongPasswordException extends Exception {
    public WrongPasswordException() {super("Wrong password.");}
}
