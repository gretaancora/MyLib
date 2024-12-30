package it.uniroma2.dicii.ispw.mylib.view.gui.controller.librarian;

import it.uniroma2.dicii.ispw.mylib.controller.ManageReservationsController;
import it.uniroma2.dicii.ispw.mylib.engineering.bean.BookBean;
import it.uniroma2.dicii.ispw.mylib.engineering.bean.BorrowBean;
import it.uniroma2.dicii.ispw.mylib.engineering.exceptions.DAOException;
import it.uniroma2.dicii.ispw.mylib.engineering.singleton.Configurations;
import it.uniroma2.dicii.ispw.mylib.model.Borrow;
import it.uniroma2.dicii.ispw.mylib.model.Librarian;
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

public class ManagePendingReservationsGUI extends HomeLibrarianGUI {
    @FXML
    private TableView<BorrowBean> resultsTable;
    @FXML
    private TableColumn<BorrowBean, String> costumer;
    @FXML
    private TableColumn<BorrowBean, String> book;
    @FXML
    private TableColumn<BorrowBean, String> copy;
    @FXML
    private TableColumn<BorrowBean, String> position;
    @FXML
    private Label errorLabel;
    @FXML
    private TableColumn<BorrowBean, Button> activate;

    List<BorrowBean> borrowBeans = new ArrayList<>();
    private static final Logger logger = Logger.getLogger(Configurations.LOGGER_NAME);

    public ManagePendingReservationsGUI(Librarian librarian) {this.librarian = librarian;}

    public ManagePendingReservationsGUI() {}

    public void initialize(){

        //creo un'istanza di controller applicativo corrispondente
        var manageReservationsController = new ManageReservationsController();

        //chiamo la funzione nel controller applicativo per ottenere una lista di BEAN che contiene tutte le info per la tabella
        try {
            borrowBeans = manageReservationsController.getPendingReservations();
        } catch (DAOException e) {
            errorLabel.setText("Error in getting pending borrows. Try again...");
        }

        resultsTable.getItems().addAll(borrowBeans);

        costumer.setCellValueFactory(new PropertyValueFactory<>("costumer"));
        book.setCellValueFactory(cellData -> {
            BorrowBean borrowBean = cellData.getValue();
            BookBean bookBean = borrowBean.getBook();
            String isbn = bookBean.getIsbn();
            return new SimpleStringProperty(isbn);
        });
        copy.setCellValueFactory(new PropertyValueFactory<>("copy"));
        position.setCellValueFactory(new PropertyValueFactory<>("position"));
        //Imposto la factory per la colonna "Activate"
        activate.setCellFactory(param -> new ActivateButtonCell());

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
                    errorLabel.setText("Error in activating the selected borrow. Try again...");
                }
                loadConfirmation(bean);
            });
        }

        private void loadConfirmation(BorrowBean bean) {
            try {
                FXMLLoader loader = new FXMLLoader(ManagePendingReservationsGUI.class.getResource("/view/confirmActivation.fxml"));
                loader.setControllerFactory(c -> new ConfirmActivationGUI(librarian, bean));
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
