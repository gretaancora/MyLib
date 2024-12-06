package it.uniroma2.dicii.ispw.librarymanagmentsystem.view.gui.controller;

import it.uniroma2.dicii.ispw.librarymanagmentsystem.controller.MakeReservationController;
import it.uniroma2.dicii.ispw.librarymanagmentsystem.engineering.bean.BookBean;
import it.uniroma2.dicii.ispw.librarymanagmentsystem.engineering.bean.BorrowBean;
import it.uniroma2.dicii.ispw.librarymanagmentsystem.engineering.dao.MakeReservationDAO;
import it.uniroma2.dicii.ispw.librarymanagmentsystem.model.Costumer;
import it.uniroma2.dicii.ispw.librarymanagmentsystem.model.User;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;
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
    private static final Logger logger = Logger.getLogger(MakeReservationGUI.class.getName());

    public MakeReservationGUI(User user, BookBean bookBean) {
        this.user = user;
        this.book = bookBean;
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
        if(((Costumer)user).getPending_borrows().size() >= 2){
            ErrorLabel.setText("You have reached the maximum number of pending reservations.");
            return;
        }

        var borrowBean = new BorrowBean(book, user.getEmail());

        var makeReservationController = new MakeReservationController();
        makeReservationController.reserveBook(borrowBean);
        loadConfirmation();
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
}
