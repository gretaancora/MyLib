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
        LoginBean loggedInBean;

        loggedInBean = dao.getUserInfoByEmail(loginBean.getEmail());


        if (!BCrypt.checkpw(loginBean.getPassword(), loggedInBean.getPassword())){
            throw new WrongCredentialsException();
        }else{
            if(loggedInBean.getType().name().equalsIgnoreCase("librarian")) {

                return dao.loadLibrarian(loggedInBean.getEmail());

            } else if (loggedInBean.getType().name().equalsIgnoreCase("costumer")) {

                return dao.loadCostumer(loggedInBean.getEmail());

            } else{
                throw new UnsupportedUserTypeException();
            }
        }

    }

}
