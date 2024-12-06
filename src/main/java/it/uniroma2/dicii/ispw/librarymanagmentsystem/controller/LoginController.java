package it.uniroma2.dicii.ispw.librarymanagmentsystem.controller;

import it.uniroma2.dicii.ispw.librarymanagmentsystem.engineering.bean.LoginBean;
import it.uniroma2.dicii.ispw.librarymanagmentsystem.engineering.dao.UserDAO;
import it.uniroma2.dicii.ispw.librarymanagmentsystem.engineering.exceptions.*;
import it.uniroma2.dicii.ispw.librarymanagmentsystem.engineering.factory.DAOFactory;
import it.uniroma2.dicii.ispw.librarymanagmentsystem.model.User;
import org.mindrot.jbcrypt.BCrypt;

public class LoginController {

    public LoginController() {}

    public User start(LoginBean loginBean) throws WrongCredentialsException, UserNotFoundException, UnsupportedUserTypeException, DAOException {
        UserDAO dao = DAOFactory.getDAOFactory().createUserDAO();
        LoginBean loggedinBean;

        loggedinBean = dao.getUserInfoByEmail(loginBean.getEmail());


        if (!BCrypt.checkpw(loginBean.getPassword(), loggedinBean.getPassword())){
            System.out.println("Le password non corrispondono.");
            throw new WrongCredentialsException();
        }else{
            if(loggedinBean.getUserType().equalsIgnoreCase("librarian")) {

                return dao.loadLibrarian(loggedinBean.getEmail());

            } else if (loggedinBean.getUserType().equalsIgnoreCase("costumer")) {

                return dao.loadCostumer(loggedinBean.getEmail());

            } else{
                throw new UnsupportedUserTypeException();
            }
        }

    }

}
