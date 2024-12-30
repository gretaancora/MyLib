package it.uniroma2.dicii.ispw.mylib.view.cli.librarian;

import it.uniroma2.dicii.ispw.mylib.other.Printer;
import it.uniroma2.dicii.ispw.mylib.view.cli.State;
import it.uniroma2.dicii.ispw.mylib.view.cli.StateMachine;
import it.uniroma2.dicii.ispw.mylib.model.Librarian;
import it.uniroma2.dicii.ispw.mylib.view.cli.StateMachineImpl;

import java.util.InputMismatchException;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class ManageReservationsState extends State {

    private Librarian librarian;

    public ManageReservationsState(Librarian librarian) {
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

                switch (choice) {
                    case 1 -> stateMachine.goNext(new ManagePendingReservationsState(librarian));
                    case 2, 3 -> Printer.println("Feature not implemented yet.");
                    case 4 -> new StateMachineImpl().start();
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
        Printer.println("1) pending reservations");
        Printer.println("2) active reservations");
        Printer.println("3) expired reservations");
        Printer.println("4) logout");
    }
    @Override
    public void showHeadline() {
        Printer.printlnBlu("--------------MANAGE RESERVATIONS--------------");
    }

}
