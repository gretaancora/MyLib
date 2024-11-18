package it.uniroma2.dicii.ispw.librarymanagmentsystem.engineering.factory;

import it.uniroma2.dicii.ispw.librarymanagmentsystem.engineering.dao.UserDAO;
import it.uniroma2.dicii.ispw.librarymanagmentsystem.engineering.dao.UserJSONDAO;

public class JsonDAOFactory extends DAOFactory {
    @Override
    public UserDAO createUserDAO() {
        return new UserJSONDAO();
    }

    /*@Override
    public PlaylistDAO createPlaylistDAO() {
        return new PlaylistDAOJSON();
    }

    @Override
    public NoticeDAO createNoticeDAO() {
        return new NoticeDAOJSON();
    }*/
}
