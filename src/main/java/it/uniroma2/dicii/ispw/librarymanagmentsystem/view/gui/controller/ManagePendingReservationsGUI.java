package it.uniroma2.dicii.ispw.librarymanagmentsystem.view.gui.controller;

import it.uniroma2.dicii.ispw.librarymanagmentsystem.controller.ManageReservationsController;
import it.uniroma2.dicii.ispw.librarymanagmentsystem.engineering.bean.BookBean;
import it.uniroma2.dicii.ispw.librarymanagmentsystem.engineering.bean.BorrowBean;
import it.uniroma2.dicii.ispw.librarymanagmentsystem.engineering.exceptions.DAOException;
import it.uniroma2.dicii.ispw.librarymanagmentsystem.model.Borrow;
import it.uniroma2.dicii.ispw.librarymanagmentsystem.model.User;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class ManagePendingReservationsGUI extends HomeLibrarianGUI{
    @FXML
    private TableView<BorrowBean> resultsTable;
    @FXML
    private TableColumn<BorrowBean, String> Costumer;
    @FXML
    private TableColumn<BorrowBean, String> Book;
    private TableColumn<BorrowBean, String> Copy;
    @FXML
    private Label ErrorLabel;
    @FXML
    private TableColumn<BorrowBean, Button> Activate;


    //inizializzo una lista, in cui popolo gli elementi della tabella
    //private RichiesteArrivateCollection richiesteArrivateCollection; //istanza del Concrete Subject del pattern Observer
    List<Borrow> borrows = new ArrayList<>();
    List<BorrowBean> borrowBeans = new ArrayList<>();

    private static final Logger logger = Logger.getLogger(ManagePendingReservationsGUI.class.getName());

    public ManagePendingReservationsGUI(User user) {this.user = user;}

    public ManagePendingReservationsGUI() {}

    public void initialize(){

        /*
        //pattern Observer
        richiesteArrivateCollection = RichiesteArrivateCollection.getInstance();

        richiesteArrivateCollection.attach(this);

         */

        //creo un'istanza di controller applicativo corrispondente
        var manageReservationsController = new ManageReservationsController(user);

        //chiamo la funzione nel controller applicativo per ottenere una lista di BEAN che contiene tutte le info per la tabella
        try {
            borrowBeans = manageReservationsController.getPendingReservations();
        } catch (DAOException e) {
            ErrorLabel.setText("Error in getting pending borrows. Try again...");
        }


        Costumer.setCellValueFactory(new PropertyValueFactory<>("costumer"));
        Book.setCellValueFactory(cellData -> {
            BorrowBean borrowBean = cellData.getValue();
            BookBean book = borrowBean.getBook();
            String isbn = book.getISBN();
            return new SimpleStringProperty(isbn);
        });
        Copy.setCellValueFactory(new PropertyValueFactory<>("copy"));

        // Aggiungo tutti i nuovi elementi alla TableView
        resultsTable.getItems().clear();
        resultsTable.getItems().addAll(borrowBeans);

        //Imposto la factory per la colonna "Activate"
        Activate.setCellFactory(param -> new ActivateButtonCell());

    }



    public class ActivateButtonCell extends TableCell<BorrowBean, Button> {
        private final Button btn = new Button("Activate");

        public ActivateButtonCell() {
            btn.setOnAction(event -> {
                BorrowBean bean = getTableView().getItems().get(getIndex());
                var manageReservationsController = new ManageReservationsController(user);
                try {
                    manageReservationsController.activateReservation(bean);
                } catch (DAOException e) {
                    ErrorLabel.setText("Error in activating the selected borrow. Try again...");
                }
            });
        }

        @Override
        protected void updateItem(Button item, boolean empty) {
            super.updateItem(item, empty);
            if (empty) {
                setGraphic(null);
            } else {
                setGraphic(btn);
            }
        }

    }

}
