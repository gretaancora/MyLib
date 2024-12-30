module it.uniroma2.dicii.ispw.librarymanagmentsystem {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires com.google.gson;
    requires jbcrypt;

    exports it.uniroma2.dicii.ispw.mylib;
    opens it.uniroma2.dicii.ispw.mylib to javafx.fxml;

    exports it.uniroma2.dicii.ispw.mylib.controller;
    opens it.uniroma2.dicii.ispw.mylib.controller to javafx.fxml;

    exports it.uniroma2.dicii.ispw.mylib.engineering.bean;
    opens it.uniroma2.dicii.ispw.mylib.engineering.bean to javafx.fxml, com.google.gson;

    exports it.uniroma2.dicii.ispw.mylib.engineering.dao;
    opens it.uniroma2.dicii.ispw.mylib.engineering.dao to javafx.fxml;

    exports it.uniroma2.dicii.ispw.mylib.engineering.exceptions;
    opens it.uniroma2.dicii.ispw.mylib.engineering.exceptions to javafx.fxml;

    exports it.uniroma2.dicii.ispw.mylib.engineering.factory;
    opens it.uniroma2.dicii.ispw.mylib.engineering.factory to javafx.fxml;

    exports it.uniroma2.dicii.ispw.mylib.engineering.query;
    opens it.uniroma2.dicii.ispw.mylib.engineering.query to javafx.fxml;

    exports it.uniroma2.dicii.ispw.mylib.engineering.singleton;
    opens it.uniroma2.dicii.ispw.mylib.engineering.singleton to javafx.fxml;

    exports it.uniroma2.dicii.ispw.mylib.model;
    opens it.uniroma2.dicii.ispw.mylib.model to javafx.fxml, com.google.gson;

    exports it.uniroma2.dicii.ispw.mylib.other;
    opens it.uniroma2.dicii.ispw.mylib.other to javafx.fxml;

    exports it.uniroma2.dicii.ispw.mylib.view.cli.costumer;
    opens it.uniroma2.dicii.ispw.mylib.view.cli.costumer to javafx.fxml;

    exports it.uniroma2.dicii.ispw.mylib.view.cli.librarian;
    opens it.uniroma2.dicii.ispw.mylib.view.cli.librarian to javafx.fxml;

    exports it.uniroma2.dicii.ispw.mylib.view.cli;
    opens it.uniroma2.dicii.ispw.mylib.view.cli to javafx.fxml;

    exports it.uniroma2.dicii.ispw.mylib.view.gui.controller;
    opens it.uniroma2.dicii.ispw.mylib.view.gui.controller to javafx.fxml;
    exports it.uniroma2.dicii.ispw.mylib.view.gui.controller.costumer;
    opens it.uniroma2.dicii.ispw.mylib.view.gui.controller.costumer to javafx.fxml;
    exports it.uniroma2.dicii.ispw.mylib.view.gui.controller.librarian;
    opens it.uniroma2.dicii.ispw.mylib.view.gui.controller.librarian to javafx.fxml;

}