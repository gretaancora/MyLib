package it.uniroma2.dicii.ispw.MyLib.controller;

import it.uniroma2.dicii.ispw.MyLib.engineering.bean.LoginBean;
import it.uniroma2.dicii.ispw.MyLib.engineering.dao.UserDAO;
import it.uniroma2.dicii.ispw.MyLib.engineering.exceptions.*;
import it.uniroma2.dicii.ispw.MyLib.engineering.factory.DAOFactory;
import it.uniroma2.dicii.ispw.MyLib.model.User;
import org.mindrot.jbcrypt.BCrypt;

public class LoginController {

    public LoginController() {}

    public User start(LoginBean loginBean) throws WrongCredentialsException, UserNotFoundException, UnsupportedUserTypeException, DAOException {
        UserDAO dao = DAOFactory.getDAOFactory().createUserDAO();
        LoginBean loggedinBean;

        loggedinBean = dao.getUserInfoByEmail(loginBean.getEmail());


        if (!BCrypt.checkpw(loginBean.getPassword(), loggedinBean.getPassword())){
            System.out.println("Passwords don't match");
            throw new WrongCredentialsException();
        }else{
            if(loggedinBean.getType().name().equalsIgnoreCase("librarian")) {

                return dao.loadLibrarian(loggedinBean.getEmail());

            } else if (loggedinBean.getType().name().equalsIgnoreCase("costumer")) {

                return dao.loadCostumer(loggedinBean.getEmail());

            } else{
                throw new UnsupportedUserTypeException();
            }
        }

    }

}
