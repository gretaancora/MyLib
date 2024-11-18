package it.uniroma2.dicii.ispw.librarymanagmentsystem.controller;

import it.uniroma2.dicii.ispw.librarymanagmentsystem.engineering.bean.RegisterBean;
import it.uniroma2.dicii.ispw.librarymanagmentsystem.engineering.dao.UserDAO;
import it.uniroma2.dicii.ispw.librarymanagmentsystem.engineering.exceptions.EmailAlreadyInUseException;
import it.uniroma2.dicii.ispw.librarymanagmentsystem.other.Printer;
import it.uniroma2.dicii.ispw.librarymanagmentsystem.engineering.factory.DAOFactory;
import it.uniroma2.dicii.ispw.librarymanagmentsystem.model.Costumer;

public class RegisterController {
    public void registerCostumer(RegisterBean regBean) throws EmailAlreadyInUseException {
        UserDAO dao = DAOFactory.getDAOFactory().createUserDAO();

        //creo costumer a partire dai dati del bean
        var costumer = new Costumer(regBean.getEmail(), regBean.getPassword(), regBean.getName(), regBean.getSurname());
        //chiamo la DAO per la registrazione del costumer
        try {
            dao.insertCostumer(costumer);
        } catch (EmailAlreadyInUseException e) {
            Printer.println("Email already in use.");
            throw new EmailAlreadyInUseException();
        }
    }

}
