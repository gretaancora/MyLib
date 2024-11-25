package it.uniroma2.dicii.ispw.librarymanagmentsystem.view.gui.controller;

import it.uniroma2.dicii.ispw.librarymanagmentsystem.controller.MakeReservationController;
import it.uniroma2.dicii.ispw.librarymanagmentsystem.engineering.bean.FilterBean;
import it.uniroma2.dicii.ispw.librarymanagmentsystem.engineering.bean.BookBean;
import it.uniroma2.dicii.ispw.librarymanagmentsystem.model.Book;
import it.uniroma2.dicii.ispw.librarymanagmentsystem.model.User;
import it.uniroma2.dicii.ispw.librarymanagmentsystem.other.Printer;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class BorrowBookGUI extends HomeCostumerGUI{
    @FXML
    private TextField input;
    @FXML
    private ComboBox<String> filterType;
    @FXML
    private Label inputError;
    private String filter;
    private String type;

    private static final Logger logger = Logger.getLogger(BorrowBookGUI.class.getName());
    BookBean filtri = new BookBean();
    List<BookBean> searchResultsBeans = new ArrayList<>();

    public BorrowBookGUI(User user) {this.user = user;}

    @FXML
    public void initialize() {
        filterType.getItems().addAll("Title", "Author");
    }

    public void searchMethod(){

        if(this.input.getText().isEmpty()) {
            inputError.setText("Required field.");

        } else {
            this.filter = this.input.getText();
            this.type = filterType.getValue();

            //creo bean con i filtri della ricerca da passare <l controller
            var filterBean = new FilterBean(filter, type);

            //istanzio il controller per effettuare la ricercae gli passo il bean
            var makeReservationController = new MakeReservationController();

            //chiama il controller applicativo e gli passa il BEAN che contiene la subject
            loadResults(makeReservationController.searchMethod(filterBean));

            Printer.println("Search finished.");
        }
    }


    public void loadResults (List<Book> books) {

        try {
            FXMLLoader loader = new FXMLLoader(BorrowBookGUI.class.getResource("/com/example/studypal/view/studente/searchResults.fxml"));
            loader.setControllerFactory(c -> new RisultatiRicercaGuiController(user, risultatiRicercaBean, filtri));
            Parent parent = loader.load();
            Scene scene = new Scene(parent);
            Stage stage = (Stage) cercaMateria.getScene().getWindow();
            stage.setScene(scene);
        } catch (IOException e) {
            logger.severe("errore in CercaRipetizioneGui " + e.getMessage());
        }
    }
}
