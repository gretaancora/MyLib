package it.uniroma2.dicii.ispw.MyLib.view.cli.librarian;

import it.uniroma2.dicii.ispw.MyLib.other.Printer;
import it.uniroma2.dicii.ispw.MyLib.view.cli.State;
import it.uniroma2.dicii.ispw.MyLib.view.cli.StateMachine;
import it.uniroma2.dicii.ispw.MyLib.model.Librarian;
import it.uniroma2.dicii.ispw.MyLib.view.cli.StateMachineImpl;

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

                if(choice==1){
                    stateMachine.goNext(new ManagePendingReservationsState(librarian));
                }else if(choice==2){
                    Printer.println("Feature not implemented yet.");
                }else if(choice==3) {
                    Printer.println("Feature not implemented yet.");
                }else if(choice==4) {
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
