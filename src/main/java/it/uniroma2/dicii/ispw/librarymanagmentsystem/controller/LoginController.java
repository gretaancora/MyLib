package it.uniroma2.dicii.ispw.librarymanagmentsystem.controller;

import it.uniroma2.dicii.ispw.librarymanagmentsystem.engineering.bean.LoginBean;
import it.uniroma2.dicii.ispw.librarymanagmentsystem.engineering.dao.UserDAO;
import it.uniroma2.dicii.ispw.librarymanagmentsystem.engineering.exceptions.*;
import it.uniroma2.dicii.ispw.librarymanagmentsystem.engineering.factory.DAOFactory;
import it.uniroma2.dicii.ispw.librarymanagmentsystem.model.User;

public class LoginController {

    public LoginController() {}

    public User start(LoginBean loginBean) throws WrongCredentialsException, UserNotFoundException, UnsupportedUserTypeException {
        UserDAO dao = DAOFactory.getDAOFactory().createUserDAO();
        LoginBean loggedinBean = null;

        try {
            loggedinBean = dao.getUserInfoByEmail(loginBean.getEmail());
        } catch (UserNotFoundException e) {
            throw new UserNotFoundException();
        } catch (DAOException e) {
            e.printStackTrace();
        }

        if (!loggedinBean.getPassword().equals(loginBean.getPassword())){
            throw new WrongCredentialsException();
        }else{
            if(loggedinBean.getUserType().equalsIgnoreCase("librarian")) {
                try {
                    return dao.loadLibrarian(loggedinBean.getEmail());
                } catch (DAOException e) {
                    throw new RuntimeException(e);
                }
            }else if (loggedinBean.getUserType().equalsIgnoreCase("costumer")) {
                try {
                    return dao.loadCostumer(loggedinBean.getEmail());
                } catch (DAOException e) {
                    throw new RuntimeException(e);
                }
            }else{
                throw new UnsupportedUserTypeException();
            }
        }

    }

}
