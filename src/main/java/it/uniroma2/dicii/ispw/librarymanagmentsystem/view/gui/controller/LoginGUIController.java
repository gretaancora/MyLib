package it.uniroma2.dicii.ispw.librarymanagmentsystem.view.gui.controller;

import it.uniroma2.dicii.ispw.librarymanagmentsystem.controller.LoginController;
import it.uniroma2.dicii.ispw.librarymanagmentsystem.engineering.bean.LoginBean;
import it.uniroma2.dicii.ispw.librarymanagmentsystem.engineering.exceptions.DAOException;
import it.uniroma2.dicii.ispw.librarymanagmentsystem.engineering.exceptions.UnsupportedUserTypeException;
import it.uniroma2.dicii.ispw.librarymanagmentsystem.engineering.exceptions.UserNotFoundException;
import it.uniroma2.dicii.ispw.librarymanagmentsystem.engineering.exceptions.WrongCredentialsException;
import it.uniroma2.dicii.ispw.librarymanagmentsystem.model.Librarian;
import it.uniroma2.dicii.ispw.librarymanagmentsystem.model.User;
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

    private static final Logger logger = Logger.getLogger(LoginGUIController.class.getName());



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
            FXMLLoader loader = new FXMLLoader(LoginGUIController.class.getResource("src/main/resources/view/register.fxml"));
            loader.setControllerFactory(c -> new RegisterGUIController(this.user));
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
                loader = new FXMLLoader(LoginGUIController.class.getResource("src/main/resources/view/homeLibrarian.fxml"));
                loader.setControllerFactory(c -> new HomeLibrarianGUI(user));
            } else {
                loader = new FXMLLoader(LoginGUIController.class.getResource("src/main/resources/view/homeCostumer.fxml"));
                loader.setControllerFactory(c -> new HomeCostumerGUI(user));
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
