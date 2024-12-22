module it.uniroma2.dicii.ispw.librarymanagmentsystem {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires com.google.gson;
    requires jbcrypt;

    exports it.uniroma2.dicii.ispw.MyLib;
    opens it.uniroma2.dicii.ispw.MyLib to javafx.fxml;

    exports it.uniroma2.dicii.ispw.MyLib.controller;
    opens it.uniroma2.dicii.ispw.MyLib.controller to javafx.fxml;

    exports it.uniroma2.dicii.ispw.MyLib.engineering.bean;
    opens it.uniroma2.dicii.ispw.MyLib.engineering.bean to javafx.fxml, com.google.gson;

    exports it.uniroma2.dicii.ispw.MyLib.engineering.dao;
    opens it.uniroma2.dicii.ispw.MyLib.engineering.dao to javafx.fxml;

    exports it.uniroma2.dicii.ispw.MyLib.engineering.exceptions;
    opens it.uniroma2.dicii.ispw.MyLib.engineering.exceptions to javafx.fxml;

    exports it.uniroma2.dicii.ispw.MyLib.engineering.factory;
    opens it.uniroma2.dicii.ispw.MyLib.engineering.factory to javafx.fxml;

    exports it.uniroma2.dicii.ispw.MyLib.engineering.query;
    opens it.uniroma2.dicii.ispw.MyLib.engineering.query to javafx.fxml;

    exports it.uniroma2.dicii.ispw.MyLib.engineering.singleton;
    opens it.uniroma2.dicii.ispw.MyLib.engineering.singleton to javafx.fxml;

    exports it.uniroma2.dicii.ispw.MyLib.model;
    opens it.uniroma2.dicii.ispw.MyLib.model to javafx.fxml, com.google.gson;

    exports it.uniroma2.dicii.ispw.MyLib.other;
    opens it.uniroma2.dicii.ispw.MyLib.other to javafx.fxml;

    exports it.uniroma2.dicii.ispw.MyLib.view.cli.costumer;
    opens it.uniroma2.dicii.ispw.MyLib.view.cli.costumer to javafx.fxml;

    exports it.uniroma2.dicii.ispw.MyLib.view.cli.librarian;
    opens it.uniroma2.dicii.ispw.MyLib.view.cli.librarian to javafx.fxml;

    exports it.uniroma2.dicii.ispw.MyLib.view.cli;
    opens it.uniroma2.dicii.ispw.MyLib.view.cli to javafx.fxml;

    exports it.uniroma2.dicii.ispw.MyLib.view.gui.controller;
    opens it.uniroma2.dicii.ispw.MyLib.view.gui.controller to javafx.fxml;

}