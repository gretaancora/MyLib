package it.uniroma2.dicii.ispw.librarymanagmentsystem.engineering.factory;

import it.uniroma2.dicii.ispw.librarymanagmentsystem.engineering.dao.UserDAO;
import it.uniroma2.dicii.ispw.librarymanagmentsystem.engineering.dao.UserMySQLDAO;

public class MySQLDAOFactory extends DAOFactory {
    @Override
    public UserDAO createUserDAO() {
        return new UserMySQLDAO();
    }

    /*@Override
    public PlaylistDAO createPlaylistDAO() {
        return new PlaylistDAOMySQL();
    }

    @Override
    public NoticeDAO createNoticeDAO() {
        return new NoticeDAOMySQL();
    }*/
}
