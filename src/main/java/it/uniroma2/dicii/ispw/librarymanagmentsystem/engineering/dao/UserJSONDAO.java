package it.uniroma2.dicii.ispw.librarymanagmentsystem.engineering.dao;

import it.uniroma2.dicii.ispw.librarymanagmentsystem.engineering.bean.LoginBean;
import it.uniroma2.dicii.ispw.librarymanagmentsystem.engineering.exceptions.DAOException;
import it.uniroma2.dicii.ispw.librarymanagmentsystem.engineering.exceptions.EmailAlreadyInUseException;
import it.uniroma2.dicii.ispw.librarymanagmentsystem.engineering.exceptions.UserNotFoundException;
import it.uniroma2.dicii.ispw.librarymanagmentsystem.other.ConfigurationJSON;
import it.uniroma2.dicii.ispw.librarymanagmentsystem.other.Printer;
import it.uniroma2.dicii.ispw.librarymanagmentsystem.model.Costumer;
import it.uniroma2.dicii.ispw.librarymanagmentsystem.model.Librarian;
import com.google.gson.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class UserJSONDAO implements UserDAO {
    private static final String BASE_DIRECTORY = ConfigurationJSON.USER_BASE_DIRECTORY;

    @Override
    public void insertCostumer(Costumer costumer) throws EmailAlreadyInUseException {

    }


    @Override
    public Costumer loadCostumer(String email) throws UserNotFoundException {
        return null;
    }

    @Override
    public Librarian loadLibrarian(String email) throws UserNotFoundException {
        return null;
    }

    @Override
    public LoginBean getUserInfoByEmail(String email) throws UserNotFoundException, DAOException {
        try {
            Path userInfoFile = Paths.get(BASE_DIRECTORY, email, ConfigurationJSON.USER_INFO_FILE_NAME);

            if (!Files.exists(userInfoFile)) {
                throw new UserNotFoundException(); // Lanciare l'eccezione se il file non esiste
            }

            String content = Files.readString(userInfoFile);

            //da controllare come funziona
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            JsonObject jsonObject = gson.fromJson(content, JsonObject.class);
            return new LoginBean(email, jsonObject.getAsJsonPrimitive("password").getAsString(), jsonObject.getAsJsonPrimitive("type").getAsString());

        } catch (IOException e) {
            Printer.errorPrint(String.format("ClientDAOJSON: %s", e.getMessage()));
            throw new DAOException("Error in login DAO JSON.");
        }

    }
}
