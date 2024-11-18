package it.uniroma2.dicii.ispw.librarymanagmentsystem.engineering.query;

public class Query {

    Query(){}

    /*--------------------Queries Login-----------------------------*/

    //Query per verificare se l'email inserita è già stata registrata (nel momento di LOGIN)
    public static final String SEARCH_EMAIL = "SELECT * FROM utente WHERE email = '%s' ";

    //Query per fare login con i credenziali
    public static final String GET_USER = "SELECT * FROM utente WHERE email ='%s'";



    /*--------------------Queries Registrazione-------------------*/
    public static final String REGISTER_COSTUMER = "INSERT INTO utente (email, nome, cognome, password, isTutor) VALUES ('%s', '%s', '%s', '%s', %b)";

    public static final String REGISTER_LIBRARIAN = "INSERT INTO tutor (email, tariffa, luogo, materie, inPresenza, webCam, giorni, nome, cognome) VALUES ('%s', NULL, NULL, NULL, NULL, NULL, NULL, '%s', '%s')";

}
