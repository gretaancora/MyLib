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

public class HomeCostumerGUI {
    @FXML
    private VBox menu;
    protected User user;

    private static final Logger logger = Logger.getLogger(HomeCostumerGUI.class.getName());


    protected HomeCostumerGUI() {}
    public HomeCostumerGUI(User user) { this.user = user;}

    //funzione per il bottone Prenota Ripetizione
    @FXML
    public void borrowBook(){
        //metodo che porta alla pagina di gestione del profilo
        try {
            FXMLLoader loader = new FXMLLoader(HomeCostumerGUI.class.getResource("src/main/resources/view/borrowBook.fxml"));
            loader.setControllerFactory(c -> new BorrowBookGUI(user));
            Parent parent = loader.load();
            Scene scene = new Scene(parent);
            Stage stage = (Stage) menu.getScene().getWindow();
            stage.setScene(scene);
        } catch (IOException e) {
            logger.severe("Error in HomeCostumerGUI " + e.getMessage());
        }
    }


    public void logout(){
        try {

            /*
            Prima mantenevamo i dati relativi alla sessione passando un bean di controller in controller.
            eliminare la sessione significa tornare alla pagina di login senza passare alcun parametro contente i dati della sessione
            quindi di base il controller grafico deve al massimo portare ad una pagina di conferma del logout (qui non è fatto), poi semplicemente carica il login
            */

            FXMLLoader loader = new FXMLLoader(HomeCostumerGUI.class.getResource("src/main/resources/view/login.fxml"));
            loader.setControllerFactory(c -> new LoginGUIController());
            Parent parent = loader.load();
            Scene scene = new Scene(parent);
            Stage stage = (Stage) menu.getScene().getWindow();
            stage.setScene(scene);
        } catch (IOException e) {
            logger.severe("Error in HomeCostumerGUI " + e.getMessage());
        }
    }

    public void showProfile() { /*da implementare*/ }
}
