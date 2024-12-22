package it.uniroma2.dicii.ispw.MyLib.engineering.exceptions;

public class DAOException extends Exception{
    public DAOException() {
        super();
    }

    public DAOException(String message, Throwable cause) {
        super(message, cause);
    }

    public DAOException(String message) {
        super(message);
    }
}
