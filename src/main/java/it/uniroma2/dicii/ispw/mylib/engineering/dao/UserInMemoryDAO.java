package it.uniroma2.dicii.ispw.mylib.engineering.dao;

import it.uniroma2.dicii.ispw.mylib.engineering.bean.LoginBean;
import it.uniroma2.dicii.ispw.mylib.engineering.exceptions.DAOException;
import it.uniroma2.dicii.ispw.mylib.engineering.exceptions.EmailAlreadyInUseException;
import it.uniroma2.dicii.ispw.mylib.engineering.exceptions.UserNotFoundException;
import it.uniroma2.dicii.ispw.mylib.model.*;
import it.uniroma2.dicii.ispw.mylib.other.SupportedUserTypes;
import org.mindrot.jbcrypt.BCrypt;
import java.util.ArrayList;
import java.util.List;

public class UserInMemoryDAO implements UserDAO{

    private List<User> users = new ArrayList<>();
    private static final String EMAIL_1 = "user1@gmail.com";
    private static final String USER_1 = "user1";
    private static final String EMAIL_2 = "user2@gmail.com";
    private static final String USER_2 = "user2";


    public UserInMemoryDAO() {
        users.add(new Costumer(EMAIL_1, USER_1, USER_1, USER_1));
        users.add(new Librarian(EMAIL_2, USER_2, USER_2, USER_2));
    }

    @Override
    public void insertCostumer(Costumer costumer) throws EmailAlreadyInUseException, DAOException {
        for (User user : users){
            if(user.getEmail().equals(costumer.getEmail())){
                throw new EmailAlreadyInUseException();
            }
        }

        if(!users.add(costumer)){
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
            if (user.getEmail().equals(email) && user.getEmail().equals(EMAIL_1)) {
                return new LoginBean(EMAIL_1, BCrypt.hashpw(USER_1, BCrypt.gensalt()), SupportedUserTypes.COSTUMER);
            }else if(user.getEmail().equals(email) && user.getEmail().equals(EMAIL_2)){
                return new LoginBean(EMAIL_2, BCrypt.hashpw(USER_2, BCrypt.gensalt()), SupportedUserTypes.LIBRARIAN);
            }
        }

        throw new UserNotFoundException();
    }

}
