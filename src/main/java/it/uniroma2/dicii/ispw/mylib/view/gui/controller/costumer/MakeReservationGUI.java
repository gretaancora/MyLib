package it.uniroma2.dicii.ispw.mylib.view.gui.controller.costumer;

import it.uniroma2.dicii.ispw.mylib.controller.MakeReservationController;
import it.uniroma2.dicii.ispw.mylib.engineering.bean.BookBean;
import it.uniroma2.dicii.ispw.mylib.engineering.bean.BorrowBean;
import it.uniroma2.dicii.ispw.mylib.engineering.dao.MakeReservationMySQLDAO;
import it.uniroma2.dicii.ispw.mylib.engineering.singleton.Configurations;
import it.uniroma2.dicii.ispw.mylib.model.Costumer;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;
import java.util.logging.Logger;

public class MakeReservationGUI extends HomeCostumerGUI {
    /*
    controller grafico che si occupa della pagina di conferma di una ripetizione
     */

    @FXML
    private Label isbnLabel;
    @FXML
    private Label titleLabel;
    @FXML
    private Label authorsLabel;
    @FXML
    private Label editorLabel;
    @FXML
    private Label yearLabel;
    @FXML
    private Label genresLabel;
    @FXML
    private Label errorLabel;

    BookBean book;
    List<BookBean> bookBeans;
    private static final Logger logger = Logger.getLogger(Configurations.LOGGER_NAME);

    public MakeReservationGUI(Costumer costumer, BookBean bookBean, List<BookBean> bookBeans) {
        this.costumer = costumer;
        this.book = bookBean;
        this.bookBeans = bookBeans;
    }


    /*----------------------------------------------------------------------------------------------------------------*/
    public void initialize(){

        isbnLabel.setText(book.getIsbn());
        titleLabel.setText(book.getTitle());
        editorLabel.setText(book.getEditor());
        yearLabel.setText(book.getPubYear());
        authorsLabel.setText(book.getAuthors());
        genresLabel.setText(book.getGenres());

    }

    public void reserveBook() {

        /*
        metodo lanciato quando viene premuto il pulsante invio di prenotazione*/

        //controlla che l'utente non abbia già due pending borrow
        if (costumer.getPendingBorrows()==null || costumer.getPendingBorrows().size() < 2) {
            var borrowBean = new BorrowBean(book, costumer.getEmail());

            var makeReservationController = new MakeReservationController();
            makeReservationController.reserveBook(borrowBean, costumer);
            loadConfirmation();
        } else {
            errorLabel.setText("You have reached the maximum number of pending reservations.");
        }

    }


    /*-------------------------------------------------CAMBIO PAGINA--------------------------------------------------*/
    public void loadConfirmation() {

        /*nota: istanzio come controller grafico della pagina di conferma il HomeStudenteGui che ha tutte le funzionalità che mi servono*/
        try {
            FXMLLoader loader = new FXMLLoader(MakeReservationMySQLDAO.class.getResource("/view/confirmRequest.fxml"));
            loader.setControllerFactory(c -> new HomeCostumerGUI(this.costumer));
            Parent parent = loader.load();
            Scene scene = new Scene(parent);
            Stage stage = (Stage) isbnLabel.getScene().getWindow();
            stage.setScene(scene);
        } catch (IOException e) {
            logger.severe("Error in MakeReservationGUI (loading confirmation page): " + e.getMessage());
        }
    }

    public void goToSearchResults() {
        try {
            FXMLLoader loader = new FXMLLoader(MakeReservationMySQLDAO.class.getResource("/view/searchResults.fxml"));
            loader.setControllerFactory(c -> new SearchResultsGUI(this.costumer, bookBeans));
            Parent parent = loader.load();
            Scene scene = new Scene(parent);
            Stage stage = (Stage) isbnLabel.getScene().getWindow();
            stage.setScene(scene);
        } catch (IOException e) {
            e.printStackTrace();
            logger.severe("Error in MakeReservationGUI (going back to search results): " + e.getMessage());
        }
    }
}
