package it.uniroma2.dicii.ispw.mylib.view.cli;

import it.uniroma2.dicii.ispw.mylib.controller.LoginController;
import it.uniroma2.dicii.ispw.mylib.engineering.bean.LoginBean;
import it.uniroma2.dicii.ispw.mylib.engineering.exceptions.DAOException;
import it.uniroma2.dicii.ispw.mylib.engineering.exceptions.UnsupportedUserTypeException;
import it.uniroma2.dicii.ispw.mylib.engineering.exceptions.WrongCredentialsException;
import it.uniroma2.dicii.ispw.mylib.engineering.exceptions.UserNotFoundException;
import it.uniroma2.dicii.ispw.mylib.other.Printer;
import it.uniroma2.dicii.ispw.mylib.view.cli.costumer.HomeCostumerState;
import it.uniroma2.dicii.ispw.mylib.view.cli.librarian.HomeLibrarianState;
import it.uniroma2.dicii.ispw.mylib.model.Librarian;
import it.uniroma2.dicii.ispw.mylib.model.User;
import it.uniroma2.dicii.ispw.mylib.model.Costumer;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class LoginState extends State {

    @Override
    public void execute(StateMachine stateMachine) {
        LoginBean loginBean = this.authenticate();
        var loginController = new LoginController();
        User user = null;

        try {
            user = loginController.start(loginBean);
        } catch (UserNotFoundException e) {
            Printer.errorPrint("Wrong credentials. Try again...");
            this.execute(stateMachine);
        } catch (WrongCredentialsException e) {
            Printer.errorPrint("Wrong credentials. Try again...");
            this.execute(stateMachine);
        } catch (UnsupportedUserTypeException e) {
            Printer.errorPrint("Unsupported user type. Try again...");
            stateMachine.goBack();
        } catch (DAOException e) {
            Printer.errorPrint("Error occurred during login. Try again...");
            this.execute(stateMachine);
        }

        State homeState = null;

        if (user instanceof Librarian librarian) {
            homeState = new HomeLibrarianState(librarian);
        } else if (user instanceof Costumer costumer) {
            homeState = new HomeCostumerState(costumer);
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
