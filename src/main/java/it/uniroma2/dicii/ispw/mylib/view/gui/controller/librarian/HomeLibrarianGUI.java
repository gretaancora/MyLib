package it.uniroma2.dicii.ispw.mylib.view.gui.controller.librarian;

import it.uniroma2.dicii.ispw.mylib.engineering.singleton.Configurations;
import it.uniroma2.dicii.ispw.mylib.model.Librarian;
import it.uniroma2.dicii.ispw.mylib.view.gui.controller.LoginGUIController;
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
    protected Librarian librarian;
    private static final Logger logger = Logger.getLogger(Configurations.LOGGER_NAME);

    //costruttori-------------------------------------------------------------
    protected HomeLibrarianGUI() {}
    public HomeLibrarianGUI(Librarian librarian) {this.librarian = librarian;}



    @FXML
    public void showProfile(){
        //metodo non implementato
    }



    @FXML
    public void manageReservations(){

        //metodo che porta alla pagina di gestione delle prenotazioni
        try {
            FXMLLoader loader = new FXMLLoader(HomeLibrarianGUI.class.getResource("/view/manageReservation.fxml"));
            loader.setControllerFactory(c -> new ManageReservationsGUI(librarian));
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
