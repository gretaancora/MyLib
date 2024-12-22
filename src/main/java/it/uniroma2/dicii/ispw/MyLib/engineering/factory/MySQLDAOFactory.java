package it.uniroma2.dicii.ispw.MyLib.engineering.factory;

import it.uniroma2.dicii.ispw.MyLib.engineering.dao.UserDAO;
import it.uniroma2.dicii.ispw.MyLib.engineering.dao.UserMySQLDAO;

public class MySQLDAOFactory extends DAOFactory {
    @Override
    public UserDAO createUserDAO() {
        return new UserMySQLDAO();
    }

}
