package it.uniroma2.dicii.ispw.mylib.engineering.dao;

import it.uniroma2.dicii.ispw.mylib.engineering.bean.LoginBean;
import it.uniroma2.dicii.ispw.mylib.engineering.exceptions.DAOException;
import it.uniroma2.dicii.ispw.mylib.engineering.exceptions.EmailAlreadyInUseException;
import it.uniroma2.dicii.ispw.mylib.engineering.exceptions.UserNotFoundException;
import it.uniroma2.dicii.ispw.mylib.engineering.singleton.Configurations;
import it.uniroma2.dicii.ispw.mylib.other.ConfigurationJSON;
import it.uniroma2.dicii.ispw.mylib.other.LocalDateAdapter;
import it.uniroma2.dicii.ispw.mylib.model.Costumer;
import it.uniroma2.dicii.ispw.mylib.model.Librarian;
import com.google.gson.*;
import it.uniroma2.dicii.ispw.mylib.other.SupportedUserTypes;
import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.time.LocalDate;
import java.util.logging.Logger;

public class UserJSONDAO implements UserDAO {
    private static final String BASE_DIRECTORY = ConfigurationJSON.USER_BASE_DIRECTORY;
    private static final Logger log = Logger.getLogger(Configurations.LOGGER_NAME);

    @Override
    public void insertCostumer(Costumer costumer) throws EmailAlreadyInUseException, DAOException {
        Path userDirectory = null;

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
            userDirectory = Files.createDirectories(Paths.get(BASE_DIRECTORY, costumer.getEmail()));
            Path userInfoFile = userDirectory.resolve(ConfigurationJSON.USER_INFO_FILE_NAME);
            Path costumerInfoFile = userDirectory.resolve(ConfigurationJSON.COSTUMER_INFO_FILE_NAME);

            //divido le info per i due file
            var userInfo = new LoginBean(costumer.getEmail(), costumer.getPassword(), SupportedUserTypes.COSTUMER);
            var costumerInfo = new Costumer(costumer.getEmail(), costumer.getName(), costumer.getSurname(), LocalDate.now(), true);

            // Serializza l'oggetto Login in formato JSON e scrivi nel file
            String json = new GsonBuilder().setPrettyPrinting().registerTypeAdapter(LocalDate.class, new LocalDateAdapter()).create().toJson(userInfo);
            Files.writeString(userInfoFile, json);
            json = new GsonBuilder().setPrettyPrinting().registerTypeAdapter(LocalDate.class, new LocalDateAdapter()).create().toJson(costumerInfo);
            Files.writeString(costumerInfoFile, json);

        } catch (JsonIOException | IOException e) {

            if(userDirectory!=null) {
                try {
                    /*viene creata una classe anonima che estende SimpleFileVisitor<Path>.
                    La classe anonima permette di sovrascrivere i metodi visitFile e postVisitDirectory senza
                    dover dichiarare esplicitamente una nuova classe che estenda SimpleFileVisitor.
                    */
                    Files.walkFileTree(userDirectory, new SimpleFileVisitor<>() {
                        @Override
                        public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                            Files.delete(file); // Rimuove il file
                            return FileVisitResult.CONTINUE;
                        }

                        @Override
                        public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
                            Files.delete(dir); // Rimuove la directory vuota
                            return FileVisitResult.CONTINUE;
                        }
                    });
                } catch (IOException ex) {
                    log.severe(e.getMessage());
                    throw new DAOException("Error in UserJSONDAO (removing user directory): " + e.getMessage());
                }
            }

            log.severe("Error in UserJSONDAO (insert costumer): " + e.getMessage());
            throw new DAOException();
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

                return gson.fromJson(content, Costumer.class);

            } else {
                throw new UserNotFoundException();
            }
        } catch (IOException e) {
            log.severe("Error in UserJSONDAO (load costumer): " + e.getMessage());
            throw new DAOException();
        }
    }


    @Override
    public Librarian loadLibrarian(String email) throws UserNotFoundException, DAOException {
        try {
            Path librarianInfoFile = Paths.get(BASE_DIRECTORY, email, ConfigurationJSON.LIBRARIAN_INFO_FILE_NAME);

            if (Files.exists(librarianInfoFile)) {
                String content = Files.readString(librarianInfoFile);
                Gson gson = new GsonBuilder().registerTypeAdapter(LocalDate.class, new LocalDateAdapter()).create();

                return gson.fromJson(content, Librarian.class);

            } else {
                throw new UserNotFoundException();
            }
        } catch (IOException e) {
            log.severe("Error in UserJSONDAO (load librarian): " + e.getMessage());
            throw new DAOException();
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

            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            JsonObject jsonObject = gson.fromJson(content, JsonObject.class);
            return new LoginBean(email, jsonObject.getAsJsonPrimitive("password").getAsString(), SupportedUserTypes.valueOf(jsonObject.getAsJsonPrimitive("type").getAsString()));

        } catch (IOException e) {
            log.severe("Error in UserJSONDAO (getUserInfoByEmail): " + e.getMessage());
            throw new DAOException();
        }

    }

}
