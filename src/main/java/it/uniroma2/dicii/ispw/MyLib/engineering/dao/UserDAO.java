package it.uniroma2.dicii.ispw.MyLib.engineering.dao;

import it.uniroma2.dicii.ispw.MyLib.engineering.bean.LoginBean;
import it.uniroma2.dicii.ispw.MyLib.engineering.exceptions.*;
import it.uniroma2.dicii.ispw.MyLib.model.Costumer;
import it.uniroma2.dicii.ispw.MyLib.model.Librarian;

public interface UserDAO {

    /** Inserimento dell'utente in persistenza
     * Valore di ritorno booleano per verificare la correttezza dell'operazione */
    void insertCostumer(Costumer costumer) throws EmailAlreadyInUseException, DAOException;


    /** Recupera le informazioni di un utente in persistenza, ottenuta dall'email */
    Costumer loadCostumer(String email) throws UserNotFoundException, DAOException;
    Librarian loadLibrarian(String email) throws UserNotFoundException, DAOException;


    /** Ottiene la password e il ruolo associati all'email */
    LoginBean getUserInfoByEmail(String email) throws UserNotFoundException, DAOException;
}
