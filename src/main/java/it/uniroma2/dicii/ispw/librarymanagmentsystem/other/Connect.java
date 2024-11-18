package it.uniroma2.dicii.ispw.librarymanagmentsystem.other;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class Connect {
    private String jdbc;
    private String user;
    private String password;
    private static Connect instance = null;
    private Connection conn = null;
    private static final String PATH = "src/main/resources/connection.properties";

    private Connect() {
    }

    /** Singleton */
    public static synchronized Connect getInstance() {
        if (instance == null) {
            instance = new Connect();
        }
        return instance;
    }

    public synchronized Connection getDBConnection() {
        if (this.conn == null) {
            getInfo();

            try{
                this.conn = DriverManager.getConnection(jdbc, user, password);
            } catch (SQLException e){
                Printer.errorPrint(String.format("Error in Connect.java %s", e.getMessage()));
            }

        }
        return this.conn;
    }

    private void getInfo() {
        try(FileInputStream fileInputStream = new FileInputStream(PATH)) {

            // Load DB Connection info from Properties file
            Properties prop = new Properties() ;
            prop.load(fileInputStream);

            jdbc = prop.getProperty("JDBC_URL") ;
            user = prop.getProperty("USER") ;
            password = prop.getProperty("PASSWORD") ;

        } catch (IOException e){
            Printer.errorPrint(e.getMessage());
        }
    }
}
