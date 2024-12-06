package it.uniroma2.dicii.ispw.librarymanagmentsystem.engineering.factory;

import it.uniroma2.dicii.ispw.librarymanagmentsystem.engineering.dao.UserDAO;
import it.uniroma2.dicii.ispw.librarymanagmentsystem.other.Printer;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public abstract class DAOFactory {
    private static DAOFactory me = null;

    protected DAOFactory(){
    }

    /** Recupera dal file config.properties il tipo di persistenza utilizzata,
     * se non è possibile come default viene utilizzato MYSQL */
    public static synchronized DAOFactory getDAOFactory(){
        if ( me == null ){
            Properties properties = new Properties();

            try (InputStream input = DAOFactory.class.getClassLoader().getResourceAsStream("config.properties")) {
                properties.load(input);
            } catch (IOException e){
                Printer.errorPrint(e.getMessage());
            }

            String persistenceType = properties.getProperty("PERSISTENCE_TYPE", "MYSQL");

            if (persistenceType.equalsIgnoreCase("JSON")) {
                me = new JsonDAOFactory();
            }else {
                me = new MySQLDAOFactory();
            }
        }
        return me;
    }

    public abstract UserDAO createUserDAO();
}
