package it.uniroma2.dicii.ispw.MyLib.engineering.dao;

import it.uniroma2.dicii.ispw.MyLib.engineering.bean.LoginBean;
import it.uniroma2.dicii.ispw.MyLib.engineering.exceptions.DAOException;
import it.uniroma2.dicii.ispw.MyLib.engineering.exceptions.EmailAlreadyInUseException;
import it.uniroma2.dicii.ispw.MyLib.engineering.exceptions.UserNotFoundException;
import it.uniroma2.dicii.ispw.MyLib.model.*;
import it.uniroma2.dicii.ispw.MyLib.other.SupportedUserTypes;
import org.mindrot.jbcrypt.BCrypt;
import java.util.List;

public class UserInMemoryDAO implements UserDAO{

    private List<User> users;

    public UserInMemoryDAO() {
        users.add(new Costumer("user1@gmail.com", "user1", "user1", "user1"));
        users.add(new Librarian("user2@gmail.com", "user2", "user2", "user2"));
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
            if (user.getEmail().equals(email) && user.getEmail().equals("user1@gmail.com")) {
                return new LoginBean("user1@gmail.com", BCrypt.hashpw("user1", BCrypt.gensalt()), SupportedUserTypes.COSTUMER);
            }else if(user.getEmail().equals(email) && user.getEmail().equals("user2@gmail.com")){
                return new LoginBean("user2@gmail.com", BCrypt.hashpw("user2", BCrypt.gensalt()), SupportedUserTypes.LIBRARIAN);
            }
        }

        throw new UserNotFoundException();
    }

    public List<User> getUsers() {return this.users;}
}
