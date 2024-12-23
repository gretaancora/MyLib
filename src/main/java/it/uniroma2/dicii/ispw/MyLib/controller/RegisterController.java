package it.uniroma2.dicii.ispw.MyLib.controller;

import it.uniroma2.dicii.ispw.MyLib.engineering.bean.RegisterBean;
import it.uniroma2.dicii.ispw.MyLib.engineering.dao.UserDAO;
import it.uniroma2.dicii.ispw.MyLib.engineering.exceptions.DAOException;
import it.uniroma2.dicii.ispw.MyLib.engineering.exceptions.EmailAlreadyInUseException;
import it.uniroma2.dicii.ispw.MyLib.engineering.factory.DAOFactory;
import it.uniroma2.dicii.ispw.MyLib.model.Costumer;
import org.mindrot.jbcrypt.BCrypt;

public class RegisterController {
    public void registerCostumer(RegisterBean regBean) throws EmailAlreadyInUseException, DAOException {
        UserDAO dao = DAOFactory.getDAOFactory().createUserDAO();

        //creo costumer a partire dai dati del bean
        var costumer = new Costumer(regBean.getEmail(), BCrypt.hashpw(regBean.getPassword(), BCrypt.gensalt()), regBean.getName(), regBean.getSurname());

        //chiamo la DAO per la registrazione del costumer
        dao.insertCostumer(costumer);

    }

}
