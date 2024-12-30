package it.uniroma2.dicii.ispw.mylib.view.gui.controller;

import it.uniroma2.dicii.ispw.mylib.controller.RegisterController;
import it.uniroma2.dicii.ispw.mylib.engineering.bean.RegisterBean;
import it.uniroma2.dicii.ispw.mylib.engineering.exceptions.DAOException;
import it.uniroma2.dicii.ispw.mylib.engineering.exceptions.EmailAlreadyInUseException;
import it.uniroma2.dicii.ispw.mylib.engineering.exceptions.InvalidEmail;
import it.uniroma2.dicii.ispw.mylib.engineering.singleton.Configurations;
import it.uniroma2.dicii.ispw.mylib.model.User;
import it.uniroma2.dicii.ispw.mylib.other.Printer;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.logging.Logger;
import java.util.regex.Pattern;

public class RegisterGUIController {

    @FXML
    private TextField nameField;
    @FXML
    private TextField surnameField;
    @FXML
    private TextField emailField;
    @FXML
    private TextField passwordField;
    @FXML
    private TextField confirmPasswordField;
    @FXML
    private Label fieldsError;

    protected User user;

    private static final Logger logger = Logger.getLogger(Configurations.LOGGER_NAME);
    

    // Pattern regex per validare l'email
    private static final String EMAIL_REGEX = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$";
    private static final Pattern EMAIL_PATTERN = Pattern.compile(EMAIL_REGEX);

    @FXML
    void registerMethod() {
        //metodo attivato dal pulsante di conferma sulla schermata di registrazione

        String name;
        String surname;
        String email;
        String password;
        String confirmPassword;


        //se sono stati compilati tutti i campi
        if(!this.nameField.getText().isEmpty() && !this.surnameField.getText().isEmpty() && !this.emailField.getText().isEmpty() && !this.passwordField.getText().isEmpty() && !this.confirmPasswordField.getText().isEmpty()){

            //prendo i dati inseriti dall'utente
            name = this.nameField.getText();
            surname = this.surnameField.getText();


            //controllo la forma dell'email inserito
            email = this.emailField.getText();
            try{
                isValidEmail(email);
            } catch(InvalidEmail e){
                fieldsError.setText("Invalid email.");
                return;
            }



            password = this.passwordField.getText();
            confirmPassword = this.confirmPasswordField.getText();

            //controllo sulle password
            if (!confirmPassword.equals(password)){
                fieldsError.setText("You inserted two different passwords!");
                return;
            }

        }
        else{
            fieldsError.setText("You must insert all the information required!");
            return;
        }

        try {
            //inserisco gli input ottenuti in BEAN
            RegisterBean registerBean = new RegisterBean(name, surname, email, password);

            //istanzio un controller applicativo e gli passo il bean contenente i dati per registrare l'utente
            RegisterController registerController = new RegisterController();
            registerController.registerCostumer(registerBean);

            Printer.println("---------------------------------------------------------");
            Printer.println("Registration succeeded!");
            loadConfirmation();

        }catch(EmailAlreadyInUseException e) {
            Printer.errorPrint("Application controller: " + e.getMessage());
            fieldsError.setText("Email already in use.");
        } catch (DAOException e) {
            Printer.errorPrint("Application controller: " + e.getMessage());
            fieldsError.setText("Error occurred during registration. Try again...");
        }
    }


    //funzione per il controllo della forma di email-------------------------------------------------
    public static void isValidEmail(String email) throws InvalidEmail {
        if (email == null || !EMAIL_PATTERN.matcher(email).matches()) {
            throw new InvalidEmail();
        }
    }


    //cambio pagina: quando effettuato correttamente la registrazione--------------------------------------------------------------
    public void loadConfirmation () {
        try {
            FXMLLoader loader = new FXMLLoader(RegisterGUIController.class.getResource("/view/regConfirmation.fxml"));
            loader.setControllerFactory(c -> new RegisterGUIController());
            Parent parent = loader.load();
            Scene scene = new Scene(parent);
            Stage stage = (Stage) fieldsError.getScene().getWindow();
            stage.setScene(scene);
        } catch (IOException e) {
            logger.severe("Error in RegisterGUIController " + e.getMessage());
        }
    }


    public void goToLogin () {
        try {
            FXMLLoader loader = new FXMLLoader(RegisterGUIController.class.getResource("/view/login.fxml"));
            Parent parent = loader.load();
            Scene scene = new Scene(parent);

            Stage stage = (Stage) fieldsError.getScene().getWindow();
            if (stage != null) {
                stage.setScene(scene);
                stage.show();
            } else {
                Printer.errorPrint("Not possible to obtain login window.");
            }

            assert stage != null;
            stage.setScene(scene);
        } catch (IOException e) {
            logger.severe("Error in RegisterGUIController " + e.getMessage());
        }
    }
}
