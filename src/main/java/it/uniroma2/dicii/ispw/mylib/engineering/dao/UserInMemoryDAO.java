package it.uniroma2.dicii.ispw.mylib.engineering.dao;

import it.uniroma2.dicii.ispw.mylib.engineering.bean.LoginBean;
import it.uniroma2.dicii.ispw.mylib.engineering.exceptions.DAOException;
import it.uniroma2.dicii.ispw.mylib.engineering.exceptions.EmailAlreadyInUseException;
import it.uniroma2.dicii.ispw.mylib.engineering.exceptions.UserNotFoundException;
import it.uniroma2.dicii.ispw.mylib.engineering.singleton.Configurations;
import it.uniroma2.dicii.ispw.mylib.model.*;
import it.uniroma2.dicii.ispw.mylib.other.Printer;
import it.uniroma2.dicii.ispw.mylib.other.SupportedUserTypes;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class UserInMemoryDAO implements UserDAO{

    private static List<User> users = new ArrayList<>();
    private static final Logger log = Logger.getLogger(Configurations.LOGGER_NAME);

    //viene runnato una sola volta
    static {
        users.add(new Costumer("user1@gmail.com", "$2a$10$mNuM3heDto6ECbifGRuLm.0.6khzYOOX5jDk3fQ0ChAxt8N6eepWq", "user1", "user1", SupportedUserTypes.COSTUMER, new ArrayList<>()));
        users.add(new Librarian("user2@gmail.com", "$2a$10$hPA5CMm1W2.3m2zMS2z4.Ow62gKwGmOKbUZq2J/qtfJRBEHX8q1aO", "user2", "user2", SupportedUserTypes.LIBRARIAN));
    }

    @Override
    public void insertCostumer(Costumer costumer) throws EmailAlreadyInUseException, DAOException {
        for (User user : users){
            if(user.getEmail().equals(costumer.getEmail())){
                throw new EmailAlreadyInUseException();
            }
        }

        if(!users.add(new Costumer(costumer.getEmail(), costumer.getPassword(), costumer.getName(), costumer.getSurname(), SupportedUserTypes.COSTUMER, new ArrayList<>()))){
            log.severe("Error in RegisterController: trying to add the new costumer to the users' list.");
            Printer.errorPrint("Error occurred during registration.");
            throw new DAOException();
        }
    }

    @Override
    public Costumer loadCostumer(String email) throws UserNotFoundException {
        for(User user: users){
            if(user.getEmail().equals(email)){
                return (Costumer) user;
            }
        }

        throw new UserNotFoundException();
    }

    @Override
    public Librarian loadLibrarian(String email) throws UserNotFoundException {
        for (User user : users) {
            if (user.getEmail().equals(email)) {
                return (Librarian) user;
            }
        }

        throw new UserNotFoundException();
    }

    @Override
    public LoginBean getUserInfoByEmail(String email) throws UserNotFoundException {
        for (User user : users) {
            if (user.getEmail().equals(email)) {
                return new LoginBean(user.getEmail(), user.getPassword(), user.getType());
            }
        }

        throw new UserNotFoundException();
    }

}
