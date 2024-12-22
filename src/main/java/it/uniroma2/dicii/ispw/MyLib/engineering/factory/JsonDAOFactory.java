package it.uniroma2.dicii.ispw.MyLib.engineering.factory;

import it.uniroma2.dicii.ispw.MyLib.engineering.dao.UserDAO;
import it.uniroma2.dicii.ispw.MyLib.engineering.dao.UserJSONDAO;

public class JsonDAOFactory extends DAOFactory {
    @Override
    public UserDAO createUserDAO() {
        return new UserJSONDAO();
    }
}
