package it.uniroma2.dicii.ispw.librarymanagmentsystem.view.cli.costumer;

import it.uniroma2.dicii.ispw.librarymanagmentsystem.other.Printer;
import it.uniroma2.dicii.ispw.librarymanagmentsystem.view.cli.State;
import it.uniroma2.dicii.ispw.librarymanagmentsystem.view.cli.StateMachine;
import it.uniroma2.dicii.ispw.librarymanagmentsystem.view.cli.StateMachineImpl;
import it.uniroma2.dicii.ispw.librarymanagmentsystem.model.Costumer;

import java.util.InputMismatchException;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class HomeCostumerState extends State {
    private Costumer costumer;

    public HomeCostumerState(Costumer costumer) {
        super();
        this.costumer = costumer;
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
                    stateMachine.goNext(new BorrowState(costumer));
                }else if(choice==2){
                    stateMachine.goNext(new ManageReservationsState(costumer));
                }else if(choice==3){
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
        Printer.println("1) borrow a book");
        Printer.println("2) manage reservations");
        Printer.println("3) logout");
    }
    @Override
    public void showHeadline() {
        Printer.printlnBlu("--------------HOME--------------");
    }
}
