package it.uniroma2.dicii.ispw.mylib.view.gui.controller.librarian;

import it.uniroma2.dicii.ispw.mylib.engineering.bean.BorrowBean;
import it.uniroma2.dicii.ispw.mylib.model.Librarian;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class ConfirmActivationGUI extends HomeLibrarianGUI {
    @FXML
    private Label initialDate;
    @FXML
    private Label endDate;
    private Librarian lib;
    private BorrowBean bean;

    public ConfirmActivationGUI(Librarian lib, BorrowBean bean) {
        this.lib = lib;
        this.bean = bean;
    }

    public void initialize(){
        initialDate.setText(String.valueOf(bean.getInDate()));
        endDate.setText(String.valueOf(bean.getEndDate()));
    }
}
