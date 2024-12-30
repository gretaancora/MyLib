package it.uniroma2.dicii.ispw.MyLib.controller;

import it.uniroma2.dicii.ispw.MyLib.engineering.bean.RegisterBean;
import it.uniroma2.dicii.ispw.MyLib.engineering.dao.UserDAO;
import it.uniroma2.dicii.ispw.MyLib.engineering.exceptions.DAOException;
import it.uniroma2.dicii.ispw.MyLib.engineering.exceptions.EmailAlreadyInUseException;
import it.uniroma2.dicii.ispw.MyLib.engineering.factory.DAOFactory;
import it.uniroma2.dicii.ispw.MyLib.engineering.singleton.Configurations;
import it.uniroma2.dicii.ispw.MyLib.model.Costumer;
import it.uniroma2.dicii.ispw.MyLib.other.Printer;
import org.mindrot.jbcrypt.BCrypt;

import java.util.logging.Logger;

public class RegisterController {
    private static final Logger log = Logger.getLogger(Configurations.LOGGER_NAME);

    public boolean registerCostumer(RegisterBean regBean) throws EmailAlreadyInUseException, DAOException {

        if(regBean != null) {
            UserDAO dao = DAOFactory.getDAOFactory().createUserDAO();

            //creo costumer a partire dai dati del bean
            var costumer = new Costumer(regBean.getEmail(), BCrypt.hashpw(regBean.getPassword(), BCrypt.gensalt()), regBean.getName(), regBean.getSurname());

            //chiamo la DAO per la registrazione del costumer
            try {
                dao.insertCostumer(costumer);
            } catch (DAOException e) {
                log.severe("Error in RegisterController: " + e.getMessage());
                Printer.errorPrint("Error occurred during registration.");
                throw new DAOException();
            }

            return true;
        }

        return false;

    }

}
