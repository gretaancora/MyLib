package it.uniroma2.dicii.ispw.mylib.engineering.factory;

import it.uniroma2.dicii.ispw.mylib.engineering.dao.*;

public class MySQLDAOFactory extends DAOFactory {
    @Override
    public UserDAO createUserDAO() {
        return new UserMySQLDAO();
    }

    @Override
    public MakeReservationDAO createMakeReservationDAO() {
        return new MakeReservationMySQLDAO();
    }

    @Override
    public ManageReservationDAO createManageReservationDAO() {
        return new ManageReservationMySQLDAO();
    }


}
