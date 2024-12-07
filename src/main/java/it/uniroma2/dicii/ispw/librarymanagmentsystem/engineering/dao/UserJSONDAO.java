package it.uniroma2.dicii.ispw.librarymanagmentsystem.engineering.dao;

import it.uniroma2.dicii.ispw.librarymanagmentsystem.engineering.bean.LoginBean;
import it.uniroma2.dicii.ispw.librarymanagmentsystem.engineering.exceptions.DAOException;
import it.uniroma2.dicii.ispw.librarymanagmentsystem.engineering.exceptions.EmailAlreadyInUseException;
import it.uniroma2.dicii.ispw.librarymanagmentsystem.engineering.exceptions.UserNotFoundException;
import it.uniroma2.dicii.ispw.librarymanagmentsystem.other.ConfigurationJSON;
import it.uniroma2.dicii.ispw.librarymanagmentsystem.other.LocalDateAdapter;
import it.uniroma2.dicii.ispw.librarymanagmentsystem.other.Printer;
import it.uniroma2.dicii.ispw.librarymanagmentsystem.model.Costumer;
import it.uniroma2.dicii.ispw.librarymanagmentsystem.model.Librarian;
import com.google.gson.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;

public class UserJSONDAO implements UserDAO {
    private static final String BASE_DIRECTORY = ConfigurationJSON.USER_BASE_DIRECTORY;

    @Override
    public void insertCostumer(Costumer costumer) throws EmailAlreadyInUseException, DAOException {
        try {
            // Verifica se la cartella persistence esiste, altrimenti la crea
            Path persistenceDirectory = Paths.get(ConfigurationJSON.PERSISTENCE_BASE_DIRECTORY);
            if (!Files.exists(persistenceDirectory)) {
                Files.createDirectories(persistenceDirectory);
            }

            // Verifica se l'utente esiste già
            if (checkIfUserExists(costumer.getEmail())) {
                throw new EmailAlreadyInUseException();
            }

            // Crea la directory dell'utente e il file di informazioni
            Path userDirectory = Files.createDirectories(Paths.get(BASE_DIRECTORY, costumer.getEmail()));
            Path userInfoFile = userDirectory.resolve(ConfigurationJSON.USER_INFO_FILE_NAME);

            // Serializza l'oggetto Login in formato JSON e scrivi nel file
            String json = new GsonBuilder().setPrettyPrinting().create().toJson(costumer);
            Files.writeString(userInfoFile, json);

        } catch (IOException e) {
            throw new DAOException("Error in UserJSONDAO: " + e.getMessage());
        }
    }

    private boolean checkIfUserExists(String email) {
        // Costruito il percorso della directory dell'utente basandosi sulla mail come nome utente
        Path userDirectory = Paths.get(BASE_DIRECTORY, email);

        // Verifica se la directory dell'utente esiste
        return Files.exists(userDirectory);
    }


    @Override
    public Costumer loadCostumer(String email) throws UserNotFoundException, DAOException {
        try {
            Path costumerInfoFile = Paths.get(BASE_DIRECTORY, email, ConfigurationJSON.COSTUMER_INFO_FILE_NAME);

            if (Files.exists(costumerInfoFile)) {
                String content = Files.readString(costumerInfoFile);
                Gson gson = new GsonBuilder().registerTypeAdapter(LocalDate.class, new LocalDateAdapter()).create();

                Costumer costumer = gson.fromJson(content, Costumer.class);

                return costumer;

            } else {
                throw new UserNotFoundException();
            }
        } catch (IOException e) {
            throw new DAOException("Error in UserJSONDAO: " + e.getMessage());
        }
    }


    @Override
    public Librarian loadLibrarian(String email) throws UserNotFoundException, DAOException {
        try {
            Path librarianInfoFile = Paths.get(BASE_DIRECTORY, email, ConfigurationJSON.LIBRARIAN_INFO_FILE_NAME);

            if (Files.exists(librarianInfoFile)) {
                String content = Files.readString(librarianInfoFile);

                Gson gson = new GsonBuilder().registerTypeAdapter(LocalDate.class, new LocalDateAdapter()).create();
                Librarian librarian = gson.fromJson(content, Librarian.class);

                return librarian;

            } else {
                throw new UserNotFoundException();
            }
        } catch (IOException e) {
            throw new DAOException("Error in UserJSONDAO: " + e.getMessage());
        }
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
