# Library Management System — MyLib

This repository contains the final project for the **Software Engineering and Web Programming** course at the **University of Rome Tor Vergata**, Faculty of Computer Engineering.

---

## 📌 Executive Summary

**MyLib** is a Java-based library management application designed with two interaction modes: a **JavaFX graphical user interface (GUI)** and a **command-line interface (CLI)**. The interface can be selected through the project configuration.

The application includes user authentication and registration flows, with separate home screens for librarians and customers. The repository also contains the application source code, Maven build configuration, and supporting project documentation.

---

## 🎯 Technical Scope & Key Features

### 1. Application Interfaces

- **Graphical Interface (GUI):** Built with JavaFX and FXML.
- **Command-Line Interface (CLI):** Provides a text-based interaction flow implemented through a state machine.
- **Configurable Startup:** The desired interface is selected in `src/main/resources/config.properties`.

### 2. User Authentication & Roles

- **Login:** Users can sign in using their email address and password.
- **Registration:** New users can access the registration flow from the login screen.
- **Role-Based Navigation:** The application loads a different home screen depending on whether the authenticated user is a librarian or a customer.

### 3. Configuration & Persistence

Application settings are stored in `src/main/resources/config.properties`. The configuration includes the interface type, persistence mode, and database connection parameters.

The repository's default configuration uses the demo persistence mode. Database settings are provided for configuring a local MySQL connection when using a compatible persistence implementation.

### 4. Logging & Error Handling

- Application events are recorded using Java's logging framework.
- The startup process configures a log file named `file.log` in the application's working directory.
- Authentication and data-access errors are handled by the application and surfaced through its interface.

---

## 🧰 Technologies & Dependencies

- **Java 21** — Application language and compilation target.
- **JavaFX / FXML** — Graphical user interface.
- **Apache Maven** — Build and dependency management.
- **Maven Wrapper** — Runs Maven without requiring a separate Maven installation.
- **Gson** — JSON processing.
- **jBCrypt** — Password-hashing library.
- **JUnit 4 and JUnit 5** — Test dependencies.

Dependency versions and build settings are defined in [`pom.xml`](pom.xml).

---

## 🗂 Project Structure

```text
MyLib/
├── documentazione/              # Project documentation
├── src/
│   └── main/
│       ├── java/                 # Application source code
│       └── resources/            # FXML views, styles, and configuration
├── .mvn/wrapper/                 # Maven Wrapper configuration
├── mvnw                          # Maven Wrapper for Unix-like systems
├── mvnw.cmd                      # Maven Wrapper for Windows
├── pom.xml                       # Maven build and dependency configuration
└── README.md                     # Project overview and instructions
```

---

## 🚀 Getting Started

### 1. Prerequisites

- **JDK 21**
- Git

The Maven Wrapper is included in the repository, so a separate Maven installation is not required.

### 2. Clone the Repository

```bash
git clone https://github.com/gretaancora/MyLib.git
cd MyLib
```

### 3. Configure the Application

Open `src/main/resources/config.properties` and configure the desired interface and persistence settings. For example:

```properties
INTERFACE_TYPE=gui
PERSISTENCE_TYPE=demo
DB_URL=jdbc:mysql://localhost:3306/mylib_db
DB_USER=your_username
DB_PASSWD=your_password
```

Set `INTERFACE_TYPE` to `gui` for the graphical interface or `cli` for the command-line interface. The example above uses demo persistence. Update the database parameters only when configuring a database-backed setup.

**Security note:** Do not commit real database credentials to version control. Use local or environment-specific configuration for secrets.

### 4. Run the Application

**Linux / macOS**

```bash
./mvnw clean javafx:run
```

**Windows**

```bat
mvnw.cmd clean javafx:run
```

The application reads the selected interface from `config.properties` at startup.

### 5. Build the Project

**Linux / macOS**

```bash
./mvnw clean package
```

**Windows**

```bat
mvnw.cmd clean package
```

### 6. Run Tests

**Linux / macOS**

```bash
./mvnw test
```

**Windows**

```bat
mvnw.cmd test
```

---

## 📚 Documentation

Additional project documentation is available in the [`documentazione/`](documentazione/) directory. Build settings and dependency declarations can be found in [`pom.xml`](pom.xml).

---

## 🎓 Academic Context

MyLib was developed as the final project for the **Software Engineering and Web Programming** course at the **University of Rome Tor Vergata**, Faculty of Computer Engineering.

---
