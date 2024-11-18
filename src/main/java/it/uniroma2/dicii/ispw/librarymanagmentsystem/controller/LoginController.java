package it.uniroma2.dicii.ispw.librarymanagmentsystem.controller;

import it.uniroma2.dicii.ispw.librarymanagmentsystem.engineering.bean.LoginBean;
import it.uniroma2.dicii.ispw.librarymanagmentsystem.engineering.dao.UserDAO;
import it.uniroma2.dicii.ispw.librarymanagmentsystem.engineering.exceptions.*;
import it.uniroma2.dicii.ispw.librarymanagmentsystem.engineering.factory.DAOFactory;
import it.uniroma2.dicii.ispw.librarymanagmentsystem.model.User;

public class LoginController {

    public LoginController() {}

    public User start(LoginBean loginBean) throws WrongPasswordException, UserNotFoundException, UnsupportedUserTypeException {
        UserDAO dao = DAOFactory.getDAOFactory().createUserDAO(); // Crea l'istanza corretta del DAO (Impostata nel file di configurazione)
        LoginBean loggedinBean = null;

        try {
            loggedinBean = dao.getUserInfoByEmail(loginBean.getEmail());
        } catch (UserNotFoundException e) {
            throw new UserNotFoundException();
        } catch (DAOException e) {
            e.printStackTrace();
        }

        if (!loggedinBean.getPassword().equals(loginBean.getPassword())){
            throw new WrongPasswordException();
        }else{
            if(loggedinBean.getUserType().equalsIgnoreCase("librarian")) {
                return dao.loadLibrarian(loggedinBean.getEmail());
            }else if (loggedinBean.getUserType().equalsIgnoreCase("costumer")) {
                return dao.loadCostumer(loggedinBean.getEmail());
            }else{
                throw new UnsupportedUserTypeException();
            }
        }

    }

}
