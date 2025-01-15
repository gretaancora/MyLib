package it.uniroma2.dicii.ispw.mylib.view.cli;

import it.uniroma2.dicii.ispw.mylib.controller.RegisterController;
import it.uniroma2.dicii.ispw.mylib.engineering.bean.RegisterBean;
import it.uniroma2.dicii.ispw.mylib.engineering.exceptions.DAOException;
import it.uniroma2.dicii.ispw.mylib.engineering.exceptions.EmailAlreadyInUseException;
import it.uniroma2.dicii.ispw.mylib.engineering.exceptions.InvalidEmail;
import it.uniroma2.dicii.ispw.mylib.other.Printer;

import java.util.Scanner;
import java.util.regex.Pattern;

public class RegisterState extends State {
    private static final String EMAIL_REGEX = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$";
    private static final Pattern EMAIL_PATTERN = Pattern.compile(EMAIL_REGEX);

    private final Scanner scanner = new Scanner(System.in);

    @Override
    public void execute(StateMachine stateMachine) {

        String email;
        while (true) {
            try{
                Printer.print("Email: ");
                email = scanner.nextLine();
                isValidEmail(email);
                break;
            } catch(InvalidEmail e){
                Printer.errorPrint("Invalid email! Try again...");
            }
        }

        String password;
        while(true) {
            Printer.print("Password: ");
            password = scanner.next();

            Printer.print("Confirm Password: ");
            String confirmPassword = scanner.next();

            if(password.equals(confirmPassword)) break;
            else Printer.println("The passwords don't match. Try again...");
        }

        Printer.print("Name: ");
        String name = scanner.next();

        Printer.print("Surname: ");
        String surname = scanner.next();


        try {
            var registerController = new RegisterController();
            registerController.registerCostumer(new RegisterBean(name, surname, email, password));
        } catch (EmailAlreadyInUseException e) {
            Printer.errorPrint("Email already in use.");
            stateMachine.goBack();
        } catch (DAOException e) {
            Printer.errorPrint("Error occurred during registration. Try again...");
            this.execute(stateMachine);
        }

        Printer.println("Registration succeeded!");
        stateMachine.goBack();

    }


    @Override
    public void showHeadline() {
        Printer.printlnBlu("--------------REGISTER--------------");
    }

    private static void isValidEmail(String email) throws InvalidEmail {
        if (email == null || !EMAIL_PATTERN.matcher(email).matches()) {
            throw new InvalidEmail();
        }
    }

}
