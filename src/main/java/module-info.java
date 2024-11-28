module it.uniroma2.dicii.ispw.librarymanagmentsystem {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires com.google.gson;


    opens it.uniroma2.dicii.ispw.librarymanagmentsystem to javafx.fxml;
    exports it.uniroma2.dicii.ispw.librarymanagmentsystem to javafx.graphics;
}