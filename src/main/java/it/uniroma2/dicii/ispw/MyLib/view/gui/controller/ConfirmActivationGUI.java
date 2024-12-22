package it.uniroma2.dicii.ispw.MyLib.view.gui.controller;

import it.uniroma2.dicii.ispw.MyLib.engineering.bean.BorrowBean;
import it.uniroma2.dicii.ispw.MyLib.model.User;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class ConfirmActivationGUI extends HomeLibrarianGUI{
    @FXML
    private Label InitialDate;
    @FXML
    private Label EndDate;
    private User user;
    private BorrowBean bean;

    public ConfirmActivationGUI(User user, BorrowBean bean) {
        this.user = user;
        this.bean = bean;
    }

    public void initialize(){
        InitialDate.setText(String.valueOf(bean.getInDate()));
        EndDate.setText(String.valueOf(bean.getEndDate()));
    }
}
