package it.uniroma2.dicii.ispw.MyLib.engineering.factory;

import it.uniroma2.dicii.ispw.MyLib.engineering.dao.*;

public class InMemoryDAOFactory extends DAOFactory {
    @Override
    public UserDAO createUserDAO() {
        return new UserInMemoryDAO();
    }

    @Override
    public MakeReservationDAO createMakeReservationDAO() {
        return new MakeReservationInMemoryDAO();
    }

    @Override
    public ManageReservationDAO createManageReservationDAO() {
        return new ManageReservationInMemoryDAO();
    }
}
