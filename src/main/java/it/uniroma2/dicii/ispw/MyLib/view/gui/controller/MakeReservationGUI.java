package it.uniroma2.dicii.ispw.MyLib.view.gui.controller;

import it.uniroma2.dicii.ispw.MyLib.controller.MakeReservationController;
import it.uniroma2.dicii.ispw.MyLib.engineering.bean.BookBean;
import it.uniroma2.dicii.ispw.MyLib.engineering.bean.BorrowBean;
import it.uniroma2.dicii.ispw.MyLib.engineering.dao.MakeReservationDAO;
import it.uniroma2.dicii.ispw.MyLib.model.Costumer;
import it.uniroma2.dicii.ispw.MyLib.model.User;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;
import java.util.logging.Logger;

public class MakeReservationGUI extends HomeCostumerGUI{
    /*
    controller grafico che si occupa della pagina di conferma di una ripetizione
     */

    @FXML
    private Label ISBNLabel;
    @FXML
    private Label TitleLabel;
    @FXML
    private Label AuthorsLabel;
    @FXML
    private Label EditorLabel;
    @FXML
    private Label YearLabel;
    @FXML
    private Label GenresLabel;
    @FXML
    private Label ErrorLabel;

    BookBean book;
    List<BookBean> bookBeans = null;
    private static final Logger logger = Logger.getLogger(MakeReservationGUI.class.getName());

    public MakeReservationGUI(User user, BookBean bookBean, List<BookBean> bookBeans) {
        this.user = user;
        this.book = bookBean;
        this.bookBeans = bookBeans;
    }


    /*----------------------------------------------------------------------------------------------------------------*/
    public void initialize(){

        ISBNLabel.setText(book.getISBN());
        TitleLabel.setText(book.getTitle());
        EditorLabel.setText(book.getEditor());
        YearLabel.setText(book.getPubYear());
        AuthorsLabel.setText(book.getAuthors());
        GenresLabel.setText(book.getGenres());

    }

    public void reserveBook() {

        /*
        metodo lanciato quando viene premuto il pulsante invio di prenotazione*/

        //controlla che l'utente non abbia già due pending borrow
        if (((Costumer) user).getPending_borrows()==null || ((Costumer) user).getPending_borrows().size() < 2) {
            var borrowBean = new BorrowBean(book, user.getEmail());

            var makeReservationController = new MakeReservationController();
            makeReservationController.reserveBook(borrowBean);
            loadConfirmation();
        } else {
            ErrorLabel.setText("You have reached the maximum number of pending reservations.");
        }

    }


    /*-------------------------------------------------CAMBIO PAGINA--------------------------------------------------*/
    public void loadConfirmation() {

        /*nota: istanzio come controller grafico della pagina di conferma il HomeStudenteGui che ha tutte le funzionalità che mi servono*/
        try {
            FXMLLoader loader = new FXMLLoader(MakeReservationDAO.class.getResource("/view/confirmRequest.fxml"));
            loader.setControllerFactory(c -> new HomeCostumerGUI(this.user));
            Parent parent = loader.load();
            Scene scene = new Scene(parent);
            Stage stage = (Stage) ISBNLabel.getScene().getWindow();
            stage.setScene(scene);
        } catch (IOException e) {
            logger.severe("Error in MakeReservationGUI (loading confirmation page): " + e.getMessage());
        }
    }

    public void goToSearchResults() {
        try {
            FXMLLoader loader = new FXMLLoader(MakeReservationDAO.class.getResource("/view/searchResults.fxml"));
            loader.setControllerFactory(c -> new SearchResultsGUI(this.user, bookBeans));
            Parent parent = loader.load();
            Scene scene = new Scene(parent);
            Stage stage = (Stage) ISBNLabel.getScene().getWindow();
            stage.setScene(scene);
        } catch (IOException e) {
            e.printStackTrace();
            logger.severe("Error in MakeReservationGUI (going back to search results): " + e.getMessage());
        }
    }
}
