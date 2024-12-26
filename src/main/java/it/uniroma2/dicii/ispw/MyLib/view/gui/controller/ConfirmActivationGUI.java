package it.uniroma2.dicii.ispw.MyLib.view.gui.controller;

import it.uniroma2.dicii.ispw.MyLib.engineering.bean.BorrowBean;
import it.uniroma2.dicii.ispw.MyLib.model.Costumer;
import it.uniroma2.dicii.ispw.MyLib.model.Librarian;
import it.uniroma2.dicii.ispw.MyLib.model.User;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class ConfirmActivationGUI extends HomeLibrarianGUI{
    @FXML
    private Label InitialDate;
    @FXML
    private Label EndDate;
    private Librarian librarian;
    private BorrowBean bean;

    public ConfirmActivationGUI(Librarian librarian, BorrowBean bean) {
        this.librarian = librarian;
        this.bean = bean;
    }

    public void initialize(){
        InitialDate.setText(String.valueOf(bean.getInDate()));
        EndDate.setText(String.valueOf(bean.getEndDate()));
    }
}
