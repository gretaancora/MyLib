package it.uniroma2.dicii.ispw.MyLib.view.gui.controller;

import it.uniroma2.dicii.ispw.MyLib.controller.ManageReservationsController;
import it.uniroma2.dicii.ispw.MyLib.engineering.bean.BookBean;
import it.uniroma2.dicii.ispw.MyLib.engineering.bean.BorrowBean;
import it.uniroma2.dicii.ispw.MyLib.engineering.exceptions.DAOException;
import it.uniroma2.dicii.ispw.MyLib.model.Borrow;
import it.uniroma2.dicii.ispw.MyLib.model.User;
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
    @FXML
    private TableColumn<BorrowBean, String> Copy;
    @FXML
    private TableColumn<BorrowBean, String> Position;
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
        var manageReservationsController = new ManageReservationsController();

        //chiamo la funzione nel controller applicativo per ottenere una lista di BEAN che contiene tutte le info per la tabella
        try {
            borrowBeans = manageReservationsController.getPendingReservations();
        } catch (DAOException e) {
            ErrorLabel.setText("Error in getting pending borrows. Try again...");
        }

        resultsTable.getItems().addAll(borrowBeans);

        Costumer.setCellValueFactory(new PropertyValueFactory<>("costumer"));
        Book.setCellValueFactory(cellData -> {
            BorrowBean borrowBean = cellData.getValue();
            BookBean book = borrowBean.getBook();
            String isbn = book.getISBN();
            return new SimpleStringProperty(isbn);
        });
        Copy.setCellValueFactory(new PropertyValueFactory<>("copy"));
        Position.setCellValueFactory(new PropertyValueFactory<>("position"));
        //Imposto la factory per la colonna "Activate"
        Activate.setCellFactory(param -> new ActivateButtonCell());

    }



    public class ActivateButtonCell extends TableCell<BorrowBean, Button> {
        private final Button btn = new Button("Activate");

        public ActivateButtonCell() {
            btn.setOnAction(event -> {
                BorrowBean bean = getTableView().getItems().get(getIndex());
                var manageReservationsController = new ManageReservationsController();
                try {
                    bean = manageReservationsController.activateReservation(bean);
                } catch (DAOException e) {
                    ErrorLabel.setText("Error in activating the selected borrow. Try again...");
                }
                loadConfirmation(bean);
            });
        }

        private void loadConfirmation(BorrowBean bean) {
            try {
                FXMLLoader loader = new FXMLLoader(ManagePendingReservationsGUI.class.getResource("/view/confirmActivation.fxml"));
                loader.setControllerFactory(c -> new ConfirmActivationGUI(user, bean));
                Parent parent = loader.load();
                Scene scene = new Scene(parent);
                Stage stage = (Stage) resultsTable.getScene().getWindow();
                stage.setScene(scene);
            } catch (IOException e) {
                e.printStackTrace();
                logger.severe("Error in ManagePendingReservationsGUI " + e.getMessage());
            }
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
