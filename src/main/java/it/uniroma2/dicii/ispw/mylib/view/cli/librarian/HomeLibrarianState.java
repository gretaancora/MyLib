package it.uniroma2.dicii.ispw.mylib.view.cli.librarian;

import it.uniroma2.dicii.ispw.mylib.other.Printer;
import it.uniroma2.dicii.ispw.mylib.view.cli.State;
import it.uniroma2.dicii.ispw.mylib.view.cli.StateMachine;
import it.uniroma2.dicii.ispw.mylib.view.cli.StateMachineImpl;
import it.uniroma2.dicii.ispw.mylib.model.Librarian;

import java.util.InputMismatchException;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class HomeLibrarianState extends State {
    private Librarian librarian;
    public HomeLibrarianState(Librarian librarian) {
        super();
        this.librarian = librarian;
    }

    @Override
    public void execute(StateMachine stateMachine) {
        showMenu();

        Scanner scan = new Scanner(System.in);
        int choice;

        while(true) {
            Printer.print("Enter your choice: ");

            try{
                choice = scan.nextInt();
                scan.nextLine();

                switch (choice){
                    case 1 -> Printer.println("Feature not implemented yet.");
                    case 2 -> stateMachine.goNext(new ManageReservationsState(librarian));
                    case 3 -> new StateMachineImpl().start();
                    default -> Printer.println("Invalid choice. Try again...");
                }

            } catch (InputMismatchException e){
                Printer.errorPrint("Invalid choice. Try again...");
                scan.nextLine();
            }catch (NoSuchElementException e){
                Printer.errorPrint("Invalid choice. Try again...");
            }
        }
    }

    @Override
    public void showMenu() {
        Printer.println("Choose one of the following options: ");
        Printer.println("1) show profile");
        Printer.println("2) manage reservations");
        Printer.println("3) logout");
    }
    @Override
    public void showHeadline() {
        Printer.printlnBlu("--------------HOME--------------");
    }
}
