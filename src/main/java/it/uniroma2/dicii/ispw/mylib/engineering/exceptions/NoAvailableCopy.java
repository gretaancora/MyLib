package it.uniroma2.dicii.ispw.mylib.engineering.exceptions;

public class NoAvailableCopy extends Exception {
    public NoAvailableCopy(String title) {super("No available copy of: " + title);}
    public NoAvailableCopy() {super();}
}
