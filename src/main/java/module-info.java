module it.uniroma2.dicii.ispw.librarymanagmentsystem {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires com.google.gson;
    requires jbcrypt;

    exports it.uniroma2.dicii.ispw.librarymanagmentsystem;
    opens it.uniroma2.dicii.ispw.librarymanagmentsystem to javafx.fxml;

    exports it.uniroma2.dicii.ispw.librarymanagmentsystem.controller;
    opens it.uniroma2.dicii.ispw.librarymanagmentsystem.controller to javafx.fxml;

    exports it.uniroma2.dicii.ispw.librarymanagmentsystem.engineering.bean;
    opens it.uniroma2.dicii.ispw.librarymanagmentsystem.engineering.bean to javafx.fxml;

    exports it.uniroma2.dicii.ispw.librarymanagmentsystem.engineering.dao;
    opens it.uniroma2.dicii.ispw.librarymanagmentsystem.engineering.dao to javafx.fxml;

    exports it.uniroma2.dicii.ispw.librarymanagmentsystem.engineering.exceptions;
    opens it.uniroma2.dicii.ispw.librarymanagmentsystem.engineering.exceptions to javafx.fxml;

    exports it.uniroma2.dicii.ispw.librarymanagmentsystem.engineering.factory;
    opens it.uniroma2.dicii.ispw.librarymanagmentsystem.engineering.factory to javafx.fxml;

    exports it.uniroma2.dicii.ispw.librarymanagmentsystem.engineering.query;
    opens it.uniroma2.dicii.ispw.librarymanagmentsystem.engineering.query to javafx.fxml;

    exports it.uniroma2.dicii.ispw.librarymanagmentsystem.engineering.singleton;
    opens it.uniroma2.dicii.ispw.librarymanagmentsystem.engineering.singleton to javafx.fxml;

    exports it.uniroma2.dicii.ispw.librarymanagmentsystem.model;
    opens it.uniroma2.dicii.ispw.librarymanagmentsystem.model to javafx.fxml;

    exports it.uniroma2.dicii.ispw.librarymanagmentsystem.other;
    opens it.uniroma2.dicii.ispw.librarymanagmentsystem.other to javafx.fxml;

    exports it.uniroma2.dicii.ispw.librarymanagmentsystem.view.cli.costumer;
    opens it.uniroma2.dicii.ispw.librarymanagmentsystem.view.cli.costumer to javafx.fxml;

    exports it.uniroma2.dicii.ispw.librarymanagmentsystem.view.cli.librarian;
    opens it.uniroma2.dicii.ispw.librarymanagmentsystem.view.cli.librarian to javafx.fxml;

    exports it.uniroma2.dicii.ispw.librarymanagmentsystem.view.cli;
    opens it.uniroma2.dicii.ispw.librarymanagmentsystem.view.cli to javafx.fxml;


    exports it.uniroma2.dicii.ispw.librarymanagmentsystem.view.gui.controller;
    opens it.uniroma2.dicii.ispw.librarymanagmentsystem.view.gui.controller to javafx.fxml;

}