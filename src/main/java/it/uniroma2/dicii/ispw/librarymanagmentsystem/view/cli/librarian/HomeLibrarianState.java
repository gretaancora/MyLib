package it.uniroma2.dicii.ispw.librarymanagmentsystem.view.cli.librarian;

import it.uniroma2.dicii.ispw.librarymanagmentsystem.other.Printer;
import it.uniroma2.dicii.ispw.librarymanagmentsystem.view.cli.State;
import it.uniroma2.dicii.ispw.librarymanagmentsystem.view.cli.StateMachine;
import it.uniroma2.dicii.ispw.librarymanagmentsystem.view.cli.StateMachineImpl;
import it.uniroma2.dicii.ispw.librarymanagmentsystem.model.Librarian;

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

                if(choice==1){
                    stateMachine.goNext(new ManagePendingReservationsState(librarian));
                }else if(choice==2){
                    new StateMachineImpl().start();
                }else{
                    Printer.println("Invalid choice. Try again...");
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
        Printer.println("1) manage pending reservations");
        Printer.println("2) logout");
    }
    @Override
    public void showHeadline() {
        Printer.printlnBlu("--------------HOME--------------");
    }
}
