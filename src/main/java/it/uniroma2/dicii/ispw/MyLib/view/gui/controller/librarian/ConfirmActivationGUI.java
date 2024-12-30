package it.uniroma2.dicii.ispw.MyLib.view.gui.controller.librarian;

import it.uniroma2.dicii.ispw.MyLib.engineering.bean.BorrowBean;
import it.uniroma2.dicii.ispw.MyLib.model.Librarian;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class ConfirmActivationGUI extends HomeLibrarianGUI {
    @FXML
    private Label InitialDate;
    @FXML
    private Label EndDate;
    private Librarian lib;
    private BorrowBean bean;

    public ConfirmActivationGUI(Librarian lib, BorrowBean bean) {
        this.lib = lib;
        this.bean = bean;
    }

    public void initialize(){
        InitialDate.setText(String.valueOf(bean.getInDate()));
        EndDate.setText(String.valueOf(bean.getEndDate()));
    }
}
