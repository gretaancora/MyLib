package it.uniroma2.dicii.ispw.MyLib.view.cli;

import it.uniroma2.dicii.ispw.MyLib.other.Printer;
import java.util.InputMismatchException;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class InitialState extends State {

    @Override
    public void execute(StateMachine stateMachine) {
        Scanner input = new Scanner(System.in);
        int choice;

        showMenu();

        while (true) {
            try {
                Printer.println("Enter your choice: ");
                choice = input.nextInt();
                input.nextLine();
                if(choice == 1) {
                    stateMachine.goNext(new LoginState());
                }else if (choice == 2) {
                    stateMachine.goNext(new RegisterState());
                }else {
                    Printer.println("Invalid choice. Try again...");
                }
            } catch (InputMismatchException e) {
                input.nextLine();
                System.out.println("Invalid choice. Try again...");
            } catch (NoSuchElementException e) {
                System.out.println("Invalid choice. Try again...");
            }
        }
    }

    @Override
    public void showMenu() {
        Printer.println("Choose one of the following options: ");
        Printer.println("1) Login");
        Printer.println("2) Register");
    }

    @Override
    public void showHeadline() {
        Printer.printlnBlu("--------------WELCOME TO MYLIB--------------");
        Printer.println("An account is needed in order to continue!");
    }
}
