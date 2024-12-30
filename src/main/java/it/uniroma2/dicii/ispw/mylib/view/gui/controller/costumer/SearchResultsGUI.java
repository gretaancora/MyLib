package it.uniroma2.dicii.ispw.mylib.view.gui.controller.costumer;

import it.uniroma2.dicii.ispw.mylib.engineering.bean.BookBean;
import it.uniroma2.dicii.ispw.mylib.engineering.singleton.Configurations;
import it.uniroma2.dicii.ispw.mylib.model.Costumer;
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

public class SearchResultsGUI extends HomeCostumerGUI {

    @FXML
    private TableView<BookBean> resultsTable;
    @FXML
    private TableColumn<BookBean, String> isbn;
    @FXML
    private TableColumn<BookBean, String> title;
    @FXML
    private TableColumn<BookBean, String> authors;
    @FXML
    private TableColumn<BookBean, String> editor;
    @FXML
    private TableColumn<BookBean, String> year;
    @FXML
    private TableColumn<BookBean, String> genres;
    @FXML
    private TableColumn<BookBean, String> availability;
    @FXML
    private TableColumn<BookBean, Button> borrow;


    //inizializzo una lista, in cui popolo gli elementi della tabella
    List<BookBean> bookBeans = null;
    private static final Logger logger = Logger.getLogger(Configurations.LOGGER_NAME);

    public SearchResultsGUI() {}

    protected SearchResultsGUI(Costumer costumer, List<BookBean> bookBeans){
        this.costumer = costumer;
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
        isbn.setCellValueFactory(new PropertyValueFactory<>("ISBN"));
        title.setCellValueFactory(new PropertyValueFactory<>("title"));
        authors.setCellValueFactory(new PropertyValueFactory<>("authors"));
        editor.setCellValueFactory(new PropertyValueFactory<>("editor"));
        year.setCellValueFactory(new PropertyValueFactory<>("pubYear"));
        genres.setCellValueFactory(new PropertyValueFactory<>("genres"));
        availability.setCellValueFactory(new PropertyValueFactory<>("availability"));

        borrow.setCellFactory(param -> new ButtonCell());
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
            loader.setControllerFactory(c -> new MakeReservationGUI(this.costumer, bookBean, getBookBeans()));
            Parent parent = loader.load();
            Scene scene = new Scene(parent);
            Stage stage = (Stage) resultsTable.getScene().getWindow();
            stage.setScene(scene);
        } catch (IOException e) {
            //logger.severe("Error in SearchResultsGUI (choosing book): " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void notifyAvailability(BookBean bookBean) {
        //da implementare
    }

    public void goToBorrowBook() {

        try {
            FXMLLoader loader = new FXMLLoader(SearchResultsGUI.class.getResource("/view/borrowBook.fxml"));
            loader.setControllerFactory(c -> new BorrowBookGUI(costumer));
            Parent parent = loader.load();
            Scene scene = new Scene(parent);
            Stage stage = (Stage) resultsTable.getScene().getWindow();
            stage.setScene(scene);
        } catch (IOException e) {
            logger.severe("Error in SearchResultsGUI (going back to search): " + e.getMessage());

        }

    }

    public List<BookBean> getBookBeans() {return this.bookBeans;}
}
