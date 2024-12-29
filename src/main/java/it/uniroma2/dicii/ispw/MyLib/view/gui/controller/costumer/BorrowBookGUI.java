package it.uniroma2.dicii.ispw.MyLib.view.gui.controller.costumer;

import it.uniroma2.dicii.ispw.MyLib.controller.MakeReservationController;
import it.uniroma2.dicii.ispw.MyLib.engineering.bean.FilterBean;
import it.uniroma2.dicii.ispw.MyLib.engineering.bean.BookBean;
import it.uniroma2.dicii.ispw.MyLib.engineering.exceptions.DAOException;
import it.uniroma2.dicii.ispw.MyLib.engineering.singleton.Configurations;
import it.uniroma2.dicii.ispw.MyLib.model.Costumer;
import it.uniroma2.dicii.ispw.MyLib.other.Printer;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;
import java.util.logging.Logger;

public class BorrowBookGUI extends HomeCostumerGUI {
    @FXML
    private TextField searchBook;
    @FXML
    private ComboBox<String> filterType;
    @FXML
    private Label fieldsError;

    private String filter;
    private String type;

    private static final Logger logger = Logger.getLogger(Configurations.LOGGER_NAME);

    public BorrowBookGUI(Costumer costumer) {this.costumer = costumer;}

    @FXML
    public void initialize() {
        filterType.getItems().addAll("Title", "Author");
    }

    public void searchMethod(){

        if(this.searchBook.getText().isEmpty()) {
            fieldsError.setText("Required field.");

        } else {
            this.filter = this.searchBook.getText();
            this.type = filterType.getValue();

            //creo bean con i filtri della ricerca da passare <l controller
            var filterBean = new FilterBean(filter, type);

            //istanzio il controller per effettuare la ricercae gli passo il bean
            var makeReservationController = new MakeReservationController();

            //chiama il controller applicativo e gli passa il BEAN che contiene la subject
            try {
                loadResults(makeReservationController.searchMethod(filterBean));
            } catch (DAOException e) {
                logger.severe("Error in BorrowBookGUI: " + e.getMessage());
                fieldsError.setText("Error searching for book.");
            }

            Printer.println("Search finished.");
        }
    }


    public void loadResults (List<BookBean> books) {

        try {
            FXMLLoader loader = new FXMLLoader(BorrowBookGUI.class.getResource("/view/searchResults.fxml"));
            loader.setControllerFactory(c -> new SearchResultsGUI(costumer, books));
            Parent parent = loader.load();
            Scene scene = new Scene(parent);
            Stage stage = (Stage) searchBook.getScene().getWindow();
            stage.setScene(scene);
        } catch (IOException e) {
            logger.severe("Error in BorrwBookGUI " + e.getMessage());
            fieldsError.setText("Error loading search results.");
        }
    }
}
