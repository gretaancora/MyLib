package it.uniroma2.dicii.ispw.librarymanagmentsystem.view.gui.controller;

import it.uniroma2.dicii.ispw.librarymanagmentsystem.model.User;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.logging.Logger;

public class ManageReservationsGUI extends HomeLibrarianGUI{
    //controller grafico che contiene i metodi di gestione delle prenotazioni

    @FXML
    private AnchorPane pendingBox;
    private static final Logger logger = Logger.getLogger(ManageReservationsGUI.class.getName());

    public ManageReservationsGUI(){}
    public ManageReservationsGUI(User user) {this.user = user;}

    //vado alla pagina di tutte le richieste arrivate
    public void managePendingReservations(){

        //metodo che porta alla pagina di gestione delle prenotazioni
        try {
            FXMLLoader loader = new FXMLLoader(ManageReservationsGUI.class.getResource("/view/pendingReservations.fxml"));
            loader.setControllerFactory(c -> new ManagePendingReservationsGUI(user));
            Parent parent = loader.load();
            Scene scene = new Scene(parent);
            Stage stage = (Stage) pendingBox.getScene().getWindow();
            stage.setScene(scene);
        } catch (IOException e) {
            e.printStackTrace();
            logger.severe("Error in ManageReservationsGUI " + e.getMessage());
        }
    }



    public void manageActiveReservations(){
        //metodo non implementato
    }

    public void showExpiredReservations(){
        //metodo non implementato
    }

}
