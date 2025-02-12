package it.uniroma2.dicii.ispw.mylib.controller;

import it.uniroma2.dicii.ispw.mylib.engineering.bean.LoginBean;
import it.uniroma2.dicii.ispw.mylib.engineering.dao.UserDAO;
import it.uniroma2.dicii.ispw.mylib.engineering.exceptions.*;
import it.uniroma2.dicii.ispw.mylib.engineering.factory.DAOFactory;
import it.uniroma2.dicii.ispw.mylib.engineering.singleton.Configurations;
import it.uniroma2.dicii.ispw.mylib.model.User;
import org.mindrot.jbcrypt.BCrypt;
import java.util.logging.Logger;

public class LoginController {

    private static final Logger log = Logger.getLogger(Configurations.LOGGER_NAME);

    public User start(LoginBean loginBean) throws WrongCredentialsException, UserNotFoundException, UnsupportedUserTypeException, DAOException {
        UserDAO dao = DAOFactory.getDAOFactory().createUserDAO();
        LoginBean loggedInBean;

        loggedInBean = dao.getUserInfoByEmail(loginBean.getEmail());


        if (!BCrypt.checkpw(loginBean.getPassword(), loggedInBean.getPassword())){
            log.severe("Error in Login Controller: Wrong credentials.");
            throw new WrongCredentialsException();
        }else{
            if(loggedInBean.getType().name().equalsIgnoreCase("librarian")) {

                return dao.loadLibrarian(loggedInBean.getEmail());

            } else if (loggedInBean.getType().name().equalsIgnoreCase("costumer")) {

                return dao.loadCostumer(loggedInBean.getEmail());

            } else{
                log.severe("Error in LoginController: Unsupported user type.");
                throw new UnsupportedUserTypeException();
            }
        }

    }

}
