package it.uniroma2.dicii.ispw.MyLib.view.gui.controller;

import it.uniroma2.dicii.ispw.MyLib.controller.LoginController;
import it.uniroma2.dicii.ispw.MyLib.engineering.bean.LoginBean;
import it.uniroma2.dicii.ispw.MyLib.engineering.exceptions.DAOException;
import it.uniroma2.dicii.ispw.MyLib.engineering.exceptions.UnsupportedUserTypeException;
import it.uniroma2.dicii.ispw.MyLib.engineering.exceptions.UserNotFoundException;
import it.uniroma2.dicii.ispw.MyLib.engineering.exceptions.WrongCredentialsException;
import it.uniroma2.dicii.ispw.MyLib.engineering.singleton.Configurations;
import it.uniroma2.dicii.ispw.MyLib.model.Costumer;
import it.uniroma2.dicii.ispw.MyLib.model.Librarian;
import it.uniroma2.dicii.ispw.MyLib.model.User;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.logging.Logger;

public class LoginGUIController {
    @FXML
    private TextField emailField;

    @FXML
    private TextField passwordField;

    @FXML
    private Label credentialsError;

    @FXML
    private Label wrongCredentials;

    protected User user;

    private static final Logger logger = Logger.getLogger(Configurations.LOGGER_NAME);



    @FXML
    void loginMethod() {

        String email;
        String password;


        //controlla se i campi sono compilati
        if (!this.emailField.getText().isEmpty() && !this.passwordField.getText().isEmpty()) {
            email = this.emailField.getText();
            password = this.passwordField.getText();
        } else {
            credentialsError.setText("You must insert all the information required!");
            return;
        }

        try {

            var cred = new LoginBean(email, password);

            //istanziamo il controller applicativo che si deve occupare del login e gli passiamo il bean contenente le credenziali
            LoginController loginController = new LoginController();

            //prendiamo i dati dell'utente loggato (sessione)
            this.user = loginController.start(cred);

            //in base al ruolo dell'utente loggato carichiamo la pagina corretta della home
            loadHome(user);

        } catch (UserNotFoundException u) {
            credentialsError.setVisible(false);
            wrongCredentials.setText("Wrong credentials.");
        } catch (UnsupportedUserTypeException e) {
            credentialsError.setVisible(false);
            wrongCredentials.setText("Wrong credentials.");
        } catch (WrongCredentialsException e) {
            credentialsError.setVisible(false);
            wrongCredentials.setText("Wrong credentials.");
        } catch (DAOException e) {
            credentialsError.setVisible(false);
            wrongCredentials.setText("Error occurred during login. Try again....");
        }

    }

    //cambio pagina
    public void registerMethod () {
        try {
            FXMLLoader loader = new FXMLLoader(LoginGUIController.class.getResource("/view/register.fxml"));
            loader.setControllerFactory(c -> new RegisterGUIController());
            Parent parent = loader.load();
            Scene scene = new Scene(parent);
            Stage stage = (Stage) credentialsError.getScene().getWindow();
            stage.setScene(scene);
        } catch (IOException e) {
            logger.severe("Error in LoginGuiController " + e.getMessage());

        }
    }


    //metodo che carica la home corretta in base al ruolo
    public void loadHome(User user) {

        try {
            FXMLLoader loader;

            if (user instanceof Librarian) {
                loader = new FXMLLoader(LoginGUIController.class.getResource("/view/homeLibrarian.fxml"));
                loader.setControllerFactory(c -> new HomeLibrarianGUI((Librarian) user));
            } else {
                loader = new FXMLLoader(LoginGUIController.class.getResource("/view/homeCostumer.fxml"));
                loader.setControllerFactory(c -> new HomeCostumerGUI((Costumer) user));
            }
            Parent parent = loader.load();
            Scene scene = new Scene(parent);
            Stage stage = (Stage) credentialsError.getScene().getWindow();
            stage.setScene(scene);
        } catch (IOException e) {
            logger.severe("Error in LoginGuiController " + e.getMessage());
        }
    }


}
