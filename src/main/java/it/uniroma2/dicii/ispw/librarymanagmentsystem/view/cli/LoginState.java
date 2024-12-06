package it.uniroma2.dicii.ispw.librarymanagmentsystem.view.cli;

import it.uniroma2.dicii.ispw.librarymanagmentsystem.controller.LoginController;
import it.uniroma2.dicii.ispw.librarymanagmentsystem.engineering.bean.LoginBean;
import it.uniroma2.dicii.ispw.librarymanagmentsystem.engineering.exceptions.DAOException;
import it.uniroma2.dicii.ispw.librarymanagmentsystem.engineering.exceptions.UnsupportedUserTypeException;
import it.uniroma2.dicii.ispw.librarymanagmentsystem.engineering.exceptions.WrongCredentialsException;
import it.uniroma2.dicii.ispw.librarymanagmentsystem.engineering.exceptions.UserNotFoundException;
import it.uniroma2.dicii.ispw.librarymanagmentsystem.other.Printer;
import it.uniroma2.dicii.ispw.librarymanagmentsystem.view.cli.costumer.HomeCostumerState;
import it.uniroma2.dicii.ispw.librarymanagmentsystem.view.cli.librarian.HomeLibrarianState;
import it.uniroma2.dicii.ispw.librarymanagmentsystem.model.Librarian;
import it.uniroma2.dicii.ispw.librarymanagmentsystem.model.User;
import it.uniroma2.dicii.ispw.librarymanagmentsystem.model.Costumer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

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
        //ho finito, mi preparo al passaggio di stato
        State homeState;

        //il login controller deve gestire il caso in cui lo user non ha un ruolo supportato
        if (user instanceof Librarian) {
            homeState = new HomeLibrarianState((Librarian) user); //perché devo castare esplicitamente??
        } else {
            homeState = new HomeCostumerState((Costumer) user);
        }

        stateMachine.goNext(homeState);
    }

    public LoginBean authenticate(){
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        String password;
        String email;

        while (true) {
            try {
                System.out.print("email: ");
                email = reader.readLine();
                System.out.print("password: ");
                password = reader.readLine();
                return new LoginBean(email, password);
            } catch (IOException e) {
                Printer.errorPrint("Error occurred getting credentials. Try again...");
            }
        }
    }

    @Override
    public void showHeadline() {
        Printer.printlnBlu("--------------LOGIN--------------");
    }
}
