package it.uniroma2.dicii.ispw.mylib.engineering.factory;

import it.uniroma2.dicii.ispw.mylib.engineering.dao.MakeReservationDAO;
import it.uniroma2.dicii.ispw.mylib.engineering.dao.ManageReservationDAO;
import it.uniroma2.dicii.ispw.mylib.engineering.dao.UserDAO;
import it.uniroma2.dicii.ispw.mylib.engineering.dao.UserJSONDAO;
import it.uniroma2.dicii.ispw.mylib.other.Printer;

public class JsonDAOFactory extends DAOFactory {
    @Override
    public UserDAO createUserDAO() {
        return new UserJSONDAO();
    }

    @Override
    public MakeReservationDAO createMakeReservationDAO() {
        Printer.errorPrint("Feature 'make reservation' with json not implemented yet.");
        return null;
    }

    @Override
    public ManageReservationDAO createManageReservationDAO() {
        Printer.errorPrint("Feature 'manage reservation' with json not implemented yet.");
        return null;
    }
}
