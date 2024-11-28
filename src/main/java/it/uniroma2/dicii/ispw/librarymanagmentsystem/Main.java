package it.uniroma2.dicii.ispw.librarymanagmentsystem;

import it.uniroma2.dicii.ispw.librarymanagmentsystem.other.Printer;
import it.uniroma2.dicii.ispw.librarymanagmentsystem.view.cli.StateMachineImpl;
import it.uniroma2.dicii.ispw.librarymanagmentsystem.view.gui.utils.FxmlFiles;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Main extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        // Carica le proprietà dal file di configurazione
        Properties properties = loadConfigurationProperties();

        // Ottieni il tipo di interfaccia dalle proprietà
        String interfaceType = properties.getProperty("INTERFACE_TYPE");

        if (interfaceType.equalsIgnoreCase("gui")) {
            // Interfaccia grafica
            launchGUI(stage);
        } else if (interfaceType.equalsIgnoreCase("cli")) {
            // Interfaccia a riga di comando
            launchCLI();
        } else {
            Printer.errorPrint("Type of interface specified not supported, launching gui interface...");
            // Interfaccia grafica
            launchGUI(stage);
        }
    }

    private void launchGUI(Stage stage) throws IOException {
        var fxmlLoader = new FXMLLoader(getClass().getResource("src/main/resources/view/login.fxml"));
        var scene = new Scene(fxmlLoader.load());
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();
    }

    private void launchCLI() {
        var cli = new StateMachineImpl();
        cli.start();
    }

    /** Lettura dal file di configurazione per la scelta dell'interfaccia */
    private Properties loadConfigurationProperties() {
        var properties = new Properties();
        try (InputStream input = getClass().getClassLoader().getResourceAsStream("config.properties")) {
            if (input != null) {
                properties.load(input);
            } else {
                Printer.errorPrint("Configuration file not found.");
            }
        } catch (IOException e) {
            Printer.errorPrint(String.format("Error reading configuration file: %s", e.getMessage()));
        }
        return properties;
    }

    public static void main(String[] args) {
        launch();
    }
}
