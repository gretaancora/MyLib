package it.uniroma2.dicii.ispw.MyLib.view.cli;

import it.uniroma2.dicii.ispw.MyLib.controller.LoginController;
import it.uniroma2.dicii.ispw.MyLib.engineering.bean.LoginBean;
import it.uniroma2.dicii.ispw.MyLib.engineering.exceptions.DAOException;
import it.uniroma2.dicii.ispw.MyLib.engineering.exceptions.UnsupportedUserTypeException;
import it.uniroma2.dicii.ispw.MyLib.engineering.exceptions.WrongCredentialsException;
import it.uniroma2.dicii.ispw.MyLib.engineering.exceptions.UserNotFoundException;
import it.uniroma2.dicii.ispw.MyLib.engineering.singleton.Configurations;
import it.uniroma2.dicii.ispw.MyLib.other.Printer;
import it.uniroma2.dicii.ispw.MyLib.view.cli.costumer.HomeCostumerState;
import it.uniroma2.dicii.ispw.MyLib.view.cli.librarian.HomeLibrarianState;
import it.uniroma2.dicii.ispw.MyLib.model.Librarian;
import it.uniroma2.dicii.ispw.MyLib.model.User;
import it.uniroma2.dicii.ispw.MyLib.model.Costumer;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.logging.Logger;

public class LoginState extends State {

    private static final Logger log = Logger.getLogger(Configurations.LOGGER_NAME);
    @Override
    public void execute(StateMachine stateMachine) {
        LoginBean loginBean = this.authenticate();
        var loginController = new LoginController();
        User user = null;

        try {
            user = loginController.start(loginBean);
        } catch (UserNotFoundException e) {
            log.severe("Error in LoginState: " + e.getMessage());
            Printer.errorPrint("Wrong credentials. Try again...");
            this.execute(stateMachine);
        } catch (WrongCredentialsException e) {
            log.severe("Error in LoginState: " + e.getMessage());
            Printer.errorPrint("Wrong credentials. Try again...");
            this.execute(stateMachine);
        } catch (UnsupportedUserTypeException e) {
            log.severe("Error in LoginState: " + e.getMessage());
            Printer.errorPrint("Unsupported user type. Try again...");
            stateMachine.goBack();
        } catch (DAOException e) {
            log.severe("Error in LoginState: " + e.getMessage());
            Printer.errorPrint("Error occurred during login. Try again...");
            this.execute(stateMachine);
        }

        State homeState;

        if (user instanceof Librarian) {
            homeState = new HomeLibrarianState((Librarian) user);
        } else {
            homeState = new HomeCostumerState((Costumer) user);
        }

        stateMachine.goNext(homeState);
    }

    public LoginBean authenticate() {
        var in = new Scanner(System.in);
        String password;
        String email;

        while (true) {
            try {
                Printer.print("email: ");
                email = in.nextLine();
                break;
            } catch (NoSuchElementException e) {
                Printer.println("Insert an email!");
            }
        }

        while (true) {
            try {
                Printer.print("password: ");
                password = in.nextLine();
                break;
            } catch (NoSuchElementException e) {
                Printer.println("Insert a password!");
            }
        }

        return new LoginBean(email, password);

    }

    @Override
    public void showHeadline() {
        Printer.printlnBlu("--------------LOGIN--------------");
    }
}
