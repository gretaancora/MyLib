package it.uniroma2.dicii.ispw.librarymanagmentsystem.view.gui.controller;

import it.uniroma2.dicii.ispw.librarymanagmentsystem.model.User;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.logging.Logger;

public class HomeLibrarianGUI {
    @FXML
    private VBox menu;
    protected User user;
    private static final Logger logger = Logger.getLogger(HomeLibrarianGUI.class.getName());

    //costruttori-------------------------------------------------------------
    protected HomeLibrarianGUI() {}
    public HomeLibrarianGUI(User user) {this.user = user;}



    @FXML
    public void showProfile(){
        //metodo non implementato
    }



    @FXML
    public void manageReservations(){

        //metodo che porta alla pagina di gestione delle prenotazioni
        try {
            FXMLLoader loader = new FXMLLoader(HomeLibrarianGUI.class.getResource("/view/manageReservation.fxml"));
            loader.setControllerFactory(c -> new ManageReservationsGUI(user));
            Parent parent = loader.load();
            Scene scene = new Scene(parent);
            Stage stage = (Stage) menu.getScene().getWindow();
            stage.setScene(scene);
        } catch (IOException e) {
            logger.severe("Error in HomeLibrarianGUI " + e.getMessage());
        }

    }

    public void logout(){
        try {
            FXMLLoader loader = new FXMLLoader(HomeLibrarianGUI.class.getResource("/view/login.fxml"));
            loader.setControllerFactory(c -> new LoginGUIController());
            Parent parent = loader.load();
            Scene scene = new Scene(parent);
            Stage stage = (Stage) menu.getScene().getWindow();
            stage.setScene(scene);
        } catch (IOException e) {
            logger.severe("Error in HomeLibrarianGUI " + e.getMessage());
        }
    }
}
