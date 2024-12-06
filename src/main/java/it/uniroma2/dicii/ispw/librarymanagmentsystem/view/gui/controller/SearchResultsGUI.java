package it.uniroma2.dicii.ispw.librarymanagmentsystem.view.gui.controller;

import it.uniroma2.dicii.ispw.librarymanagmentsystem.engineering.bean.BookBean;
import it.uniroma2.dicii.ispw.librarymanagmentsystem.model.User;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;
import java.util.logging.Logger;

public class SearchResultsGUI extends HomeCostumerGUI{

    @FXML
    private TableView<BookBean> resultsTable;
    @FXML
    private TableColumn<BookBean, String> ISBN;
    @FXML
    private TableColumn<BookBean, String> Title;
    @FXML
    private TableColumn<BookBean, String> Authors;
    @FXML
    private TableColumn<BookBean, String> Editor;
    @FXML
    private TableColumn<BookBean, String> Year;
    @FXML
    private TableColumn<BookBean, String> Genres;
    @FXML
    private TableColumn<BookBean, String> Availability;
    @FXML
    private TableColumn<BookBean, Button> Borrow;


    //inizializzo una lista, in cui popolo gli elementi della tabella
    List<BookBean> bookBeans = null;
    private static final Logger logger = Logger.getLogger(SearchResultsGUI.class.getName());

    public SearchResultsGUI() {}

    protected SearchResultsGUI(User user,List<BookBean> bookBeans){
        this.user = user;
        this.bookBeans = bookBeans;
    }

    @FXML
    public void initialize() {
        configureTable();
        impostaColonneTabella();
    }

    private void configureTable() {
        resultsTable.getItems().addAll(bookBeans);
    }

    private void impostaColonneTabella() {
        ISBN.setCellValueFactory(new PropertyValueFactory<>("ISBN"));
        Title.setCellValueFactory(new PropertyValueFactory<>("title"));
        Authors.setCellValueFactory(new PropertyValueFactory<>("authors"));
        Editor.setCellValueFactory(new PropertyValueFactory<>("editor"));
        Year.setCellValueFactory(new PropertyValueFactory<>("year"));
        Genres.setCellValueFactory(new PropertyValueFactory<>("genres"));
        Availability.setCellValueFactory(new PropertyValueFactory<>("availability"));

        Borrow.setCellFactory(param -> new ButtonCell());
    }


    public class ButtonCell extends TableCell<BookBean, Button> {
        private final Button btn1 = new Button("Borrow"); // Per i libri disponibili
        private final Button btn2 = new Button("Notify"); // Per i libri non disponibili

        public ButtonCell() {
            // Configura il pulsante "Borrow"
            btn1.setOnAction(event -> {
                BookBean bookBean = getTableView().getItems().get(getIndex());
                chooseBook(bookBean); // Metodo per gestire il prestito
            });

            // Configura il pulsante "Notify"
            btn2.setOnAction(event -> {
                BookBean bookBean = getTableView().getItems().get(getIndex());
                notifyAvailability(bookBean); // Metodo per notificare la disponibilità
            });
        }

        @Override
        protected void updateItem(Button item, boolean empty) {
            super.updateItem(item, empty);

            if (empty || getTableRow() == null || getTableRow().getItem() == null) {
                // Se la cella è vuota, rimuove qualsiasi contenuto
                setGraphic(null);
                setText(null);
            } else {
                // Recupera l'oggetto BookBean associato alla riga corrente
                BookBean bookBean = (BookBean) getTableRow().getItem();

                // Verifica il valore di availability
                if (bookBean.getAvailability().equalsIgnoreCase("available")) { // Se disponibile, mostra "Borrow"
                    setGraphic(btn1);
                } else { // Se non disponibile, mostra "Notify"
                    setGraphic(btn2);
                }
            }
        }
    }



    public void chooseBook(BookBean bookBean) {

        /* carica la pagina di conferma della prenotazione */

        try {
            FXMLLoader loader = new FXMLLoader(SearchResultsGUI.class.getResource("/view/makeReservation.fxml"));
            loader.setControllerFactory(c -> new MakeReservationGUI(this.user, bookBean));
            Parent parent = loader.load();
            Scene scene = new Scene(parent);
            Stage stage = (Stage) resultsTable.getScene().getWindow();
            stage.setScene(scene);
        } catch (IOException e) {
            logger.severe("Error in SearchResultsGUI (choosing book): " + e.getMessage());
        }
    }

    public void notifyAvailability(BookBean bookBean) {
        //da implementare
    }

    public void goToBorrowBook() {

        try {
            FXMLLoader loader = new FXMLLoader(SearchResultsGUI.class.getResource("/view/borrowBook.fxml"));
            loader.setControllerFactory(c -> new BorrowBookGUI(user));
            Parent parent = loader.load();
            Scene scene = new Scene(parent);
            Stage stage = (Stage) resultsTable.getScene().getWindow();
            stage.setScene(scene);
        } catch (IOException e) {
            logger.severe("Error in SearchResultsGUI (going back to search): " + e.getMessage());

        }

    }
}
