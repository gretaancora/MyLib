package it.uniroma2.dicii.ispw.librarymanagmentsystem.other;

public class ConfigurationJSON {
    private ConfigurationJSON(){}
    public static final String PERSISTENCE_BASE_DIRECTORY = "src/main/resources/persistence";
    public static final String USER_BASE_DIRECTORY = "src/main/resources/persistence/users";
    public static final String PENDING_RESERVATIONS_BASE_DIRECTORY = "src/main/resources/persistence/pendingReservations";
    public static final String USER_INFO_FILE_NAME = "userInfo.json";
    public static final String NOTICE_FILE_NAME = "notice.json";
    public static final String FILE_EXTENCTION = ".json";
}
