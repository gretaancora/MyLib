package it.uniroma2.dicii.ispw.mylib.engineering.exceptions;

public class MaxPendingBorrowsException extends Exception {
    public MaxPendingBorrowsException() {super("Maximum number of pending borrows reached.");}
}
